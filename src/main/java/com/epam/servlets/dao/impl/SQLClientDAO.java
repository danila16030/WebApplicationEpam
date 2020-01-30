package com.epam.servlets.dao.impl;

import com.epam.servlets.dao.ClientDAO;
import com.epam.servlets.dao.impl.util.ConverterFromResultSet;
import com.epam.servlets.dao.impl.util.auxiliary.ClientFields;
import com.epam.servlets.dao.pool.ConnectionPool;
import com.epam.servlets.dao.pool.ConnectionPoolException;
import com.epam.servlets.entities.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SQLClientDAO implements ClientDAO {

    private String sqlCreateNewClient = "INSERT INTO client (login) VALUES(?)";
    private String sqlChangePoints = "UPDATE  client SET loyaltyPoints=?,block=? WHERE login=?";
    private String sqlChangeBalance = "UPDATE  client SET balance=? WHERE login=?";
    private String sqlGetClient = "SELECT * FROM client WHERE login=?";
    private String sqlGetAllClient = "SELECT * FROM client";
    private String sqlChangeBalanceAndOrder = "UPDATE  client SET balance=?,loyaltyPoints=? WHERE login=?";
    private Map<String, PreparedStatement> preparedStatementMap;

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final ConverterFromResultSet converterFromResultSet = ConverterFromResultSet.getInstance();

    public SQLClientDAO() {
        preparedStatementMap = new HashMap<>();
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
        } catch (ConnectionPoolException e) {
            //    logger.error(e);
        }

        prepareStatement(connection, sqlCreateNewClient);
        prepareStatement(connection, sqlChangePoints);
        prepareStatement(connection, sqlChangeBalance);
        prepareStatement(connection, sqlGetClient);
        prepareStatement(connection, sqlGetAllClient);
        prepareStatement(connection, sqlChangeBalanceAndOrder);
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
    public void createNewClient(String login) {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlCreateNewClient);
            if (preparedStatement != null) {
                preparedStatement.setString(1, login);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changePoint(String point, int block, String name) {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlChangePoints);
            if (preparedStatement != null) {
                preparedStatement.setString(1, point);
                preparedStatement.setInt(2, block);
                preparedStatement.setString(3, name);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getBalance(String name) {
        int balance = 0;
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlGetClient);
            if (preparedStatement != null) {
                preparedStatement.setString(1, name);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    balance = resultSet.getInt(ClientFields.BALANCE.name());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    @Override
    public boolean isBlock(String name) {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlGetClient);
            if (preparedStatement != null) {
                preparedStatement.setString(1, name);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    if (resultSet.getBoolean(ClientFields.BLOCK.name())) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public int getPoint(String name) {
        int point = 0;
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlGetClient);
            if (preparedStatement != null) {
                preparedStatement.setString(1, name);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    point = resultSet.getInt(ClientFields.LOYALTYPOINTS.name());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return point;
    }

    @Override
    public void changeBalance(int balance, String name) {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlChangeBalance);
            if (preparedStatement != null) {
                preparedStatement.setInt(1, balance);
                preparedStatement.setString(2, name);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Client> getClientList(String name) {
        ArrayList<Client> resultList = new ArrayList<>();
        ResultSet resultSet;
        if (name.equals("")) {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlGetAllClient);
            try {
                resultSet = preparedStatement.executeQuery();
                resultList = converterFromResultSet.getClientList(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlGetClient);
            try {
                preparedStatement.setString(1, name);
                resultSet = preparedStatement.executeQuery();
                resultList = converterFromResultSet.getClientList(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    @Override
    public void changeBalanceAndPoints(int balance, int point, String name) {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlChangeBalanceAndOrder);
            if (preparedStatement != null) {
                preparedStatement.setInt(1, balance);
                preparedStatement.setInt(2, point);
                preparedStatement.setString(3, name);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
