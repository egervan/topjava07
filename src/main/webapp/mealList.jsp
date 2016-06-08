<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list size ${mealList.size()}</h2>
<table border="1">
    <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Exceed</th>
        </tr>
    </thead>

    <c:forEach items="${mealList}" var="meal">
       <tr>
        <td>${meal.dateTime}</td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
        <td>${meal.exceed}</td>
       </tr>
    </c:forEach>
</table>

</body>
</html>
