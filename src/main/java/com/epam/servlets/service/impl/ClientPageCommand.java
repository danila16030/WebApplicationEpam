package com.epam.servlets.service.impl;

import com.epam.servlets.entities.Client;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class ClientPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        if (req.getParameter("move").equals("logout")) {
            return logOut(req);
        }
        if (req.getParameter("move").equals("user")) {
            return userPage(req);
        }
        return null;
    }

    private String logOut(HttpServletRequest req) {
        String name = (String) req.getAttribute("user");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("UPDATE  users SET inSystem=false WHERE login='" + name + "'");
            connection.close();
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return "/WebApplication_war_exploded";
    }

    private String userPage(HttpServletRequest req) {
        String name = (String) req.getAttribute("user");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM client WHERE login='" + name + "'");
            while (res.next()) {
                Client client = new Client(res.getString(1), res.getInt(2), res.getString(3), res.getBoolean(4));
                req.setAttribute("client", client);
                return "user";
            }
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
