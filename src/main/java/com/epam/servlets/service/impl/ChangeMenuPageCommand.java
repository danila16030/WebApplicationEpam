package com.epam.servlets.service.impl;

import com.epam.servlets.entities.Product;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;

public class ChangeMenuPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        if (req.getParameter("delete") != null) {
            return deleteProduct(req);
        }
        if (req.getParameter("time") != null) {
            return updateProduct(req);
        } else {
            return getProduct(req);
        }
    }

    private String deleteProduct(HttpServletRequest req) {
        String productName = req.getParameter("product");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM menu WHERE product='" + productName + "'");
            return getProduct(req);
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String updateProduct(HttpServletRequest req) {
        String tag = req.getParameter("tag");
        String productName = req.getParameter("product");
        String previousName = req.getParameter("previous");
        String cost = req.getParameter("cost");
        String time = req.getParameter("time");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("UPDATE  menu SET product='" + productName + "',tag='" + tag + "'," +
                    "cookingTime='" + time + "',cost='" + cost + "' WHERE product='" + previousName + "'");
            return getProduct(req);
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getProduct(HttpServletRequest req) {
        String[] tags = req.getParameterValues("tag");
        String productName = req.getParameter("product");
        ArrayList<Product> listResults = new ArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            if (tags != null) {
                for (int i = 0; i < tags.length; i++) {
                    ResultSet res = stmt.executeQuery("SELECT * FROM menu WHERE tag='" + tags[i] + "'");
                    while (res.next()) {
                        Blob image = res.getBlob(5);
                        listResults.add(new Product(res.getString(1), res.getInt(2),
                                res.getString(3),
                                Base64.getEncoder().encodeToString(image.getBytes(1, (int) image.length())),
                                res.getString(4)));
                    }
                }
            } else {
                ResultSet res = stmt.executeQuery("SELECT * FROM menu WHERE product='" + productName + "'");
                if (res.next()) {
                    Blob image = res.getBlob(5);
                    listResults.add(new Product(res.getString(1), res.getInt(2),
                            res.getString(3),
                            Base64.getEncoder().encodeToString(image.getBytes(1, (int) image.length())),
                            res.getString(4)));
                } else {
                    req.setAttribute("inf", "not exist");
                }
            }
            req.setAttribute("listResults", listResults);
            return "changeMenu";
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException |
                InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
