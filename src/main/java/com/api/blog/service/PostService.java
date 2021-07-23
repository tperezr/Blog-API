package com.api.blog.service;

import com.api.blog.dto.PostDetailsDto;
import com.api.blog.dto.PostDto;
import com.api.blog.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PostService {

    List<PostDetailsDto> findAllPostsDetailsOrdByDate();

    List<PostDetailsDto> findAllPostsDetailsByTitle(String title);

    List<PostDetailsDto> findAllPostsDetailsByCategory(String category);

    List<PostDetailsDto> findAllPostsDetailsByTitleAndCategory(String title, String category);

    PostDto findPostById(Long id);

    Boolean savePost(PostDto post);

    void updatePost(Long id, Map<Object,Object> fields);

    Boolean deletePost(Long id);

}
