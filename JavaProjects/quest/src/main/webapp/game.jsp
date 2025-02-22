<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%
    String playerName = (String) session.getAttribute("playerName");
%>

<h2>Welcome, <%= playerName %>! You lost your memory. Will you answer when the UFO calls you?</h2>
<form action="game" method="get">
    <button name="choice" value="accept">Yes</button>
    <button name="choice" value="decline">No</button>
</form>
