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
        String userName = req.getParameter("username");
        ArrayList<Client> listResults = new ArrayList();
        Client client = new Client();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            if (userName.equals("")) {
                ResultSet res = stmt.executeQuery("SELECT * FROM users");
                while (res.next()) {
                    client.setLogin(res.getString(1));
                    client.setLoyaltyPoints(res.getInt(2));
                    listResults.add(client);
                }
                req.setAttribute("listResult", listResults);
            } else {
                ResultSet res = stmt.executeQuery("SELECT * FROM users WHERE login='" + userName + "'");
                client.setLogin(res.getString(1));
                client.setLoyaltyPoints(res.getInt(2));
                listResults.add(client);
                req.setAttribute("listResult", listResults);
            }
            return "changePoint";
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
