package com.epam.servlets.service.impl;

import com.epam.servlets.dao.*;
import com.epam.servlets.entities.Product;
import com.epam.servlets.service.Command;
import com.epam.servlets.service.CommandException;
import com.epam.servlets.timer.MyTimer;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class OrderPageCommand implements Command {
    private MenuDAO menuDAO = DAOFactory.getInstance().getSqlMenuDAO();
    private ClientDAO clientDAO = DAOFactory.getInstance().getSqlClientDAO();
    private OrderDAO orderDAO = DAOFactory.getInstance().getSqlOrderDAO();
    private MyTimer myTimer = MyTimer.getInstance();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        if (req.getParameter("time") != null) {
            return orderADish(req);
        } else {
            return getProductForOrderPage(req);
        }
    }

    private String getProductForOrderPage(HttpServletRequest req) throws CommandException {
        Product product;
        String productName = req.getParameter("order");
        if (productName == null) {
            productName = req.getParameter("product");
        } else {
            productName = productName.substring(15);

        }
        try {
            product = menuDAO.getProductForOrder(productName);
        } catch (DAOException e) {
            throw new CommandException("Error in DAO", e);
        }

        req.getSession().setAttribute("product", product);
        return "orderPage";
    }

    private String orderADish(HttpServletRequest req) throws CommandException {
        String userName = (String) req.getAttribute("user");
        String product = req.getParameter("product");
        String card = req.getParameter("card");
        String time;
        int point;
        int clientBalance;
        int productCost;
        try {
            if (!clientDAO.isBlock(userName)) {
                clientBalance = clientDAO.getBalance(userName);
                point = clientDAO.getPoint(userName);
                String productName = product.trim();
                time = menuDAO.getProductTime(productName);
                productCost = menuDAO.getProductCost(productName);
                LocalTime now = LocalTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime end = LocalTime.parse(time, formatter);
                end = end.plus(now.getHour(), ChronoUnit.HOURS);
                end = end.plus(now.getMinute(), ChronoUnit.MINUTES);
                String requestedTime = req.getParameter("time");
                LocalTime orderTime = LocalTime.parse(requestedTime, formatter);
                if (clientBalance > productCost || card == null) {
                    if (end.isBefore(orderTime) && now.isBefore(end)) {
                        if (card != null) {
                            clientBalance = clientBalance - productCost;
                            point += 1;
                        } else {
                            card = "cash";
                        }
                        orderDAO.makeOrder(product, requestedTime, userName, card);
                        clientDAO.changeBalanceAndPoints(clientBalance, point, userName);
                        myTimer.orderTimer();
                        req.getSession().setAttribute("inf", "cool");
                        return getProductForOrderPage(req);
                    } else {
                        req.getSession().setAttribute("inf", "time");
                        return getProductForOrderPage(req);
                    }
                } else {
                    req.getSession().setAttribute("inf", "money");
                    return getProductForOrderPage(req);
                }
            } else {
                req.getSession().setAttribute("inf", "block");
                return getProductForOrderPage(req);
            }
        }catch (DAOException e){
            throw new CommandException("Error in DAO", e);
        }
    }
}
