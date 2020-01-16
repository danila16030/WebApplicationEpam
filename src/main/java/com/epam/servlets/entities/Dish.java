package com.epam.servlets.entities;


import java.sql.Time;

public class Dish {
    private String name;
    private int cost;
    private Time cookingTime;
    private Time orderTime;

    private String image;
    private int id;

    public Dish(String name, int cost, Time cookingTime,Time orderTime) {
        this.name = name;
        this.cost = cost;
        this.cookingTime = cookingTime;
        this.orderTime=orderTime;
    }

    public Dish(String name, int cost, Time cookingTime, String image) {
        this.name = name;
        this.cost = cost;
        this.cookingTime = cookingTime;
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setCookingTime(Time cookingTime) {
        this.cookingTime = cookingTime;
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


    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", cookingTime=" + cookingTime +
                ", image='" + image + '\'' +
                ", id=" + id +
                '}';
    }
}
