package com.epam.servlets.service;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    boolean execute(HttpServletRequest req);

}
