package com.epam.servlets.service.impl;

import com.epam.servlets.entities.Product;
import com.epam.servlets.service.Command;


import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;

public class FirstCourseCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        if (req.getParameter("move").equals("0")) {
            return getProductList(req);
        } else {
            if (req.getParameter("time") != null) {
                return orderADish(req);
            } else {
                return getProductForOrderPage(req);
            }
        }
    }

    private String getProductForOrderPage(HttpServletRequest req) {
        Product product = null;
        String productName = req.getParameter("move").substring(15);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM menu WHERE product='" + productName.trim() + "'  ");
            if (res.next()) {
                Blob image = res.getBlob(5);
                product = new Product(res.getString(1), res.getInt(2), res.getString(3), Base64.getEncoder().encodeToString(image.getBytes(1, (int) image.length())));
            }
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        req.setAttribute("product", product);
        return "orderPage";
    }

    private String orderADish(HttpServletRequest req) {
        String userName = (String) req.getAttribute("user");
        String product = req.getParameter("move");
        product = product.substring(14);
        String time = null;
        int clientBalance;
        int productCost;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM client WHERE login='" + userName + "' ");
            if (res.next()) {
                clientBalance = res.getInt(6);
                Statement stmt1 = connection.createStatement();
                String productName = product.trim();
                ResultSet res1 = stmt1.executeQuery("SELECT * FROM menu WHERE product='" + productName + "'");
                if (res1.next()) {
                    time = String.valueOf(res1.getString(3));
                }
                productCost = res1.getInt(2);
                String fullOrder = res.getString(3);
                LocalTime start = LocalTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime end = LocalTime.parse(time, formatter);
                end = end.plus(start.getHour(), ChronoUnit.HOURS);
                end = end.plus(start.getMinute(), ChronoUnit.MINUTES);
                String requestedTime = req.getParameter("time");
                LocalTime orderTime = LocalTime.parse(requestedTime, formatter);
                if (clientBalance > productCost) {
                    if (end.isBefore(orderTime)) {
                        if (fullOrder != null) {
                            fullOrder = fullOrder + product + orderTime;
                        } else {
                            fullOrder = product + orderTime;
                        }
                        clientBalance = clientBalance - productCost;
                        stmt.executeUpdate("UPDATE  client SET `order`='" + fullOrder + "', balance='" + clientBalance + "' WHERE login='" + userName + "'");
                        connection.close();
                        req.setAttribute("inf", "cool");
                        return getProductForOrderPage(req);
                    } else {
                        req.setAttribute("inf", "time");
                        return getProductForOrderPage(req);
                    }
                } else {
                    req.setAttribute("inf", "money");
                    return getProductForOrderPage(req);
                }
            }
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | SQLException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private String getProductList(HttpServletRequest req) {
        ArrayList<Product> listResults = new ArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM menu WHERE tag='firstCourse' ");
            while (res.next()) {
                Blob image = res.getBlob(5);
                listResults.add(new Product(res.getString(1), res.getInt(2), res.getString(3), Base64.getEncoder().encodeToString(image.getBytes(1, (int) image.length())), res.getDouble(7), res.getInt(8)));
            }
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        req.setAttribute("listResults", listResults);
        return "firstCourse";
    }
}
