package com.api.blog.service;

import com.api.blog.dto.PostDetailsDto;
import com.api.blog.dto.PostDto;
import com.api.blog.model.Category;
import com.api.blog.model.Post;
import com.api.blog.model.User;
import com.api.blog.repository.CategoryRepository;
import com.api.blog.repository.PostRepository;
import com.api.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PostDetailsDto> findAllPostsDetailsOrdByDate() {
        List<Post> posts = postRepository.
                findAll(Sort.by(Sort.Direction.DESC,"fechaCreacion"));
        List<PostDetailsDto> postDetailsDto = createPostsDetailsDto(posts);
        return postDetailsDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDetailsDto> findAllPostsDetailsByTitle(String title) {
        List<Post> posts = postRepository.findAllByTitulo(title);
        List<PostDetailsDto> postsDetailsDto = createPostsDetailsDto(posts);
        return postsDetailsDto.isEmpty() ? null : postsDetailsDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDetailsDto> findAllPostsDetailsByCategory(String categoryName) throws IllegalArgumentException{
        Optional<Category> category = categoryRepository.findByNombre(categoryName);
        if(category.isEmpty()){
            return null;
        }
        List<Post> posts = postRepository.findAllByCategoria(category.get());
        List<PostDetailsDto> postsDetailsDto = createPostsDetailsDto(posts);
        return postsDetailsDto.isEmpty() ? null : postsDetailsDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDetailsDto> findAllPostsDetailsByTitleAndCategory(String title, String categoryName) throws IllegalArgumentException{
        Optional<Category> category = categoryRepository.findByNombre(categoryName);
        if(category.isEmpty()){
            return null;
        }
        List<Post> posts = postRepository.findAllByTituloAndCategoria(title,category.get());
        List<PostDetailsDto> postsDetailsDto = createPostsDetailsDto(posts);
        return postsDetailsDto.isEmpty() ? null : postsDetailsDto;
    }

    @Override
    @Transactional(readOnly = true)
    public PostDto findPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        PostDto postDto;
        if(post.isPresent()){
            postDto = createPostDto(post.get());
            return postDto;
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean savePost(PostDto postDto) {
        if (testImage(postDto.getImagen())) {
            Optional<Category> categoria = categoryRepository.findByNombre(postDto.getCategoria());
            Optional<User> usuario = userRepository.findByEmail(postDto.getUsuario());
            if(usuario.isPresent() && categoria.isPresent()){
                Post post = new Post(
                        null,
                        postDto.getTitulo(),
                        postDto.getContenido(),
                        postDto.getImagen(),
                        postDto.getFechaCreacion(),
                        false,
                        categoria.get(),
                        usuario.get()
                );
                postRepository.save(post);
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public void updatePost(Long id, Map<Object,Object> fields)
            throws IllegalArgumentException, NullPointerException{
        Optional<Post> post = postRepository.findById(id);
        if(post.isEmpty()){
            throw new IllegalArgumentException("Not existing post");
        }
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Post.class,(String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field,post.get(),value);
        });
        postRepository.save(post.get());
    }

    @Override
    @Transactional
    public Boolean deletePost(Long id) throws IllegalArgumentException{
        Optional<Post> post = postRepository.findById(id);
        if(post.isEmpty()){
          return false;
        }
        postRepository.delete(post.get());
        return true;
    }

    private PostDto createPostDto(Post post){
        PostDto postDto = new PostDto(
                post.getId(),
                post.getTitulo(),
                post.getContenido(),
                post.getImagen(),
                post.getFechaCreacion(),
                post.getCategoria().getNombre(),
                post.getUsuario().getEmail()
        );
        return postDto;
    }

    private List<PostDetailsDto> createPostsDetailsDto(List<Post> posts){
        List<PostDetailsDto> postsDto = posts.stream()
                .map(post -> new PostDetailsDto(
                        post.getId(),
                        post.getTitulo(),
                        post.getImagen(),
                        post.getCategoria().getNombre(),
                        post.getFechaCreacion()
                )).collect(Collectors.toList());
        return postsDto;
    }

    private Boolean testImage(String url){
        try {
            BufferedImage image = ImageIO.read(new URL(url));
            if(image != null){
                return true;
            } else{
                return false;
            }

        } catch (IOException e) {
            return false;
        }
    }
}
