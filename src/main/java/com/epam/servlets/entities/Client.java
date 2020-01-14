package com.epam.servlets.entities;

public class Client {
    private String login;
    private int loyaltyPoints;
    private String order;
    private boolean block;
    private int id;

    public Client() {
    }

    public Client(String login, int loyaltyPoints, String order, boolean block) {
        this.login = login;
        this.loyaltyPoints = loyaltyPoints;
        this.order = order;
        this.block = block;
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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (loyaltyPoints != client.loyaltyPoints) return false;
        if (block != client.block) return false;
        if (id != client.id) return false;
        if (login != null ? !login.equals(client.login) : client.login != null) return false;
        return order != null ? order.equals(client.order) : client.order == null;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + loyaltyPoints;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (block ? 1 : 0);
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "login='" + login + '\'' +
                ", loyaltyPoints=" + loyaltyPoints +
                ", order='" + order + '\'' +
                ", block=" + block +
                ", id=" + id +
                '}';
    }
}
