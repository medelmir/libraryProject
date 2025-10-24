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
			background-color: #f4f4f4;
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
            background-color: #bd4343;
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
    </style>
</head>
<body>
	<div class="background-image">
        <img src="https://expodif.fr/wp-content/uploads/2022/04/Bibliotheque-de-livres.png" alt="">
    </div>
<h2>Mes livres empruntés</h2>

<c:if test="${not empty message}">
	<p style="color:blue;">${message}</p>
</c:if>

<c:choose>
	<c:when test="${empty borrowedBooks}">
		<p>Aucun livre emprunté.</p>
	</c:when>
	<c:otherwise>
		<table border="2">
			<tr>
				<th>Titre</th>
				<th>Auteur</th>
				<th>ISBN</th>
				<th>Action</th>
			</tr>
			<c:forEach var="book" items="${borrowedBooks}">
				<tr>
					<td>${book.title}</td>
					<td>${book.author}</td>
					<td>${book.isbn}</td>
					<td>
						<form method="post" action="borrowed">
							<input type="hidden" name="action" value="return" />
							<input type="hidden" name="isbn" value="${book.isbn}" />
							<input type="submit" value="Rendre" class="btn-rendre" />
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:otherwise>
</c:choose>

<div style="margin-top: 20px;">
    <a href="catalog" style="background-color: #d1b01d; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">Retour au catalogue</a>
    <a href="checkout" style="background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">Procéder au checkout</a>
</div>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
