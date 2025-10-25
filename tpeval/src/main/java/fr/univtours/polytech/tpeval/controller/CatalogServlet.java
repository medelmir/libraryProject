package fr.univtours.polytech.tpeval.controller;

import fr.univtours.polytech.tpeval.model.Library;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import fr.univtours.polytech.tpeval.model.Book;

/**
 * Main Controller managing the display of the book catalog.
 * This Servlet is responsible for initializing the Library Model 
 * and transferring data to the catalog.jsp view.
 */
@WebServlet(name = "CatalogServlet", urlPatterns = {"/catalog"})
public class CatalogServlet extends HttpServlet {
    
    /** Reference to the Library Model (the catalog) */
    private Library library;

    /**
     * Initializes the Servlet. This method is executed only once when the application starts.
     * It creates the list of sample books and stores the Library in the application context 
     * (ServletContext) to make it accessible to other Servlets (like BorrowServlet).
     * @throws ServletException If an error occurs during initialization.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        library = new Library();
        
        // Add sample books
        library.addBook(new Book("1234567890", "Java Programming", "John Doe", 3,"A guide to Java", "physical"));
        library.addBook(new Book("0987654321", "Web Development", "Jane Smith", 2,"Learn web basics", "online"));
        library.addBook(new Book("1122334455", "Python Programming", "Alice Johnson", 5,"Master Python", "physical"));
        library.addBook(new Book("5544332211", "Data Science", "Bob Brown", 4,"Data analysis techniques", "online"));
        library.addBook(new Book("6677889900", "Machine Learning", "Charlie Davis", 1,"Intro to ML", "physical"));

        // Store the library in the ServletContext to guarantee access 
        // to other Servlets (resolves startup dependency issues).
        getServletContext().setAttribute("library", library);
    }

    /**
     * Handles the HTTP GET request to display the catalog.
     * @param request The HTTP request containing data.
     * @param response The HTTP response.
     * @throws ServletException If a Servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Retrieve the list of books from the Model
        List<Book> books = library.getAllBooks();
        
        // Add the list of books as a request attribute for the View
        request.setAttribute("books", books);
        
        // Forward control to the View (catalog.jsp)
        request.getRequestDispatcher("/catalog.jsp").forward(request, response);
    }
}