<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Borrowed Books</title>
</head>
<body>
<h1>Your Borrowed Books</h1>

<p>
    <a href="${pageContext.request.contextPath}/catalog">Back to catalog</a> |
    <a href="${pageContext.request.contextPath}/checkout">Proceed to checkout</a>
</p>

<c:if test="${not empty borrowStatus}">
    <p>${borrowStatus}</p>
</c:if>

<c:choose>
    <c:when test="${empty borrowedList or empty borrowedList.entries}">
        <p>You have not borrowed any books yet.</p>
    </c:when>
    <c:otherwise>
        <table border="1" cellpadding="6">
            <thead>
            <tr>
                <th>Title</th>
                <th>ISBN</th>
                <th>Format</th>
                <th>Qty</th>
                <th>Monthly Rate (&euro;)</th>
                <th>Subtotal (&euro;)</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="entry" items="${borrowedList.entries}">
                <tr>
                    <td>${entry.title}</td>
                    <td>${entry.isbn}</td>
                    <td>${entry.format}</td>
                    <td>${entry.quantity}</td>
                    <td>${entry.monthlyRate}</td>
                    <td>${entry.quantity * entry.monthlyRate}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/return" method="post">
                            <input type="hidden" name="isbn" value="${entry.isbn}" />
                            <button type="submit">Return one</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <p>
            Total borrowed: ${borrowedList.totalQuantity} book(s).
            Monthly cost: ${borrowedList.totalMonthlyCost} &euro;.
        </p>
    </c:otherwise>
</c:choose>

</body>
</html>
