package com.epam.servlets.dao.impl.util;

import com.epam.servlets.dao.impl.util.auxiliary.CommentFields;
import com.epam.servlets.dao.impl.util.auxiliary.MenuFields;
import com.epam.servlets.entities.Comment;
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

    public ArrayList<Product> getProductList(ResultSet resultSet) throws SQLException {
        ArrayList<Product> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(createProduct(resultSet));
        }
        return list;
    }

    private Product createProduct(ResultSet resultSet) throws SQLException {
        Blob image = resultSet.getBlob(MenuFields.EXEMPLUM.name());
        String name = resultSet.getString(MenuFields.PRODUCT.name());
        int cost = resultSet.getInt(MenuFields.COST.name());
        String cookingTime = resultSet.getString(MenuFields.COOKINGTIME.name());
        double averageScope = resultSet.getDouble(MenuFields.AVERAGE.name());
        int votersNumber = resultSet.getInt(MenuFields.VOTESNUMBER.name());
        return new Product(name, cost, cookingTime, Base64.getEncoder().encodeToString(image.getBytes(1,
                (int) image.length())), averageScope, votersNumber);
    }

    public Product getProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        if (resultSet.next()) {

            product = createProduct(resultSet);
        }
        return product;
    }


    public ArrayList<Comment> getComents(ResultSet resultSet) throws SQLException {
        ArrayList<Comment> list = new ArrayList<>();
        while (resultSet.next()) {
            String author = resultSet.getString(CommentFields.AUTHOR.name());
            String date = resultSet.getString(CommentFields.DATE.name());
            String time = resultSet.getString(CommentFields.AUTHOR.name());
            String comment = resultSet.getString(CommentFields.COMMENT.name());
            String rate = resultSet.getString(CommentFields.EVALUATION.name());
            list.add(new Comment(author, date, time, comment, rate));
        }
        return list;
    }
}
