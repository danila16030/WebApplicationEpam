package com.epam.servlets.checker;

public class RedirectCheck {

    public boolean needRedirect(String page) {
        return page.equals("index") || page.equals("/WebApplication_war_exploded") || page.equals("admin")
                || page.equals("changePoints") || page.equals("changeMenu") || page.equals("singIn")
                || page.equals("orderPage") || page.equals("comments") || page.equals("balance")
                || page.equals("register");
    }
}
