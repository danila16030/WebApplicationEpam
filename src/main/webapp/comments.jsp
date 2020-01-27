<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: chech
  Date: 20.01.2020
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="product" class="com.epam.servlets.entities.Product" scope="request"/>
<jsp:useBean id="listResults" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="inf" class="java.lang.String" scope="request"/>
<html>
<head>
    <title>Comments about ${product.name}</title>
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
    <label> If you want, you can leave a comment:
        <input type="text" id="com" name="comment" pattern="^[A-Za-z ]{1,}" required
               class="w3-input w3-animate-input w3-border w3-round-large"
               style="width: 30%"><br/>
    </label>
    <label> You can also rate this product:
        <input type="radio" name="rate" value="1"> 1
        <input type="radio" name="rate" value="2"> 2
        <input type="radio" name="rate" value="3"> 3
        <input type="radio" name="rate" value="4"> 4
        <input type="radio" name="rate" value="5"> 5<br/>
    </label>
    <input type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom"
           id="inf" name="com" value="Submit"/>
</form>

<c:if test="${inf.equals('no comments')}">
    <div class="w3-panel w3-blue w3-display-container w3-card-4 w3-round">
        <span onclick="this.parentElement.style.display='none'"
              class="w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey">
            x</span>
        <h5>No one has commented on this product yet so you can be the first.</h5>
    </div>
</c:if>

<c:if test="${inf.equals('added')}">
    <div class="w3-panel w3-green w3-display-container w3-card-4 w3-round">
        <span onclick="this.parentElement.style.display='none'"
              class="w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey">
            x</span>
        <h5>Your comment has been successfully added.</h5>
    </div>
</c:if>

<c:if test="${inf.equals('update')}">
    <div class="w3-panel w3-green w3-display-container w3-card-4 w3-round">
        <span onclick="this.parentElement.style.display='none'"
              class="w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey">
            x</span>
        <h5>You have already commented this product so the old comment has been changed.</h5>
    </div>
</c:if>

<label>
    <form>
        <table border="1">
            <tr>
                <th> Author</th>
                <th> Date</th>
                <th> Time</th>
                <th> Comment</th>
                <th> Grade</th>
            </tr>
            <c:forEach var="comment" items="${listResults}">
                <tr>
                    <td>${comment.author} </td>
                    <td>${comment.date}</td>
                    <td>${comment.time}</td>
                    <td>${comment.comment} </td>
                    <td>${comment.rate} </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</label>
<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/WebApplication_war_exploded/firstCourse?move=0'">Back
        to menu
    </button>
</div>
</body>
</html>
