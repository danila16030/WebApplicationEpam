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
import java.util.ArrayList;
import java.util.List;

public class Controller extends HttpServlet {
    String s;
    Boolean circle = false;

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
}