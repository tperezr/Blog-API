package com.api.blog.controller;

import com.api.blog.dto.PostDetailsDto;
import com.api.blog.model.Post;
import com.api.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping
    public List<PostDetailsDto>  getPostsDetails(){
        List<Post> posts = postService.findAllPostsDetailsOrdByDate();
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

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPostById(@PathVariable Long id){
        Optional<Post> post = postService.findPostById(id);
        return post.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado") :
                ResponseEntity.accepted().body(post.get());
    }

    @PostMapping
    public void savePost(@RequestBody Post post){
        postService.savePost(post);
    }
}
