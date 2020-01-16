package com.epam.servlets.service.impl;

import com.epam.servlets.entities.Client;
import com.epam.servlets.entities.Dish;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        String[] list;
        ArrayList<Dish> orderList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM client WHERE login='" + name + "'");
            if (res.next()) {
                Client client = new Client();
                client.setLogin(res.getString(1));
                client.setLoyaltyPoints(res.getInt(2));
                client.setBlock(res.getBoolean(4));
                String orderString = res.getString(3);
                if (orderString != null) {
                    list = orderString.split(" ");
                    for (int i = 0; i <= list.length - 1; i++) {
                        Statement stmt1 = connection.createStatement();
                        ResultSet res1 = stmt1.executeQuery("SELECT * FROM menu WHERE dish='" + list[i] + "'");
                        if (res1.next()) {
                            orderList.add(new Dish(res1.getString(1), res1.getInt(2), res1.getTime(3), res1.getString(4)));
                        }
                    }
                    client.setOrderList(orderList);
                }
                req.setAttribute("client", client);
                connection.close();
                return "user";
            }
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
