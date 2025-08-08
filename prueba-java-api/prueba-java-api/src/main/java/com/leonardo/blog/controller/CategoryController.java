package com.leonardo.blog.controller;

import com.leonardo.blog.model.Category;
import com.leonardo.blog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<Category>> listarCategorias() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }
}
