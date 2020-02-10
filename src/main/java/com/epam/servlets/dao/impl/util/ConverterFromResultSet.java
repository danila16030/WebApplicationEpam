package com.epam.servlets.dao.impl.util;

import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.impl.util.fields.ClientFields;
import com.epam.servlets.dao.impl.util.fields.CommentFields;
import com.epam.servlets.dao.impl.util.fields.MenuFields;
import com.epam.servlets.dao.impl.util.fields.OrderFields;
import com.epam.servlets.entities.Client;
import com.epam.servlets.entities.Comment;
import com.epam.servlets.entities.Order;
import com.epam.servlets.entities.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConverterFromResultSet {
    private static final ConverterFromResultSet instance = new ConverterFromResultSet();
    private static final Logger logger = LogManager.getLogger(ConverterFromResultSet.class);

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
            logger.error(e);
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
            logger.error(e);
            throw new DAOException(e);
        }
        return list;
    }

    private Product createProductForChange(ResultSet resultSet) throws DAOException {
        Product product;
        try {
            String imagePath = resultSet.getString(MenuFields.EXEMPLUM);
            String name = resultSet.getString(MenuFields.PRODUCT);
            int cost = resultSet.getInt(MenuFields.COST);
            String cookingTime = resultSet.getString(MenuFields.COOKINGTIME);
            String tag = resultSet.getString(MenuFields.TAG);
            product = new Product(name, cost, cookingTime, imagePath, tag);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return product;
    }

    private Product createProductForComment(ResultSet resultSet) throws DAOException {
        Product product;
        try {
            String imagePath = resultSet.getString(MenuFields.EXEMPLUM);
            String name = resultSet.getString(MenuFields.PRODUCT);
            int cost = resultSet.getInt(MenuFields.COST);
            String cookingTime = resultSet.getString(MenuFields.COOKINGTIME);
            double averageScope = resultSet.getDouble(MenuFields.AVERAGE);
            int votersNumber = resultSet.getInt(MenuFields.VOTESNUMBER);
            int id = resultSet.getInt(MenuFields.ID);
            product = new Product(name, cost, cookingTime, imagePath, averageScope, votersNumber, id);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return product;
    }

    public ArrayList<Client> getClientList(ResultSet resultSet) throws DAOException {
        ArrayList<Client> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String login = resultSet.getString(ClientFields.LOGIN);
                if (!login.equals("admin")) {
                    int point = resultSet.getInt(ClientFields.LOYALTYPOINTS);
                    boolean block = resultSet.getBoolean(ClientFields.BLOCK);
                    int balance = resultSet.getInt(ClientFields.BALANCE);
                    Client client = new Client();
                    client.setLogin(login);
                    client.setLoyaltyPoints(point);
                    client.setBlock(block);
                    client.setBalance(balance);
                    list.add(client);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
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
            logger.error(e);
            throw new DAOException(e);
        }
        return product;
    }

    public Product getProductForOrder(ResultSet resultSet) throws DAOException {
        Product product = new Product();
        try {
            if (resultSet.next()) {
                String imagePath = resultSet.getString(MenuFields.EXEMPLUM);
                String name = resultSet.getString(MenuFields.PRODUCT);
                int cost = resultSet.getInt(MenuFields.COST);
                String cookingTime = resultSet.getString(MenuFields.COOKINGTIME);
                int id = resultSet.getInt(MenuFields.ID);
                product = new Product(name, cost, cookingTime, imagePath, id);
            }
        } catch (SQLException e) {
            logger.error(e);
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
             logger.error(e);
            throw new DAOException(e);
        }
        return product;
    }


    public ArrayList<Comment> getComments(ResultSet resultSet) throws DAOException {
        ArrayList<Comment> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String author = resultSet.getString(CommentFields.AUTHOR);
                String date = resultSet.getString(CommentFields.DATE);
                String time = resultSet.getString(CommentFields.AUTHOR);
                String comment = resultSet.getString(CommentFields.COMMENT);
                String rate = resultSet.getString(CommentFields.EVALUATION);
                list.add(new Comment(author, date, time, comment, rate));
            }
        } catch (SQLException e) {
             logger.error(e);
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
                product = resultSet.getString(OrderFields.PRODUCT);
                time = resultSet.getString(OrderFields.ORDERTIME);
                method = resultSet.getString(OrderFields.PAYMENTMETHOD);
                customer = resultSet.getString(OrderFields.CUSTOMER);
                order = new Order(product, time, method);
                order.setCustomer(customer);
                list.add(order);
                while (resultSet.next()) {
                    product = resultSet.getString(OrderFields.PRODUCT);
                    time = resultSet.getString(OrderFields.ORDERTIME);
                    method = resultSet.getString(OrderFields.PAYMENTMETHOD);
                    customer = resultSet.getString(OrderFields.CUSTOMER);
                    order = new Order(product, time, method);
                    order.setCustomer(customer);
                    list.add(order);
                }
            }
        } catch (SQLException e) {
             logger.error(e);
            throw new DAOException(e);
        }
        return list;
    }
}
