<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Adding meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Add a new meal</h2>
<hr>
<form method="post" action="meals" name="mForm">
    <input type="hidden" name="id" value="${meal.id}">
    <label>
        Description: <input type="text" name="description" required value="<c:out value="${meal.description}"/>"/>
    </label>
    <br>
    <label>
        Calories: <input type="number" name="calories" required value="<c:out value="${meal.calories}"/>"/>
    </label>
    <br>
    <label>
        Time: <input type="datetime-local" name="dateTime" required value="<c:out value="${meal.dateTime}"/>"/>
    </label>
    <br>
    <input type="submit" name="submit" value="Add">
</form>
</body>
</html>
