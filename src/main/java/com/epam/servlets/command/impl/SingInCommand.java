package com.epam.servlets.command.impl;

import com.epam.servlets.command.Command;
import com.epam.servlets.command.CommandException;
import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.UserDAO;
import com.epam.servlets.listener.LoginCollector;

import javax.servlet.http.HttpServletRequest;

public class SingInCommand implements Command {
    private LoginCollector loginCollector = LoginCollector.getInstance();
    private UserDAO userDAO = DAOFactory.getInstance().getSqlUserDAO();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        String name = req.getParameter("name");
        String password = req.getParameter("pass");
        try {
            if (userDAO.findUserByLoginAndPassword(name, password)) {
                String result = userDAO.singInByLogin(name);
                if (result.equals("singIn")) {
                    req.getSession().setAttribute("inf", "already");
                }
                if (result.equals("admin")) {
                    req.getSession().setAttribute("inf", "");
                    req.getSession().setAttribute("role", "admin");
                }
                loginCollector.addLogin(req.getSession().getId(), name);
                req.getSession().setAttribute("user",name);
                return result;
            }
        } catch (DAOException e) {
            throw new CommandException("Error in DAO", e);
        }
        req.getSession().setAttribute("inf", "wrong");
        return "singIn";
    }
}
