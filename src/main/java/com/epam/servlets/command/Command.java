package com.epam.servlets.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest req) throws CommandException;
}
