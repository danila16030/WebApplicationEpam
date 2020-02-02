package com.epam.servlets.service.impl;

import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.MenuDAO;
import com.epam.servlets.entities.Product;
import com.epam.servlets.service.Command;
import com.epam.servlets.service.CommandException;
import com.epam.servlets.service.factory.CommandEnum;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;
import java.util.ArrayList;

public class FirstCourseCommand implements Command {
    private MenuDAO menuDAO = DAOFactory.getInstance().getSqlMenuDAO();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        if (req.getParameter("order") != null) {
            Command command = CommandEnum.getCurrentCommand("orderPage");
            return command.execute(req);
        }
        if (req.getParameter("com") != null) {
            Command command = CommandEnum.getCurrentCommand("comments");
            return command.execute(req);
        }
        ArrayList<Product> listResults;
        try {
            listResults = menuDAO.getProductList("firstCourse");
        } catch (DAOException e) {
            throw new CommandException("Error in DAO", e);
        }
        req.setAttribute("listResults", listResults);
        return "firstCourse";
    }
}




