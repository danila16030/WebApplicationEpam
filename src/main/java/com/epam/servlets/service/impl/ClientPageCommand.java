package com.epam.servlets.service.impl;

import com.epam.servlets.entities.Client;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class ClientPageCommand implements Command {
    @Override
    public boolean execute(HttpServletRequest req) {
        if (req.getParameter("move").equals("logout")) {
            return logOut(req);
        }
        if (req.getParameter("move").equals("user")) {
            return userPage(req);
        }
        return false;
    }

    private boolean logOut(HttpServletRequest req) {
        String name = (String) req.getAttribute("user");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("UPDATE  users SET inSystem=false WHERE login='" + name + "'");
            connection.close();
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean userPage(HttpServletRequest req) {
        String name = (String) req.getAttribute("user");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM client WHERE login='" + name + "'");
            while (res.next()) {
                Client client = new Client(res.getString(1), res.getInt(2), res.getString(3), res.getBoolean(4));
                req.setAttribute("client", client);
                return true;
            }
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
