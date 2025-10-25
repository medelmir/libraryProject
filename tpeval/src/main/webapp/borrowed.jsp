<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Mes livres empruntés</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: #ffffff; 
        }
        th, td {
            padding: 10px;
            text-align: left;
            border: 3px solid #ddd;
        }
        th {
            background-color: #f4f4f4;
        }
        .btn-rendre {
            padding: 5px 10px;
            background-color: #bd4343; /* Rouge */
            color: white;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
        .btn-rendre:hover {
            background-color: #a04545;
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
        .message {
            color: green;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .nav-links a {
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            margin-right: 15px;
            display: inline-block;
        }
    </style>
</head>
<body>
    <div class="background-image">
        <img src="https://expodif.fr/wp-content/uploads/2022/04/Bibliotheque-de-livres.png" alt="">
    </div>
    <h2>Mes livres empruntés</h2>

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
                                <form method="post" action="return"> 
                                    <input type="hidden" name="isbn" value="${book.isbn}" />
                                    <button type="submit" class="btn-rendre">Rendre</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>Aucun livre emprunté.</p>
        </c:otherwise>
    </c:choose>

    <div class="nav-links" style="margin-top: 20px;">
        <a href="catalog" style="background-color: #d1b01d; color: white;">Retour au catalogue</a>
        <a href="checkout" style="background-color: #4CAF50; color: white;">Procéder au checkout</a>
    </div>
</body>
</html>