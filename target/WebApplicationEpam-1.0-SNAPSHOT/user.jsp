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
                <td>${product.readyTime}</td>
                <td><input type="text" id="time" value="${product.readyTime}" disabled></td>
                </td>
            </tr>
        </c:forEach>
    </table>
</form>

Block: <%= client.isBlock()%><br>

<label>Balance: ${client.balance}     
    <input type="button" id="money" value="replenish balance"
           onclick="location.href='/WebApplication_war_exploded/balance'">
</label>

<script>
    function getDate(string) {
        return new Date(0, 0, 0, string.split(':')[0], string.split(':')[1], string.split(':')[2]);
    }

    function SetTime(value) {
        var date1 = new Date();
        let firstDate = value.value;
        let secondDate = date1.toLocaleTimeString();
        let different = (getDate(firstDate) - getDate(secondDate));
        let hours = Math.floor((different % 86400000) / 3600000);
        let minutes = Math.round(((different % 86400000) % 3600000) / 60000);
        let seconds = Math.round((((different % 86400000) % 3600000) % 60000) / 1000);
        let cookTime = hours + ':' + minutes + ':' + seconds;
        if (minutes < 0 || seconds < 0 || hours < 0) {
            if (hours < -4) {
                value.value = "Expired";
            }
            value.value = "Ready";
        } else {
            value.value = cookTime;
        }
    }

    var time = document.getElementById("time");
    if (time.value != "") {
        var buttons = document.getElementsByTagName("input");
        var inputList = Array.prototype.slice.call(buttons);
        inputList.forEach(SetTime);
    }
</script>
</body>

</html>
