package fr.univtours.polytech.tpeval.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional; 

/**
 * Model: Manages the in-memory collection of all available books. 
 * This class represents the library's main catalog.
 */
public class Library implements Serializable {
    
    /** Internal list storing all available Book objects. */
    private final List<Book> books = new ArrayList<>();

    /**
     * Default constructor.
     */
    public Library() {
    }

    /**
     * Adds an initialized book to the library catalog.
     * Used primarily during initialization in the CatalogServlet.
     * @param book The book to add.
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Retrieves the list of all books in the catalog.
     *
     * IMPORTANT: The internal list is returned directly to allow the controller 
     * to update the 'availableCopies' stock field.
     * @return The modifiable list of all books.
     */
    public List<Book> getAllBooks() {
        return this.books; 
    }

    /**
     * Searches for a specific book in the catalog using its unique ISBN.
     * This method is used by controllers to locate the book to reserve or update.
     * @param isbn The ISBN of the book being searched.
     * @return An Optional object containing the Book if found, or an empty Optional.
     */
    public Optional<Book> getBookByIsbn(String isbn) {
        return this.books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst();
    }
}