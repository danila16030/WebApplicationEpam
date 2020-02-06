package com.epam.servlets.command.impl;

import com.epam.servlets.command.Command;
import com.epam.servlets.command.CommandException;
import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class SingInCommand implements Command {
    private UserDAO userDAO = DAOFactory.getInstance().getSqlUserDAO();
    private static final Logger logger = LogManager.getLogger(SingInCommand.class);

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
                    req.getSession().setAttribute("role", "admin");
                }
                return result;
            }
        } catch (DAOException e) {
            throw new CommandException("Error in DAO", e);
        }
        req.getSession().setAttribute("inf", "wrong");
        return "singIn";
    }
}
