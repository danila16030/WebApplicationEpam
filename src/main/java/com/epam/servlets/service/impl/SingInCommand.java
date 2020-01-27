package com.epam.servlets.service.impl;

import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class SingInCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        String name = req.getParameter("name");
        String password = req.getParameter("pass");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM user WHERE login='" + name + "' AND password='" + password + "'");
            if (res.next()) {
                if (!res.getBoolean(3)) {
                    if (res.getString(4).equals("admin")) {
                        stmt.executeUpdate("UPDATE  user SET inSystem=true WHERE login='" + name + "'");
                        return "admin";
                    }
                    stmt.executeUpdate("UPDATE  user SET inSystem=true WHERE login='" + name + "'");
                     return "client";
                } else {
                    return "singIn";
                }
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        req.setAttribute("inf", "wrong");
        return "singIn";
    }
}
