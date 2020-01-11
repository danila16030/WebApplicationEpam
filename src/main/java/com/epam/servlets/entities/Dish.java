package com.epam.servlets.entities;

import java.sql.Blob;
import java.sql.Time;

public class Dish {
    String name;
    int cost;
    Time cookingTime;
    String tag;
    Blob image;

    public Dish(String name, int cost, Time cookingTime, String tag, Blob image) {
        this.name = name;
        this.cost = cost;
        this.cookingTime = cookingTime;
        this.tag = tag;
        this.image = image;
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

    public Blob getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", cookingTime=" + cookingTime +
                ", tag='" + tag + '\'' +
                ", image=" + image +
                '}';
    }
}
