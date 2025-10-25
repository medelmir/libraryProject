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
import java.util.Collection; // Utilisé car getBooks() retourne une Collection

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    private static final double PRICE_PHYSICAL = 10.0;
    private static final double PRICE_ONLINE = 5.0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        BorrowedList borrowedList = (BorrowedList) session.getAttribute("borrowedList");
        if (borrowedList == null) {
            borrowedList = new BorrowedList();
            session.setAttribute("borrowedList", borrowedList);
        }
        
        // CORRECTION: Utiliser getBooks()
        Collection<Book> books = borrowedList.getBooks();

        // Calculer le nombre total de livres empruntés
        int totalBooks = books.size();
        
        // Vérifier si l'utilisateur n'a pas dépassé la limite de 2 livres (Logique ajoutée par l'utilisateur)
        if (totalBooks > 2) {
            request.setAttribute("errorMessage", "Vous ne pouvez pas emprunter plus de 2 livres à la fois.");
        }
        
        // Calculer le coût total mensuel
        double totalCost = 0;
        int physicalCount = 0;
        int onlineCount = 0;
        
        for (Book book : books) {
            if ("physical".equals(book.getFormat())) {
                totalCost += PRICE_PHYSICAL; 
                physicalCount++;
            } else if ("online".equals(book.getFormat()) || "pdf".equals(book.getFormat())) {
                totalCost += PRICE_ONLINE; 
                onlineCount++;
            }
        }
        
        // Ajouter les attributs pour l'affichage
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
        
        if (borrowedList == null) {
            response.sendRedirect("catalog"); 
            return;
        }

        if ("confirm".equals(action)) {
            // CORRECTION: Utiliser getBooks()
            if (borrowedList.getBooks().size() > 2) {
                request.setAttribute("errorMessage", "Vous ne pouvez pas emprunter plus de 2 livres à la fois.");
                // CORRECTION: Utiliser getBooks()
                request.setAttribute("borrowedBooks", borrowedList.getBooks());
                // CORRECTION: Utiliser getBooks()
                request.setAttribute("totalBooks", borrowedList.getBooks().size());
                request.getRequestDispatcher("/checkout.jsp").forward(request, response);
                return;
            }
            
            // Vider la session après confirmation
            session.removeAttribute("borrowedList");
            session.setAttribute("message", "Votre commande a été confirmée !");
            response.sendRedirect("borrowed"); 

        } else if ("cancel".equals(action)) {
            // Annuler tous les emprunts
            // NOTE: La logique de remise en stock (update Library) manque ici mais la méthode 'clear()' est utilisable.
            
            // CORRECTION: Utiliser clear()
            borrowedList.clear();
            session.setAttribute("borrowedList", borrowedList); 
            session.setAttribute("message", "Tous les emprunts ont été annulés et le panier est vide.");

            response.sendRedirect("catalog");
        }
    }
}