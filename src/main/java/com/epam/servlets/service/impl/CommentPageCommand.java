package com.epam.servlets.service.impl;

import com.epam.servlets.entities.Comment;
import com.epam.servlets.entities.Product;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;

public class CommentPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        if (req.getParameter("comment") == null) {
            return getComment(req);
        } else {
            return setComment(req);
        }
    }

    private String getComment(HttpServletRequest req) {
        String productName = req.getParameter("about");
        ArrayList<Comment> listResults = new ArrayList();
        Product product = new Product();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM comment WHERE aboutProduct='" + productName + "'");
            if (res.next()) {
                listResults.add(new Comment(res.getString(1), res.getString(3), res.getString(4), res.getString(5), res.getString(7)));
                while (res.next()) {
                    listResults.add(new Comment(res.getString(1), res.getString(3), res.getString(4), res.getString(5), res.getString(7)));
                }
            } else {
                req.setAttribute("inf", "no comments");
            }
            req.setAttribute("listResults", listResults);
            Statement stmt1 = connection.createStatement();
            ResultSet res1 = stmt1.executeQuery("SELECT * FROM menu WHERE product='" + productName + "'  ");
            if (res1.next()) {
                Blob image = res1.getBlob(5);
                product = new Product(res1.getString(1), res1.getInt(2), res1.getString(3), Base64.getEncoder().encodeToString(image.getBytes(1, (int) image.length())));
            }
            req.setAttribute("product", product);
            connection.close();
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return "comments";
    }

    private String setComment(HttpServletRequest req) {
        String comment = req.getParameter("comment");
        String productName = req.getParameter("about");
        String author = (String) req.getAttribute("user");
        LocalTime now = LocalTime.now();
        String time = now.format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        LocalDate date = LocalDate.now();
        String rate = req.getParameter("rate");
        double average = 0;
        double votesNumber = 0;
        double term = 0;
        if (rate == null) {
            rate = "no rating";
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM comment WHERE author='" + author + "' AND aboutProduct='" + productName + "'");
            if (res.next()) {
                req.setAttribute("inf", "update");
                stmt.executeUpdate("UPDATE comment SET date='" + date + "',time='" + time + "', comment='" + comment + "',evaluation='" + rate + "' WHERE author='" + author + "' AND aboutProduct='" + productName + "'");
            } else {
                String query = "INSERT INTO comment (author , date,time,comment,aboutProduct,evaluation) VALUES('" + author + "', '" + date + "','" + time + "','" + comment + "','" + productName + "','" + rate + "' )";
                stmt.executeUpdate(query);
                req.setAttribute("inf", "added");
            }
            if (!rate.equals("no rating")) {
                ResultSet res1 = stmt.executeQuery("SELECT * FROM comment WHERE aboutProduct='" + productName + "'");
                while (res1.next()) {
                    votesNumber++;
                    if (!res1.getString(7).equals("no rate")) {
                        term += Integer.parseInt(res1.getString(7));
                    }
                }
                average = term / votesNumber;
                stmt.executeUpdate("UPDATE  menu SET average='" + average + "',votesNumber ='" + votesNumber + "' WHERE product='" + productName + "'");
            }
            connection.close();
            return getComment(req);
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
