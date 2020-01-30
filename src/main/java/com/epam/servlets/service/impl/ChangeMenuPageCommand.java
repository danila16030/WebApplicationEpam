package com.epam.servlets.service.impl;

import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.MenuDAO;
import com.epam.servlets.entities.Product;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ChangeMenuPageCommand implements Command {

    private MenuDAO menuDAO = DAOFactory.getInstance().getSqlMenuDAO();

    @Override
    public String execute(HttpServletRequest req) {


        if (req.getParameter("create") != null) {
            return createProduct(req);
        }
        if (req.getParameter("delete") != null) {
            return deleteProduct(req);
        }
        if (req.getParameter("time") != null) {
            return updateProduct(req);
        } else {
            return getProduct(req);
        }
    }

    private String createProduct(HttpServletRequest req) {
        String tag = req.getParameter("tag");
        String productName = req.getParameter("product");
        String cost = req.getParameter("cost");
        String time = req.getParameter("time");
        String file = req.getParameter("file");
        return getProduct(req);
    }

    private String deleteProduct(HttpServletRequest req) {
        String productName = req.getParameter("product");
        menuDAO.deleteProduct(productName);
        return getProduct(req);
    }

    private String updateProduct(HttpServletRequest req) {
        String tag[] = req.getParameterValues("tag");
        String[] productName = req.getParameterValues("product");
        String[] previousName = req.getParameterValues("previous");
        String[] cost = req.getParameterValues("cost");
        String[] time = req.getParameterValues("time");
        menuDAO.updateProduct(tag, productName, previousName, cost, time);
        return getProduct(req);
    }

    private String getProduct(HttpServletRequest req) {
        String productName = req.getParameter("product");
        ArrayList<Product> listResults = new ArrayList();
        List<String> tags = null;
        if (req.getParameter("tag") != null) {
            tags = Arrays.asList(req.getParameterValues("tag"));
            tags = tags.stream().distinct().collect(Collectors.toList());
        }
        if (tags != null) {
            listResults = menuDAO.getProductListForChange(tags);
        } else {
            if (menuDAO.findProductByName(productName)) {
                listResults.add(menuDAO.getProductForChange(productName));
            } else {
                req.setAttribute("inf", "not exist");
            }
        }
        req.setAttribute("listResults", listResults);
        return "changeMenu";
    }
}
