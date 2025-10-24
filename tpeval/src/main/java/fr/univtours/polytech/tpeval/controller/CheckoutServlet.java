package fr.univtours.polytech.tpeval.controller;

import fr.univtours.polytech.tpeval.model.Book;
import fr.univtours.polytech.tpeval.model.BorrowedList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Récupérer la liste des emprunts de la session
        BorrowedList borrowedList = (BorrowedList) session.getAttribute("borrowedList");
        if (borrowedList == null) {
            borrowedList = new BorrowedList();
            session.setAttribute("borrowedList", borrowedList);
        }
        
        // Calculer le nombre total de livres empruntés
        int totalBooks = borrowedList.getBorrowedBooks().size();
        
        // Vérifier si l'utilisateur n'a pas dépassé la limite de 2 livres
        if (totalBooks > 2) {
            request.setAttribute("errorMessage", "Vous ne pouvez pas emprunter plus de 2 livres à la fois.");
        }
        
        // Ajouter les attributs pour l'affichage
        List<Book> books = borrowedList.getBorrowedBooks();
        
        // Calculer le coût total mensuel
        double totalCost = 0;
        int physicalCount = 0;
        int onlineCount = 0;
        
        for (Book book : books) {
            if ("physical".equals(book.getFormat())) {
                totalCost += 10;
                physicalCount++;
            } else if ("online".equals(book.getFormat())) {
                totalCost += 5;
                onlineCount++;
            }
        }
        
        request.setAttribute("borrowedBooks", books);
        request.setAttribute("totalBooks", totalBooks);
        request.setAttribute("totalCost", totalCost);
        request.setAttribute("physicalCount", physicalCount);
        request.setAttribute("onlineCount", onlineCount);
        
        // Rediriger vers la page de confirmation
        request.getRequestDispatcher("/checkout.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        BorrowedList borrowedList = (BorrowedList) session.getAttribute("borrowedList");
        
        String action = request.getParameter("action");
        
        if ("confirm".equals(action)) {
            if (borrowedList.getBorrowedBooks().size() > 2) {
                request.setAttribute("errorMessage", "Vous ne pouvez pas emprunter plus de 2 livres à la fois.");
                request.setAttribute("borrowedBooks", borrowedList.getBorrowedBooks());
                request.setAttribute("totalBooks", borrowedList.getBorrowedBooks().size());
                request.getRequestDispatcher("/checkout.jsp").forward(request, response);
                return;
            }
            
          
            // Vider la session après confirmation
            session.removeAttribute("borrowedList");
            response.sendRedirect("borrowed");

        }else if ("cancel".equals(action)) {
            // Annuler tous les emprunts
            if (borrowedList != null) {
                List<Book> books = borrowedList.getBorrowedBooks();
                for (Book book : books) {
                    // Remettre les copies disponibles à leur état initial
                    book.setAvailableCopies(book.getAvailableCopies() + 1);
                }
                borrowedList.clear();
                session.setAttribute("borrowedList", borrowedList);
            }
            
            response.sendRedirect("catalog");
        }
    }
}
