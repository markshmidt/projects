package com.tictactoe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(name = "InitServlet", value = "/start")
public class InitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //new session
        HttpSession currentSession = req.getSession();

        //new field
        Field field = new Field();

        //getting list of field states
        List<Sign> data = field.getFieldData();

        //adding field parameters to the session to store field's state between sessions
        currentSession.setAttribute("field", field);
        // field states sorted by index to draw crosses and zeros
        currentSession.setAttribute("data", data);

        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);

    }

}