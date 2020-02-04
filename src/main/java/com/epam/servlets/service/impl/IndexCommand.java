package com.epam.servlets.service.impl;

import com.epam.servlets.service.Command;
import com.epam.servlets.service.CommandException;

import javax.servlet.http.HttpServletRequest;

public class IndexCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) throws CommandException {
        if (req.getParameter("language") != null && req.getParameter("language").equals("ru")) {
            req.getSession().setAttribute("locale", "be_RU");
        } else {
            req.getSession().setAttribute("locale", "en_RU");
        }
        return "index";
    }
}
