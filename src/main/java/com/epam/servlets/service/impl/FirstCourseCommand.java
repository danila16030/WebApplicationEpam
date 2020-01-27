package com.epam.servlets.service.impl;

import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.MenuDAO;
import com.epam.servlets.entities.Product;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class FirstCourseCommand implements Command {
    private MenuDAO menuDAO = DAOFactory.getInstance().getSqlMenuDAO();

    @Override
    public String execute(HttpServletRequest req) {
        ArrayList<Product> listResults = new ArrayList();
        listResults = menuDAO.getProductList("firstCourse");
        req.setAttribute("listResults", listResults);
        return "firstCourse";
    }
}




