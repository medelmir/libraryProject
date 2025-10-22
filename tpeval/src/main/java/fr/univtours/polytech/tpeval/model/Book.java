package fr.univtours.polytech.tpeval.model;

import java.io.Serializable;


public class Book implements Serializable {
    private String isbn;
    private String title;
    private String author;
    private int availableCopies;
    private String description;
    private String format;

    public Book() {
    }

    public Book(String isbn, String title, String author, int availableCopies,
                String description, String format) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.availableCopies = availableCopies;
        this.description = description;
        this.format = format;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
