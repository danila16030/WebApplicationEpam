package com.epam.command;

import com.epam.servlets.command.Command;
import com.epam.servlets.command.CommandException;
import com.epam.servlets.command.factory.CommandEnum;
import com.epam.servlets.dao.CommentDAO;
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

public class CommentPageCommandTest {
    @Mock
    private CommentDAO commentDAO;
    @InjectMocks
    private static Command command = CommandEnum.getCurrentCommand("comments");
    private MockHttpServletRequest request = new MockHttpServletRequest();


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setComment() throws DAOException, CommandException {
        request.setParameter("comment", anyString());
        command.execute(request);
        verify(commentDAO, atLeastOnce()).findCommentAboutProductByAuthor(anyString(), anyString());
    }

    @Test
    public void getComments() throws DAOException, CommandException {
        command.execute(request);
        verify(commentDAO, atLeastOnce()).findCommentAboutProduct(anyString());
    }
}
