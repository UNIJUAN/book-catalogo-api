package com.example.catalog.bookcatalog.service;

import com.example.catalog.bookcatalog.model.Book;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class GutendexService {
    private static final String API_BASE_URL = "https://gutendex.com/books";
    private static final HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String FIELD_LANGUAGES = "languages";
    private static final String FIELD_AUTHORS = "authors";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_DOWNLOAD_COUNT = "download_count";
    private static final String FIELD_NAME = "name";

    public Optional<Book> searchBookByTitle(String title) {
        try {
            String encodedTitle = java.net.URLEncoder.encode(title, "UTF-8");
            String fullUrl = API_BASE_URL + "?search=" + encodedTitle;
            System.out.println("\nBuscando en el catálogo de Proyecto Gutenberg...");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fullUrl))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            return processHttpRequest(request);

        } catch (IOException e) {
            System.out.println("Error al buscar en el catálogo: " + e.getMessage());
            return Optional.empty();
        }
    }

    private Optional<Book> processHttpRequest(HttpRequest request) {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                JsonNode root = mapper.readTree(response.body());
                JsonNode results = root.get("results");

                if (results != null && results.size() > 0) {
                    return processApiResponse(response.body());
                } else {
                    System.out.println("El libro no está disponible en el catálogo de Proyecto Gutenberg.");
                    System.out.println("Nota: Solo están disponibles libros de dominio público.");
                }
            }
            return Optional.empty();
        } catch (IOException e) {
            System.out.println("Error procesando la respuesta: " + e.getMessage());
            return Optional.empty();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("La búsqueda fue interrumpida: " + e.getMessage());
            return Optional.empty();
        }
    }

    private Optional<Book> processApiResponse(String responseBody) {
        try {
            JsonNode root = mapper.readTree(responseBody);
            JsonNode results = root.get("results");

            if (results != null && !results.isEmpty()) {
                JsonNode bookNode = results.get(0);
                Book book = new Book();
                book.setTitle(bookNode.get(FIELD_TITLE).asText());

                if (bookNode.has(FIELD_LANGUAGES) && !bookNode.get(FIELD_LANGUAGES).isEmpty()) {
                    book.setLanguage(bookNode.get(FIELD_LANGUAGES).get(0).asText());
                }

                book.setDownloadCount(String.valueOf(bookNode.get(FIELD_DOWNLOAD_COUNT).asLong()));

                Set<String> authors = new HashSet<>();
                if (bookNode.has(FIELD_AUTHORS)) {
                    bookNode.get(FIELD_AUTHORS).forEach(author ->
                            authors.add(author.get(FIELD_NAME).asText())
                    );
                }
                book.setAuthors(authors);

                return Optional.of(book);
            }
        } catch (IOException e) {
            System.out.println("Error procesando la respuesta JSON: " + e.getMessage());
        }
        return Optional.empty();
    }
}