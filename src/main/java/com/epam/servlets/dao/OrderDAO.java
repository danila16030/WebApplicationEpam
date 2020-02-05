package com.epam.servlets.dao;

import com.epam.servlets.entities.Order;

import java.util.ArrayList;

public interface OrderDAO {
    void makeOrder(String productName,String productId, String orderTime, String customer, String paymentMethod) throws DAOException;

    void removeOrder(String product, String time,String customer) throws DAOException;

    ArrayList<Order> getClientOrder(String customer) throws DAOException;

    ArrayList<Order> getAllOrder() throws DAOException;
}
