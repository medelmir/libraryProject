package fr.univtours.polytech.tpeval.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Book JavaBean entity.
 */
public class BookTest {

    private final String ISBN = "1234567890";
    private final String TITLE = "Java Basics";
    private final String AUTHOR = "J. Doe";
    private final int COPIES = 5;
    private final String FORMAT = "physical";
    
    @Test
    void testBookInitialization() {
        Book book = new Book(ISBN, TITLE, AUTHOR, COPIES, "A guide", FORMAT);
        
        assertEquals(ISBN, book.getIsbn(), "ISBN should match the constructor value.");
        assertEquals(TITLE, book.getTitle(), "Title should match the constructor value.");
        assertEquals(COPIES, book.getAvailableCopies(), "Copies should match the constructor value.");
        assertEquals(FORMAT, book.getFormat(), "Format should match the constructor value.");
    }
    
    @Test
    void testAvailableCopiesMutation() {
        Book book = new Book(ISBN, TITLE, AUTHOR, COPIES, "A guide", FORMAT);
        
        // Test setAvailableCopies
        book.setAvailableCopies(COPIES - 1);
        assertEquals(COPIES - 1, book.getAvailableCopies(), "setAvailableCopies should update the value.");

        // Test boundary condition
        book.setAvailableCopies(0);
        assertEquals(0, book.getAvailableCopies(), "setAvailableCopies should allow zero.");
    }

    @Test
    void testSetterMethods() {
        Book book = new Book();
        book.setTitle("New Title");
        book.setAuthor("New Author");
        book.setIsbn("000000");

        assertEquals("New Title", book.getTitle());
        assertEquals("New Author", book.getAuthor());
        assertEquals("000000", book.getIsbn());
    }
}