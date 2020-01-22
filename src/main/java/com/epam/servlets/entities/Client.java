package com.epam.servlets.entities;

import java.util.ArrayList;

public class Client {
    private String login;
    private int loyaltyPoints;
    private ArrayList<Product> orderList;
    private boolean block;
    private int id;
    private int balance;


    public Client() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (loyaltyPoints != client.loyaltyPoints) return false;
        if (block != client.block) return false;
        if (id != client.id) return false;
        if (balance != client.balance) return false;
        if (login != null ? !login.equals(client.login) : client.login != null) return false;
        return orderList != null ? orderList.equals(client.orderList) : client.orderList == null;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + loyaltyPoints;
        result = 31 * result + (orderList != null ? orderList.hashCode() : 0);
        result = 31 * result + (block ? 1 : 0);
        result = 31 * result + id;
        result = 31 * result + balance;
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "login='" + login + '\'' +
                ", loyaltyPoints=" + loyaltyPoints +
                ", orderList=" + orderList +
                ", block=" + block +
                ", id=" + id +
                ", balance=" + balance +
                '}';
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public ArrayList<Product> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Product> orderList) {
        this.orderList = orderList;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
