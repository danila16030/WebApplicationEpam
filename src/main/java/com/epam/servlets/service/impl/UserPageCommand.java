package com.epam.servlets.service.impl;

import com.epam.servlets.dao.ClientDAO;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.MenuDAO;
import com.epam.servlets.entities.Client;
import com.epam.servlets.entities.Product;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class UserPageCommand implements Command {
    private MenuDAO menuDAO = DAOFactory.getInstance().getSqlMenuDAO();
    private ClientDAO clientDAO = DAOFactory.getInstance().getSqlClientDAO();

    @Override
    public String execute(HttpServletRequest req) {
        String name = (String) req.getAttribute("user");
        String[] list;
         ArrayList<Product> orderList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
            Statement stmt = connection.createStatement();
            String time;
            String orderTimeS;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            ResultSet res = stmt.executeQuery("SELECT * FROM client WHERE login='" + name + "'");
            if (res.next()) {
                Client client = new Client();
                client.setLogin(res.getString(1));
                client.setLoyaltyPoints(res.getInt(2));
                client.setBalance(res.getInt(6));
                String orderString = res.getString(3);
                if (orderString != null) {
                    list = orderString.split(" ");
                    for (int i = 1; i <= list.length - 1; i += 2) {
                        if (menuDAO.findProductByName(list[i])) {
                            String requestedTime = list[i + 1];
                            LocalTime orderTime = LocalTime.parse(requestedTime, formatter);
                            orderTimeS = orderTime.format(formatter);
                            LocalTime now = LocalTime.now();
                            if (now.isBefore(orderTime)) {
                                orderTime = orderTime.minus(now.getHour(), ChronoUnit.HOURS);
                                orderTime = orderTime.minus(now.getMinute(), ChronoUnit.MINUTES);
                                time = orderTime.format(formatter);
                                orderList.add(new Product(list[i], orderTimeS, time));
                            } else {
                                orderTime = orderTime.plus(1, ChronoUnit.HOURS);
                                if (orderTime.isBefore(now)) {
                                    removeOrder(i, list, name, client.getLoyaltyPoints());
                                } else {
                                    orderList.add(new Product(list[i], orderTimeS, "Ready"));
                                }
                            }
                        }
                    }
                    client.setOrderList(orderList);
                }
                client.setBlock(res.getBoolean(4));
                req.setAttribute("client", client);
                connection.close();
                return "user";
            }
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private void removeOrder(int position, String[] list, String name, int points) {
        String order = "";
        for (int i = 1; i < list.length; i++) {
            if (i == position) {
                i += 1;
            } else {
                order = order + " " + list[i];
            }
        }
        points = position - 10;
        if (points < 0) {
            clientDAO.removeOrder(order, points, name, 1);
        } else {
            clientDAO.removeOrder(order, points, name, 0);
        }
    }
}
