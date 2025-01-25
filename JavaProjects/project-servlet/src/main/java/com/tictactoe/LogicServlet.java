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

        // get index of what was clicked
        int index = getSelectedIndex(req);
        Sign currentSign = field.getField().get(index);

        //if sign not empty, send user to the same page without changes
        if (Sign.EMPTY != currentSign) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        // put cross
        field.getField().put(index, Sign.CROSS);
        if (checkWin(resp, currentSession, field)) {
            return;
        }

        //get empty space
        int emptyFieldIndex = field.getEmptyFieldIndex();

        //put zero
        if (emptyFieldIndex >= 0) {
            field.getField().put(emptyFieldIndex, Sign.NOUGHT);
            // Проверяем, не победил ли нолик после добавление последнего нолика
            if (checkWin(resp, currentSession, field)) {
                return;
            }
        }else {
            //if draw
            // add flag that it's a draw
            currentSession.setAttribute("draw", true);

            // count list of signs
            List<Sign> data = field.getFieldData();

            // update list in session
            currentSession.setAttribute("data", data);

            // redirect
            resp.sendRedirect("/index.jsp");
            return;
        }
        // count list of attributes
        List<Sign> data = field.getFieldData();

        // Update field object and attributes
        currentSession.setAttribute("data", data);
        currentSession.setAttribute("field", field);

        resp.sendRedirect("/index.jsp");
    }

    //checking if there are three crosses/noughts in one row
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