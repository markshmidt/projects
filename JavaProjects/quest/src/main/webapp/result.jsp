<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String message = (String) request.getAttribute("message");
    if (message == null) message = "Ошибка!";
%>

<h2><%= message %></h2>

<form action="game" method="get">
    <button type="submit">Начать заново</button>
</form>
