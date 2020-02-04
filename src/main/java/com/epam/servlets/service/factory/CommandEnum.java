package com.epam.servlets.service.factory;

import com.epam.servlets.service.Command;
import com.epam.servlets.service.impl.*;

public enum CommandEnum {
    INDEX(new IndexCommand()),
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
