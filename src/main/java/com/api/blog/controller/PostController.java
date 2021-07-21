package com.api.blog.controller;

import com.api.blog.dto.PostDetailsDto;
import com.api.blog.dto.PostDto;
import com.api.blog.model.Post;
import com.api.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping
    public List<PostDetailsDto> getPostsDetails(){
        List<Post> posts = postService.findAllPostsDetailsOrdByDate();
        List<PostDetailsDto> postsDto = createPostsDetailsDto(posts);
        return postsDto;
    }

    @GetMapping(params = "title")
    public ResponseEntity<?> getPostsByTitle(@RequestParam String title){
        if(title.isBlank()){
            return ResponseEntity.badRequest().body("Title can not be empty");
        }
        List<Post> posts = postService.findAllPostsByTitle(title);
        List<PostDetailsDto> postsDto = createPostsDetailsDto(posts);
        return ResponseEntity.ok(postsDto);
    }

    @GetMapping(params = "category")
    public ResponseEntity<?> getPostsByCategory(@RequestParam String category){
        if(category.isBlank()){
            return ResponseEntity.badRequest().body("Category can not be empty");
        }
        List<Post> posts;
        List<PostDetailsDto> postsDto;
        try{
            posts = postService.findAllPostsByCategory(category);
            postsDto = createPostsDetailsDto(posts);
        } catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Invalid category");
        }

        return ResponseEntity.ok(postsDto);
    }

    @GetMapping(params = {"title","category"})
    public ResponseEntity<?> getPostsByCategory(@RequestParam String title, @RequestParam String category){
        if(title.isBlank() || category.isBlank()){
            return ResponseEntity.badRequest().body("Category and title can not be empty");
        }
        List<Post> posts;
        List<PostDetailsDto> postsDto;
        try{
            posts = postService.findAllPostsByTitleAndCategory(title,category);
            postsDto = createPostsDetailsDto(posts);
        } catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Invalid category");
        }
        return ResponseEntity.ok(postsDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id){
        Optional<Post> post = postService.findPostById(id);
        return post.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                ResponseEntity.ok(createPostDto(post.get()));
    }

    @PostMapping
    public ResponseEntity<?> savePost(@RequestBody Post post){
        postService.savePost(post);
        return ResponseEntity.ok("Successfully saved");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        try{
            postService.deletePost(id);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Deleted");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Map<Object,Object> fields){
        try{
            postService.updatePost(id,fields);
        } catch (IllegalArgumentException e){
            //Post not found
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NullPointerException e){
            return ResponseEntity.badRequest().body("Invalid fields");
        }

        return ResponseEntity.ok("Successfully updated");
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
}
