package com.epam.servlets.entities;

public class Comment {
    private String time;
    private String date;
    private String author;
    private String comment;
    private String rate;
    private int id;

    public Comment(String author, String date, String time, String comment,String rate) {
        this.time = time;
        this.date = date;
        this.author = author;
        this.comment = comment;
        this.rate =rate;
    }



    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", comment='" + comment + '\'' +
                ", rate=" + rate +
                ", id=" + id +
                '}';
    }

}
