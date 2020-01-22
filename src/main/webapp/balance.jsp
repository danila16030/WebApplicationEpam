<%--
  Created by IntelliJ IDEA.
  User: chech
  Date: 19.01.2020
  Time: 21:49
  To change this template use File | Settings | File Templates.
--%><%--
  Created by IntelliJ IDEA.
  User: chech
  Date: 18.01.2020
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Balance</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-grey">

<form method="post" class="w3-selection w3-light-grey w3-padding">
    <label>Please enter the amount of money you want to receive:(maximum 15 symbols)
        <input type="text" name="money" required pattern="^[0-9]{1,15}"
               class="w3-input w3-animate-input w3-border w3-round-large"
               style="width: 30%"><br/>
    </label>
    <input type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom"
           value="Submit"/>
</form>
<%
    if (request.getAttribute("inf") != null && request.getAttribute("inf").equals("cool")) {
        out.println("<div class=\"w3-panel w3-green w3-display-container w3-card-4 w3-round\">\n" +
                "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">×</span>\n" +
                "   <h5>Order completed successfully </h5 ></div > ");
    }
%>
<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/WebApplication_war_exploded/user'">Back on client
        page
    </button>
</div>
</body>
</html>
