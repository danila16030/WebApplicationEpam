package com.epam.servlets.command.impl;

import com.epam.servlets.dao.*;
import com.epam.servlets.entities.Product;
import com.epam.servlets.command.Command;
import com.epam.servlets.command.CommandException;
import com.epam.servlets.timer.MyTimer;

import javax.servlet.http.HttpServletRequest;
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
        String productId = req.getParameter("productId");
        try {
            product = menuDAO.getProductForOrder(productId);
        } catch (DAOException e) {
            throw new CommandException("Error in DAO", e);
        }

        req.getSession().setAttribute("product", product);
        return "orderPage";
    }

    private String orderADish(HttpServletRequest req) throws CommandException {
        String userName = (String) req.getAttribute("user");
        String productId = req.getParameter("productId");//везде где id исправить
        String productName=req.getParameter("productName");
        String card = req.getParameter("card");
        String time;
        int point;
        int clientBalance;
        int productCost;
        try {
            if (!clientDAO.isBlock(userName)) {
                clientBalance = clientDAO.getBalance(userName);
                point = clientDAO.getPoint(userName);
                time = menuDAO.getProductTime(productId);
                productCost = menuDAO.getProductCost(productId);
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
                        orderDAO.makeOrder(productName,productId, requestedTime, userName, card);
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
        } catch (DAOException e) {
            throw new CommandException("Error in DAO", e);
        }
    }
}
