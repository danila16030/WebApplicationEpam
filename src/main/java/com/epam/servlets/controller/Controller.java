package com.epam.servlets.controller;


import com.epam.servlets.entities.Dish;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;

public class Controller extends HttpServlet {
    String s;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        s = request.getServletPath();
        s = s.substring(1);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(s + ".jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        s = req.getServletPath();
        s = s.substring(1);
        if (s.equals("singIn")) {
            singIn(req);
        }
        if (s.equals("register")) {
            register(req);
        }
        if (s.equals("firstCourse")) {
            firstCourse(req,resp);
        }
        doGet(req, resp);
    }

    private void firstCourse(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        ArrayList<Dish> listResults = new ArrayList();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM menu WHERE tag='firstCourse' ");
            while (res.next()) {
                listResults.add(new Dish(res.getString(1), res.getInt(2), res.getTime(3), res.getString(4)));
            }
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        req.setAttribute("listResults", listResults);
    }

    private void register(HttpServletRequest req) {
        String name = req.getParameter("name");
        String password = req.getParameter("pass");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM users WHERE login='" + name + "' ");
            if (res.next()) {
                req.setAttribute("name", "exist");
                return;
            }
            String query = "INSERT INTO cafe.users (login , password) VALUES('" + name + "', '" + password + "')";
            stmt.executeUpdate(query);
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void singIn(HttpServletRequest req) {
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
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (!connect) {
            req.setAttribute("name", "wrong");
        }
    }
}