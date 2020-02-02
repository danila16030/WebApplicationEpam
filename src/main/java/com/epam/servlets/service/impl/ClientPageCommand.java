package com.epam.servlets.service.impl;

import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.UserDAO;
import com.epam.servlets.service.Command;
import com.epam.servlets.service.CommandException;

import javax.servlet.http.HttpServletRequest;

public class ClientPageCommand implements Command {
    private UserDAO userDAO = DAOFactory.getInstance().getSqlUserDAO();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        if (req.getParameter("move").equals("logout")) {
            return logOut(req);
        }
        return null;
    }

    private String logOut(HttpServletRequest req) throws CommandException {
        String name = (String) req.getAttribute("user");
        try {
            userDAO.logOut(name);
        } catch (DAOException e) {
            throw new CommandException("Error in DAO", e);
        }
        return "/WebApplication_war_exploded";
    }
}
