package fr.univtours.polytech.tpeval.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Modèle : Maintient la liste des livres empruntés par un utilisateur (dans la session).
 */
public class BorrowedList implements Serializable {
    
    private final Map<String, Book> borrowedBooks;

    public BorrowedList() {
        this.borrowedBooks = new LinkedHashMap<>();
    }

    /** Ajoute un livre (copie) à la liste empruntée. */
    public void addBook(Book book) {
        this.borrowedBooks.put(book.getIsbn(), book);
    }

    /** Retire un livre de la liste par ISBN. */
    public Book removeBook(String isbn) {
        return this.borrowedBooks.remove(isbn);
    }

    /** Récupère la collection de tous les livres empruntés (Méthode utilisée partout). */
    public Collection<Book> getBooks() {
        return Collections.unmodifiableCollection(this.borrowedBooks.values());
    }

    /** Vérifie si la liste est vide. */
    public boolean isEmpty() {
        return this.borrowedBooks.isEmpty();
    }
    
    /** Ajouté pour permettre la réinitialisation (action 'cancel' ou 'confirm'). */
    public void clear() {
        this.borrowedBooks.clear();
    }
}