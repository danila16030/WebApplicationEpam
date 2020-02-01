package com.epam.servlets.service.impl;

import com.epam.servlets.dao.ClientDAO;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.MenuDAO;
import com.epam.servlets.dao.OrderDAO;
import com.epam.servlets.entities.Client;
import com.epam.servlets.entities.Order;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class UserPageCommand implements Command {
    private MenuDAO menuDAO = DAOFactory.getInstance().getSqlMenuDAO();
    private ClientDAO clientDAO = DAOFactory.getInstance().getSqlClientDAO();
    private OrderDAO orderDAO = DAOFactory.getInstance().getSqlOrderDAO();

    @Override
    public String execute(HttpServletRequest req) {
        String name = (String) req.getAttribute("user");
        ArrayList<Order> orderList = new ArrayList<>();
        String time;
        Client client;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        ArrayList<Order> userOrder = orderDAO.getClientOrder(name);
        client = clientDAO.getClientList(name).get(0);
        if (!userOrder.isEmpty()) {
            for (Order order : userOrder) {
                if (menuDAO.findProductByName(order.getProductName())) {
                    String requestedTime = order.getTime();
                    LocalTime orderTime = LocalTime.parse(requestedTime, formatter);
                    LocalTime now = LocalTime.now();
                    if (now.isBefore(orderTime)) {
                        orderTime = orderTime.minus(now.getHour(), ChronoUnit.HOURS);
                        orderTime = orderTime.minus(now.getMinute(), ChronoUnit.MINUTES);
                        time = orderTime.format(formatter);
                        orderList.add(new Order(order.getProductName(), order.getTime(), time, order.getPaymentMethod()));
                    } else {
                        orderList.add(new Order(order.getProductName(), order.getTime(), "Ready", order.getPaymentMethod()));
                    }
                }
            }
            client.setOrderList(orderList);
        }
        req.setAttribute("client", client);
        return "user";
    }
}
