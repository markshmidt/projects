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
        // Current session
        HttpSession currentSession = req.getSession();

        // Get field object out of session
        Field field = extractField(currentSession);

        // Check if there's already a winner
        if (currentSession.getAttribute("winner") != null) {
            resp.sendRedirect("/index.jsp");
            return;
        }

        // Get index of what was clicked
        int index = getSelectedIndex(req);
        Sign currentSign = field.getField().get(index);

        // If cell is not empty, redirect
        if (Sign.EMPTY != currentSign) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        // Place 'X'
        field.getField().put(index, Sign.CROSS);
        if (checkWin(resp, currentSession, field)) {
            return;
        }

        // Get all empty spaces
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
            // Block X from winning
            field.getField().put(blockIndex, Sign.NOUGHT);
        } else {
            // No block needed; place O in the first empty space
            int firstEmptyIndex = emptyFields.get(0);
            field.getField().put(firstEmptyIndex, Sign.NOUGHT);
        }

        // Check if O wins after the last move
        if (checkWin(resp, currentSession, field)) {
            return;
        }

        // Update session attributes
        currentSession.setAttribute("data", field.getFieldData());
        currentSession.setAttribute("field", field);

        // Redirect to the game page
        resp.sendRedirect("/index.jsp");
    }


    // Checking if there are three crosses/noughts in one row
    private boolean checkWin(HttpServletResponse response, HttpSession currentSession, Field field) throws IOException {
        Sign winner = field.checkWin();
        if (Sign.CROSS == winner || Sign.NOUGHT == winner) {
            // Add flag showing winner
            currentSession.setAttribute("winner", winner);

            // Counting list of signs
            List<Sign> data = field.getFieldData();

            // Update the list in session
            currentSession.setAttribute("data", data);

            // Redirect
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
