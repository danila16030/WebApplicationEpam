package com.epam.servlets.service.impl;

import com.epam.servlets.entities.Client;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;

public class ChangePointPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        if (req.getParameter("points") == null) {
            return getClients(req);
        } else {
            return setPoints(req);
        }
    }

    private String getClients(HttpServletRequest req) {
        String userName = req.getParameter("username");
        ArrayList<Client> listResults = new ArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            if (userName != null) {
                if (userName.equals("")) {
                    ResultSet res = stmt.executeQuery("SELECT * FROM client");
                    while (res.next()) {
                        Client client = new Client();
                        client.setLogin(res.getString(1));
                        client.setLoyaltyPoints(res.getInt(2));
                        listResults.add(client);
                    }
                } else {
                    ResultSet res = stmt.executeQuery("SELECT * FROM client WHERE login='" + userName + "'");
                    if (res.next()) {
                        Client client = new Client();
                        client.setLogin(res.getString(1));
                        client.setLoyaltyPoints(res.getInt(2));
                        listResults.add(client);
                    } else {
                        req.setAttribute("inf", "not exist");
                    }
                }
                req.setAttribute("listResults", listResults);
            }
            return "changePoints";
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String setPoints(HttpServletRequest req) {
        String userName = req.getParameter("user");
        long points = Long.parseLong(req.getParameter("points"));
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("UPDATE  client SET loyaltyPoints='" + points + "' WHERE login='" + userName + "'");
            return "changePoints";
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
