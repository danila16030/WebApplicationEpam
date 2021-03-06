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
    private int page;

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
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        } else {
            page = 0;
        }

        int listNumber = (int) Math.ceil(clientList.size() / 10.0);
        ArrayList pages = new ArrayList();
        for (int i = 0; i < listNumber; i++) {
            pages.add(i);
        }
        if (page > 0) {
            clientList = new ArrayList<>(clientList.subList(page * 10 - 1, page + 10));
        } else {
            if (clientList.size() >10) {
                clientList = new ArrayList<>(clientList.subList(page * 10, page + 10));
            }
        }
        req.getSession().setAttribute("pagesP", pages);
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
        return getClients(req);
    }
}
