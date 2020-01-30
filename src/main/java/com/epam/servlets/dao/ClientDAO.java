package com.epam.servlets.dao;

import com.epam.servlets.entities.Client;

import java.util.ArrayList;

public interface ClientDAO {
    void createNewClient(String login);

    void changePoint(String point, int block, String name);

    int getBalance(String name);

    boolean isBlock(String name);

    int getPoint(String name);

    void changeBalance(int balance, String name);

    ArrayList<Client> getClientList(String name);

    void changeBalanceAndPoints(int balance, int point, String name);
}
