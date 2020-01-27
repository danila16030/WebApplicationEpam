package com.epam.servlets.service.impl;

import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.UserDAO;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;

public class SingInCommand implements Command {
    private UserDAO userDAO = DAOFactory.getInstance().getSqlUserDAO();

    @Override
    public String execute(HttpServletRequest req) {
        String name = req.getParameter("name");
        String password = req.getParameter("pass");
        if (userDAO.findUserByLoginAndPassword(name, password)) {
            String result = userDAO.singInByLogin(name);
            if (result.equals("singIn")) {
                req.setAttribute("inf", "already");
            }
            return result;
        }
        req.setAttribute("inf", "wrong");
        return "singIn";
    }
}
