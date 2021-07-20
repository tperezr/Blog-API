package com.api.blog.repository;

import com.api.blog.model.Category;
import com.api.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByTituloAndCategoria(String title, Category category);
    List<Post> findAllByTitulo(String title);
    List<Post> findAllByCategoria(Category category);
}
