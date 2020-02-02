package com.epam.servlets.dao.impl.util;

import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.impl.util.auxiliary.ClientFields;
import com.epam.servlets.dao.impl.util.auxiliary.CommentFields;
import com.epam.servlets.dao.impl.util.auxiliary.MenuFields;
import com.epam.servlets.dao.impl.util.auxiliary.OrderFields;
import com.epam.servlets.entities.Client;
import com.epam.servlets.entities.Comment;
import com.epam.servlets.entities.Order;
import com.epam.servlets.entities.Product;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

public class ConverterFromResultSet {
    private static final ConverterFromResultSet instance = new ConverterFromResultSet();

    private ConverterFromResultSet() {
    }

    public static ConverterFromResultSet getInstance() {
        return instance;
    }

    public ArrayList<Product> getProductList(ResultSet resultSet) throws DAOException {
        ArrayList<Product> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                list.add(createProductForComment(resultSet));
            }
        } catch (SQLException e) {
            // logger.error(e);
            throw new DAOException(e);
        }
        return list;
    }

    public ArrayList<Product> getProductListForChange(ResultSet resultSet) throws DAOException {
        ArrayList<Product> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                list.add(createProductForChange(resultSet));
            }
        } catch (SQLException e) {
            // logger.error(e);
            throw new DAOException(e);
        }
        return list;
    }

    private Product createProductForChange(ResultSet resultSet) throws DAOException {
        Product product;
        try {
            Blob image = resultSet.getBlob(MenuFields.EXEMPLUM.name());
            String name = resultSet.getString(MenuFields.PRODUCT.name());
            int cost = resultSet.getInt(MenuFields.COST.name());
            String cookingTime = resultSet.getString(MenuFields.COOKINGTIME.name());
            String tag = resultSet.getString(MenuFields.TAG.name());
            product = new Product(name, cost, cookingTime, Base64.getEncoder().encodeToString(image.getBytes(1,
                    (int) image.length())), tag);
        } catch (SQLException e) {
            // logger.error(e);
            throw new DAOException(e);
        }
        return product;
    }

    private Product createProductForComment(ResultSet resultSet) throws DAOException {
        Product product;
        try {
            Blob image = resultSet.getBlob(MenuFields.EXEMPLUM.name());
            String name = resultSet.getString(MenuFields.PRODUCT.name());
            int cost = resultSet.getInt(MenuFields.COST.name());
            String cookingTime = resultSet.getString(MenuFields.COOKINGTIME.name());
            double averageScope = resultSet.getDouble(MenuFields.AVERAGE.name());
            int votersNumber = resultSet.getInt(MenuFields.VOTESNUMBER.name());
            product = new Product(name, cost, cookingTime, Base64.getEncoder().encodeToString(image.getBytes(1,
                    (int) image.length())), averageScope, votersNumber);
        } catch (SQLException e) {
            // logger.error(e);
            throw new DAOException(e);
        } return product;
    }

    public ArrayList<Client> getClientList(ResultSet resultSet) throws DAOException {
        ArrayList<Client> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String login = resultSet.getString(ClientFields.LOGIN.name());
                if (!login.equals("admin")) {
                    int point = resultSet.getInt(ClientFields.LOYALTYPOINTS.name());
                    boolean block = resultSet.getBoolean(ClientFields.BLOCK.name());
                    int balance = resultSet.getInt(ClientFields.BALANCE.name());
                    Client client = new Client();
                    client.setLogin(login);
                    client.setLoyaltyPoints(point);
                    client.setBlock(block);
                    client.setBalance(balance);
                    list.add(client);
                }
            }
        } catch (SQLException e) {
            // logger.error(e);
            throw new DAOException(e);
        }
        return list;
    }

    public Product getProductForChange(ResultSet resultSet) throws DAOException {
        Product product = new Product();
        try {
            if (resultSet.next()) {
                product = createProductForChange(resultSet);
            }
        } catch (SQLException e) {
            // logger.error(e);
            throw new DAOException(e);
        }
        return product;
    }

    public Product getProductForOrder(ResultSet resultSet) throws DAOException {
        Product product = new Product();
        try {
            if (resultSet.next()) {
                Blob image = resultSet.getBlob(MenuFields.EXEMPLUM.name());
                String name = resultSet.getString(MenuFields.PRODUCT.name());
                int cost = resultSet.getInt(MenuFields.COST.name());
                String cookingTime = resultSet.getString(MenuFields.COOKINGTIME.name());
                product = new Product(name, cost, cookingTime, Base64.getEncoder().encodeToString(image.getBytes(1,
                        (int) image.length())));
            }
        } catch (SQLException e) {
            // logger.error(e);
            throw new DAOException(e);
        }
        return product;
    }

    public Product getProductForComment(ResultSet resultSet) throws DAOException {
        Product product = new Product();
        try {
            if (resultSet.next()) {
                product = createProductForComment(resultSet);
            }
        } catch (SQLException e) {
            // logger.error(e);
            throw new DAOException(e);
        }
        return product;
    }


    public ArrayList<Comment> getComments(ResultSet resultSet) throws DAOException {
        ArrayList<Comment> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String author = resultSet.getString(CommentFields.AUTHOR.name());
                String date = resultSet.getString(CommentFields.DATE.name());
                String time = resultSet.getString(CommentFields.AUTHOR.name());
                String comment = resultSet.getString(CommentFields.COMMENT.name());
                String rate = resultSet.getString(CommentFields.EVALUATION.name());
                list.add(new Comment(author, date, time, comment, rate));
            }
        } catch (SQLException e) {
            // logger.error(e);
            throw new DAOException(e);
        }
        return list;
    }

    public ArrayList<Order> getOrderList(ResultSet resultSet) throws DAOException {
        ArrayList<Order> list = new ArrayList<>();
        String product;
        String time;
        String method;
        String customer;
        Order order;
        try {
            if (resultSet.next()) {
                product = resultSet.getString(OrderFields.PRODUCT.name());
                time = resultSet.getString(OrderFields.ORDERTIME.name());
                method = resultSet.getString(OrderFields.PAYMENTMETHOD.name());
                customer = resultSet.getString(OrderFields.CUSTOMER.name());
                order = new Order(product, time, method);
                order.setCustomer(customer);
                list.add(order);
                while (resultSet.next()) {
                    product = resultSet.getString(OrderFields.PRODUCT.name());
                    time = resultSet.getString(OrderFields.ORDERTIME.name());
                    method = resultSet.getString(OrderFields.PAYMENTMETHOD.name());
                    customer = resultSet.getString(OrderFields.CUSTOMER.name());
                    order = new Order(product, time, method);
                    order.setCustomer(customer);
                    list.add(order);
                }
            }
        } catch (SQLException e) {
            // logger.error(e);
            throw new DAOException(e);
        }
        return list;
    }
}
