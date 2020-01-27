package com.epam.servlets.service.impl;

import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.UserDAO;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterCommand implements Command {
    private UserDAO userDAO = DAOFactory.getInstance().getSqlUserDAO();

    @Override
    public String execute(HttpServletRequest req) {

        String name = req.getParameter("name");
        String password = req.getParameter("pass");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            if (userDAO.findUserByLogin(name)) {
                req.setAttribute("inf", "exist");
                return "register";
            }
            userDAO.creteNewUser(name, password);
            String query2 = "INSERT INTO client (login) VALUES('" + name + "')";
            stmt.executeUpdate(query2);
            connection.close();
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return "client";
    }
}
