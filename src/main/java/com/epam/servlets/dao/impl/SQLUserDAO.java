package com.epam.servlets.dao.impl;

import com.epam.servlets.dao.UserDAO;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SQLUserDAO implements UserDAO {

    private String sqlFindUserByLoginAndPassword = "SELECT * FROM user WHERE login=? AND password=?";
    private String sqlSingINUser = "UPDATE  user SET inSystem=true WHERE login=?";
    private String sqlCreateNewUser = "INSERT INTO user (login , password,inSystem) VALUES(?,?,true)";
    private String sqlFindUserByLogin = "SELECT * FROM user WHERE login=?";
    private String sqlLogOut = "UPDATE  user SET inSystem=false WHERE login=?";
    private Map<String, PreparedStatement> preparedStatementMap;

    public SQLUserDAO() {
        preparedStatementMap = new HashMap<>();
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        prepareStatement(connection, sqlFindUserByLoginAndPassword);
        prepareStatement(connection, sqlSingINUser);
        prepareStatement(connection, sqlCreateNewUser);
        prepareStatement(connection, sqlFindUserByLogin);

      /*  if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
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
        ResultSet resultSet = null;
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
    public boolean findUserByLoginAndPassword(String login, String password) {
        return false;
    }

    @Override
    public void singInByLogin(String login) {

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
}
