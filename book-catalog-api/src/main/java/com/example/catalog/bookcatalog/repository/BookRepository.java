package com.example.catalog.bookcatalog.repository;

import com.example.catalog.bookcatalog.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Buscar libro por título (ignorando mayúsculas/minúsculas)
    Book findByTitleIgnoreCase(String title);

    // Listar libros por idioma
    List<Book> findByLanguage(String language);

    // Obtener todos los autores únicos
    @Query("SELECT DISTINCT author FROM Book b JOIN b.authors author")
    Set<String> findAllAuthors();

    // Verificar si existe un libro por título
    boolean existsByTitleIgnoreCase(String title);
}