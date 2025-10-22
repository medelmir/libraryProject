<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Checkout</title>
</head>
<body>
<h1>Checkout</h1>

<p>
    <a href="${pageContext.request.contextPath}/catalog">Back to catalog</a> |
    <a href="${pageContext.request.contextPath}/borrowed">Manage borrowed books</a>
</p>

<c:if test="${not empty checkoutMessage}">
    <p>${checkoutMessage}</p>
</c:if>
<c:if test="${not empty checkoutError}">
    <p>${checkoutError}</p>
</c:if>

<c:choose>
    <c:when test="${empty borrowedList or empty borrowedList.entries}">
        <p>You have not reserved any books yet.</p>
    </c:when>
    <c:otherwise>
        <table border="1" cellpadding="6">
            <thead>
            <tr>
                <th>Title</th>
                <th>Format</th>
                <th>Quantity</th>
                <th>Monthly Rate (&euro;)</th>
                <th>Subtotal (&euro;)</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="entry" items="${borrowedList.entries}">
                <tr>
                    <td>${entry.title}</td>
                    <td>${entry.format}</td>
                    <td>${entry.quantity}</td>
                    <td>${entry.monthlyRate}</td>
                    <td>${entry.quantity * entry.monthlyRate}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <p>
            Total books: ${totalQuantity}.
            Monthly total: ${totalMonthlyCost} &euro;.
        </p>

        <c:if test="${limitExceeded}">
            <p>You have borrowed more than two books. Please return at least one to complete checkout.</p>
        </c:if>

        <c:if test="${not limitExceeded}">
            <form action="${pageContext.request.contextPath}/checkout" method="post">
                <input type="hidden" name="action" value="confirm" />
                <button type="submit">Confirm checkout</button>
            </form>
        </c:if>
    </c:otherwise>
</c:choose>

</body>
</html>
