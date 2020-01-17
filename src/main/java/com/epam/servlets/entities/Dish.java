package com.epam.servlets.entities;


import java.sql.Time;

public class Dish {
    private String name;
    private int cost;
    private Time cookingTime;
    private String readyTime;

    private String image;
    private int id;

    public Dish(String name, int cost, String readyTime) {
        this.name = name;
        this.cost = cost;
        this.readyTime = readyTime;
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
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", cookingTime=" + cookingTime +
                ", orderTime='" + readyTime + '\'' +
                ", image='" + image + '\'' +
                ", id=" + id +
                '}';
    }
}
