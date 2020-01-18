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
        } else {
            s = request.getServletPath();
            s = s.substring(1);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(s + ".jsp");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String page;
        s = req.getServletPath();
        s = s.substring(1);
        Command command = CommandEnum.getCurrentCommand(s);
        page = command.execute(req);
        if (page.equals("client") || page.equals("/WebApplication_war_exploded") ) {
            resp.sendRedirect(page);
            return;
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(page + ".jsp");
        requestDispatcher.forward(req, resp);
    }


}