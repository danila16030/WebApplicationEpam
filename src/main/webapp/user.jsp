<%--
  Created by IntelliJ IDEA.
  User: chech
  Date: 05.01.2020
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="client" class="com.epam.servlets.entities.Client" scope="request"/>
<html>
<head>
    <title>${client.login}</title>
    <link rel="stylesheet" type="text/css"/>
    <style type="text/css">
    </style>
</head>
<body>

<br>

Login: <%= client.getLogin()%><br>

Loyalty points: <%= client.getLoyaltyPoints()%><br>

Order:
<form>
    <table border="1">
        <tr>
            <th> Name</th>
            <th> Will be ready in</th>
            <th> Remaining time before cooking</th>
        </tr>
        <c:forEach var="product" items="${client.orderList}">
            <tr>
                <td>${product.name} </td>
                <td>${product.orderTime}</td>
                <td>${product.cookingProcess}</td>
                </td>
            </tr>
        </c:forEach>
    </table>
</form>

Block: <%= client.isBlock()%><br>

<label>Balance:${client.balance}
    <input type="button" id="money" name="money" value="replenish balance"
           onclick="location.href='/WebApplication_war_exploded/balance'">
</label>

</body>

</html>
