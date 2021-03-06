package com.epam.servlets.command.impl;

import com.epam.servlets.dao.CommentDAO;
import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.MenuDAO;
import com.epam.servlets.entities.Comment;
import com.epam.servlets.entities.Product;
import com.epam.servlets.command.Command;
import com.epam.servlets.command.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CommentPageCommand implements Command {

    private CommentDAO commentDAO = DAOFactory.getInstance().getSqlCommentDAO();

    private MenuDAO menuDAO = DAOFactory.getInstance().getSqlMenuDAO();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        if (req.getParameter("comment") == null) {
            return getComment(req);
        } else {
            return setComment(req);
        }
    }

    private String getComment(HttpServletRequest req) throws CommandException {
        String productId = req.getParameter("productId");

        ArrayList<Comment> commentList = new ArrayList();
        Product product;
        try {
            if (commentDAO.findCommentAboutProduct(productId)) {
                commentList = commentDAO.getCommentsAboutProduct(productId);
                req.getSession().setAttribute("inf", "");
            } else {
                req.getSession().setAttribute("inf", "no comments");
            }
            req.getSession().setAttribute("commentList", commentList);
            product = menuDAO.getProductForComment(productId);
        } catch (DAOException e) {
            throw new CommandException("Error in DAO", e);
        }
        req.getSession().setAttribute("product", product);

        return "comments";
    }

    private String setComment(HttpServletRequest req) throws CommandException {
        String comment = req.getParameter("comment");
        String productId=req.getParameter("productId");
        String author = (String) req.getAttribute("user");
        LocalTime now = LocalTime.now();
        String time = now.format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        LocalDate date = LocalDate.now();
        String rate = req.getParameter("rate");
        if (rate == null) {
            rate = "no rating";
        }
        try {
            if (commentDAO.findCommentAboutProductByAuthor(author, productId)) {
                req.getSession().setAttribute("inf", "update");
                commentDAO.updateComment(date, productId, time, comment, rate, author);
            } else {
                commentDAO.createComment(date, productId, time, comment, rate, author);
                req.getSession().setAttribute("inf", "added");
            }
            if (!rate.equals("no rating")) {
                commentDAO.updateRate(productId);
            }
        } catch (DAOException e) {
            throw new CommandException("Error in DAO", e);
        }
        return getComment(req);
    }
}
