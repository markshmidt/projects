package org.example.quest;

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
        if (gamesPlayed == null) {
            gamesPlayed = 0;
        }
        session.setAttribute("gamesPlayed", gamesPlayed + 1);

        String choice = request.getParameter("choice");

        if (choice == null) {
            session.setAttribute("message", "Ты потерял память. Принять вызов?");
            session.setAttribute("option1", "Принять вызов");
            session.setAttribute("option2", "Отклонить вызов");
            session.setAttribute("link1", "game?choice=accept");
            session.setAttribute("link2", "result?outcome=lose");
        } else {
            switch (choice) {
                case "accept":
                    session.setAttribute("message", "Ты принял вызов. Поднимешься на мостик?");
                    session.setAttribute("option1", "Подняться");
                    session.setAttribute("option2", "Отказаться");
                    session.setAttribute("link1", "game?choice=bridge");
                    session.setAttribute("link2", "result?outcome=lose");
                    break;

                case "bridge":
                    session.setAttribute("message", "Ты поднялся на мостик. Кто ты?");
                    session.setAttribute("option1", "Рассказать правду");
                    session.setAttribute("option2", "Солгать");
                    session.setAttribute("link1", "result?outcome=win");
                    session.setAttribute("link2", "game?choice=lie");
                    break;

                case "lie":
                    session.setAttribute("message", "Твоя ложь разоблачена! Что делать?");
                    session.setAttribute("option1", "Признаться");
                    session.setAttribute("option2", "Продолжать лгать");
                    session.setAttribute("link1", "result?outcome=win");
                    session.setAttribute("link2", "result?outcome=lose");
                    break;

                case "win":
                    session.setAttribute("message", "Поздравляем! Ты победил!");
                    session.setAttribute("option1", "Начать заново");
                    session.setAttribute("option2", "");
                    session.setAttribute("link1", "start");
                    session.setAttribute("link2", "#");
                    break;


                default:
                    session.setAttribute("message", "Ошибка! Попробуй снова.");
                    session.setAttribute("option1", "Начать заново");
                    session.setAttribute("option2", "");
                    session.setAttribute("link1", "start");
                    session.setAttribute("link2", "#");
                    break;
            }
        }

        request.getRequestDispatcher("game.jsp").forward(request, response);
    }
}
