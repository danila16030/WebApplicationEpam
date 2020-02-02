package com.epam.servlets.service.impl;

import com.epam.servlets.dao.ClientDAO;
import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.service.Command;
import com.epam.servlets.service.CommandException;

import javax.servlet.http.HttpServletRequest;

public class BalancePageCommand implements Command {
    private ClientDAO clientDAO = DAOFactory.getInstance().getSqlClientDAO();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        try {
            String name = (String) req.getAttribute("user");
            String amount = req.getParameter("money");
            int balance = clientDAO.getBalance(name);
            balance = balance + Integer.parseInt(amount);
            clientDAO.changeBalance(balance, name);
            req.getSession().setAttribute("inf", "cool");
        } catch (DAOException e) {
            throw new CommandException("Error in DAO", e);
        }
        return "balance";
    }
}
