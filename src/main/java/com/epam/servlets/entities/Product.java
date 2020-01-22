package com.epam.servlets.entities;


import java.sql.Time;

public class Product {
    private String name;
    private int cost;
    private Time cookingTime;
    private String readyTime;
    private String image;
    private int id;
    private double averageScope;
    private int votersNumber;

    public Product() {
    }

    public Product(String name, int cost, String readyTime) {
        this.name = name;
        this.cost = cost;
        this.readyTime = readyTime;
    }

    public Product(String name, int cost, Time cookingTime, String image, double averageScope, int votersNumber) {
        this.name = name;
        this.cost = cost;
        this.cookingTime = cookingTime;
        this.image = image;
        this.votersNumber = votersNumber;
        this.averageScope = averageScope;
    }

    public Product(String name, int cost, Time cookingTime, String image) {
        this.name = name;
        this.cost = cost;
        this.cookingTime = cookingTime;
        this.image = image;
    }

    public void setAverageScope(double averageScope) {
        this.averageScope = averageScope;
    }

    public double getAverageScope() {
        return averageScope;
    }

    public void setAverageScope(int averageScope) {
        this.averageScope = averageScope;
    }

    public int getVotersNumber() {
        return votersNumber;
    }

    public void setVotersNumber(int votersNumber) {
        this.votersNumber = votersNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }


    public void setImage(String image) {
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public Time getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Time cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getReadyTime() {
        return readyTime;
    }

    public void setReadyTime(String readyTime) {
        this.readyTime = readyTime;
    }

    public String getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (cost != product.cost) return false;
        if (id != product.id) return false;
        if (Double.compare(product.averageScope, averageScope) != 0) return false;
        if (votersNumber != product.votersNumber) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (cookingTime != null ? !cookingTime.equals(product.cookingTime) : product.cookingTime != null) return false;
        if (readyTime != null ? !readyTime.equals(product.readyTime) : product.readyTime != null) return false;
        return image != null ? image.equals(product.image) : product.image == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + cost;
        result = 31 * result + (cookingTime != null ? cookingTime.hashCode() : 0);
        result = 31 * result + (readyTime != null ? readyTime.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + id;
        temp = Double.doubleToLongBits(averageScope);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + votersNumber;
        return result;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", cookingTime=" + cookingTime +
                ", readyTime='" + readyTime + '\'' +
                ", image='" + image + '\'' +
                ", id=" + id +
                ", averageScope=" + averageScope +
                ", votersNumber=" + votersNumber +
                '}';
    }
}