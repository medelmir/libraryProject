package fr.univtours.polytech.tpeval.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional; 


public class Library implements Serializable {
    private final List<Book> books = new ArrayList<>();

    public Library() {
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getAllBooks() {
        // Retourne la liste interne (modifiables) pour que le contrôleur puisse changer 'availableCopies'
        return this.books; 
    }

    /** Permet au contrôleur de trouver le livre par ISBN pour mettre à jour les copies. */
    public Optional<Book> getBookByIsbn(String isbn) {
        return this.books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst();
    }
}