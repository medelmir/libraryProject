package fr.univtours.polytech.tpeval.model;

import java.io.Serializable;
import java.util.ArrayList;

public class BorrowedList implements Serializable {
	private static final long serialVersionUID = 1L;

	private ArrayList<Book> borrowedBooks = new ArrayList<>();

	public BorrowedList() {
	}

	public boolean addBook(Book book) {
		if (book != null && !borrowedBooks.contains(book)) {
			if (book.getAvailableCopies() > 0) {
				borrowedBooks.add(book);
				book.setAvailableCopies(book.getAvailableCopies() - 1);
				return true;
			}
		}
		return false;
	}

	public void removeBook(Book book) {
		borrowedBooks.remove(book);
	}

	public ArrayList<Book> getBorrowedBooks() {
		return new ArrayList<>(borrowedBooks);
	}

	public void clear() {
		borrowedBooks.clear();
	}
}