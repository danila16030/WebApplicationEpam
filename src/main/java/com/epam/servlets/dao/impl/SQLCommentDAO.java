package com.epam.servlets.dao.impl;

import com.epam.servlets.dao.CommentDAO;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.MenuDAO;
import com.epam.servlets.dao.impl.util.ConverterFromResultSet;
import com.epam.servlets.dao.impl.util.auxiliary.CommentFields;
import com.epam.servlets.dao.pool.ConnectionPool;
import com.epam.servlets.dao.pool.ConnectionPoolException;
import com.epam.servlets.entities.Comment;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SQLCommentDAO implements CommentDAO {
    private String sqlFindCommentsAboutProduct = "SELECT * FROM comment WHERE aboutProduct=?";
    private String sqlFindCommentByProductNameAndAuthor = "SELECT * FROM comment WHERE author=? AND aboutProduct=?";
    private String sqlUpdateComment = "UPDATE comment SET date=?,time=?, comment=?,evaluation=? WHERE author=? AND aboutProduct=?";
    private String sqlCreateNewComment = "INSERT INTO comment (author , date,time,comment,aboutProduct,evaluation) VALUES(?, ?,?,?,?,? )";
    private Map<String, PreparedStatement> preparedStatementMap;

    private static final ConverterFromResultSet converterFromResultSet = ConverterFromResultSet.getInstance();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();


    public SQLCommentDAO() {
        preparedStatementMap = new HashMap<>();
        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();
        } catch (ConnectionPoolException e) {
            //    logger.error(e);
        }

        prepareStatement(connection, sqlFindCommentsAboutProduct);
        prepareStatement(connection, sqlFindCommentByProductNameAndAuthor);
        prepareStatement(connection, sqlUpdateComment);
        prepareStatement(connection, sqlCreateNewComment);

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
    public boolean findCommentAboutProductByAuthor(String author, String productName) {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlFindCommentByProductNameAndAuthor);
            if (preparedStatement != null) {
                preparedStatement.setString(1, author);
                preparedStatement.setString(2, productName);
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
    public void updateComment(LocalDate date, String productName, String time, String comment, String rate, String author) {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlUpdateComment);
            if (preparedStatement != null) {
                preparedStatement.setDate(1, Date.valueOf(date));
                preparedStatement.setString(2, time);
                preparedStatement.setString(3, comment);
                preparedStatement.setString(4, rate);
                preparedStatement.setString(5, author);
                preparedStatement.setString(6, productName);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createComment(LocalDate date, String productName, String time, String comment, String rate, String author) {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlCreateNewComment);
            if (preparedStatement != null) {
                preparedStatement.setString(1, author);
                preparedStatement.setDate(2, Date.valueOf(date));
                preparedStatement.setString(3, time);
                preparedStatement.setString(4, comment);
                preparedStatement.setString(5, productName);
                preparedStatement.setString(6, rate);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean findCommentAboutProduct(String productName) {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlFindCommentsAboutProduct);
            if (preparedStatement != null) {
                preparedStatement.setString(1, productName);
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
    public void updateRate(String productName) {
        ResultSet resultSet;
        double average = 0;
        double votesNumber = 0;
        double term = 0;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlFindCommentsAboutProduct);
            if (preparedStatement != null) {
                preparedStatement.setString(1, productName);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    if (!resultSet.getString(CommentFields.EVALUATION.name()).equals("no rating")) {
                        votesNumber++;
                        term += Double.parseDouble(resultSet.getString(CommentFields.EVALUATION.name()));
                    }
                }
                average = term / votesNumber;
            }
            MenuDAO menuDAO = DAOFactory.getInstance().getSqlMenuDAO();
            menuDAO.updateRate(average, votesNumber, productName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Comment> getCommentsAboutProduct(String productName) {
        ArrayList<Comment> resultList = new ArrayList<>();
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlFindCommentsAboutProduct);
            if (preparedStatement != null) {
                preparedStatement.setString(1, productName);
                resultSet = preparedStatement.executeQuery();
                resultList = converterFromResultSet.getComments(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
