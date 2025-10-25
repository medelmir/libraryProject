<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Catalogue de la bibliothèque</title>
    <style>
        /* Ajout de styles généraux pour le corps de la page */
        body {
            font-family: Arial, sans-serif;
            padding: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            /* Rétablissement de la couleur de fond */
            background-color: #ffffff; 
        }
        th, td {
            padding: 10px;
            text-align: left;
            /* Rétablissement de la bordure (ajustée à 1px pour la cohérence) */
            border: 1px solid #ddd;
        }
        th {
            background-color: #f4f4f4;
        }
        .btn-borrow {
            padding: 5px 10px;
            background-color: #4CAF50; /* Vert */
            color: white;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
        .btn-borrow:hover {
            background-color: #45a049;
        }
        .background-image img {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            object-fit: cover;
            z-index: -1;
            opacity: 0.3;
            filter: blur(2px);
        }
    </style>
</head>
<body>
    <div class="background-image">
        <img src="https://expodif.fr/wp-content/uploads/2022/04/Bibliotheque-de-livres.png" alt="">
    </div>
    <h1>Catalogue de la bibliothèque</h1>
    
    <table>
        <thead>
            <tr>
                <th>Titre</th>
                <th>Auteur</th>
                <th>ISBN</th>
                <th>Format</th>
                <th>Copies disponibles</th>
                <th>Description</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="book" items="${books}">
                <tr>
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td>${book.isbn}</td>
                    <td>${book.format}</td>
                    <td>${book.availableCopies}</td>
                    <td>${book.description}</td>
                    <td>
                        <form action="borrow" method="post">
                            <input type="hidden" name="action" value="reserve">
                            <input type="hidden" name="isbn" value="${book.isbn}">
                            <button type="submit" class="btn-borrow" ${book.availableCopies <= 0 ? 'disabled' : ''}>
                                ${book.availableCopies <= 0 ? 'Indisponible' : 'Emprunter'}
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <p>
        <a href="borrowed">Voir ma liste d'emprunts et finaliser la commande</a>
    </p>
</body>
</html>