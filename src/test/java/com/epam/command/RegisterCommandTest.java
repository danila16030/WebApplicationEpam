package com.epam.command;

import com.epam.servlets.command.Command;
import com.epam.servlets.command.CommandException;
import com.epam.servlets.command.factory.CommandEnum;
import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.UserDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class RegisterCommandTest {
    @Mock
    private UserDAO userDAO;
    @InjectMocks
    private static Command command = CommandEnum.getCurrentCommand("register");
    private MockHttpServletRequest request = new MockHttpServletRequest();


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void register() throws DAOException, CommandException {
        request.setParameter("pass", anyString());
        command.execute(request);
        verify(userDAO, atLeastOnce()).creteNewUser(anyString(), anyString());
    }
}
