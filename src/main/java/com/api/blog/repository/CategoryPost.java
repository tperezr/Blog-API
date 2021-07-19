package com.api.blog.repository;

import com.api.blog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryPost extends JpaRepository<Category, Long> {
}
