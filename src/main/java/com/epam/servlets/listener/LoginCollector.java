package com.epam.servlets.listener;

import java.util.HashMap;
import java.util.Map;

public class LoginCollector {
    private Map<String, String> names = new HashMap<>();
    private static LoginCollector instance=new LoginCollector();

    public static LoginCollector getInstance(){
        return instance;
    }

    private LoginCollector(){

    }

    public void addLogin(String sessionId, String login) {
        names.put(sessionId, login);
    }

    public String getLogin(String sessionId) {
        return names.get(sessionId);
    }

}
