package com.epam.servlets.dao;

import com.epam.servlets.dao.impl.SQLUserDAO;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    private final UserDAO sqlUserDAO = new SQLUserDAO();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public UserDAO getSqlUserDAO() {
        return sqlUserDAO;
    }
}
