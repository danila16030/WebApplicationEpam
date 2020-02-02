<%--
  Created by IntelliJ IDEA.
  User: chech
  Date: 28.12.2019
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cafe</title>
    <link rel="stylesheet" type="text/css" href="css/mainPage.css"/>
    <style type="text/css">
    </style>
</head>
<div>    <!-- buttons holder -->
    <button type="submit" class="singIn"
            onclick="location.href=location.href+'singIn'">Sing in
    </button>
    <button type="submit" class="register"
            onclick="location.href=location.href+'register'">Register
    </button>
    <form method="post">
        <nav>
            <ul class="topmenu">
                <li><a href="">Home</a></li>
                <li><a href="" class="down">Dishes</a>
                    <ul class="submenu">
                        <li><a href="firstCourse?move=0"> First course</a></li>
                        <li><a href="secondCourse?move=0">Second course</a></li>
                        <li><a href="">Garnish</a></li>
                        <li><a href="">Salad</a></li>
                    </ul>
                </li>
                <li><a href="" class="down">Drink</a>
                    <ul class="submenu">
                        <li><a href="">Sparkling water</a></li>
                        <li><a href="">Juice</a></li>
                        <li><a href="">Alcohol</a></li>
                        <li><a href="">Cocktails</a></li>
                    </ul>
                </li>
                <li><a href="">Contact</a></li>
            </ul>
        </nav>
    </form>
</div>

</html>
