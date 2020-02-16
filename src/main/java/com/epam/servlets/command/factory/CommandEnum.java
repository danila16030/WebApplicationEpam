package com.epam.servlets.command.factory;

import com.epam.servlets.command.Command;
import com.epam.servlets.command.impl.*;

public enum CommandEnum {
    SINGIN(new SingInCommand()),
    CLIENT(new ClientPageCommand()),
    REGISTER(new RegisterCommand()),
    FIRSTCOURSE(new FirstCourseCommand()),
    BALANCE(new BalancePageCommand()),
    USER(new UserPageCommand()),
    COMMENTS(new CommentPageCommand()),
    CHANGEPOINTS(new ChangePointPageCommand()),
    CHANGEMENU(new ChangeMenuPageCommand()),
    ORDERPAGE(new OrderPageCommand()),
    SECONDCOURSE(new SecondCourseCommand());

    private Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public static Command getCurrentCommand(String action) {
        return CommandEnum.valueOf(action.toUpperCase()).command;
    }
}
