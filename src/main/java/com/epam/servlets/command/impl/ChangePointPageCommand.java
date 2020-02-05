package com.epam.servlets.command.impl;

import com.epam.servlets.dao.ClientDAO;
import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.entities.Client;
import com.epam.servlets.command.Command;
import com.epam.servlets.command.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class ChangePointPageCommand implements Command {
    private ClientDAO clientDAO = DAOFactory.getInstance().getSqlClientDAO();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        if (req.getParameter("points") == null) {
            return getClients(req);
        } else {
            return setPoints(req);
        }
    }

    private String getClients(HttpServletRequest req) throws CommandException {
        String userName = req.getParameter("username");
        ArrayList<Client> clientList;
        try {
            clientList = clientDAO.getClientList(userName);
        } catch (DAOException e) {
            throw new CommandException("Error in DAO", e);
        }
        if (clientList.isEmpty()) {
            req.getSession().setAttribute("inf", "not exist");
        }
        req.getSession().setAttribute("clientList", clientList);
        return "changePoints";
    }

    private String setPoints(HttpServletRequest req) throws CommandException {
        String[] userName = req.getParameterValues("user");
        String[] points = req.getParameterValues("points");
        String[] blocks = req.getParameterValues("block");
        int block = 0;
        for (int i = 0; i < userName.length; i++) {
            if (blocks != null) {
                for (int k = 0; k < blocks.length; k++) {
                    if (blocks[k].equals(userName[i])) {
                        block = 1;
                        break;
                    }
                }
            }
            try {
                clientDAO.changePointAndBlock(points[i], block, userName[i]);
            } catch (DAOException e) {
                throw new CommandException("Error in DAO", e);
            }
            block = 0;
        }
        return "changePoints";
    }
}
