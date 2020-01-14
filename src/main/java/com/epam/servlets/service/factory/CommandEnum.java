package com.epam.servlets.service.factory;

import com.epam.servlets.service.Command;
import com.epam.servlets.service.impl.*;

public enum CommandEnum {
    SINGIN(new SingInCommand()),
    CLIENT(new ClientPageCommand()),
    REGISTER(new RegisterCommand()),
    FIRSTCOURSE(new FirstCourseCommand());

    private Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public static Command getCurrentCommand(String action) {
        return CommandEnum.valueOf(action.toUpperCase()).command;
    }
}
