package com.epam.servlets.fileManager;

import java.io.File;

public class FileManager {
    private static final String RELOAD_PATH = "C:/Users/chech/IdeaProjects/EpamWeb/WebApplication/src/main/webapp/image";

    public void moveImageToResourceFolder(File file, String fileName) throws FileManagerException {
        File menuDirectory = new File(RELOAD_PATH);
        if (!menuDirectory.isDirectory()) {
            if (!menuDirectory.mkdir()) {
                throw new FileManagerException("cant create folder");
            }
        }
        file.renameTo(new File(menuDirectory, fileName + ".jpg"));
    }
}
