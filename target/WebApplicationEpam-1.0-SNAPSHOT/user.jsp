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
<form>
    <tr>
        <th> Login</th>
        <th> Loyalty points</th>
        <th> Order</th>
        <th> Block</th>
    </tr>
    <tr>
        <td>${client.login}</td>
        <td>${client.loyaltyPoints}</td>
        <td>${client.order}</td>
        <td>${client.block}</td>
    </tr>
    </table>
</form>
</body>
</html>
