package com.leonardo.blog.repository;

import com.leonardo.blog.model.Article;
import com.leonardo.blog.model.ArticleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findBySlug(String slug);
    boolean existsByTitle(String title);
    boolean existsBySlug(String slug);

    List<Article> findByStatus(ArticleStatus status);
}
