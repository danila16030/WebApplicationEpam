package com.epam.servlets.command.impl;

import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.UserDAO;
import com.epam.servlets.command.Command;
import com.epam.servlets.command.CommandException;

import javax.servlet.http.HttpServletRequest;

public class ClientPageCommand implements Command {
    private UserDAO userDAO = DAOFactory.getInstance().getSqlUserDAO();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        if (req.getParameter("move") != null && req.getParameter("move").equals("logout")) {
            return logOut(req);
        }
        return "index";
    }

    private String logOut(HttpServletRequest req) throws CommandException {
        String name = (String) req.getAttribute("user");
        try {
            userDAO.logOut(name);
            req.getSession().setAttribute("user", "");
        } catch (DAOException e) {
            throw new CommandException("Error in DAO", e);
        }
        return "index";
    }
}
