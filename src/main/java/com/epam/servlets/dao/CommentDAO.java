package com.epam.servlets.dao;

import com.epam.servlets.entities.Comment;

import java.time.LocalDate;
import java.util.ArrayList;

public interface CommentDAO {
    boolean findCommentAboutProductByAuthor(String author, String productName) throws DAOException;

    void updateComment(LocalDate date, String productName, String time, String comment, String rate, String author) throws DAOException;

    void createComment(LocalDate date, String productName, String time, String comment, String rate, String author) throws DAOException;

    boolean findCommentAboutProduct(String productName) throws DAOException;

    void updateRate(String productName) throws DAOException;

    ArrayList<Comment> getCommentsAboutProduct(String productName) throws DAOException;
}
