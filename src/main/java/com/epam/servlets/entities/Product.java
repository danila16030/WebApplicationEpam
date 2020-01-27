package com.epam.servlets.entities;


public class Product {
    private String name;
    private int cost;
    private String cookingTime;
    private String cookingProcess;
    private String orderTime;
    private String image;
    private int id;
    private double averageScope;
    private int votersNumber;
    private String tag;

    public Product() {
    }

    public Product(String name, String orderTime, String cookingProcess) {
        this.name = name;
        this.orderTime=orderTime;
        this.cookingProcess = cookingProcess;
    }

    public Product(String name, int cost, String cookingTime, String image, double averageScope, int votersNumber) {
        this.name = name;
        this.cost = cost;
        this.cookingTime = cookingTime;
        this.image = image;
        this.votersNumber = votersNumber;
        this.averageScope = averageScope;
    }

    public Product(String name, int cost, String cookingTime, String image) {
        this.name = name;
        this.cost = cost;
        this.cookingTime = cookingTime;
        this.image = image;
    }

    public Product(String name, int cost, String cookingTime, String image, String tag) {
        this.name = name;
        this.cost = cost;
        this.cookingTime = cookingTime;
        this.image = image;
        this.tag = tag;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
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

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getCookingProcess() {
        return cookingProcess;
    }

    public void setCookingProcess(String cookingProcess) {
        this.cookingProcess = cookingProcess;
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
        if (cookingProcess != null ? !cookingProcess.equals(product.cookingProcess) : product.cookingProcess != null)
            return false;
        if (orderTime != null ? !orderTime.equals(product.orderTime) : product.orderTime != null) return false;
        if (image != null ? !image.equals(product.image) : product.image != null) return false;
        return tag != null ? tag.equals(product.tag) : product.tag == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + cost;
        result = 31 * result + (cookingTime != null ? cookingTime.hashCode() : 0);
        result = 31 * result + (cookingProcess != null ? cookingProcess.hashCode() : 0);
        result = 31 * result + (orderTime != null ? orderTime.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
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
                ", cookingProcess='" + cookingProcess + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", image='" + image + '\'' +
                ", id=" + id +
                ", averageScope=" + averageScope +
                ", votersNumber=" + votersNumber +
                ", tag='" + tag + '\'' +
                '}';
    }
}
