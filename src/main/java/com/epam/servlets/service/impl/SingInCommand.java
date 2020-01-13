package com.epam.servlets.service.impl;

import com.epam.servlets.entities.User;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.List;

public class SingInCommand implements Command {
    @Override
    public boolean execute(HttpServletRequest req) {
        boolean connect = false;
        String name = req.getParameter("name");
        String password = req.getParameter("pass");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM users WHERE login='" + name + "' AND password='" + password + "'");
            if (res.next()) {
                connect = true;
                if (!res.getBoolean(3)) {
              //      userList.add(new User(res.getString(1), res.getString(2), res.getString(3)));
                    stmt.executeUpdate("UPDATE  users SET inSystem=true WHERE login='" + name + "'");
                    return true;
                } else {
                    req.setAttribute("inf", "already");
                }
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (!connect) {
            req.setAttribute("inf", "wrong");
        }
        return false;
    }
}
