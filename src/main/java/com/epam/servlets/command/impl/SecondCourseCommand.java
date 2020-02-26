package com.epam.servlets.command.impl;

import com.epam.servlets.command.factory.CommandEnum;
import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.MenuDAO;
import com.epam.servlets.entities.Product;
import com.epam.servlets.command.Command;
import com.epam.servlets.command.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class SecondCourseCommand implements Command {
    private MenuDAO menuDAO = DAOFactory.getInstance().getSqlMenuDAO();
    private ArrayList<Product> listResults;
    private int page;

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
        return getMenu(req);
    }

    private String getMenu(HttpServletRequest req) throws CommandException {
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        } else {
            page = 0;
        }
        try {
            listResults = menuDAO.getProductList("SecondCourse");
        } catch (DAOException e) {
            throw new CommandException("Error in DAO", e);
        }
        int listNumber = (int) Math.ceil(listResults.size() / 4.0);
        ArrayList pages = new ArrayList();
        for (int i = 0; i < listNumber; i++) {
            pages.add(i);
        }
        if (page > 0) {
            listResults = new ArrayList<>(listResults.subList(page * 5 - 1, page + 5));
        } else {
            if (listResults.size() > 5) {
                listResults = new ArrayList<>(listResults.subList(page * 5, page + 5));
            }
        }
        req.setAttribute("pages", pages);
        req.setAttribute("listResults", listResults);
        return "secondCourse";
    }
}
