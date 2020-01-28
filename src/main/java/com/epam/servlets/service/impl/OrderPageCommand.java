package com.epam.servlets.service.impl;

import com.epam.servlets.dao.ClientDAO;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.MenuDAO;
import com.epam.servlets.entities.Product;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class OrderPageCommand implements Command {
    private MenuDAO menuDAO = DAOFactory.getInstance().getSqlMenuDAO();
    private ClientDAO clientDAO = DAOFactory.getInstance().getSqlClientDAO();

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
        int clientBalance;
        int productCost;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM client WHERE login='" + userName + "' ");
            if (res.next()) {
                if (!res.getBoolean(4)) {
                    clientBalance = res.getInt(6);
                    String productName = product.trim();
                    time = menuDAO.getProductTime(productName);
                    productCost = menuDAO.getProductCost(productName);
                    String fullOrder = res.getString(3);
                    LocalTime now = LocalTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                    LocalTime end = LocalTime.parse(time, formatter);
                    end = end.plus(now.getHour(), ChronoUnit.HOURS);
                    end = end.plus(now.getMinute(), ChronoUnit.MINUTES);
                    String requestedTime = req.getParameter("time");
                    LocalTime orderTime = LocalTime.parse(requestedTime, formatter);
                    if (clientBalance > productCost || card == null) {
                        if (end.isBefore(orderTime)) {
                            if (fullOrder != null) {
                                fullOrder = fullOrder + " " + product + " " + orderTime;
                            } else {
                                fullOrder = " " + product + " " + orderTime;
                            }
                            clientBalance = clientBalance - productCost;
                            int point = res.getInt(2) + 1;
                            clientDAO.makeOrder(fullOrder, clientBalance, point, userName);
                            connection.close();
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
                }
            } else {
                req.setAttribute("inf", "block");
                return getProductForOrderPage(req);
            }
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | SQLException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
