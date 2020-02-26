package com.epam.command;

import com.epam.servlets.command.Command;
import com.epam.servlets.command.CommandException;
import com.epam.servlets.command.factory.CommandEnum;
import com.epam.servlets.dao.ClientDAO;
import com.epam.servlets.dao.DAOException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class ChangePointPageCommandTest {
    @Mock
    private ClientDAO clientDAO;
    @InjectMocks
    private static Command command = CommandEnum.getCurrentCommand("CHANGEPOINTS");
    private MockHttpServletRequest request = new MockHttpServletRequest();


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setPoints() throws DAOException, CommandException {
        request.setParameter("points", anyString());
        request.setParameter("user", anyString());
        request.setParameter("block", anyString());
        command.execute(request);
        verify(clientDAO, atLeastOnce()).changePointAndBlock(anyObject(), anyInt(), anyObject());
    }

    @Test
    public void getPoints() throws DAOException, CommandException {
        command.execute(request);
        verify(clientDAO, atLeastOnce()).getClientList(anyString());
    }
}
