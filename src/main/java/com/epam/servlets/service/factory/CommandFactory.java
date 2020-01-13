package com.epam.servlets.service.factory;

import com.epam.servlets.service.Command;

public class CommandFactory {
    private static CommandFactory commandFactory;

    private CommandFactory() {
    }

    public static CommandFactory getInstance() {
        if (commandFactory == null) {
            commandFactory = new CommandFactory();
        }
        return commandFactory;
    }

    public Command getCurrentCommand(String command) {
        Command currentCommand;
        currentCommand = CommandEnum.getCurrentCommand(command);
        return currentCommand;
    }
}