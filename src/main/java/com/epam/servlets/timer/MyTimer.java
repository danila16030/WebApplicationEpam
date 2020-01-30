package com.epam.servlets.timer;


import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.OrderDAO;
import com.epam.servlets.entities.Order;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MyTimer {

    private static final MyTimer instance = new MyTimer();
    private Timer timer;
    Order nearestOrder;
    private OrderDAO orderDAO = DAOFactory.getInstance().getSqlOrderDAO();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        getInstance().getNearestOrder();
    }

    private MyTimer() {
    }

    public static MyTimer getInstance() {
        return instance;
    }

    private Order getNearestOrder() {
        ArrayList<Order> orders;
        LocalTime closer = null;
        orders = orderDAO.getAllOrder();
        for (Order order : orders) {
            String time = order.getTime();
            if (closer == null) {
                closer = LocalTime.parse(time, formatter);
                nearestOrder = order;
            } else {
                LocalTime orderTime = LocalTime.parse(time, formatter);
                if (orderTime.isBefore(closer)) {
                    closer = orderTime;
                    nearestOrder = order;
                }
            }
        }
        return nearestOrder;
    }

    public void orderTimer() {
        timer = new Timer();
        Order order = getNearestOrder();
        if (order != null) {
            LocalTime closer = LocalTime.parse(order.getTime(), formatter);
            LocalTime now = LocalTime.now();
            closer = closer.minus(now.getHour(), ChronoUnit.HOURS);
            closer = closer.minus(now.getMinute(), ChronoUnit.MINUTES);
            int delay = closer.getHour() * 3600;
            delay = delay + closer.getMinute() * 60;
            delay = delay * 1000;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    removeOrder(order);
                    orderTimer();
                }
            }, delay);
        }
    }

    private void removeOrder(Order order) {
        orderDAO.removeOrder(order.getProductName(), order.getTime());
        if(!order.getPaymentMethod().equals("cash")){
            //уменьшать баллы клиента и блокир
        }
    }
}

