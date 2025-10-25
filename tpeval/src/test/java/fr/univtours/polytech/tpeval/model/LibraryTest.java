package fr.univtours.polytech.tpeval.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Library Model, which manages the main catalog.
 */
public class LibraryTest {

    private Library library;
    private Book javaBook;
    private Book webBook;

    @BeforeEach
    void setUp() {
        library = new Library();
        javaBook = new Book("J123", "Java Programming", "Doe", 3, "Desc", "physical");
        webBook = new Book("W456", "Web Dev", "Smith", 1, "Desc", "online");
        
        library.addBook(javaBook);
        library.addBook(webBook);
    }

    @Test
    void testAddAndGetAllBooks() {
        assertEquals(2, library.getAllBooks().size(), "Library should contain 2 books after setup.");
        assertTrue(library.getAllBooks().contains(javaBook), "List should contain Java book.");
    }

    @Test
    void testGetBookByExistingIsbn() {
        Optional<Book> foundBook = library.getBookByIsbn("J123");
        
        assertTrue(foundBook.isPresent(), "Book with ISBN J123 should be found.");
        assertEquals(javaBook, foundBook.get(), "Retrieved book should be the Java book object.");
    }

    @Test
    void testGetBookByNonExistingIsbn() {
        Optional<Book> foundBook = library.getBookByIsbn("NonExistent");
        
        assertFalse(foundBook.isPresent(), "Book with non-existent ISBN should not be found.");
    }

    @Test
    void testAvailableCopiesCanBeModifiedExternally() {
        // Test that the object retrieved from the library can be modified (Crucial for BorrowServlet)
        Optional<Book> bookOpt = library.getBookByIsbn("J123");
        
        assertTrue(bookOpt.isPresent());
        bookOpt.get().setAvailableCopies(0);
        
        // Check if the change is reflected in the Library's internal list
        assertEquals(0, javaBook.getAvailableCopies(), "Modification via setter should affect the object in the library.");
    }
}