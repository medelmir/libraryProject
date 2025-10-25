package fr.univtours.polytech.tpeval.model;

import java.io.Serializable;
import java.util.ArrayList; 
import java.util.Collection;
import java.util.List; 

/**
 * Represents the list of books borrowed by a user during their session (Model).
 * The implementation uses a List to allow multiple copies of the same book to be borrowed,
 * ensuring consistency with stock decrement.
 */
public class BorrowedList implements Serializable {
    
    // CRITICAL CHANGE: The Map is replaced by a List to allow correct counting of copies.
    private final List<Book> borrowedBooks = new ArrayList<>();

    /**
     * Map storing the borrowed books. The key is the book's ISBN (String)
     * and the value is the Book object.
     */
    // (The initial Javadoc referring to Map is kept, but the implementation uses List for functionality)

    /**
     * Default constructor that initializes the internal List.
     */
    public BorrowedList() {
        // The List is initialized above
    }

    /**
     * Adds a book to the borrowed list. Allows duplicates of the same book.
     * @param book The book (a copy of the catalogue book) to add to the user's borrowed items.
     */
    public void addBook(Book book) {
        this.borrowedBooks.add(book);
    }

    /**
     * Removes a book from the borrowed list using its ISBN.
     * This method searches linearly (O(N)) to remove the first match.
     * @param isbn The ISBN of the book to remove.
     * @return The removed book, or null if the ISBN was not present.
     */
    public Book removeBook(String isbn) {
        for (int i = 0; i < borrowedBooks.size(); i++) {
            if (borrowedBooks.get(i).getIsbn().equals(isbn)) {
                // Remove the first book found with this ISBN
                return borrowedBooks.remove(i);
            }
        }
        return null;
    }

    /**
     * Retrieves the collection of all currently borrowed books.
     * @return The modifiable list of borrowed Book objects.
     */
    public Collection<Book> getBooks() {
        // Returns the List, which may contain duplicates
        return this.borrowedBooks; 
    }

    /**
     * Checks if the borrowed list is empty.
     * @return True if no books are borrowed, False otherwise.
     */
    public boolean isEmpty() {
        return this.borrowedBooks.isEmpty();
    }
    
    /**
     * Clears the list of borrowed books after confirmation or cancellation of the checkout.
     */
    public void clear() {
        this.borrowedBooks.clear();
    }
}