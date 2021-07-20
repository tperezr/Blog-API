package com.api.blog.service;

import com.api.blog.model.Post;
import com.api.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> findAllPostsDetailsOrdByDate() {
        List<Post> posts = postRepository.
                findAll(Sort.by(Sort.Direction.DESC,"fechaCreacion"));
        return posts;
    }

    @Override
    public Optional<Post> findPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post;
    }

    @Override
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void updatePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        Post post = new Post();
        post.setId(id);
        postRepository.delete(post);
    }
}
