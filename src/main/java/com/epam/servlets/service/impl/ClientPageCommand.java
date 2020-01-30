package com.epam.servlets.service.impl;

import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.UserDAO;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;

public class ClientPageCommand implements Command {
    private UserDAO userDAO = DAOFactory.getInstance().getSqlUserDAO();

    @Override
    public String execute(HttpServletRequest req) {
        if (req.getParameter("move").equals("logout")) {
            return logOut(req);
        }
        return null;
    }

    private String logOut(HttpServletRequest req) {
        String name = (String) req.getAttribute("user");
        userDAO.logOut(name);
        return "/WebApplication_war_exploded";
    }
}
