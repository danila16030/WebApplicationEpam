package com.epam.servlets.service.impl;

import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class RegisterCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        String name = req.getParameter("name");
        String password = req.getParameter("pass");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM users WHERE login='" + name + "' ");
            if (res.next()) {
                req.setAttribute("inf", "exist");
                return "register";
            }
            String query = "INSERT INTO users (login , password,inSystem) VALUES('" + name + "', '" + password + " ',true )";
            String query2 = "INSERT INTO client (login) VALUES('" + name + "')";
            stmt.executeUpdate(query);
            stmt.executeUpdate(query2);
            connection.close();
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return "client";
    }
}
