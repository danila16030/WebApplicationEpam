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

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class ChangeMenuPageCommandTest {
    @Mock
    private MenuDAO menuDAO;
    @InjectMocks
    private static Command command = CommandEnum.getCurrentCommand("CHANGEMENU");
    private MockHttpServletRequest request = new MockHttpServletRequest();


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getProductByProductName() throws CommandException, DAOException {
        request.setParameter("product", anyString());
        command.execute(request);
        verify(menuDAO, atLeastOnce()).findProductByName(anyString());
    }

    @Test
    public void getProductByTags() throws CommandException, DAOException {
        request.setParameter("tag", anyString());
        command.execute(request);
        verify(menuDAO, atLeastOnce()).getProductListForChange(anyList());
    }


    @Test
    public void updateProduct() throws CommandException, DAOException {
        request.setParameter("time", anyString());
        request.setParameter("product", anyString());
        command.execute(request);
        verify(menuDAO, atLeastOnce()).updateProduct(anyObject(),anyObject(),anyObject(),anyObject(),anyObject());
    }

    @Test
    public void deleteProduct() throws DAOException, CommandException {
        request.setParameter("delete", anyString());
        request.setParameter("product", anyString());
        command.execute(request);
        verify(menuDAO, atLeastOnce()).deleteProduct(anyString());
    }

}
