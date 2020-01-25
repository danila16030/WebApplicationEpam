package com.epam.servlets.service.impl;

import com.epam.servlets.entities.Client;
import com.epam.servlets.entities.Product;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class UserPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        String name = (String) req.getAttribute("user");
        String[] list;
        ArrayList<Product> orderList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            String time;
            String orderTimeS;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            ResultSet res = stmt.executeQuery("SELECT * FROM client WHERE login='" + name + "'");
            if (res.next()) {
                Client client = new Client();
                client.setLogin(res.getString(1));
                client.setLoyaltyPoints(res.getInt(2));
                client.setBalance(res.getInt(6));
                String orderString = res.getString(3);
                if (orderString != null) {
                    list = orderString.split(" ");
                    for (int i = 1; i <= list.length - 1; i += 2) {
                        Statement stmt1 = connection.createStatement();
                        ResultSet res1 = stmt1.executeQuery("SELECT * FROM menu WHERE product='" + list[i] + "'");
                        if (res1.next()) {
                            String requestedTime = list[i + 1];
                            LocalTime orderTime = LocalTime.parse(requestedTime, formatter);
                            orderTimeS = orderTime.format(formatter);
                            LocalTime now = LocalTime.now();
                            if (now.isBefore(orderTime)) {
                                orderTime = orderTime.minus(now.getHour(), ChronoUnit.HOURS);
                                orderTime = orderTime.minus(now.getMinute(), ChronoUnit.MINUTES);
                                time = orderTime.format(formatter);
                                orderList.add(new Product(res1.getString(1), orderTimeS, time));
                            } else {
                                orderTime = orderTime.plus(1, ChronoUnit.HOURS);
                                if (orderTime.isBefore(now)) {
                                    orderList.add(new Product(res1.getString(1), orderTimeS, "Ready"));
                                } else {
                                    removeOrder(i, list, name, client.getLoyaltyPoints());
                                }
                            }
                        }
                    }
                    client.setOrderList(orderList);
                }
                client.setBlock(res.getBoolean(4));
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

    private void removeOrder(int position, String[] list, String name, int points) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            String order = "";
            for (int i = 1; i < list.length; i++) {
                if (i == position) {
                    i += 1;
                } else {
                    order = order + list[i];
                }
            }
            points = position - 10;
            if (points < 0) {
                stmt.executeUpdate("UPDATE  client SET `order`='" + order + "',loyaltyPoints='" + points + "',block=1 WHERE login='" + name + "'");
            } else {
                stmt.executeUpdate("UPDATE  client SET `order`='" + order + "',loyaltyPoints='" + points + "',block=0 WHERE login='" + name + "'");
            }
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
