package com.epam.servlets.service.impl;

import com.epam.servlets.dao.ClientDAO;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.entities.Client;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class ChangePointPageCommand implements Command {
    private ClientDAO clientDAO = DAOFactory.getInstance().getSqlClientDAO();

    @Override
    public String execute(HttpServletRequest req) {
        if (req.getParameter("points") == null) {
            return getClients(req);
        } else {
            return setPoints(req);
        }
    }

    private String getClients(HttpServletRequest req) {
        String userName = req.getParameter("username");
        ArrayList<Client> listResults;
        listResults = clientDAO.getClientList(userName);
        if (listResults.isEmpty()) {
            req.setAttribute("inf", "not exist");
        }
        req.setAttribute("listResults", listResults);
        return "changePoints";
    }

    private String setPoints(HttpServletRequest req) {
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
            clientDAO.changePoint(points[i], block, userName[i]);
            block = 0;
        }
        return "changePoints";
    }
}
