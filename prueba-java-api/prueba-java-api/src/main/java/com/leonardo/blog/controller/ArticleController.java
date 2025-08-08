package com.leonardo.blog.controller;

import com.leonardo.blog.model.*;
import com.leonardo.blog.repository.ArticleRepository;
import com.leonardo.blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    // Crear artículo
    @PostMapping
    public ResponseEntity<?> crearArticulo(@RequestBody Map<String, Object> payload) {
        try {
            String titulo = (String) payload.get("title");
            String cuerpo = (String) payload.get("body");
            Long categoriaId = Long.valueOf(payload.get("category_id").toString());
            List<Integer> autores = (List<Integer>) payload.get("authors");
            List<Long> autoresIds = autores.stream().map(Integer::longValue).toList();
            ArticleStatus estado = ArticleStatus.valueOf(((String) payload.get("status")).toUpperCase());

            Article nuevo = articleService.crearArticulo(titulo, cuerpo, categoriaId, autoresIds, estado);
            return ResponseEntity.ok(nuevo);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.unprocessableEntity().body(Map.of(
                    "errors", Map.of("title", List.of(e.getMessage()))
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/blog/{slug}")
    public ResponseEntity<?> verArticuloPorSlug(@PathVariable String slug) {
        return articleRepository.findBySlug(slug)
                .filter(article -> article.getStatus() == ArticleStatus.PUBLISHED)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Article>> listarArticulos(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long category_id,
            @RequestParam(required = false) Long author_id
    ) {
        List<Article> articulos = articleRepository.findAll();

        // Filtrar por status
        if (status != null) {
            try {
                ArticleStatus estado = ArticleStatus.valueOf(status.toUpperCase());
                articulos = articulos.stream()
                        .filter(a -> a.getStatus() == estado)
                        .toList();
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        // Filtrar por categoría
        if (category_id != null) {
            articulos = articulos.stream()
                    .filter(a -> a.getCategory().getId().equals(category_id))
                    .toList();
        }

        // Filtrar por autor
        if (author_id != null) {
            articulos = articulos.stream()
                    .filter(a -> a.getAuthors().stream().anyMatch(author -> author.getId().equals(author_id)))
                    .toList();
        }

        return ResponseEntity.ok(articulos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verArticuloPorId(@PathVariable Long id) {
        return articleRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarArticulo(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        return articleService.actualizarArticulo(id, payload)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarArticulo(@PathVariable Long id) {
        if (!articleRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        articleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }






}
