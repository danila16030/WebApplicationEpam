package com.epam.servlets.dao;

import com.epam.servlets.entities.Comment;

import java.time.LocalDate;
import java.util.ArrayList;

public interface CommentDAO {
    boolean findCommentAboutProductByAuthor(String author, String productName);

    void updateComment(LocalDate date, String productName, String time, String comment, String rate, String author);

    void createComment(LocalDate date, String productName, String time, String comment, String rate, String author);

    boolean findCommentAboutProduct(String productName);

    void updateRate(String productName);

    ArrayList<Comment> getCommentsAboutProduct(String productName);
}
