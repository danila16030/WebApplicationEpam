package com.epam.servlets.controller;

import com.epam.servlets.checker.RedirectCheck;
import com.epam.servlets.service.Service;
import com.epam.servlets.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class Controller extends HttpServlet {
    private static String com;
    private Service service = new Service();
    private RedirectCheck redirectCheck = new RedirectCheck();
    private static final Logger logger = LogManager.getLogger(Controller.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("move") != null || request.getServletPath().equals("/user")) {
            processRequest(request, response);
        } else {
            com = request.getServletPath();
            com = com.substring(1);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(com + ".jsp");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws
            IOException, ServletException {
        String page;
        com = req.getServletPath();
        com = com.substring(1);
        try {
            page = service.execute(req, com);
            if (redirectCheck.needRedirect(page)) {
                resp.sendRedirect(page);
                return;
            }
            req.getSession().setAttribute("inf", "");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(page + ".jsp");
            requestDispatcher.forward(req, resp);
        } catch (ServiceException e) {
            logger.error(e);
            resp.sendRedirect("errorPage");
            System.out.println(e);
        }
    }


}