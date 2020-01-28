package com.epam.servlets.service.impl;

import com.epam.servlets.dao.ClientDAO;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;

public class BalancePageCommand implements Command {
    private ClientDAO clientDAO = DAOFactory.getInstance().getSqlClientDAO();

    @Override
    public String execute(HttpServletRequest req) {
        String name = (String) req.getAttribute("user");
        String amount = req.getParameter("money");
        int balance = clientDAO.getBalance(name);
        balance = balance + Integer.parseInt(amount);
        clientDAO.changeBalance(balance, name);
        req.setAttribute("inf", "cool");
        return "balance";
    }
}
