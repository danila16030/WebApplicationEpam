package com.epam.servlets.dao.impl;

import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.OrderDAO;
import com.epam.servlets.dao.impl.util.ConverterFromResultSet;
import com.epam.servlets.dao.pool.ConnectionPool;
import com.epam.servlets.dao.pool.PoolException;
import com.epam.servlets.entities.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SQLOrderDAO implements OrderDAO {

    private String sqlMakeOrder = "INSERT INTO  `order` (productId,orderTime,customer,paymentMethod,productName) VALUES(?,?,?,?,?)";
    private String sqlRemoveOrder = "DELETE FROM `order` WHERE product=? AND orderTime=? AND customer=? ";
    private String sqlGetCustomerOrder = "SELECT * FROM `order` WHERE customer=?";
    private String sqlGetAllOrder = "SELECT * FROM `order`";
    private Map<String, PreparedStatement> preparedStatementMap;

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final ConverterFromResultSet converterFromResultSet = ConverterFromResultSet.getInstance();


    public SQLOrderDAO() {
        preparedStatementMap = new HashMap<>();
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
        } catch (PoolException e) {
            //    logger.error(e);
        }

        prepareStatement(connection, sqlMakeOrder);
        prepareStatement(connection, sqlRemoveOrder);
        prepareStatement(connection, sqlGetCustomerOrder);
        prepareStatement(connection, sqlGetAllOrder);

        if (connection != null) {
            connectionPool.closeConnection(connection);
        }
    }


    private void prepareStatement(Connection connection, String sql) {
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatementMap.put(sql, preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void makeOrder(String productName ,String productId, String orderTime, String customer,String paymentMethod) throws DAOException {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlMakeOrder);
            if (preparedStatement != null) {
                preparedStatement.setString(1, productId);
                preparedStatement.setString(2, orderTime);
                preparedStatement.setString(3, customer);
                preparedStatement.setString(4, paymentMethod);
                preparedStatement.setString(5, productName);
                preparedStatement.executeUpdate();
            }else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeOrder(String product, String time,String customer) throws DAOException {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlRemoveOrder);
            if (preparedStatement != null) {
                preparedStatement.setString(1, product);
                preparedStatement.setString(2, time);
                preparedStatement.setString(3, customer);
                preparedStatement.executeUpdate();
            }else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Order> getClientOrder(String customer) throws DAOException {
        ArrayList<Order> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlGetCustomerOrder);
            if (preparedStatement != null) {
                preparedStatement.setString(1, customer);
                resultSet = preparedStatement.executeQuery();
                list = converterFromResultSet.getOrderList(resultSet);
            }else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<Order> getAllOrder() throws DAOException {
        ArrayList<Order> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlGetAllOrder);
            if (preparedStatement != null) {
                resultSet = preparedStatement.executeQuery();
                list = converterFromResultSet.getOrderList(resultSet);
            }else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
