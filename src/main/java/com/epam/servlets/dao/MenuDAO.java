package com.epam.servlets.dao;

import com.epam.servlets.entities.Product;

import java.util.ArrayList;

public interface MenuDAO {
    void updateRate(double average, double votesNumber, String productName);

    ArrayList<Product> getProductList(String tag);

    Product getProduct(String productName);
}
