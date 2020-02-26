package com.epam.command;

import com.epam.servlets.command.Command;
import com.epam.servlets.command.CommandException;
import com.epam.servlets.command.factory.CommandEnum;
import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.MenuDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class FirstCourseCommandTest {
    @Mock
    private MenuDAO menuDAO;
    @InjectMocks
    private static Command command = CommandEnum.getCurrentCommand("firstCourse");
    private MockHttpServletRequest request = new MockHttpServletRequest();


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getMenu() throws DAOException, CommandException {
        command.execute(request);
        verify(menuDAO, atLeastOnce()).getProductList(anyString());
    }
}
