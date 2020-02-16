package com.epam.dao;

import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

public class SQLUserDAOTest {
    private static final Logger logger = LogManager.getLogger(SQLUserDAOTest.class);
    private static final UserDAO userDAO = DAOFactory.getInstance().getSqlUserDAO();
    private static String testingLogin;
    private static String testingPassword;

    @BeforeClass
    public static void setupUser() {
        testingLogin = "testUser";
        testingPassword = "$2a$10$NW5d90AbVQ8uc9ruwvucqONNsIJiKqklMb8sQimvcJqcl/3HJtqH2";
    }

    @Test
    public void addUser() {
        try {
            userDAO.creteNewUser(testingLogin, testingPassword);
        } catch (DAOException e) {
            logger.error(e);
        }

    }
}
