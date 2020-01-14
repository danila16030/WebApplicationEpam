package com.epam.servlets.entities;


import java.sql.Time;

public class Dish {
    String name;
    int cost;
    Time cookingTime;
    String tag;
    String image;
    private int id;



    public Dish(String name, int cost, Time cookingTime, String tag, String image) {
        this.name = name;
        this.cost = cost;
        this.cookingTime = cookingTime;
        this.tag = tag;
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

    public Time getCookingTime() {
        return cookingTime;
    }

    public String getTag() {
        return tag;
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
                ", tag='" + tag + '\'' +
                ", image='" + image + '\'' +
                ", id=" + id +
                '}';
    }
}
