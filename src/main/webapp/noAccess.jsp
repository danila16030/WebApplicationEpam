<%--
  Created by IntelliJ IDEA.
  User: chech
  Date: 05.02.2020
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="path" class="java.lang.String" scope="session"/>
<html>
<head>
    <title>403 Forbidden</title>
</head>
<body>
<h1>Forbidden</h1>
<p>You don't have permission to access ${path} on this server.</p>
<hr>
</body>
</html>
