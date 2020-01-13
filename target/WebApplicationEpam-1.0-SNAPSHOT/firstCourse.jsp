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
    <title>FirstCourse</title>
    <link rel="stylesheet" type="text/css" href="productPage.css"/>
    <style type="text/css">
    </style>
</head>
<jsp:useBean id="listResults" class="java.util.ArrayList" scope="request"/>
<div>
    <form method="post">
        <nav>
            <ul class="topmenu">
                <li><a href="/WebApplication_war_exploded">Home</a></li>
                <li><a href="" class="down">Dishes</a>
                    <ul class="submenu">
                        <li><a href="firstCourse"> First course</a></li>
                        <li><a href="">Second course</a></li>
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

<form>
    <table border="1">
        <tr>
            <th> Name</th>
            <th> Cost</th>
            <th> Cooking time</th>
            <th> Exemplum</th>
        </tr>
        <c:forEach var="product" items="${listResults}">
            <tr>
                <td>${product.name}</td>
                <td>${product.cost}</td>
                <td>${product.cookingTime}</td>
                <td><img src="${product.image}"></td>
            </tr>
        </c:forEach>
    </table>
</form>
</html>
