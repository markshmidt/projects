<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%
    HttpSession session = request.getSession();
    String playerName = (String) session.getAttribute("playerName");
%>

<h2>Привет, <%= playerName %>! Ты потерял память. Принять вызов НЛО?</h2>
<form action="game" method="get">
    <button name="choice" value="accept">Принять вызов</button>
    <button name="choice" value="decline">Отклонить вызов</button>
</form>
