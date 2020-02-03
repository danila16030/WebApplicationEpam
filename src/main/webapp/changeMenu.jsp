<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: chech
  Date: 22.01.2020
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="menuList" class="java.util.ArrayList" scope="session"/>
<html>
<head>
    <title>changeMenu</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script type="text/javascript" src="/js/jq.js"></script>
</head>
<body>
<div>
    <form method="post">
        <label>Enter the product name you want to find:
            <input type="text" name="product" style="width: 30%" pattern="^[A-Za-z0-9 ]{1,}"><br/>
        </label>
        <button type="submit">Search</button>
    </form>
</div>
<div>
    <form method="post">
        <label>Select the tag by which to search for product product:
            <input type="checkbox" name="tag" value="firstCourse">First course
            <input type="checkbox" name="tag" value="secondCourse">Second course
            <input type="checkbox" name="tag" value="garnish">Garnish
            <input type="checkbox" name="tag" value="salad">Salad
            <input type="checkbox" name="tag" value="sparklingWater">Sparkling water
            <input type="checkbox" name="tag" value="juice">Juice
            <input type="checkbox" name="tag" value="alcohol">Alcohol
            <br/>
        </label>
        <button type="submit">Search</button>
    </form>
</div>
Create new Product:
<div>
    <form method="post" enctype="multipart/form-data">
        <table border="1">
            <tr>
                <th> Name</th>
                <th> Cost</th>
                <th> Cooking time</th>
                <th> Exemplum</th>
                <th> Tag</th>
            </tr>
            <tr>
                <td><input type="text" name="product"
                           pattern="^[A-Za-z0-9 ]{1,}" required></td>
                <td><input type="text" name="cost"
                           pattern="^[0-9]{1,}" required></td>
                <td><input type="time" name="time"
                           pattern="^[A-Za-z0-9]{1,}" required></td>
                <td><input type="file" name="file" required></td>
                <td><input type="text" name="tag"
                           pattern="^[A-Za-z]{1,}" required></td>
            </tr>
        </table>
        <input type="submit" id="btn" name="create" value="Click to create new product">
    </form>
</div>
<form method="post" enctype="multipart/form-data">
    <td><input type="file" name="file" required></td>
    <input type="submit" name="create" value="Click">
</form>
Required products:
<div>
    <form method="post">
        <table border="1">
            <tr>
                <th> Name</th>
                <th> Cost</th>
                <th> Cooking time</th>
                <th> Exemplum</th>
                <th> Tag</th>
            </tr>
            <c:forEach var="product" items="${menuList}">
                <tr>
                    <input type="hidden" name="previous" value="${product.name}">
                    <td><input type="text" name="product" value="${product.name}"
                               pattern="^[A-Za-z0-9 ]{1,}" required>
                    </td>
                    <td><input type="text" name="cost" value="${product.cost}"
                               pattern="^[0-9]{1,}" required></td>
                    <td><input type="time" name="time" value="${product.cookingTime}"
                               pattern="^[A-Za-z0-9]{1,}" required></td>
                    <td><img src="data:image/jpg;base64, ${product.image}" width="15%" height="15%"></td>
                    <td><input type="text" name="tag" value="${product.tag}"
                               pattern="^[A-Za-z]{1,}" required>
                        <input type="submit" id="de" name="delete" value="click to delete this product">
                    </td>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${!empty menuList}">
            <button type="submit">Click to confirm changes</button>
        </c:if>
    </form>
</div>

</body>

</html>
