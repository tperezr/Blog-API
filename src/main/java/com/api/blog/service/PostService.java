package com.api.blog.service;

import com.api.blog.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PostService {

    List<Post> findAllPostsDetailsOrdByDate();

    List<Post> findAllPostsByTitle(String title);

    List<Post> findAllPostsByCategory(String category);

    List<Post> findAllPostsByTitleAndCategory(String title, String category);

    Optional<Post> findPostById(Long id);

    void savePost(Post post);

    void updatePost(Long id, Map<Object,Object> fields);

    void deletePost(Long id);

}
