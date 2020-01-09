<%--
  Created by IntelliJ IDEA.
  User: chech
  Date: 05.01.2020
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>firstCourse</title>
    <link rel="stylesheet" type="text/css" href="dishPage.css"/>
    <style type="text/css">
    </style>
</head>
<body>
<jsp:useBean id="listResults" class="java.util.ArrayList" scope="request"/>
<c:out value="${listResults}"/>
<form method="post">
    <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom">Submit</button>
</form>

<form>
    <table border="1">
        <tr>
            <th> Name</th>
            <th> Cost</th>
            <th> Cooking time</th>
            <th> Exemplum</th>
        </tr>
        <c:forEach var="product" items="${listResults}">
            <tr>\
                <td><c:out value=" ${product.name}"/></td>
                <td>${product.cost}</td>
                <td>${product.cookingTime}</td>
                <td><img src="https://html5book.ru/wp-content/uploads/2015/04/dress-2.png"></td>
            </tr>
        </c:forEach>
    </table>
</form>
</body>
</html>