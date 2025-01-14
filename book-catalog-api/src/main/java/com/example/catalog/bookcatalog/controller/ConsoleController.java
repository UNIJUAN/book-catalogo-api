package com.example.catalog.bookcatalog.controller;

import com.example.catalog.bookcatalog.model.Book;
import com.example.catalog.bookcatalog.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import java.util.Scanner;

@Controller
public class ConsoleController implements CommandLineRunner {
    private final BookService bookService;
    private final Scanner scanner;

    public ConsoleController(BookService bookService) {
        this.bookService = bookService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) {
        boolean running = true;

        while (running) {
            displayMenu();
            int option = readOption();

            switch (option) {
                case 1 -> searchBook();
                case 2 -> listBooks();
                case 3 -> listAuthors();
                case 4 -> listBooksByLanguage();
                case 0 -> running = false;
                default -> System.out.println("\nOpción no válida. Por favor, intente de nuevo.\n");
            }
        }
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n=== Catálogo de Libros - Proyecto Gutenberg ===");
        System.out.println("(Biblioteca digital de libros de dominio público)");
        System.out.println("\nElija la opción a través de su número:");
        System.out.println("1- buscar libro por título");
        System.out.println("2- listar libros registrados");
        System.out.println("3- listar autores registrados");
        System.out.println("4- listar libros por idioma");
        System.out.println("0- salir");
    }

    private int readOption() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void searchBook() {
        System.out.println("\nIngrese el título del libro:");
        String title = scanner.nextLine();

        System.out.println("\nBuscando '" + title + "'...");
        Book book = bookService.searchAndSaveBook(title);

        if (book != null) {
            System.out.println("\n¡Libro encontrado y guardado!");
            System.out.println("Título: " + book.getTitle());
            System.out.println("Autor(es): " + String.join(", ", book.getAuthors()));
            System.out.println("Idioma: " + book.getLanguage());
        }
        System.out.println();
    }

    private void listBooks() {
        System.out.println("\nLibros registrados:");
        bookService.getAllBooks().forEach(book ->
                System.out.println("- " + book.getTitle() + " (" + String.join(", ", book.getAuthors()) + ")")
        );
        System.out.println();
    }

    private void listAuthors() {
        System.out.println("\nAutores registrados:");
        bookService.getAllAuthors().forEach(System.out::println);
        System.out.println();
    }

    private void listBooksByLanguage() {
        System.out.println("\nIngrese el idioma para buscar los libros:");
        System.out.println("es- español");
        System.out.println("en- inglés");
        System.out.println("fr- francés");
        System.out.println("pt- portugués");

        String language = scanner.nextLine();
        System.out.println();
        bookService.getBooksByLanguage(language).forEach(book ->
                System.out.println("- " + book.getTitle())
        );
        System.out.println();
    }
}