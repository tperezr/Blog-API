package com.api.blog.controller;

import com.api.blog.dto.PostDetailsDto;
import com.api.blog.dto.PostDto;
import com.api.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
@Validated
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping
    public List<PostDetailsDto> getPostsDetails(){
        List<PostDetailsDto> postsDto = postService.findAllPostsDetailsOrdByDate();
        return postsDto;
    }

    @GetMapping(params = "title")
    public ResponseEntity<?> getPostsByTitle(
            @NotBlank(message = "can not be empty") @RequestParam String title){
        List<PostDetailsDto> postDetailsDto = postService.findAllPostsDetailsByTitle(title);
        if(postDetailsDto != null){
            return ResponseEntity.ok(postDetailsDto);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(params = "category")
    public ResponseEntity<?> getPostsByCategory(
            @NotBlank(message = "can not be empty") @RequestParam String category){
        List<PostDetailsDto> postsDto = postService.findAllPostsDetailsByCategory(category);
        if(postsDto != null){
            return ResponseEntity.ok(postsDto);
        }
        return ResponseEntity.badRequest().body("Invalid category");
    }

    @GetMapping(params = {"title","category"})
    public ResponseEntity<?> getPostsByCategory(
            @NotBlank(message = "can not be empty") @RequestParam String title,
            @NotBlank(message = "can not be empty") @RequestParam String category){
        List<PostDetailsDto> postsDto = postService.findAllPostsDetailsByTitleAndCategory(title,category);
        if(postsDto != null){
            return ResponseEntity.ok(postsDto);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(
            @Min(value = 1,message = "must be >= 1") @PathVariable Long id){
        PostDto postDto = postService.findPostById(id);
        return postDto == null ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                ResponseEntity.ok(postDto);
    }

    @PostMapping
    public ResponseEntity<?> savePost(@Valid @RequestBody PostDto post){
        if(postService.savePost(post)){
            return ResponseEntity.ok("Successfully saved");
        }
        return ResponseEntity.badRequest().body("Invalid fields");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(
            @Min(value = 1,message = "must be >= 1") @PathVariable Long id){
        Boolean wasDeleted = postService.deletePost(id);
        if(wasDeleted){
            return ResponseEntity.ok("Deleted");
        }
        return ResponseEntity.badRequest().body("Invalid id post");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePost(
            @Min(value = 1,message = "must be >= 1") @PathVariable Long id,
            @RequestBody Map<Object,Object> fields){
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
}
