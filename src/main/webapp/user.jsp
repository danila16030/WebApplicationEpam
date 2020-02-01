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
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <style type="text/css">
    </style>
</head>
<body>
<br>
<label>Login: <%= client.getLogin()%><br>
</label>
<label>Loyalty points: <%= client.getLoyaltyPoints()%><br>
</label>
Order:
<body class="w3-light-grey">
<form>
    <table border="1">
        <tr>
            <th> Name</th>
            <th> Will be ready in</th>
            <th> Remaining time before cooking</th>
            <th> Payment method</th>
        </tr>
        <c:forEach var="order" items="${client.orderList}">
            <tr>
                <td>${order.productName} </td>
                <td>${order.time}</td>
                <td>${order.leftTime}</td>
                <td>${order.paymentMethod}</td>
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
</body>
</html>
