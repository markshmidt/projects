package org.example.quest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static org.mockito.Mockito.*;

class GameServletTest {

    private GameServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        servlet = new GameServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("game.jsp")).thenReturn(dispatcher);
    }

    @Test
    void testDoGet_InitialGame() throws ServletException, IOException {
        when(request.getParameter("choice")).thenReturn(null);
        when(session.getAttribute("gamesPlayed")).thenReturn(null);

        servlet.doGet(request, response);

        verify(session).getAttribute("gamesPlayed");
        verify(session).setAttribute("gamesPlayed", 1);

        verify(session).setAttribute("message", "You lost your memory. Now the UFO is calling you. Will you answer?");
        verify(session).setAttribute("option1", "Answer. They will bring my memories back.");
        verify(session).setAttribute("option2", "Decline. WTF?!");
        verify(session).setAttribute("link1", "game?choice=accept");
        verify(session).setAttribute("link2", "result?outcome=lose");

        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoGet_ChoiceAccept() throws ServletException, IOException {
        when(session.getAttribute("gamesPlayed")).thenReturn(2);
        when(request.getParameter("choice")).thenReturn("accept");

        servlet.doGet(request, response);

        verify(session).setAttribute("message", "You accepted the call. Will you get up a bridge to talk to the captain?");
        verify(session).setAttribute("option1", "Sure, let's talk!");
        verify(session).setAttribute("option2", "Decline the proposal");
        verify(session).setAttribute("link1", "game?choice=bridge");
        verify(session).setAttribute("link2", "result?outcome=lose");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoGet_ChoiceBridge() throws ServletException, IOException {
        when(request.getParameter("choice")).thenReturn("bridge");

        servlet.doGet(request, response);

        verify(session).setAttribute("message", "You got up a bridge. The UFO's captain asks: Who are you?");
        verify(session).setAttribute("option1", "Tell the truth. You must be honest.");
        verify(session).setAttribute("option2", "Lie! You can't trust them!");
        verify(session).setAttribute("link1", "result?outcome=win");
        verify(session).setAttribute("link2", "game?choice=lie");
        verify(dispatcher).forward(request, response);
    }
}
