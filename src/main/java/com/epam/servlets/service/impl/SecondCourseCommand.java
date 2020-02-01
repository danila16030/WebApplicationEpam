package com.epam.servlets.service.impl;

import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.MenuDAO;
import com.epam.servlets.entities.Product;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class SecondCourseCommand implements Command {
    private MenuDAO menuDAO = DAOFactory.getInstance().getSqlMenuDAO();

    @Override
    public String execute(HttpServletRequest req) {
        ArrayList<Product> listResults;
        listResults = menuDAO.getProductList("secondCourse");
        req.setAttribute("listResults", listResults);
        return "secondCourse";
    }
}