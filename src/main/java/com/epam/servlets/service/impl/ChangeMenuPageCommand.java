package com.epam.servlets.service.impl;

import com.epam.servlets.entities.Product;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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
        String tag[] = req.getParameterValues("tag");
        String[] productName = req.getParameterValues("product");
        String[] previousName = req.getParameterValues("previous");
        String[] cost = req.getParameterValues("cost");
        String[] time = req.getParameterValues("time");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            for (int i = 0; i < previousName.length; i++) {
                stmt.executeUpdate("UPDATE  menu SET product='" + productName[i] + "',tag='" + tag[i] + "'," +
                        "cookingTime='" + time[i] + "',cost='" + cost[i] + "' WHERE product='" + previousName[i] + "'");
            }
            return getProduct(req);
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getProduct(HttpServletRequest req) {
        String productName = req.getParameter("product");
        ArrayList<Product> listResults = new ArrayList();
        List<String> tags = null;
        if (req.getParameter("tag") != null) {
            tags = Arrays.asList(req.getParameterValues("tag"));
            tags = tags.stream().distinct().collect(Collectors.toList());
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            if (tags != null) {
                for (String tag : tags) {
                    ResultSet res = stmt.executeQuery("SELECT * FROM menu WHERE tag='" + tag + "'");
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
