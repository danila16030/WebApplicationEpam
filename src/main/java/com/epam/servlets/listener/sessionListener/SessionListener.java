package com.epam.servlets.listener.sessionListener;

import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.UserDAO;
import com.epam.servlets.listener.LoginCollector;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
    private LoginCollector loginCollector = LoginCollector.getInstance();
    private UserDAO userDAO = DAOFactory.getInstance().getSqlUserDAO();

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
     }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        String login = loginCollector.getLogin(session.getId());
        if (login != null) {
            try {
                userDAO.logOut(login);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
    }
}
