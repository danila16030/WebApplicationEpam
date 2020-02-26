package com.epam.servlets.dao.impl;

import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.UserDAO;
import com.epam.servlets.dao.impl.util.fields.UserFields;
import com.epam.servlets.dao.pool.ConnectionPool;
import com.epam.servlets.dao.pool.PoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SQLUserDAO implements UserDAO {

    private String sqlSingINUser = "UPDATE  user SET inSystem=true WHERE login=?";
    private String sqlCreateNewUser = "INSERT INTO user (login , password,inSystem) VALUES(?,?,true)";
    private String sqlFindUserByLogin = "SELECT * FROM user WHERE login=?";
    private String sqlLogOut = "UPDATE  user SET inSystem=false WHERE login=?";
    private String sqlInSystem = "UPDATE  user SET inSystem=true WHERE login=?";
    private Map<String, PreparedStatement> preparedStatementMap;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Logger logger = LogManager.getLogger(SQLUserDAO.class);

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();


    public SQLUserDAO() {
        preparedStatementMap = new HashMap<>();
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
        } catch (PoolException e) {
            logger.error(e);
        }

        prepareStatement(connection, sqlSingINUser);
        prepareStatement(connection, sqlCreateNewUser);
        prepareStatement(connection, sqlFindUserByLogin);
        prepareStatement(connection, sqlLogOut);
        prepareStatement(connection, sqlInSystem);
        if (connection != null) {
            connectionPool.closeConnection(connection);
        }
    }


    private void prepareStatement(Connection connection, String sql) {
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatementMap.put(sql, preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean findUserByLogin(String login) throws DAOException {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlFindUserByLogin);
            if (preparedStatement != null) {
                preparedStatement.setString(1, login);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String singInByLogin(String login) throws DAOException {
        ResultSet resultSet;
        String result;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlFindUserByLogin);
            if (preparedStatement != null) {
                preparedStatement.setString(1, login);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    if (!resultSet.getBoolean(UserFields.INSYSTEM)) {
                        result = resultSet.getString(UserFields.ROLE);
                        if (result.equals("admin")) {
                            return "admin";
                        }
                        inSystem(login);
                        return "client";
                    }
                }
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "singIn";
    }


    @Override
    public void creteNewUser(String login, String password) throws DAOException {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlCreateNewUser);
            if (preparedStatement != null) {
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void logOut(String login) throws DAOException {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlLogOut);
            if (preparedStatement != null) {
                preparedStatement.setString(1, login);
                preparedStatement.executeUpdate();
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inSystem(String login) throws DAOException {
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlInSystem);
            if (preparedStatement != null) {
                preparedStatement.setString(1, login);
                preparedStatement.executeUpdate();
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean findUserByLoginAndPassword(String login, String password) throws DAOException {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = preparedStatementMap.get(sqlFindUserByLogin);
            if (preparedStatement != null) {
                preparedStatement.setString(1, login);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    if (passwordEncoder.matches(password, resultSet.getString(UserFields.PASSWORD))) {
                        return true;
                    }
                }
            } else {
                throw new DAOException("Couldn't find prepared statement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
