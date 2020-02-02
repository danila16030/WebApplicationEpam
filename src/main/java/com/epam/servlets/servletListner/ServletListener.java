package com.epam.servlets.servletListner;

import com.epam.servlets.service.CommandException;
import com.epam.servlets.timer.MyTimer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        MyTimer myTimer = MyTimer.getInstance();
        try {
            myTimer.orderTimer();
        } catch (CommandException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
