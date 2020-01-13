package com.epam.servlets.controller;


import com.epam.servlets.entities.Dish;
import com.epam.servlets.entities.User;
import com.epam.servlets.service.Command;
import com.epam.servlets.service.factory.CommandFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Controller extends HttpServlet {
    String s;
    Boolean circle = false;
    List<User> userList = new ArrayList<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        s = request.getServletPath();
        if (request.getParameterValues("value") != null && !circle) {
            circle = true;
            doPost(request, response);
        }
        s = s.substring(1);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(s + ".jsp");
        requestDispatcher.forward(request, response);
        circle = false;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command;
        s = req.getServletPath();
        s = s.substring(1);
        command = CommandFactory.getInstance().getCurrentCommand(s.toUpperCase());
        if (s.equals("singIn")) {
            if (command.execute(req)) {
                resp.sendRedirect("client");
            } else {
                doGet(req, resp);
            }
        }
        if (s.equals("client")) {
            command.execute(req);
            resp.sendRedirect("/WebApplication_war_exploded");
        }
        if (s.equals("register")) {
            if (command.execute(req)) {
                resp.sendRedirect("client");
            } else {
                doGet(req, resp);
            }
        }
        if (s.equals("firstCourse")) {
            command.execute(req);
            doGet(req, resp);
        }
    }

/*
    private void firstCourse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ArrayList<Dish> listResults = new ArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM menu WHERE tag='firstCourse' ");
            while (res.next()) {
                listResults.add(new Dish(res.getString(1), res.getInt(2), res.getTime(3), res.getString(4), res.getBlob(5)));
            }
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        req.setAttribute("listResults", listResults);
    }


    private boolean register(HttpServletRequest req) {
        String name = req.getParameter("name");
        String password = req.getParameter("pass");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM users WHERE login='" + name + "' ");
            if (res.next()) {
                req.setAttribute("inf", "exist");
                return false;
            }
            String query = "INSERT INTO cafe.users (login , password) VALUES('" + name + "', '" + password + "')";
            stmt.executeUpdate(query);
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void logOut(HttpServletRequest req) {
        String name = userList.get(0).getLogin();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("UPDATE  users SET inSystem=false WHERE login='" + name + "'");
            connection.close();
            req.setAttribute("inf", "out");
            userList.remove(0);
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private boolean singIn(HttpServletRequest req) {
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
                    userList.add(new User(res.getString(1), res.getString(2), res.getString(3)));
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
    */
}