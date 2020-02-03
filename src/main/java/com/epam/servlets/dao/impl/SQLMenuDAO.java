package com.epam.servlets.dao.impl;

import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.MenuDAO;
import com.epam.servlets.dao.impl.util.ConverterFromResultSet;
import com.epam.servlets.dao.impl.util.auxiliary.MenuFields;
import com.epam.servlets.dao.pool.ConnectionPool;
import com.epam.servlets.dao.pool.PoolException;
import com.epam.servlets.entities.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLMenuDAO implements MenuDAO {
    private String sqlUpdateRate = "UPDATE  menu SET average=?,votesNumber =? WHERE product=?";
    private String sqlGetProductByTag = "SELECT * FROM menu WHERE tag=?";
    private String sqlGetProductByName = "SELECT * FROM menu WHERE product=?";
    private String sqlFindProductByName = "SELECT * FROM menu WHERE product=?";
    private String sqlDeleteProduct = "DELETE FROM menu WHERE product=?";
    private String sqlUpdateProduct = "UPDATE  menu SET product=?,tag=?,cookingTime=?,cost=? WHERE product =?";
    private String sqlCreateNewProduct = "INSERT INTO menu (product, cost,cookingTime,tag,exemplum) VALUES(?,?,?,?,?)";
    private Map<String, PreparedStatement> preparedStatementMap;

    private static final ConverterFromResultSet converterFromResultSet = ConverterFromResultSet.getInstance();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();


    public SQLMenuDAO() {
        preparedStatementMap = new HashMap<>();
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
        } catch (PoolException e) {
            //    logger.error(e);
        }

        prepareStatement(connection, sqlUpdateRate);
        prepareStatement(connection, sqlGetProductByTag);
        prepareStatement(connection, sqlGetProductByName);
        prepareStatement(connection, sqlFindProductByName);
        prepareStatement(connection, sqlDeleteProduct);
        prepareStatement(connection, sqlUpdateProduct);
        prepareStatement(connection, sqlCreateNewProduct);

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
    public void updateRate(double average, double votesNumber, String productName) throws DAOException {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlUpdateRate);
            if (preparedStatement != null) {
                preparedStatement.setDouble(1, average);
                preparedStatement.setDouble(2, votesNumber);
                preparedStatement.setString(3, productName);
                preparedStatement.executeUpdate();
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Product> getProductList(String tag) throws DAOException {
        ArrayList<Product> resultList = new ArrayList<>();
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlGetProductByTag);
            if (preparedStatement != null) {
                preparedStatement.setString(1, tag);
                resultSet = preparedStatement.executeQuery();
                resultList = converterFromResultSet.getProductList(resultSet);
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public ArrayList<Product> getProductListForChange(List<String> tags) throws DAOException {
        ArrayList<Product> resultList = new ArrayList<>();
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlGetProductByTag);
            for (String tag : tags) {
                if (preparedStatement != null) {
                    preparedStatement.setString(1, tag);
                    resultSet = preparedStatement.executeQuery();
                    resultList.addAll(converterFromResultSet.getProductListForChange(resultSet));
                } else {
                    throw new DAOException("Couldn't find prepared statement");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Product getProductForComment(String productName) throws DAOException {
        Product product = new Product();
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlGetProductByName);
            if (preparedStatement != null) {
                preparedStatement.setString(1, productName);
                resultSet = preparedStatement.executeQuery();
                product = converterFromResultSet.getProductForComment(resultSet);
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public Product getProductForOrder(String productName) throws DAOException {
        Product product = new Product();
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlGetProductByName);
            if (preparedStatement != null) {
                preparedStatement.setString(1, productName);
                resultSet = preparedStatement.executeQuery();
                product = converterFromResultSet.getProductForOrder(resultSet);
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public String getProductTime(String productName) throws DAOException {
        String time = null;
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlGetProductByName);
            if (preparedStatement != null) {
                preparedStatement.setString(1, productName);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    time = resultSet.getString(MenuFields.COOKINGTIME.name());
                }
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return time;
    }

    @Override
    public int getProductCost(String productName) throws DAOException {
        int cost = 0;
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlGetProductByName);
            if (preparedStatement != null) {
                preparedStatement.setString(1, productName);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    cost = resultSet.getInt(MenuFields.COST.name());
                }
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cost;
    }

    @Override
    public void deleteProduct(String productName) throws DAOException {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlDeleteProduct);
            if (preparedStatement != null) {
                preparedStatement.setString(1, productName);
                preparedStatement.executeUpdate();
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProduct(String[] tag, String[] productName, String[] previousName, String[] cost, String[] time) throws DAOException {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlUpdateProduct);
            if (preparedStatement != null) {
                for (int i = 0; i < previousName.length; i++) {
                    preparedStatement.setString(1, productName[i]);
                    preparedStatement.setString(2, tag[i]);
                    preparedStatement.setString(3, time[i]);
                    preparedStatement.setString(4, cost[i]);
                    preparedStatement.setString(5, previousName[i]);
                    preparedStatement.executeUpdate();
                }
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product getProductForChange(String productName) throws DAOException {
        Product product = new Product();
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlGetProductByName);
            if (preparedStatement != null) {
                preparedStatement.setString(1, productName);
                resultSet = preparedStatement.executeQuery();
                product = converterFromResultSet.getProductForChange(resultSet);
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public boolean findProductByName(String productName) throws DAOException {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlFindProductByName);
            if (preparedStatement != null) {
                preparedStatement.setString(1, productName);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
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
    public void createNewProduct(String tag, String productName, String cost, String time) throws DAOException {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlCreateNewProduct);
            if (preparedStatement != null) {
                preparedStatement.setString(1, productName);
                preparedStatement.setString(2, cost);
                preparedStatement.setString(3, time);
                preparedStatement.setString(4, tag);
                preparedStatement.setString(5, "image/" + productName + ".jpg");
                preparedStatement.executeUpdate();
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
