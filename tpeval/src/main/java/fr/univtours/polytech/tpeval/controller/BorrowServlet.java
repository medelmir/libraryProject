package fr.univtours.polytech.tpeval.controller;

import fr.univtours.polytech.tpeval.model.Book;
import fr.univtours.polytech.tpeval.model.BorrowedList;
import fr.univtours.polytech.tpeval.model.Library;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * Controller managing all actions related to borrowing and returning books.
 * It is mapped to the URLs /borrowed (display), /borrow (borrow action), and /return (return action).
 */
@WebServlet(name = "BorrowServlet", urlPatterns = {"/borrowed"})
public class BorrowServlet extends HttpServlet {
    
    /** Reference to the Library Model stored in the application context. */
    private Library library;

    /**
     * Initializes the Servlet. Retrieves the reference to the Library from the application context.
     * @param config The Servlet configuration object.
     * @throws ServletException If the Library has not been initialized by CatalogServlet.
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        ServletContext context = config.getServletContext();
        this.library = (Library) context.getAttribute("library");
        
        if (this.library == null) {
            throw new ServletException("Library not initialized in ServletContext. Ensure CatalogServlet runs first.");
        }
    }

    /**
     * Handles the HTTP GET request. Used to display the list of borrowed books.
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @throws ServletException If a Servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Forwards the request to the View (JSP) to display the borrowed list.
        request.getRequestDispatcher("/borrowed.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST request. Used for modification actions (borrow or return).
     * @param request The HTTP request containing the path (/borrow or /return) and the ISBN.
     * @param response The HTTP response.
     * @throws ServletException If a Servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        String isbn = request.getParameter("isbn");

        if (isbn == null || isbn.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ISBN parameter is missing.");
            return;
        }

        HttpSession session = request.getSession(); 
        
        // Retrieves or creates the user-specific borrowed list (BorrowedList Model)
        BorrowedList borrowedList = (BorrowedList) session.getAttribute("borrowedList");
        if (borrowedList == null) {
            borrowedList = new BorrowedList();
            session.setAttribute("borrowedList", borrowedList);
        }

        if (path.equals("/borrow")) {
            // Handles the borrow (reserve) action from the catalog
            handleBorrow(isbn, borrowedList, session, request, response); 
        } else if (path.equals("/return")) {
            // Handles the return action from the borrowed.jsp page
            handleReturn(isbn, borrowedList, session, request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Logic for managing the book reservation (decreasing stock and adding to the cart).
     * @param isbn The ISBN of the book to borrow.
     * @param borrowedList The user's borrowed list Model.
     * @param session The user's HTTP session.
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @throws ServletException If a Servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    private void handleBorrow(String isbn, BorrowedList borrowedList, HttpSession session, 
                              HttpServletRequest request, HttpServletResponse response) 
                              throws ServletException, IOException {
        
        // REMOVED LOGIC: The pre-emptive check (if size >= 2) is removed to allow borrowing 3+ books.
        // The validation will now occur only on the Checkout page (Exercice 4).
        
        Optional<Book> bookOpt = library.getBookByIsbn(isbn);
        
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            
            if (book.getAvailableCopies() > 0) {
                
                // 1. Update the main stock (Library Model)
                book.setAvailableCopies(book.getAvailableCopies() - 1); 
                
                // 2. Create a copy and add it to the borrowed list (BorrowedList Model)
                Book borrowedBookCopy = new Book(book.getIsbn(), book.getTitle(), 
                                                 book.getAuthor(), 1, 
                                                 book.getDescription(), book.getFormat());

                borrowedList.addBook(borrowedBookCopy);

                session.setAttribute("message", "Book reserved successfully: " + book.getTitle()); 
            } else {
                session.setAttribute("message", "Cannot reserve " + book.getTitle() + ". No copies available.");
            }
        } else {
            session.setAttribute("message", "Error: Book not found in the catalog.");
        }
        
        // PRG Pattern: Redirects to the catalog to show the decrease in copies
        response.sendRedirect(request.getContextPath() + "/catalog"); 
    }

    /**
     * Logic for managing the book return (increasing stock and removing from the cart).
     * @param isbn The ISBN of the book to return.
     * @param borrowedList The user's borrowed list Model.
     * @param session The user's HTTP session.
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @throws ServletException If a Servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    private void handleReturn(String isbn, BorrowedList borrowedList, HttpSession session, 
                              HttpServletRequest request, HttpServletResponse response) 
                              throws ServletException, IOException {
        
        // 1. Remove the book from the borrowed list (BorrowedList Model)
        Book returnedBook = borrowedList.removeBook(isbn);

        if (returnedBook != null) {
            
            Optional<Book> libraryBookOpt = library.getBookByIsbn(isbn);
            if (libraryBookOpt.isPresent()) {
                Book libraryBook = libraryBookOpt.get();
                
                // 2. Update the main stock (Library Model)
                libraryBook.setAvailableCopies(libraryBook.getAvailableCopies() + 1); 
                
                session.setAttribute("message", "Book returned successfully: " + libraryBook.getTitle()); 
            } else {
                session.setAttribute("message", "Warning: Book returned but not found in main catalog.");
            }
        } else {
            session.setAttribute("message", "Error: Book not found in your borrowed list.");
        }
        
        // PRG Pattern: Redirects to the borrowed list to show the book removal
        response.sendRedirect(request.getContextPath() + "/borrowed"); 
    }
}