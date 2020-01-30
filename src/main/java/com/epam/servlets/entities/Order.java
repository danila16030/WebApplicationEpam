package com.epam.servlets.entities;

public class Order {
    private String productName;
    private String time;
    private String paymentMethod;
    private String leftTime;

    public Order(String productName, String time, String paymentMethod) {
        this.productName = productName;
        this.time = time;
        this.paymentMethod = paymentMethod;
    }

    public Order(String productName, String time, String leftTime, String paymentMethod) {
        this.productName = productName;
        this.time = time;
        this.paymentMethod = paymentMethod;
        this.leftTime = leftTime;
    }

    public String getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(String leftTime) {
        this.leftTime = leftTime;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Order{" +
                "productName='" + productName + '\'' +
                ", time='" + time + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", leftTime='" + leftTime + '\'' +
                '}';
    }
}
