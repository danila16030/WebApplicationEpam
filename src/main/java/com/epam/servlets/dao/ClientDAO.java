package com.epam.servlets.dao;

import com.epam.servlets.entities.Client;

import java.util.ArrayList;

public interface ClientDAO {
    void createNewClient(String login);

    void removeOrder(String order, int points, String name, int block);

    void changePoint(String point, int block, String name);

    int getBalance(String name);

    void changeBalance(int balance, String name);

    ArrayList<Client> getClientList(String name);

    void makeOrder(String order, int balance, int point, String name);
}
