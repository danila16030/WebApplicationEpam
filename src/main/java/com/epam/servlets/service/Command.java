package com.epam.servlets.service;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest req);

}
