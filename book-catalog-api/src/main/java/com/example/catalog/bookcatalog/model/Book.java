package com.example.catalog.bookcatalog.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "author_name")
    private Set<String> authors = new HashSet<>();

    @Column(nullable = false)
    private String language;

    private String downloadCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Constructor vacío requerido por JPA
    public Book() {}

    // Constructor con parámetros principales
    public Book(String title, Set<String> authors, String language) {
        this.title = title;
        this.authors = authors;
        this.language = language;
    }

    // Método toString para mejor representación del objeto
    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authors=" + String.join(", ", authors) +
                ", language='" + language + '\'' +
                ", downloadCount='" + downloadCount + '\'' +
                '}';
    }
}