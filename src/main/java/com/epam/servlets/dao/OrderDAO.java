package com.epam.servlets.dao;

import com.epam.servlets.entities.Order;

import java.util.ArrayList;

public interface OrderDAO {
    void makeOrder(String product, String orderTime, String customer, String paymentMethod);

    void removeOrder(String product, String time);

    ArrayList<Order> getClientOrder(String customer);

    ArrayList<Order> getAllOrder();
}
