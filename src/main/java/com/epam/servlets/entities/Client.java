package com.epam.servlets.entities;

import java.util.ArrayList;

public class Client {
    private String login;
    private int loyaltyPoints;
    private ArrayList<Dish> orderList;
    private boolean block;
    private int id;

    public Client() {
    }

    public Client(String login, int loyaltyPoints, ArrayList<Dish> orderList, boolean block) {
        this.login = login;
        this.loyaltyPoints = loyaltyPoints;
        this.orderList = orderList;
        this.block = block;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (loyaltyPoints != client.loyaltyPoints) return false;
        if (block != client.block) return false;
        if (id != client.id) return false;
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
        return result;
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

    public ArrayList<Dish> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Dish> orderList) {
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

    @Override
    public String toString() {
        return "Client{" +
                "login='" + login + '\'' +
                ", loyaltyPoints=" + loyaltyPoints +
                ", orderList=" + orderList +
                ", block=" + block +
                ", id=" + id +
                '}';
    }
}
