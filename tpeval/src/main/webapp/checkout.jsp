<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Confirmation des emprunts</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 20px;
        }
        .container {
            width: 80%;
            margin: 20px auto;
            padding: 20px;
            background-color: white;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            border-radius: 8px;
        }
        .error-message {
            background-color: #ffebee;
            color: #c62828;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
            border-left: 5px solid #c62828;
        }
        .summary {
            background-color: #f5f5f5;
            padding: 20px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f4f4f4;
        }
        .actions {
            margin-top: 20px;
            text-align: center;
        }
        .btn {
            padding: 10px 20px;
            margin: 0 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }
        .btn-confirm {
            background-color: #4CAF50;
            color: white;
        }
        .btn-cancel {
            background-color: #f44336;
            color: white;
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
    <div class="container">
        <h1>Confirmation des emprunts</h1>
        
        <c:if test="${not empty errorMessage}">
            <div class="error-message">
                ${errorMessage}
            </div>
        </c:if>
        
        <div class="summary">
            <h2>Résumé de vos emprunts</h2>
            <div class="cost-details">
                <p>Nombre total de livres : ${totalBooks}</p>
                <p>Détail des emprunts :</p>
                <ul>
                    <c:if test="${physicalCount > 0}">
                        <li>${physicalCount} livre(s) physique(s) : ${physicalCount * 10}€</li>
                    </c:if>
                    <c:if test="${onlineCount > 0}">
                        <li>${onlineCount} livre(s) en ligne : ${onlineCount * 5}€</li>
                    </c:if>
                </ul>
                <p class="total-cost">Coût total mensuel : ${totalCost}€</p>
            </div>
        </div>
        
        <style>
            .cost-details {
                background-color: #e8f5e9;
                padding: 15px;
                border-radius: 5px;
                margin-top: 10px;
            }
            .cost-details ul {
                list-style-type: none;
                padding-left: 0;
            }
            .cost-details li {
                margin: 5px 0;
                color: #2e7d32;
            }
            .total-cost {
                font-size: 1.2em;
                font-weight: bold;
                color: #1b5e20;
                margin-top: 10px;
                border-top: 1px solid #a5d6a7;
                padding-top: 10px;
            }
        </style>

        <c:if test="${not empty borrowedBooks}">
            <table>
                <thead>
                    <tr>
                        <th>Titre</th>
                        <th>Auteur</th>
                        <th>ISBN</th>
                        <th>Format</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="book" items="${borrowedBooks}">
                        <tr>
                            <td>${book.title}</td>
                            <td>${book.author}</td>
                            <td>${book.isbn}</td>
                            <td>${book.format}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="actions">
                <form action="checkout" method="post" style="display: inline;">
                    <input type="hidden" name="action" value="confirm">
                    <button type="submit" class="btn btn-confirm">Confirmer les emprunts</button>
                </form>
                <form action="checkout" method="post" style="display: inline;">
                    <input type="hidden" name="action" value="cancel">
                    <button type="submit" class="btn btn-cancel">Annuler</button>
                </form>
                 <p><a href="catalog" >Retourner au catalogue</a></p>
                 <p><a href="borrowed">Retourner aux livres empruntés</a></p>
            </div>
        </c:if>
        
        <c:if test="${empty borrowedBooks}">
            <p>Vous n'avez aucun livre à emprunter.</p>
            <p><a href="catalog">Retourner au catalogue</a></p>
        </c:if>
    </div>
</body>
</html>
