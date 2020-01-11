<%--
  Created by IntelliJ IDEA.
  User: chech
  Date: 30.12.2019
  Time: 12:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Super app!</h1>
</div>
<div class="w3-card-4">
    <form method="post" class="w3-selection w3-light-grey w3-padding">

        <label>Name (more than 3 symbols):
            <input type="text" name="name" pattern="^[A-Za-z0-9]{3,}" required
            class="w3-input w3-animate-input w3-border w3-round-large"
            style="width: 30%"><br/>
        </label>
        <label>Password (more than 6 symbols):
            <input type="password" name="pass" pattern="^[A-Za-z0-9]{6,}" required
                   class="w3-input w3-animate-input w3-border w3-round-large"
                   style="width: 30%"><br/>
        </label>
        <%
            if (request.getAttribute("inf")!=null&&request.getAttribute("inf").equals("exist")) {
                out.println("<div class=\"w3-panel w3-red w3-display-container w3-card-4 w3-round\">\n" +
                        "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                        "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">×</span>\n" +
                        "   <h5>User with this name is already exist</h5>\n" +
                        "</div>");
            }
        %>
        <button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom">Submit</button>
    </form>
</div>
</div>
<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/WebApplication_war_exploded'">Back to Main</button>
</div>
</body>
</html>
