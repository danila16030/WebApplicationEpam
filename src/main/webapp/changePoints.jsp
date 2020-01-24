<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.epam.servlets.entities.Client" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: chech
  Date: 22.01.2020
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listResults" class="java.util.ArrayList" scope="request"/>
<html>
<head>
    <title>changePoints</title>
</head>
<body>
<div>
    <form method="post">
        <label>Enter the login of the user you want to find or leave the field empty to see all users:
            <input type="text" name="username" style="width: 30%"><br/>
        </label>
        <button type="submit">Search</button>
    </form>
</div>

<div>
    <form method="post">
        <table border="1">
            <tr>
                <th> Login</th>
                <th> Loyalty points</th>
            </tr>
            <c:forEach var="user" items="${listResults}">
                <tr>
                    <td>${user.login}</td>
                    <td>
                            <input type="hidden" name="user" value="${user.login}">
                            <input type="text" name="points" style="width: 30%" value="${user.loyaltyPoints}"
                                   pattern="^[0-9]{1,}"><br/>
                            <button type="submit">Click to confirm changes</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</div>
</body>
</html>
