package com.epam.servlets.entities;

import java.sql.Time;

public class Dish {
    String name;
    int cost;
    Time cookingTime;
    String tag;

    public Dish(String name, int cost, Time cookingTime, String tag) {
        this.name = name;
        this.cost = cost;
        this.cookingTime = cookingTime;
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        if (cost != dish.cost) return false;
        if (name != null ? !name.equals(dish.name) : dish.name != null) return false;
        if (cookingTime != null ? !cookingTime.equals(dish.cookingTime) : dish.cookingTime != null) return false;
        return tag != null ? tag.equals(dish.tag) : dish.tag == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + cost;
        result = 31 * result + (cookingTime != null ? cookingTime.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
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

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", cookingTime=" + cookingTime +
                ", tag='" + tag + '\'' +
                '}';
    }
}
