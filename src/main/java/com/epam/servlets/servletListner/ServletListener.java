package com.epam.servlets.servletListner;

import com.epam.servlets.timer.MyTimer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        MyTimer myTimer = MyTimer.getInstance();
        myTimer.orderTimer();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
