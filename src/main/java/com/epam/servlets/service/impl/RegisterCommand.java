package com.epam.servlets.service.impl;

import com.epam.servlets.dao.ClientDAO;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.UserDAO;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;

public class RegisterCommand implements Command {
    private UserDAO userDAO = DAOFactory.getInstance().getSqlUserDAO();
    private ClientDAO clientDAO = DAOFactory.getInstance().getSqlClientDAO();

    @Override
    public String execute(HttpServletRequest req) {
        String name = req.getParameter("name");
        String password = req.getParameter("pass");
        if (userDAO.findUserByLogin(name)) {
            req.setAttribute("inf", "exist");
            return "register";
        }
        userDAO.creteNewUser(name, password);
        clientDAO.createNewClient(name);
        return "client";
    }
}
