package com.epam.command;

import com.epam.servlets.command.Command;
import com.epam.servlets.command.CommandException;
import com.epam.servlets.command.factory.CommandEnum;
import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.OrderDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class UserPageCommandTest {
    @Mock
    private OrderDAO orderDAO;
    @InjectMocks
    private static Command command = CommandEnum.getCurrentCommand("user");
    private MockHttpServletRequest request = new MockHttpServletRequest();


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getOrder() throws DAOException, CommandException {
        command.execute(request);
        verify(orderDAO, atLeastOnce()).getClientOrder(anyString());
    }
}
