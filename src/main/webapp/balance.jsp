<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<jsp:useBean id="inf" class="java.lang.String" scope="session"/>
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

<c:if test="${inf.equals('change')}">
    <div class="w3-panel w3-green w3-display-container w3-card-4 w3-round">
        <span onclick="this.parentElement.style.display='none'"
              class="w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey">
            x</span>
        <h5>Balance replenished.</h5>
    </div>
</c:if>


<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/WebApplication_war_exploded/user'">Back on client
        page
    </button>
</div>
</body>
</html>
