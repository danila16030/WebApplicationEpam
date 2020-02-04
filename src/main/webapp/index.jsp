<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
Created by IntelliJ IDEA.
User: chech
Date: 28.12.2019
Time: 14:42
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="locale" class="java.lang.String" scope="session"/>
<fmt:setBundle basename="pagecontent_${locale}" var="lang"/>

<html>
<head>
    <title><fmt:message key="mainPage.title" bundle="${lang}"/></title>
    <link rel="stylesheet" type="text/css" href="css/mainPage.css"/>
    <style type="text/css">
    </style>
</head>
<div> <!-- buttons holder -->
    <button type="submit" class="singIn"
            onclick="location.href=location.href+'singIn'"><fmt:message key="mainPage.singIn" bundle="${lang}"/>
    </button>
    <button type="submit" class="register"
            onclick="location.href=location.href+'register'"><fmt:message key="mainPage.register" bundle="${lang}"/>
    </button>

    <form method="post">
        <input type="submit" class="ru" name="language" value="ru">
        <input type="submit" class="en" name="language" value="en">
        <nav>
            <ul class="topmenu">
                <li><a href=""><fmt:message key="menu.home" bundle="${lang}"/></a></li>
                <li><a href="" class="down"><fmt:message key="menu.dishes" bundle="${lang}"/></a>
                    <ul class="submenu">
                        <li><a href="firstCourse?move=0"> <fmt:message key="menu.firstCourse" bundle="${lang}"/></a>
                        </li>
                        <li><a href="secondCourse?move=0"><fmt:message key="menu.secondCourse" bundle="${lang}"/></a>
                        </li>
                        <li><a href=""><fmt:message key="menu.garnish" bundle="${lang}"/></a></li>
                        <li><a href=""><fmt:message key="menu.salad" bundle="${lang}"/></a></li>
                    </ul>
                </li>
                <li><a href="" class="down"><fmt:message key="menu.drink" bundle="${lang}"/></a>
                    <ul class="submenu">
                        <li><a href=""><fmt:message key="menu.sparklingWater" bundle="${lang}"/></a></li>
                        <li><a href=""><fmt:message key="menu.juice" bundle="${lang}"/></a></li>
                        <li><a href=""><fmt:message key="menu.alcohol" bundle="${lang}"/></a></li>
                        <li><a href=""><fmt:message key="menu.cocktails" bundle="${lang}"/></a></li>
                    </ul>
                </li>
                <li><a href=""><fmt:message key="menu.contact" bundle="${lang}"/></a></li>
            </ul>
        </nav>
    </form>
</div>
<div>
</div>
</html>
