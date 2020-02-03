package com.epam.servlets.entities;


public class Product {
    private String name;
    private int cost;
    private String cookingTime;
    private String imagePath;
    private int id;
    private double averageScope;
    private int votersNumber;
    private String tag;

    public Product() {
    }

    public Product(String name, int cost, String cookingTime, String image, double averageScope, int votersNumber) {
        this.name = name;
        this.cost = cost;
        this.cookingTime = cookingTime;
        this.imagePath = image;
        this.votersNumber = votersNumber;
        this.averageScope = averageScope;
    }

    public Product(String name, int cost, String cookingTime, String image) {
        this.name = name;
        this.cost = cost;
        this.cookingTime = cookingTime;
        this.imagePath = image;
    }

    public Product(String name, int cost, String cookingTime, String image, String tag) {
        this.name = name;
        this.cost = cost;
        this.cookingTime = cookingTime;
        this.imagePath = image;
        this.tag = tag;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getImagePath() {
        return imagePath;
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
        if (imagePath != null ? !imagePath.equals(product.imagePath) : product.imagePath != null) return false;
        return tag != null ? tag.equals(product.tag) : product.tag == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + cost;
        result = 31 * result + (cookingTime != null ? cookingTime.hashCode() : 0);
        result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
        result = 31 * result + id;
        temp = Double.doubleToLongBits(averageScope);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + votersNumber;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", cookingTime='" + cookingTime + '\'' +
                ", image='" + imagePath + '\'' +
                ", id=" + id +
                ", averageScope=" + averageScope +
                ", votersNumber=" + votersNumber +
                ", tag='" + tag + '\'' +
                '}';
    }
}
