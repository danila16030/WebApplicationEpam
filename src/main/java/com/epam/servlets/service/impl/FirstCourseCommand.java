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
    public boolean execute(HttpServletRequest req) {
        ArrayList<Dish> listResults = new ArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM menu WHERE tag='firstCourse' ");
            while (res.next()) {
                Blob image = res.getBlob(5);
                listResults.add(new Dish(res.getString(1), res.getInt(2), res.getTime(3), res.getString(4), Base64.getEncoder().encodeToString( image.getBytes(1, (int) image.length()))));
            }
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
        req.setAttribute("listResults", listResults);
        return true;
    }
}
