package org.example.quest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ResultServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String outcome = request.getParameter("outcome");
        String message = "Ошибка!";

        if ("win".equals(outcome)) {
            message = "Поздравляем! Ты победил!";
        } else if ("lose".equals(outcome)) {
            message = "Ты проиграл. Попробуй снова.";
        }

        request.setAttribute("message", message);
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }
}
