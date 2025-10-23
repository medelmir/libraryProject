package fr.univtours.polytech.tpeval.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional; 


public class Library implements Serializable {
    private List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }


    public List<Book> getAllBooks() {
        return Collections.unmodifiableList(books);
    }


    public Optional<Book> getBookByIsbn(String isbn) {
        return this.books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst();
    }
}