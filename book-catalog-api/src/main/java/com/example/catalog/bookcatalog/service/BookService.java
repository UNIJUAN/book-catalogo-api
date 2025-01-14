package com.example.catalog.bookcatalog.service;

import com.example.catalog.bookcatalog.model.Book;
import com.example.catalog.bookcatalog.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final GutendexService gutendexService;

    public BookService(BookRepository bookRepository, GutendexService gutendexService) {
        this.bookRepository = bookRepository;
        this.gutendexService = gutendexService;
    }

    public Book searchAndSaveBook(String title) {
        Book existingBook = bookRepository.findByTitleIgnoreCase(title);
        if (existingBook != null) {
            return existingBook;
        }

        return gutendexService.searchBookByTitle(title)
                .map(bookRepository::save)
                .orElse(null);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Set<String> getAllAuthors() {
        return bookRepository.findAllAuthors();
    }

    public List<Book> getBooksByLanguage(String language) {
        return bookRepository.findByLanguage(language);
    }
}