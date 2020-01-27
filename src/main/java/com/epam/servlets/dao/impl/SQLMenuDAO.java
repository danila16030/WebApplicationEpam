package com.epam.servlets.dao.impl;

import com.epam.servlets.dao.MenuDAO;
import com.epam.servlets.dao.impl.util.ConverterFromResultSet;
import com.epam.servlets.entities.Product;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SQLMenuDAO implements MenuDAO {
    private String sqlUpdateRate = "UPDATE  menu SET average=?,votesNumber =? WHERE product=?";
    private String sqlGetProductByTag = "SELECT * FROM menu WHERE tag=?";
    private String getSqlGetProductByName = "SELECT * FROM menu WHERE product=?";
    private Map<String, PreparedStatement> preparedStatementMap;

    private static final ConverterFromResultSet converterFromResultSet = ConverterFromResultSet.getInstance();

    public SQLMenuDAO() {
        preparedStatementMap = new HashMap<>();
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe?serverTimezone=UTC", "root", "root");
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        prepareStatement(connection, sqlUpdateRate);
        prepareStatement(connection, sqlGetProductByTag);
        prepareStatement(connection, getSqlGetProductByName);
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
    public void updateRate(double average, double votesNumber, String productName) {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlUpdateRate);
            if (preparedStatement != null) {
                preparedStatement.setDouble(1, average);
                preparedStatement.setDouble(2, votesNumber);
                preparedStatement.setString(3, productName);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Product> getProductList(String tag) {
        ArrayList<Product> resultList = new ArrayList<>();
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlGetProductByTag);
            if (preparedStatement != null) {
                preparedStatement.setString(1, tag);
                resultSet = preparedStatement.executeQuery();
                resultList = converterFromResultSet.getProductList(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Product getProduct(String productName) {
        Product product = new Product();
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(getSqlGetProductByName);
            if (preparedStatement != null) {
                preparedStatement.setString(1, productName);
                resultSet = preparedStatement.executeQuery();
                product = converterFromResultSet.getProduct(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
}
