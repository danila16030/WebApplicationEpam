package com.epam.servlets.dao;

public interface UserDAO {
    boolean findUserByLogin(String login);
    boolean findUserByLoginAndPassword(String login,String password);
    void singInByLogin(String login);
    void creteNewUser(String login,String password);
    void logOut(String login);
}
