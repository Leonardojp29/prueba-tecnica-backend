package com.leonardo.blog.service;

import com.leonardo.blog.model.*;
import com.leonardo.blog.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    // Generar un slug a partir del título
    private String generarSlug(String titulo) {
        String normalizado = Normalizer.normalize(titulo, Normalizer.Form.NFD);
        String slug = normalizado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-{2,}", "-")
                .replaceAll("^-|-$", "");
        return slug;
    }

    @Transactional
    public Article crearArticulo(String titulo, String cuerpo, Long categoriaId, List<Long> autoresIds, ArticleStatus estado) {
        // Validar que el título sea único
        if (articleRepository.existsByTitle(titulo)) {
            throw new IllegalArgumentException("El título ya ha sido registrado.");
        }

        String slug = generarSlug(titulo);

        // VSe valida que el Slug sea unico
        if (articleRepository.existsBySlug(slug)) {
            throw new IllegalArgumentException("El slug generado ya existe.");
        }

        // Se valida que la categoria exista y este activada
        Category categoria = categoryRepository.findById(categoriaId)
                .orElseThrow(() -> new NoSuchElementException("La categoría no existe."));
        if (categoria.getStatus() != Status.ACTIVE) {
            throw new IllegalStateException("La categoría no está activa.");
        }

        // Se valida que el autor exista y este activado
        List<Author> autores = authorRepository.findAllById(autoresIds);
        if (autores.size() != autoresIds.size()) {
            throw new NoSuchElementException("Uno o más autores no existen.");
        }
        if (autores.stream().anyMatch(autor -> autor.getStatus() != Status.ACTIVE)) {
            throw new IllegalStateException("Uno o más autores no están activos.");
        }

        // Crea el articulo
        Article articulo = new Article();
        articulo.setTitle(titulo);
        articulo.setSlug(slug);
        articulo.setBody(cuerpo);
        articulo.setCategory(categoria);
        articulo.setAuthors(autores);
        articulo.setStatus(estado);

        return articleRepository.save(articulo);
    }

    public Optional<Article> actualizarArticulo(Long id, Map<String, Object> payload) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isEmpty()) {
            return Optional.empty();
        }

        Article article = optionalArticle.get();

        // Actualizar articlos
        article.setTitle((String) payload.get("title"));
        article.setBody((String) payload.get("body"));

        Long categoryId = Long.valueOf(payload.get("category_id").toString());
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        article.setCategory(category);

        List<Integer> authorIds = (List<Integer>) payload.get("authors");
        List<Author> authors = authorRepository.findAllById(authorIds.stream().map(Long::valueOf).toList());
        article.setAuthors(authors);

        ArticleStatus status = ArticleStatus.valueOf(((String) payload.get("status")).toUpperCase());
        article.setStatus(status);

        articleRepository.save(article);
        return Optional.of(article);
    }

}
