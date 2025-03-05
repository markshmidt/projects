package com.tictactoe;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "LogicServlet", value = "/logic")
public class LogicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // current session
        HttpSession currentSession = req.getSession();

        // get field object out of session
        Field field = extractField(currentSession);

        // check if there's already a winner
        if (currentSession.getAttribute("winner") != null) {
            resp.sendRedirect("/index.jsp");
            return;
        }

        // get index of what was clicked
        int index = getSelectedIndex(req);
        Sign currentSign = field.getField().get(index);

        // redirect if cell is not empty
        if (Sign.EMPTY != currentSign) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        // place 'X'
        field.getField().put(index, Sign.CROSS);
        if (checkWin(resp, currentSession, field)) {
            return;
        }

        // get all empty spaces
        List<Integer> emptyFields = field.getEmptyFieldIndices();

        // Check for a draw
        if (emptyFields.isEmpty()) {
            currentSession.setAttribute("draw", true);
            currentSession.setAttribute("data", field.getFieldData());
            resp.sendRedirect("/index.jsp");
            return;
        }

        // Check if we can block X from winning
        int blockIndex = field.getBlockingIndex(Sign.CROSS);

        if (blockIndex != -1) {
            field.getField().put(blockIndex, Sign.NOUGHT);
        } else {
            int firstEmptyIndex = emptyFields.get(0);
            field.getField().put(firstEmptyIndex, Sign.NOUGHT);
        }

        // check if O wins after the last move
        if (checkWin(resp, currentSession, field)) {
            return;
        }

        // update session
        currentSession.setAttribute("data", field.getFieldData());
        currentSession.setAttribute("field", field);

        // redirect to the game page
        resp.sendRedirect("/index.jsp");
    }


    // checking if there are three crosses/zeros in one row
    private boolean checkWin(HttpServletResponse response, HttpSession currentSession, Field field) throws IOException {
        Sign winner = field.checkWin();
        if (Sign.CROSS == winner || Sign.NOUGHT == winner) {
            // add flag showing winner
            currentSession.setAttribute("winner", winner);

            // counting list of signs
            List<Sign> data = field.getFieldData();

            // update the list in session
            currentSession.setAttribute("data", data);

            // redirect
            response.sendRedirect("/index.jsp");
            return true;
        }
        return false;
    }

    private int getSelectedIndex(HttpServletRequest request) {
        String click = request.getParameter("click");
        boolean isNumeric = click.chars().allMatch(Character::isDigit);
        return isNumeric ? Integer.parseInt(click) : 0;
    }

    private Field extractField(HttpSession currentSession) {
        Object fieldAttribute = currentSession.getAttribute("field");
        if (Field.class != fieldAttribute.getClass()) {
            currentSession.invalidate();
            throw new RuntimeException("Session is broken, try one more time");
        }
        return (Field) fieldAttribute;
    }
}
