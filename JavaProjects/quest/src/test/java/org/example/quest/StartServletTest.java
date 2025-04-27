package org.example.quest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static org.mockito.Mockito.*;

class StartServletTest {

    private StartServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        servlet = new StartServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testDoGet_ForwardToIndex() throws ServletException, IOException {
        when(request.getRequestDispatcher("index.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoPost_ValidPlayerName() throws ServletException, IOException {
        when(request.getParameter("playerName")).thenReturn("Alex");

        servlet.doPost(request, response);

        verify(session).setAttribute("playerName", "Alex");
        verify(session).setAttribute("gamesPlayed", 0);
        verify(response).sendRedirect("game");
    }

    @Test
    void testDoPost_EmptyPlayerName() throws ServletException, IOException {
        when(request.getParameter("playerName")).thenReturn("");

        servlet.doPost(request, response);

        verify(response).sendRedirect("index.jsp");
    }
}
