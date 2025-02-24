package org.example.quest;

import javax.servlet.ServletException;
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
            session.setAttribute("message", "You lost your memory. Now the UFO is calling you. Will you answer?");
            session.setAttribute("option1", "Answer. They will bring my memories back.");
            session.setAttribute("option2", "Decline. WTF?!");
            session.setAttribute("link1", "game?choice=accept");
            session.setAttribute("link2", "result?outcome=lose");
        } else {
            switch (choice) {
                case "accept":
                    session.setAttribute("message", "You accepted the call. Will you get up a bridge to talk to the captain?");
                    session.setAttribute("option1", "Sure, let's talk!");
                    session.setAttribute("option2", "Decline the proposal");
                    session.setAttribute("link1", "game?choice=bridge");
                    session.setAttribute("link2", "result?outcome=lose");
                    break;

                case "bridge":
                    session.setAttribute("message", "You got up a bridge. The UFO's captain asks: Who are you?");
                    session.setAttribute("option1", "Tell the truth. You must be honest.");
                    session.setAttribute("option2", "Lie! You can't trust them!");
                    session.setAttribute("link1", "result?outcome=win");
                    session.setAttribute("link2", "game?choice=lie");
                    break;

                case "lie":
                    session.setAttribute("message", "They know you are lying! What will you do?");
                    session.setAttribute("option1", "Tell the truth and ask for forgiveness.");
                    session.setAttribute("option2", "Still lie. They are aliens!");
                    session.setAttribute("link1", "result?outcome=win");
                    session.setAttribute("link2", "result?outcome=lose");
                    break;

                case "win":
                    session.setAttribute("message", "Congratulations! You won!");
                    session.setAttribute("option1", "Play again");
                    session.setAttribute("option2", "");
                    session.setAttribute("link1", "game?choice=start");
                    session.setAttribute("link2", "#");
                    break;
                case "start":
                    session.invalidate();
                    response.sendRedirect("index.jsp");
                    return;



                default:
                    session.setAttribute("message", "Oops, you died! Try again.");
                    session.setAttribute("option1", "Try again");
                    session.setAttribute("option2", "");
                    session.setAttribute("link1", "game?choice=start");
                    session.setAttribute("link2", "#");
                    break;


            }
        }

        request.getRequestDispatcher("game.jsp").forward(request, response);
    }
}
