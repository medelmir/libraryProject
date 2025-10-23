<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Livres Empruntés</title>
    <style>
        table {
            width: 80%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f4f4f4;
        }
        .btn-return {
            padding: 5px 10px;
            background-color: #FF5733; 
            color: white;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
        .btn-return:hover {
            background-color: #C70039;
        }
        .message {
            color: green;
            font-weight: bold;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <h1>Vos Livres Empruntés</h1>

    <c:if test="${not empty sessionScope.message}">
        <p class="message">${sessionScope.message}</p>
        <c:remove var="message" scope="session" />
    </c:if>

    <jsp:useBean id="borrowedList" class="fr.univtours.polytech.tpeval.model.BorrowedList" scope="session"/>

    <c:choose>
        <c:when test="${not empty borrowedList.books}">
            <table>
                <thead>
                    <tr>
                        <th>Titre</th>
                        <th>Auteur</th>
                        <th>ISBN</th>
                        <th>Format</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="book" items="${borrowedList.books}">
                        <tr>
                            <td>${book.title}</td>
                            <td>${book.author}</td>
                            <td>${book.isbn}</td>
                            <td>${book.format}</td>
                            <td>
                                <form action="return" method="post">
                                    <input type="hidden" name="isbn" value="${book.isbn}">
                                    <button type="submit" class="btn-return">Rendre</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <p style="margin-top: 20px;">
                <a href="catalog">Retour au Catalogue</a> | <a href="checkout">Finaliser la Commande</a>
            </p>
        </c:when>
        <c:otherwise>
            <p>Vous n'avez actuellement aucun livre emprunté. <a href="catalog">Parcourir le catalogue</a>.</p>
        </c:otherwise>
    </c:choose>

</body>
</html>