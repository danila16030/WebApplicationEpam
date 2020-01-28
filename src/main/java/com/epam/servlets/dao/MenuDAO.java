package com.epam.servlets.dao;

import com.epam.servlets.entities.Product;

import java.util.ArrayList;
import java.util.List;

public interface MenuDAO {
    void updateRate(double average, double votesNumber, String productName);

    ArrayList<Product> getProductList(String tag);

    ArrayList<Product> getProductListForChange(List<String> tag);

    Product getProductForComment(String productName);

    Product getProductForOrder(String productName);

    String getProductTime(String productName);

    int getProductCost(String productName);

    void deleteProduct(String productName);

    void updateProduct(String tag[], String[] productName, String[] previousName, String[] cost, String[] time);

    Product getProductForChange(String productName);

    boolean findProductByName(String productName);
}
