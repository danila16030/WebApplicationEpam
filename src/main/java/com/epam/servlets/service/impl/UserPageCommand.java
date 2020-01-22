package com.epam.servlets.service.impl;

import com.epam.servlets.entities.Client;
import com.epam.servlets.entities.Product;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
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
            ResultSet res = stmt.executeQuery("SELECT * FROM client WHERE login='" + name + "'");
            if (res.next()) {
                Client client = new Client();
                client.setLogin(res.getString(1));
                client.setLoyaltyPoints(res.getInt(2));
                client.setBlock(res.getBoolean(4));
                client.setBalance(res.getInt(6));
                String orderString = res.getString(3);
                if (orderString != null) {
                    list = orderString.split(" ");
                    for (int i = 1; i <= list.length - 1; i += 2) {
                        Statement stmt1 = connection.createStatement();
                        ResultSet res1 = stmt1.executeQuery("SELECT * FROM menu WHERE product='" + list[i] + "'");
                        if (res1.next()) {
                            orderList.add(new Product(res1.getString(1), res1.getInt(2), list[i + 1]));
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
