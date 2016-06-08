<%--
  Created by IntelliJ IDEA.
  User: Jager
  Date: 09.06.2016
  Time: 01:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Meal</title>
</head>
<body>
<div align="center">
<form action="${pageContext.request.contextPath}/updateMeal" method="post">
    <input type="number" name="id" value="${meal.id}" hidden/>
    Date and time: <input type="datetime-local" name="datetime" value="${meal.dateTime}"/><br>
    Description: <input type="text" name="description" value="${meal.description}"/><br>
    Calories <input type="number" name="calories" value="${meal.calories}"/><br>
    <input type="submit" value="Update"/>
</form>
</div>

</body>
</html>
