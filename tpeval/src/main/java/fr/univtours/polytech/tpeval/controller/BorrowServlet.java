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
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@WebServlet(name = "BorrowServlet", urlPatterns = {"/borrowed"})
public class BorrowServlet extends HttpServlet {
}
