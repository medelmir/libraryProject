package fr.univtours.polytech.tpeval.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BorrowedList Model, which manages the user's borrowed items/cart.
 * Tests ensure the list supports multiple copies and correct removal by ISBN.
 */
public class BorrowedListTest {

    private BorrowedList borrowedList;
    private Book bookA;
    private Book bookB;

    @BeforeEach
    void setUp() {
        borrowedList = new BorrowedList();
        // Create copies to be added to the list
        bookA = new Book("A111", "Book A", "Author X", 1, "Desc", "physical");
        bookB = new Book("B222", "Book B", "Author Y", 1, "Desc", "online");
    }

    @Test
    void testInitialState() {
        assertTrue(borrowedList.isEmpty(), "List should be empty upon initialization.");
        assertEquals(0, borrowedList.getBooks().size(), "Size should be zero.");
    }

    @Test
    void testAddBook() {
        borrowedList.addBook(bookA);
        assertFalse(borrowedList.isEmpty(), "List should not be empty after adding a book.");
        assertEquals(1, borrowedList.getBooks().size(), "Size should be 1.");
    }
    
    @Test
    void testMultipleCopiesOfSameBookAreAllowed() {
        Book bookA2 = new Book("A111", "Book A", "Author X", 1, "Desc", "physical");

        borrowedList.addBook(bookA);
        borrowedList.addBook(bookA2); // Same ISBN, should be allowed due to List structure

        assertEquals(2, borrowedList.getBooks().size(), "List must allow multiple copies of the same ISBN.");
    }

    @Test
    void testRemoveBookByIsbn() {
        borrowedList.addBook(bookA);
        borrowedList.addBook(bookB);
        
        // Remove book A
        Book removedBook = borrowedList.removeBook("A111");
        
        assertNotNull(removedBook, "A book should be returned when removed.");
        assertEquals(bookA, removedBook, "The removed book should be Book A.");
        assertEquals(1, borrowedList.getBooks().size(), "List size should be 1 after removal.");
        assertFalse(borrowedList.getBooks().contains(bookA), "Book A should no longer be in the list.");
        assertTrue(borrowedList.getBooks().contains(bookB), "Book B should still be in the list.");
    }
    
    @Test
    void testRemoveOneCopyWhenMultiplesExist() {
        Book bookA2 = new Book("A111", "Book A", "Author X", 1, "Desc", "physical");
        
        borrowedList.addBook(bookA);
        borrowedList.addBook(bookA2);
        
        // Remove one instance of A111
        borrowedList.removeBook("A111");
        
        assertEquals(1, borrowedList.getBooks().size(), "Only one copy should be removed.");
    }

    @Test
    void testClearList() {
        borrowedList.addBook(bookA);
        borrowedList.addBook(bookB);
        
        borrowedList.clear();
        
        assertTrue(borrowedList.isEmpty(), "List should be empty after clear().");
    }
}