package com.epam.servlets.dao.impl.util;

import com.epam.servlets.dao.impl.util.auxiliary.ClientFields;
import com.epam.servlets.dao.impl.util.auxiliary.CommentFields;
import com.epam.servlets.dao.impl.util.auxiliary.MenuFields;
import com.epam.servlets.entities.Client;
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
            list.add(createProductForComment(resultSet));
        }
        return list;
    }

    public ArrayList<Product> getProductListForChange(ResultSet resultSet) throws SQLException {
        ArrayList<Product> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(createProductForChange(resultSet));
        }
        return list;
    }

    private Product createProductForChange(ResultSet resultSet) throws SQLException {
        Blob image = resultSet.getBlob(MenuFields.EXEMPLUM.name());
        String name = resultSet.getString(MenuFields.PRODUCT.name());
        int cost = resultSet.getInt(MenuFields.COST.name());
        String cookingTime = resultSet.getString(MenuFields.COOKINGTIME.name());
        String tag = resultSet.getString(MenuFields.TAG.name());
        return new Product(name, cost, cookingTime, Base64.getEncoder().encodeToString(image.getBytes(1,
                (int) image.length())), tag);
    }

    private Product createProductForComment(ResultSet resultSet) throws SQLException {
        Blob image = resultSet.getBlob(MenuFields.EXEMPLUM.name());
        String name = resultSet.getString(MenuFields.PRODUCT.name());
        int cost = resultSet.getInt(MenuFields.COST.name());
        String cookingTime = resultSet.getString(MenuFields.COOKINGTIME.name());
        double averageScope = resultSet.getDouble(MenuFields.AVERAGE.name());
        int votersNumber = resultSet.getInt(MenuFields.VOTESNUMBER.name());
        return new Product(name, cost, cookingTime, Base64.getEncoder().encodeToString(image.getBytes(1,
                (int) image.length())), averageScope, votersNumber);
    }

    public ArrayList<Client> getClientList(ResultSet resultSet) throws SQLException {
        ArrayList<Client> list = new ArrayList<>();
        while (resultSet.next()) {
            String login = resultSet.getString(ClientFields.LOGIN.name());
            if (!login.equals("admin")) {
                int point = resultSet.getInt(ClientFields.LOYALTYPOINTS.name());
                boolean block = resultSet.getBoolean(ClientFields.BLOCK.name());
                Client client = new Client();
                client.setLogin(login);
                client.setLoyaltyPoints(point);
                client.setBlock(block);
                list.add(client);
            }
        }
        return list;
    }

    public Product getProductForChange(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        if (resultSet.next()) {
            product = createProductForChange(resultSet);
        }
        return product;
    }

    public Product getProductForOrder(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        if (resultSet.next()) {
            product = createProductForChange(resultSet);
            Blob image = resultSet.getBlob(MenuFields.EXEMPLUM.name());
            String name = resultSet.getString(MenuFields.PRODUCT.name());
            int cost = resultSet.getInt(MenuFields.COST.name());
            String cookingTime = resultSet.getString(MenuFields.COOKINGTIME.name());
            product = new Product(name, cost, cookingTime, Base64.getEncoder().encodeToString(image.getBytes(1,
                    (int) image.length())));
        }
        return product;
    }

    public Product getProductForComment(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        if (resultSet.next()) {
            product = createProductForComment(resultSet);
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
