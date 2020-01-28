package com.epam.servlets.dao;

import com.epam.servlets.dao.impl.SQLCommentDAO;
import com.epam.servlets.dao.impl.SQLMenuDAO;
import com.epam.servlets.dao.impl.SQLUserDAO;
import com.epam.servlets.dao.impl.SQLClientDAO;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    private final UserDAO sqlUserDAO = new SQLUserDAO();
    private final CommentDAO sqlCommentDAO = new SQLCommentDAO();
    private final MenuDAO sqlMenuDAO = new SQLMenuDAO();
    private final ClientDAO sqlClientDAO = new SQLClientDAO();

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


    public CommentDAO getSqlCommentDAO() {
        return sqlCommentDAO;
    }
}
