package com.epam.servlets.service.impl;

import com.epam.servlets.entities.Dish;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;

public class FirstCourseCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        if (req.getParameter("move").equals("0")) {
            return getProductList(req);
        } else {
            return orderADish(req);
        }
    }

    private String orderADish(HttpServletRequest req){
        String userName = (String) req.getAttribute("user");
        String product = req.getParameter("move");
        product=product.substring(14);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM client WHERE login='" + userName + "' ");
            if (res.next()) {
                String fullOrder=res.getString(3);
                fullOrder=fullOrder+product;
                stmt.executeUpdate("UPDATE  client SET `order`='" + fullOrder + "' WHERE login='" + userName + "'");
            }
            connection.close();
            return "firstCourse";
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
   }

    private String getProductList(HttpServletRequest req) {
        ArrayList<Dish> listResults = new ArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM menu WHERE tag='firstCourse' ");
            while (res.next()) {
                Blob image = res.getBlob(5);
                listResults.add(new Dish(res.getString(1), res.getInt(2), res.getTime(3), res.getString(4), Base64.getEncoder().encodeToString(image.getBytes(1, (int) image.length()))));
            }
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        req.setAttribute("listResults", listResults);
        return "firstCourse";
    }
}
