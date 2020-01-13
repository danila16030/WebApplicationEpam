package com.epam.servlets.service.factory;

import com.epam.servlets.service.Command;
import com.epam.servlets.service.impl.FirstCourseCommand;
import com.epam.servlets.service.impl.LogOutCommand;
import com.epam.servlets.service.impl.RegisterCommand;
import com.epam.servlets.service.impl.SingInCommand;

public enum CommandEnum {
    SINGIN(new SingInCommand()),
    CLIENT(new LogOutCommand()),
    REGISTER(new RegisterCommand()),
    FIRSTCOURSE(new FirstCourseCommand());

    private Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public static Command getCurrentCommand(String action) {
        return CommandEnum.valueOf(action.toUpperCase()).command;//toUpperCase увеличивает(делает ее как класс и передает ее в вер где логин
    }
}
