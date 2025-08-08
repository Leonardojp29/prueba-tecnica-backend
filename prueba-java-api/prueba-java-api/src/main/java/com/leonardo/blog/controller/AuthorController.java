package com.leonardo.blog.controller;

import com.leonardo.blog.model.Author;
import com.leonardo.blog.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;

    @GetMapping
    public ResponseEntity<List<Author>> listarAutores() {
        return ResponseEntity.ok(authorRepository.findAll());
    }
}
