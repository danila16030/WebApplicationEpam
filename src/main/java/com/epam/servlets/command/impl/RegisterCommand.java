package com.epam.servlets.command.impl;

import com.epam.servlets.dao.ClientDAO;
import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.UserDAO;
import com.epam.servlets.command.Command;
import com.epam.servlets.command.CommandException;

import javax.servlet.http.HttpServletRequest;

public class RegisterCommand implements Command {
    private UserDAO userDAO = DAOFactory.getInstance().getSqlUserDAO();
    private ClientDAO clientDAO = DAOFactory.getInstance().getSqlClientDAO();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        String name = req.getParameter("name");
        String password = req.getParameter("pass");
        try {
            if (userDAO.findUserByLogin(name)) {
                req.getSession().setAttribute("inf", "exist");
                return "register";
            }
            userDAO.creteNewUser(name, password);
            clientDAO.createNewClient(name);
        } catch (DAOException e) {
            throw new CommandException("Error in DAO", e);
        }
        return "client";
    }
}
