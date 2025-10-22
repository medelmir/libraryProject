<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Book Catalog</title>
</head>
<body>
<h1>Catalog</h1>

<p>
    <a href="${pageContext.request.contextPath}/">Home</a> |
    <a href="${pageContext.request.contextPath}/borrowed">Your borrowed books</a> |
    <a href="${pageContext.request.contextPath}/checkout">Proceed to checkout</a>
</p>

<c:if test="${not empty borrowStatus}">
    <p>${borrowStatus}</p>
</c:if>

<table border="1" cellpadding="6">
    <thead>
    <tr>
        <th>ISBN</th>
        <th>Title</th>
        <th>Author</th>
        <th>Available</th>
        <th>Format</th>
        <th>Description</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="book" items="${books}">
        <tr>
            <td>${book.isbn}</td>
            <td>${book.title}</td>
            <td>${book.author}</td>
            <td>${book.availableCopies}</td>
            <td>${book.format}</td>
            <td>${book.description}</td>
            <td>
                <form action="${pageContext.request.contextPath}/reserve" method="post">
                    <input type="hidden" name="isbn" value="${book.isbn}" />
                    <c:choose>
                        <c:when test="${book.availableCopies == 0}">
                            <button type="submit" disabled>Unavailable</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit">Borrow</button>
                        </c:otherwise>
                    </c:choose>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${not empty borrowedList and not empty borrowedList.entries}">
    <h2>Your Borrowed Books</h2>
    <table border="1" cellpadding="6">
        <thead>
        <tr>
            <th>Title</th>
            <th>Format</th>
            <th>Quantity</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="entry" items="${borrowedList.entries}">
            <tr>
                <td>${entry.title}</td>
                <td>${entry.format}</td>
                <td>${entry.quantity}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <p>
        Total borrowed: ${borrowedList.totalQuantity} book(s).
        Monthly cost: ${borrowedList.totalMonthlyCost} &euro;.
    </p>
    <p><a href="${pageContext.request.contextPath}/borrowed">Manage borrowed books</a></p>
</c:if>

</body>
</html>
