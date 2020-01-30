package com.epam.servlets.dao.impl;

import com.epam.servlets.dao.UserDAO;
import com.epam.servlets.dao.impl.util.auxiliary.UserFields;
import com.epam.servlets.dao.pool.ConnectionPool;
import com.epam.servlets.dao.pool.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SQLUserDAO implements UserDAO {

    private String sqlFindUserByLoginAndPassword = "SELECT * FROM user WHERE login=? AND password=?";
    private String sqlSingINUser = "UPDATE  user SET inSystem=true WHERE login=?";
    private String sqlCreateNewUser = "INSERT INTO user (login , password,inSystem) VALUES(?,?,true)";
    private String sqlFindUserByLogin = "SELECT * FROM user WHERE login=?";
    private String sqlLogOut = "UPDATE  user SET inSystem=false WHERE login=?";
    private String sqlInSystem = "UPDATE  user SET inSystem=true WHERE login=?";
    private Map<String, PreparedStatement> preparedStatementMap;

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();


    public SQLUserDAO() {
        preparedStatementMap = new HashMap<>();
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
        } catch (ConnectionPoolException e) {
            //    logger.error(e);
        }

        prepareStatement(connection, sqlFindUserByLoginAndPassword);
        prepareStatement(connection, sqlSingINUser);
        prepareStatement(connection, sqlCreateNewUser);
        prepareStatement(connection, sqlFindUserByLogin);
        prepareStatement(connection, sqlLogOut);
        prepareStatement(connection, sqlInSystem);

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
    public boolean findUserByLogin(String login) {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlFindUserByLogin);
            if (preparedStatement != null) {
                preparedStatement.setString(1, login);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String singInByLogin(String login) {
        ResultSet resultSet;
        String result;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlFindUserByLogin);
            if (preparedStatement != null) {
                preparedStatement.setString(1, login);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    if (!resultSet.getBoolean(UserFields.INSYSTEM.name())) {
                        result = resultSet.getString(UserFields.ROLE.name());
                        inSystem(login);
                        if (result.equals("admin")) {
                            return "admin";
                        }
                        return "client";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "singIn";
    }


    @Override
    public void creteNewUser(String login, String password) {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlCreateNewUser);
            if (preparedStatement != null) {
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void logOut(String login) {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlLogOut);
            if (preparedStatement != null) {
                preparedStatement.setString(1, login);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inSystem(String login) {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlInSystem);
            if (preparedStatement != null) {
                preparedStatement.setString(1, login);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean findUserByLoginAndPassword(String login, String password) {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlFindUserByLoginAndPassword);
            if (preparedStatement != null) {
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
