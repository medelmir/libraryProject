package fr.univtours.polytech.tpeval.model;

import java.io.Serializable;


/**
 * Represents the Book entity (Model). 
 * Contains the attributes and access logic for a single book's data.
 */
public class Book implements Serializable {
    
    /** Unique identifier of the book (ISBN). */
    private String isbn;
    
    /** Full title of the book. */
    private String title;
    
    /** Author of the book. */
    private String author;
    
    /** Number of copies currently available for borrowing (stock). */
    private int availableCopies;
    
    /** Brief description of the book's content. */
    private String description;
    
    /** Format of the book (e.g., "physical" or "online"). */
    private String format;

    /**
     * Default constructor.
     */
    public Book() {
    }

    /**
     * Constructor to initialize all book properties.
     * @param isbn The book's unique identifier.
     * @param title The book's title.
     * @param author The book's author.
     * @param availableCopies The initial number of available copies.
     * @param description The book's description.
     * @param format The book's format ("physical" or "online").
     */
    public Book(String isbn, String title, String author, int availableCopies,
                String description, String format) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.availableCopies = availableCopies;
        this.description = description;
        this.format = format;
    }

    /**
     * Retrieves the ISBN (Unique identifier of the book).
     * @return The ISBN as a String.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the book.
     * @param isbn The new ISBN.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Retrieves the title of the book.
     * @return The book's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     * @param title The new title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the author of the book.
     * @return The author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     * @param author The new author.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Retrieves the number of available copies in stock.
     * @return The number of available copies.
     */
    public int getAvailableCopies() {
        return availableCopies;
    }

    /**
     * Sets the number of available copies.
     * @param availableCopies The new number of available copies.
     */
    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    /**
     * Retrieves the description of the book.
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the book.
     * @param description The new description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the format of the book.
     * @return The format (e.g., "physical" or "online").
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets the format of the book.
     * @param format The new format.
     */
    public void setFormat(String format) {
        this.format = format;
    }
}