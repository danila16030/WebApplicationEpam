<%--
  Created by IntelliJ IDEA.
  User: chech
  Date: 18.01.2020
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="product" class="com.epam.servlets.entities.Product" scope="request"/>
<html>
<head>
    <title>Order</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-grey">
<label>
    <form>
        <table border="1">
            <tr>
                <th> Name</th>
                <th> Cost</th>
                <th> Cooking time</th>
                <th> Exemplum</th>
            </tr>
            <tr>
                <td>${product.name} </td>
                <td>${product.cost}</td>
                <td>${product.cookingTime}</td>
                <td><img src="data:image/jpg;base64, ${product.image}" width="15%" height="15%">
                </td>
            </tr>
        </table>
    </form>
</label>
<form method="post" class="w3-selection w3-light-grey w3-padding">
    <label>Please enter the time when you would like to pick up the order:
        <input type="time" id="time" name="time" required
               class="w3-input w3-animate-input w3-border w3-round-large"
               style="width: 30%"><br/>
    </label>
    <input type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom"
           id="inf" name="time" value="Submit"/>
</form>
<%
    if (request.getAttribute("inf") != null && request.getAttribute("inf").equals("money")) {
        out.println("<div class=\"w3-panel w3-red w3-display-container w3-card-4 w3-round\">\n" +
                "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">×</span>\n" +
                "   <h5>You dont have enough money to order this product.</h5></div>");
    }
    if (request.getAttribute("inf") != null && request.getAttribute("inf").equals("time")) {
        out.println("<div class=\"w3-panel w3-red w3-display-container w3-card-4 w3-round\">\n" +
                "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">×</span>\n" +
                "   <h5>The product will not be ready at this time. Please select the time given the cooking time.</h5></div>");
    }
    if (request.getAttribute("inf") != null && request.getAttribute("inf").equals("cool")) {
        out.println("<div class=\"w3-panel w3-green w3-display-container w3-card-4 w3-round\">\n" +
                "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">×</span>\n" +
                "   <h5>Order completed successfully </h5 ></div > ");
    }
%>
<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/WebApplication_war_exploded/firstCourse?move=0'">Back
        to menu
    </button>
</div>
</body>
</html>
