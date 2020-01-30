package com.epam.servlets.dao;

import com.epam.servlets.dao.impl.*;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    private final UserDAO sqlUserDAO = new SQLUserDAO();
    private final CommentDAO sqlCommentDAO = new SQLCommentDAO();
    private final MenuDAO sqlMenuDAO = new SQLMenuDAO();
    private final ClientDAO sqlClientDAO = new SQLClientDAO();
    private final OrderDAO sqlOrderDAO=new SQLOrderDAO();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public ClientDAO getSqlClientDAO() {
        return sqlClientDAO;
    }

    public UserDAO getSqlUserDAO() {
        return sqlUserDAO;
    }

    public MenuDAO getSqlMenuDAO() {
        return sqlMenuDAO;
    }

    public OrderDAO getSqlOrderDAO() {
        return sqlOrderDAO;
    }

    public CommentDAO getSqlCommentDAO() {
        return sqlCommentDAO;
    }
}
