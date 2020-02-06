package com.epam.servlets.fileManager;

public class FileManagerException extends Exception {
    public FileManagerException() {
        super();
    }

    public FileManagerException(String message) {
        super(message);
    }

    public FileManagerException(Exception e) {
        super(e);
    }

    public FileManagerException(String message, Exception e) {
        super(message, e);
    }
}
