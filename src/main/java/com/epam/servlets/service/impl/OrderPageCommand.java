package com.epam.servlets.service.impl;

import com.epam.servlets.dao.ClientDAO;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.MenuDAO;
import com.epam.servlets.dao.OrderDAO;
import com.epam.servlets.entities.Product;
import com.epam.servlets.service.Command;
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
    public String execute(HttpServletRequest req) {
        if (req.getParameter("time") != null) {
            return orderADish(req);
        } else {
            return getProductForOrderPage(req);
        }
    }

    private String getProductForOrderPage(HttpServletRequest req) {
        Product product;
        String productName = req.getParameter("product");
        product = menuDAO.getProductForOrder(productName);

        req.setAttribute("product", product);
        return "orderPage";
    }

    private String orderADish(HttpServletRequest req) {
        String userName = (String) req.getAttribute("user");
        String product = req.getParameter("product");
        String card = req.getParameter("card");
        String time;
        int point;
        int clientBalance;
        int productCost;
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
                    req.setAttribute("inf", "cool");
                    return getProductForOrderPage(req);
                } else {
                    req.setAttribute("inf", "time");
                    return getProductForOrderPage(req);
                }
            } else {
                req.setAttribute("inf", "money");
                return getProductForOrderPage(req);
            }
        } else {
            req.setAttribute("inf", "block");
            return getProductForOrderPage(req);
        }
    }
}
