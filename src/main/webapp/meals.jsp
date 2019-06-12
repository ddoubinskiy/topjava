<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<hr>
<table border="1" cellpadding="5" cellspacing="2">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:set var="list" scope="page" value="${requestScope.list}"/>
    <c:forEach items="${list}" var="mealElement">
        <tr>
            <td>${mealElement.dateTime}</td>
            <td>${mealElement.description}</td>
            <td>
                <c:if test="${mealElement.excess}">
                    <span style="color: red">${mealElement.calories}</span>
                </c:if>
                <c:if test="${not mealElement.excess}">
                    <span style="color: green">${mealElement.calories}</span>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
