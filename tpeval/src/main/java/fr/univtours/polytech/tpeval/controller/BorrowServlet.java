package fr.univtours.polytech.tpeval.controller;

import fr.univtours.polytech.tpeval.model.Book;
import fr.univtours.polytech.tpeval.model.BorrowedList;
import fr.univtours.polytech.tpeval.model.Library;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "BorrowServlet", urlPatterns = {"/borrowed"})
public class BorrowServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String isbn = request.getParameter("isbn");
		HttpSession session = request.getSession();

		// Récupérer la BorrowedList de la session ou la créer
		BorrowedList borrowedList = (BorrowedList) session.getAttribute("borrowedList");
		if (borrowedList == null) {
			borrowedList = new BorrowedList();
			session.setAttribute("borrowedList", borrowedList);
		}

		// Récupérer la bibliothèque depuis le contexte de l'application (créée par CatalogServlet)
		Library library = (Library) getServletContext().getAttribute("library");

		Book targetBook = null;
		for (Book book : library.getAllBooks()) {
			if (book.getIsbn().equals(isbn)) {
				targetBook = book;
				break;
			}
		}

		String message = null;
		if ("reserve".equals(action) && targetBook != null) {
			boolean reserved = borrowedList.addBook(targetBook);
			if (reserved) {
				message = "Livre réservé avec succès.";
			} else {
				message = "Impossible de réserver ce livre (plus d'exemplaires ou déjà emprunté).";
			}
		} else if ("return".equals(action) && targetBook != null) {
			borrowedList.removeBook(targetBook);
			// On augmente le nombre d'exemplaires disponibles
			targetBook.setAvailableCopies(targetBook.getAvailableCopies() + 1);
			message = "Livre rendu avec succès.";
		}

		// Mettre à jour la BorrowedList en session
		session.setAttribute("borrowedList", borrowedList);
		request.setAttribute("message", message);
		request.setAttribute("borrowedBooks", borrowedList.getBorrowedBooks());
		request.getRequestDispatcher("/borrowed.jsp").forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		BorrowedList borrowedList = (BorrowedList) session.getAttribute("borrowedList");
		if (borrowedList == null) {
			borrowedList = new BorrowedList();
			session.setAttribute("borrowedList", borrowedList);
		}
		request.setAttribute("borrowedBooks", borrowedList.getBorrowedBooks());
		request.getRequestDispatcher("/borrowed.jsp").forward(request, response);
	}
}
