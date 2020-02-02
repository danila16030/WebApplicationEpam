package com.epam.servlets.dao;

import com.epam.servlets.entities.Client;

import java.util.ArrayList;

public interface ClientDAO {
    void createNewClient(String login) throws DAOException;

    void changePointAndBlock(String point, int block, String name) throws DAOException;

    int getBalance(String name) throws DAOException;

    boolean isBlock(String name) throws DAOException;

    int getPoint(String name) throws DAOException;

    void changeBalance(int balance, String name) throws DAOException;

    ArrayList<Client> getClientList(String name) throws DAOException;

    void changeBalanceAndPoints(int balance, int point, String name) throws DAOException;
}
