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

@WebServlet(name = "CatalogServlet", urlPatterns = {"/catalog"})
public class CatalogServlet extends HttpServlet {
    private Library library;

    @Override
    public void init() throws ServletException {
        super.init();
        library = new Library();
        
        // Ajout des livres d'exemple
        library.addBook(new Book("1234567890", "Java Programming", "John Doe", 3,"A guide to Java", "physical"));
        library.addBook(new Book("0987654321", "Web Development", "Jane Smith", 2,"Learn web basics", "online"));
        library.addBook(new Book("1122334455", "Python Programming", "Alice Johnson", 5,"Master Python", "physical"));
        library.addBook(new Book("5544332211", "Data Science", "Bob Brown", 4,"Data analysis techniques", "online"));
        library.addBook(new Book("6677889900", "Machine Learning", "Charlie Davis", 1,"Intro to ML", "physical"));

        // Stocker la bibliothèque pour tous les servlets (comme BorrowServlet)
        getServletContext().setAttribute("library", library);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération de la liste des livres depuis la bibliothèque
        List<Book> books = library.getAllBooks();
        
        // Ajout de la liste des livres comme attribut de la requête
        request.setAttribute("books", books);
        
        // Redirection vers la page catalog.jsp
        request.getRequestDispatcher("/catalog.jsp").forward(request, response);
    }
}