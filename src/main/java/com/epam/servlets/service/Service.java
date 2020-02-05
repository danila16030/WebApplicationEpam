package com.epam.servlets.service;

import com.epam.servlets.command.Command;
import com.epam.servlets.command.CommandException;
import com.epam.servlets.command.factory.CommandEnum;

import javax.servlet.http.HttpServletRequest;

public class Service {
    public String execute(HttpServletRequest req, String com) throws ServiceException {
        Command command = CommandEnum.getCurrentCommand(com);
        try {
            return command.execute(req);
        } catch (CommandException e) {
            throw new ServiceException(e);
        }
    }
}
