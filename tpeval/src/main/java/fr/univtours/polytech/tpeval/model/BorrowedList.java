package fr.univtours.polytech.tpeval.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Map;

public class BorrowedList implements Serializable {
    
    private final Map<String, Book> borrowedBooks;

    public BorrowedList() {
        this.borrowedBooks = new LinkedHashMap<>();
    }

    
    public void addBook(Book book) {
        
        this.borrowedBooks.put(book.getIsbn(), book);
    }

    
    public Book removeBook(String isbn) {
        return this.borrowedBooks.remove(isbn);
    }

    
    public Collection<Book> getBooks() {
        return Collections.unmodifiableCollection(this.borrowedBooks.values());
    }

  
    public boolean isEmpty() {
        return this.borrowedBooks.isEmpty();
    }
}