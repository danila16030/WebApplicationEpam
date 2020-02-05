package com.epam.servlets.command.impl;

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

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        ArrayList<Product> listResults;
        try {
            listResults = menuDAO.getProductList("secondCourse");
        } catch (DAOException e) {
            throw new CommandException("Error in DAO", e);
        }
        req.setAttribute("listResults", listResults);
        return "secondCourse";
    }
}
