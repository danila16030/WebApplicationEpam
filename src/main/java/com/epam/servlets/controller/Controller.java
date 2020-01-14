package com.epam.servlets.controller;

import com.epam.servlets.service.Command;
import com.epam.servlets.service.factory.CommandEnum;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    String s;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("move") != null) {
            processRequest(request, response);
        }

        doForward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        s = req.getServletPath();
        s = s.substring(1);
        Command command = CommandEnum.getCurrentCommand(s);
        if (s.equals("singIn")) {
            if (command.execute(req)) {
                resp.sendRedirect("client");
            } else {
                doForward(req, resp);
            }
        }
        if (s.equals("client")) {
            if (command.execute(req)) {
                if (req.getParameter("move").equals("user")) {
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("user.jsp");
                    requestDispatcher.forward(req, resp);
                } else {
                    resp.sendRedirect("/WebApplication_war_exploded");
                }
            }

        }
        if (s.equals("register")) {
            if (command.execute(req)) {
                resp.sendRedirect("client");
            } else {
                doForward(req, resp);
            }
        }
        if (s.equals("firstCourse")) {
            command.execute(req);
            doForward(req, resp);
        }
    }

    public void doForward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        s = request.getServletPath();
        s = s.substring(1);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(s + ".jsp");
        requestDispatcher.forward(request, response);
    }
}