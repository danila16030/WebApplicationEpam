package com.epam.servlets.service;

public class CommandException extends Exception {
    public CommandException() {
        super();
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(Exception e) {
        super(e);
    }

    public CommandException(String message, Exception e) {
        super(message, e);
    }
}
