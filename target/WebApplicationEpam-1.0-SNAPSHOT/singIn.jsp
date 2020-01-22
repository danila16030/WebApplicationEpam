<%--
  Created by IntelliJ IDEA.
  User: chech
  Date: 02.01.2020
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Sing in</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Super cafe!</h1>
</div>
<div class="w3-card-4">
    <form method="post" class="w3-selection w3-light-grey w3-padding">
        <label>Name:
            <input type="text" name="name" required
                   class="w3-input w3-animate-input w3-border w3-round-large"
                   style="width: 30%"><br/>
        </label>
        <label>Password:
            <input type="password" name="pass" required
                   class="w3-input w3-animate-input w3-border w3-round-large"
                   style="width: 30%"><br/>
        </label> <%
        if (request.getAttribute("inf") != null && request.getAttribute("inf").equals("wrong")) {
            out.println("<div class=\"w3-panel w3-red w3-display-container w3-card-4 w3-round\">\n" +
                    "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                    "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">×</span>\n" +
                    "   <h5>Password or login is incorrect</h5>\n" +
                    "</div>");
        }
        if (request.getAttribute("inf") != null && request.getAttribute("inf").equals("already")) {
            out.println("<div class=\"w3-panel w3-red w3-display-container w3-card-4 w3-round\">\n" +
                    "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                    "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">×</span>\n" +
                    "   <h5>User already in system</h5>\n" +
                    "</div>");
        }
    %>
        <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom" onclick="location.href='/client'">Submit</button>
    </form>
</div>
</div>
<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/WebApplication_war_exploded'">Back to Main</button>
</div>
</body>
</html>
