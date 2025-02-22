package org.example.quest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class GameServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer gamesPlayed = (Integer) session.getAttribute("gamesPlayed");
        session.setAttribute("gamesPlayed", gamesPlayed + 1);

        String choice = request.getParameter("choice");
        if (choice == null) {
            request.getRequestDispatcher("game.jsp").forward(request, response);
            return;
        }

        if ("accept".equals(choice)) {
            request.getRequestDispatcher("game.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("result.jsp").forward(request, response);
        }
    }
}
