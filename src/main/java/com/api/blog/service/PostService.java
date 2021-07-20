package com.api.blog.service;

import com.api.blog.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    public List<Post> findAllPostsDetailsOrdByDate();

    public Optional<Post> findPostById(Long id);

    public void savePost(Post post);

    public void updatePost(Post post);

    public void deletePost(Long id);

}
