package com.epam.servlets.dao;

public interface UserDAO {
    boolean findUserByLogin(String login);

    String singInByLogin(String login);

    void creteNewUser(String login, String password);

    void logOut(String login);

    void inSystem(String login);

    boolean findUserByLoginAndPassword(String login, String password);
}
