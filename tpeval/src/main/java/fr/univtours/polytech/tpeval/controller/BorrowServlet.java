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

@WebServlet(name = "BorrowServlet", urlPatterns = {"/borrowed", "/borrow", "/return"})
public class BorrowServlet extends HttpServlet {
    private Library library;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        ServletContext context = config.getServletContext();
        this.library = (Library) context.getAttribute("library");
        
        if (this.library == null) {
            throw new ServletException("Library not initialized in ServletContext. Ensure CatalogServlet runs first.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/borrowed.jsp").forward(request, response);
    }

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
        
        BorrowedList borrowedList = (BorrowedList) session.getAttribute("borrowedList");
        if (borrowedList == null) {
            borrowedList = new BorrowedList();
            session.setAttribute("borrowedList", borrowedList);
        }

        if (path.equals("/borrow")) {
            // Gère l'action d'emprunt depuis le catalogue
            handleBorrow(isbn, borrowedList, session, request, response); 
        } else if (path.equals("/return")) {
            // Gère l'action de retour depuis la page borrowed.jsp
            handleReturn(isbn, borrowedList, session, request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleBorrow(String isbn, BorrowedList borrowedList, HttpSession session, 
                              HttpServletRequest request, HttpServletResponse response) 
                              throws ServletException, IOException {
        
        Optional<Book> bookOpt = library.getBookByIsbn(isbn);
        
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            
            if (book.getAvailableCopies() > 0) {
                
                book.setAvailableCopies(book.getAvailableCopies() - 1); 
                
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
        
        // Redirige vers le catalogue pour montrer la décrémentation des copies
        response.sendRedirect(request.getContextPath() + "/catalog"); 
    }

    private void handleReturn(String isbn, BorrowedList borrowedList, HttpSession session, 
                              HttpServletRequest request, HttpServletResponse response) 
                              throws ServletException, IOException {
        
        Book returnedBook = borrowedList.removeBook(isbn);

        if (returnedBook != null) {
            
            Optional<Book> libraryBookOpt = library.getBookByIsbn(isbn);
            if (libraryBookOpt.isPresent()) {
                Book libraryBook = libraryBookOpt.get();
                
                libraryBook.setAvailableCopies(libraryBook.getAvailableCopies() + 1); 
                
                session.setAttribute("message", "Book returned successfully: " + libraryBook.getTitle()); 
            } else {
                session.setAttribute("message", "Warning: Book returned but not found in main catalog.");
            }
        } else {
            session.setAttribute("message", "Error: Book not found in your borrowed list.");
        }
        
        // Redirige vers la liste des emprunts pour montrer le retrait du livre
        response.sendRedirect(request.getContextPath() + "/borrowed"); 
    }
}