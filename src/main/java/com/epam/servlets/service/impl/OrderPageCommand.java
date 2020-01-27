package com.epam.servlets.service.impl;

import com.epam.servlets.entities.Product;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

public class OrderPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        if (req.getParameter("time") != null) {
            return orderADish(req);
        } else {
            return getProductForOrderPage(req);
        }
    }

    private String getProductForOrderPage(HttpServletRequest req) {
        Product product = null;
        String productName = req.getParameter("product");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM menu WHERE product='" + productName + "'  ");
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
        String product = req.getParameter("product");
        String card = req.getParameter("card");
        String time = null;
        int clientBalance;
        int productCost;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM client WHERE login='" + userName + "' ");
            if (res.next()) {
                if (!res.getBoolean(4)) {
                    clientBalance = res.getInt(6);
                    Statement stmt1 = connection.createStatement();
                    String productName = product.trim();
                    ResultSet res1 = stmt1.executeQuery("SELECT * FROM menu WHERE product='" + productName + "'");
                    if (res1.next()) {
                        time = String.valueOf(res1.getString(3));
                    }
                    productCost = res1.getInt(2);
                    String fullOrder = res.getString(3);
                    LocalTime now = LocalTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                    LocalTime end = LocalTime.parse(time, formatter);
                    end = end.plus(now.getHour(), ChronoUnit.HOURS);
                    end = end.plus(now.getMinute(), ChronoUnit.MINUTES);
                    String requestedTime = req.getParameter("time");
                    LocalTime orderTime = LocalTime.parse(requestedTime, formatter);
                    if (clientBalance > productCost || card == null) {
                        if (end.isBefore(orderTime)) {
                            if (fullOrder != null) {
                                fullOrder = fullOrder + " " + product + " " + orderTime;
                            } else {
                                fullOrder = " " + product + " " + orderTime;
                            }
                            clientBalance = clientBalance - productCost;
                            int point = res.getInt(2) + 1;
                            stmt.executeUpdate("UPDATE  client SET `order`='" + fullOrder + "', balance='" + clientBalance + "',loyaltyPoints='" + point + "' WHERE login='" + userName + "'");
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
            } else {
                req.setAttribute("inf", "block");
                return getProductForOrderPage(req);
            }
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | SQLException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
