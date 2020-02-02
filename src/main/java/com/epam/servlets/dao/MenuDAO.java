package com.epam.servlets.dao;

import com.epam.servlets.entities.Product;

import java.util.ArrayList;
import java.util.List;

public interface MenuDAO {
    void updateRate(double average, double votesNumber, String productName) throws DAOException;

    ArrayList<Product> getProductList(String tag) throws DAOException;

    ArrayList<Product> getProductListForChange(List<String> tag) throws DAOException;

    Product getProductForComment(String productName) throws DAOException;

    Product getProductForOrder(String productName) throws DAOException;

    String getProductTime(String productName) throws DAOException;

    int getProductCost(String productName) throws DAOException;

    void deleteProduct(String productName) throws DAOException;

    void updateProduct(String tag[], String[] productName, String[] previousName, String[] cost, String[] time) throws DAOException;

    Product getProductForChange(String productName) throws DAOException;

    boolean findProductByName(String productName) throws DAOException;
}
