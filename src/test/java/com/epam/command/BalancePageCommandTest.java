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

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class BalancePageCommandTest {
    @Mock
    private ClientDAO clientDAO;
    @InjectMocks
    private static Command command = CommandEnum.getCurrentCommand("Balance");
    private MockHttpServletRequest request = new MockHttpServletRequest();

    @Before
    public void initReq() {
        request.setAttribute("user", "");
        request.setParameter("money", String.valueOf(0));
    }


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getBalanceTest() throws DAOException, CommandException {
        command.execute(request);
        verify(clientDAO, atLeastOnce()).getBalance(anyString());
    }

}
