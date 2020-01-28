package com.epam.servlets.service.impl;

import com.epam.servlets.dao.CommentDAO;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.MenuDAO;
import com.epam.servlets.entities.Comment;
import com.epam.servlets.entities.Product;
import com.epam.servlets.service.Command;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CommentPageCommand implements Command {

    private CommentDAO commentDAO = DAOFactory.getInstance().getSqlCommentDAO();

    private MenuDAO menuDAO = DAOFactory.getInstance().getSqlMenuDAO();

    @Override
    public String execute(HttpServletRequest req) {
        if (req.getParameter("comment") == null) {
            return getComment(req);
        } else {
            return setComment(req);
        }
    }

    private String getComment(HttpServletRequest req) {
        String productName = req.getParameter("about");
        ArrayList<Comment> listResults = new ArrayList();
        Product product;
        if (commentDAO.findCommentAboutProduct(productName)) {
            listResults = commentDAO.getCommentsAboutProduct(productName);
        } else {
            req.setAttribute("inf", "no comments");
        }
        req.setAttribute("listResults", listResults);
        product = menuDAO.getProductForComment(productName);
        req.setAttribute("product", product);

        return "comments";
    }

    private String setComment(HttpServletRequest req) {
        String comment = req.getParameter("comment");
        String productName = req.getParameter("about");
        String author = (String) req.getAttribute("user");
        LocalTime now = LocalTime.now();
        String time = now.format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        LocalDate date = LocalDate.now();
        String rate = req.getParameter("rate");
        if (rate == null) {
            rate = "no rating";
        }
        if (commentDAO.findCommentAboutProductByAuthor(author, productName)) {
            req.setAttribute("inf", "update");
            commentDAO.updateComment(date, productName, time, comment, rate, author);
        } else {
            commentDAO.createComment(date, productName, time, comment, rate, author);
            req.setAttribute("inf", "added");
        }
        if (!rate.equals("no rating")) {
            commentDAO.updateRate(productName);
        }
        return getComment(req);
    }
}
