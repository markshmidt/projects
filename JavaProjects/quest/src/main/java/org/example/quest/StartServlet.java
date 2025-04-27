package org.example.quest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class StartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String playerName = request.getParameter("playerName");
        if (playerName == null || playerName.trim().isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("playerName", playerName);
        session.setAttribute("gamesPlayed", 0);

        response.sendRedirect("game");
    }
}
