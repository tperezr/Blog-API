package com.api.blog.service;

import com.api.blog.model.Category;
import com.api.blog.model.Post;
import com.api.blog.repository.CategoryRepository;
import com.api.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAllPostsDetailsOrdByDate() {
        List<Post> posts = postRepository.
                findAll(Sort.by(Sort.Direction.DESC,"fechaCreacion"));
        return posts;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAllPostsByTitle(String title) {
        List<Post> posts = postRepository.findAllByTitulo(title);
        return posts;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAllPostsByCategory(String categoryName) throws IllegalArgumentException{
        Optional<Category> category = categoryRepository.findByNombre(categoryName);
        if(category.isEmpty()){
            throw new IllegalArgumentException();
        }
        List<Post> posts = postRepository.findAllByCategoria(category.get());
        return posts;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAllPostsByTitleAndCategory(String title, String categoryName) throws IllegalArgumentException{
        Optional<Category> category = categoryRepository.findByNombre(categoryName);
        if(category.isEmpty()){
            throw new IllegalArgumentException();
        }
        List<Post> posts = postRepository.findAllByTituloAndCategoria(title,category.get());
        return posts;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Post> findPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post;
    }

    @Override
    @Transactional
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    @Transactional
    public void updatePost(Long id, Map<Object,Object> fields) throws IllegalArgumentException, NullPointerException{
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
    public void deletePost(Long id) throws IllegalArgumentException{
        Optional<Post> post = postRepository.findById(id);
        if(post.isEmpty()){
            throw new IllegalArgumentException("Id post invalid");
        }
        postRepository.delete(post.get());
    }
}
