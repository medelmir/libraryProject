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
import java.util.Collection; // Used because getBooks() returns a Collection

/**
 * Controller managing the book reservation summary page (Checkout) and applying final business rules.
 * It calculates the total cost and handles the confirmation/cancellation of the order.
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    /** Monthly cost for reserving a physical book. */
    private static final double PRICE_PHYSICAL = 10.0;
    
    /** Monthly cost for reserving an online/PDF book. */
    private static final double PRICE_ONLINE = 5.0;

    /**
     * Handles the HTTP GET request. Prepares the summary, calculates costs, and checks borrowing limits (more than 2 books).
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @throws ServletException If a Servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        // Retrieve or create the borrowed list (BorrowedList Model)
        BorrowedList borrowedList = (BorrowedList) session.getAttribute("borrowedList");
        if (borrowedList == null) {
            borrowedList = new BorrowedList();
            session.setAttribute("borrowedList", borrowedList);
        }
        
        // Retrieve the collection of books for calculation
        Collection<Book> books = borrowedList.getBooks();

        // Calculate the total number of borrowed books
        int totalBooks = books.size();
        
        // Check for the 2-book limit (TP Requirement)
        if (totalBooks > 2) {
            request.setAttribute("errorMessage", "Vous ne pouvez pas emprunter plus de 2 livres à la fois.");
        }
        
        // Calculate the total monthly cost
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
        
        // Add calculated data to the request for the View (checkout.jsp)
        request.setAttribute("borrowedBooks", books);
        request.setAttribute("totalBooks", totalBooks);
        request.setAttribute("totalCost", totalCost);
        request.setAttribute("physicalCount", physicalCount);
        request.setAttribute("onlineCount", onlineCount);
        
        // Forward control to the View
        request.getRequestDispatcher("/checkout.jsp").forward(request, response);
    }
    
    /**
     * Handles the HTTP POST request. Responsible for confirming ('confirm') or canceling ('cancel') the checkout.
     * @param request The HTTP request containing the action ('confirm' or 'cancel').
     * @param response The HTTP response.
     * @throws ServletException If a Servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
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
            // Re-check the limit before confirming
            if (borrowedList.getBooks().size() > 2) {
                // If validation fails, return to the checkout page 
                request.setAttribute("errorMessage", "Vous ne pouvez pas emprunter plus de 2 livres à la fois.");
                request.setAttribute("borrowedBooks", borrowedList.getBooks());
                request.setAttribute("totalBooks", borrowedList.getBooks().size());
                request.getRequestDispatcher("/checkout.jsp").forward(request, response);
                return;
            }
            
            // Confirmation logic: clears the Model from the session (TP Requirement)
            session.removeAttribute("borrowedList");
            session.setAttribute("message", "Votre commande a été confirmée !");
            response.sendRedirect("borrowed"); 

        } else if ("cancel".equals(action)) {
            
            // Cancellation logic: clears the borrowed list (Model)
            borrowedList.clear();
            session.setAttribute("borrowedList", borrowedList); 
            session.setAttribute("message", "Tous les emprunts ont été annulés et le panier est vide.");

            response.sendRedirect("catalog");
        }
    }
}