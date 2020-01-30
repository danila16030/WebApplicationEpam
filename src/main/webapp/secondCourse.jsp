<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: chech
  Date: 05.01.2020
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" class="java.lang.String" scope="request"/>
<jsp:useBean id="listResults" class="java.util.ArrayList" scope="request"/>
<html>
<head>
    <title>SecondCourse</title>
    <link rel="stylesheet" type="text/css" href="css/productPage.css"/>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <style type="text/css">
    </style>
</head>
<body class="w3-light-grey">
<div>
    <form method="post">
        <nav>
            <ul class="topmenu">
                <li><a href="/WebApplication_war_exploded">Home</a></li>
                <li><a href="" class="down">Dishes</a>
                    <ul class="submenu">
                        <li><a href="firstCourse?move=0"> First course</a></li>
                        <li><a href="secondCourse?move=0">Second course</a></li>
                        <li><a href="">Garnish</a></li>
                        <li><a href="">Salad</a></li>
                    </ul>
                </li>
                <li><a href="" class="down">Drink</a>
                    <ul class="submenu">
                        <li><a href="">Sparkling water</a></li>
                        <li><a href="">Still water</a></li>
                        <li><a href="">Alcohol</a></li>
                        <li><a href="">Cocktails</a></li>
                    </ul>
                </li>
                <li><a href="">Contact</a></li>
            </ul>
        </nav>
    </form>
</div>
<input type="submit" id="user" value="${user}" style="display: none">
<form method="post">
    <table border="1">
        <tr>
            <th> Name</th>
            <th> Cost</th>
            <th> Cooking time</th>
            <th> Exemplum</th>
            <th> Average score</th>
            <th> Number of voters</th>
        </tr>
        <c:forEach var="product" items="${listResults}">
            <tr>
                <td>${product.name} </td>
                <td>${product.cost}</td>
                <td>${product.cookingTime}</td>
                <td><img src="data:image/jpg;base64, ${product.image}" width="15%" height="15%">
                    <c:if test="${!empty user}">
                        <input type="button" id="order" name="move" value="click to order ${product.name} "
                               onclick="location.href='/WebApplication_war_exploded/orderPage?product=${product.name}'">
                        <input type="button" id="com" name="comment"
                               value="View comments about ${product.name} "
                               onclick="location.href='/WebApplication_war_exploded/comments?about=${product.name}'">
                    </c:if>
                </td>
                <td>${product.averageScope}</td>
                <td>${product.votersNumber}</td>
            </tr>
        </c:forEach>
    </table>
</form>

</body>
</html>
