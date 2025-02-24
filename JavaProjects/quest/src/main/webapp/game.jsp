<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String message = (String) session.getAttribute("message");
    String option1 = (String) session.getAttribute("option1");
    String option2 = (String) session.getAttribute("option2");
    String link1 = (String) session.getAttribute("link1");
    String link2 = (String) session.getAttribute("link2");

    if (message == null) message = "Wrong! You need to start again.";
    if (option1 == null) option1 = "Start again";
    if (option2 == null) option2 = "";
    if (link1 == null) link1 = "game?choice=accept";
    if (link2 == null) link2 = "result?outcome=lose";
%>
<%
    String playerName = (String) session.getAttribute("playerName");
    if (playerName == null) playerName = "Stranger";
    String choice = request.getParameter("choice");
%>

<% if (choice == null) { %>
<h2>Welcome, <%= playerName %>!</h2>
<% } %>


<h2><%= message %></h2>

<%
    String choiceValue;
    if (link1.contains("=")) {
        String[] linkParts = link1.split("=");
        choiceValue = (linkParts.length > 1) ? linkParts[1] : "";
    } else {
        choiceValue = "";
    }
%>



<form action="game" method="get">
    <%
        String choiceValueFixed = (choiceValue.isEmpty()) ? "start" : choiceValue;
    %>

    <input type="hidden" name="choice" value="<%= choiceValueFixed %>">
    <button type="submit"><%= option1 %></button>
</form>

<%
    String[] linkPart2 = link2.split("=");
    String choiceValue2 = (linkPart2.length > 1) ? linkPart2[1] : "";
%>
<% if (!option2.isEmpty()) { %>
<form action="game" method="get">
    <input type="hidden" name="choice" value="<%= choiceValue2 %>">
    <button type="submit"><%= option2 %></button>
</form>
<% } %>

