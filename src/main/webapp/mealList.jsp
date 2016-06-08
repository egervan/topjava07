<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<div align="center">
       <h2><a href="index.html">Home</a></h2>
       <h2>Updated Meal list size ${mealMap.size()}</h2>
      <table border="1">
        <thead>
        <tr>
            <th>id</th>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Exceed</th>
            <th>Delete</th>
        </tr>
        </thead>

        <c:forEach items="${mealMap}" var="meal">

            <tr bgcolor=
            <c:if test="${meal.value.exceed}">"red"</c:if>
            <c:if test="${!meal.value.exceed}">"lawngreen"</c:if>
            >
            <td>${meal.key}</td>
            <td>
                <c:set var="dateInString" value="${fn:replace(meal.value.dateTime, 'T', ' ')}"/>
                ${dateInString}
            </td>
            <td>${meal.value.description}</td>
            <td>${meal.value.calories}</td>
            <td>${meal.value.exceed}</td>
            <td>
                <form action="${pageContext.request.contextPath}/deleteMeal" method="post">
                    <input type="number" name="id" value="${meal.key}" hidden/>
                    <input type="submit" value="delete"/>
                </form>
            </td>
            </tr>
        </c:forEach>
      </table>

    <form action="${pageContext.request.contextPath}/createMeal" method="post">
       Date and time: <input type="datetime-local" name="datetime"/>
       Description: <input type="text" name="description"/>
       Calories <input type="number" name="calories"/>
        <input type="submit" value="Add"/>
    </form>
</div>
</body>
</html>
