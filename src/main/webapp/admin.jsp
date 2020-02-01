<%--
  Created by IntelliJ IDEA.
  User: chech
  Date: 22.01.2020
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="ex" uri="WEB-INF/custom.tld" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
</head>
<body>
<ex:Out value="Welcom to the system:Administrator"/>
<button class="w3-btn w3-round-large" onclick="location.href='/WebApplication_war_exploded/changeMenu'">Change
    menu
</button>
<button class="w3-btn w3-round-large" onclick="location.href='/WebApplication_war_exploded/changePoints'">Change
    customer points
</button>
</body>
</html>
