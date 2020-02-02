package com.epam.servlets.dao.impl;

import com.epam.servlets.dao.ClientDAO;
import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.impl.util.ConverterFromResultSet;
import com.epam.servlets.dao.impl.util.auxiliary.ClientFields;
import com.epam.servlets.dao.pool.ConnectionPool;
import com.epam.servlets.dao.pool.PoolException;
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
        } catch (PoolException e) {
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
    public void createNewClient(String login) throws DAOException {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlCreateNewClient);
            if (preparedStatement != null) {
                preparedStatement.setString(1, login);
                preparedStatement.executeUpdate();
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changePointAndBlock(String point, int block, String name) throws DAOException {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlChangePoints);
            if (preparedStatement != null) {
                preparedStatement.setString(1, point);
                preparedStatement.setInt(2, block);
                preparedStatement.setString(3, name);
                preparedStatement.executeUpdate();
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getBalance(String name) throws DAOException {
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
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    @Override
    public boolean isBlock(String name) throws DAOException {
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
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public int getPoint(String name) throws DAOException {
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
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return point;
    }

    @Override
    public void changeBalance(int balance, String name) throws DAOException {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlChangeBalance);
            if (preparedStatement != null) {
                preparedStatement.setInt(1, balance);
                preparedStatement.setString(2, name);
                preparedStatement.executeUpdate();
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Client> getClientList(String name) throws DAOException {
        ArrayList<Client> resultList = new ArrayList<>();
        ResultSet resultSet;
        if (name.equals("")) {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlGetAllClient);
            if (preparedStatement != null) {
                try {
                    resultSet = preparedStatement.executeQuery();
                    resultList = converterFromResultSet.getClientList(resultSet);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } else {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlGetClient);
            if (preparedStatement != null) {
                try {
                    preparedStatement.setString(1, name);
                    resultSet = preparedStatement.executeQuery();
                    resultList = converterFromResultSet.getClientList(resultSet);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        }
        return resultList;
    }

    @Override
    public void changeBalanceAndPoints(int balance, int point, String name) throws DAOException {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlChangeBalanceAndOrder);
            if (preparedStatement != null) {
                preparedStatement.setInt(1, balance);
                preparedStatement.setInt(2, point);
                preparedStatement.setString(3, name);
                preparedStatement.executeUpdate();
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
