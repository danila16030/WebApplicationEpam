package com.epam.servlets.service.impl;

import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class BalancePageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        String name = (String) req.getAttribute("user");
        String amount = req.getParameter("money");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM client WHERE login='" + name + "' ");
            if (res.next()) {
                long balance = res.getInt(6);
                balance = Long.parseLong(balance + amount);
                stmt.executeUpdate("UPDATE  client SET balance=balance WHERE login='" + name + "'");
                req.setAttribute("inf", "cool");
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return "balance";
    }
}
