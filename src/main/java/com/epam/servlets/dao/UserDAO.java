package com.epam.servlets.dao;

public interface UserDAO {
    boolean findUserByLogin(String login) throws DAOException;

    String singInByLogin(String login) throws DAOException;

    void creteNewUser(String login, String password) throws DAOException;

    void logOut(String login) throws DAOException;

    void inSystem(String login) throws DAOException;

    boolean findUserByLoginAndPassword(String login, String password) throws DAOException;
}
