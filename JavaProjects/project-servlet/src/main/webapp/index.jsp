<%@ page import="com.tictactoe.Sign" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <title>Tic-Tac-Toe</title>
    <link href="${pageContext.request.contextPath}/static/main.css" rel="stylesheet">


</head>
<body>
<h1>Tic-Tac-Toe</h1>
<table>
    <tr>
        <td onclick="window.location='/logic?click=0'" class="${data.get(0) == CROSSES ? 'CROSS' : data.get(0) == NOUGHTS ? 'NOUGHT' : ''}">
            ${data.get(0).getSign()}
        </td>
        <td onclick="window.location='/logic?click=1'" class="${data.get(1) == CROSSES ? 'CROSS' : data.get(1) == NOUGHTS ? 'NOUGHT' : ''}">
            ${data.get(1).getSign()}
        </td>
        <td onclick="window.location='/logic?click=2'" class="${data.get(2) == CROSSES ? 'CROSS' : data.get(2) == NOUGHTS ? 'NOUGHT' : ''}">
            ${data.get(2).getSign()}
        </td>
    </tr>
    <tr>
        <td onclick="window.location='/logic?click=3'" class="${data.get(3) == CROSSES ? 'CROSS' : data.get(3) == NOUGHTS ? 'NOUGHT' : ''}">
            ${data.get(3).getSign()}
        </td>
        <td onclick="window.location='/logic?click=4'" class="${data.get(4) == CROSSES ? 'CROSS' : data.get(4) == NOUGHTS ? 'NOUGHT' : ''}">
            ${data.get(4).getSign()}
        </td>
        <td onclick="window.location='/logic?click=5'" class="${data.get(5) == CROSSES ? 'CROSS' : data.get(5) == NOUGHTS ? 'NOUGHT' : ''}">
            ${data.get(5).getSign()}
        </td>
    </tr>
    <tr>
        <td onclick="window.location='/logic?click=6'" class="${data.get(6) == CROSSES ? 'CROSS' : data.get(6) == NOUGHTS ? 'NOUGHT' : ''}">
            ${data.get(6).getSign()}
        </td>
        <td onclick="window.location='/logic?click=7'" class="${data.get(7) == CROSSES ? 'CROSS' : data.get(7) == NOUGHTS ? 'NOUGHT' : ''}">
            ${data.get(7).getSign()}
        </td>
        <td onclick="window.location='/logic?click=8'" class="${data.get(8) == CROSSES ? 'CROSS' : data.get(8) == NOUGHTS ? 'NOUGHT' : ''}">
            ${data.get(8).getSign()}
        </td>
    </tr>
</table>



<hr>
<c:set var="CROSSES" value="<%=Sign.CROSS%>"/>
<c:set var="NOUGHTS" value="<%=Sign.NOUGHT%>"/>
<c:set var="data" value="${sessionScope.data}" />

<!--
buttons to restart
-->
<c:if test="${winner != null}">
    <h1>${winner.getSign()} WINS!</h1>
    <form action="/restart" method="post">
        <button type="submit">Start Again</button>
    </form>
</c:if>
<c:if test="${draw}">
    <h1>IT'S A DRAW</h1>
    <br>
    <form action="/restart" method="post">
        <button type="submit">Start Again</button>
    </form>
</c:if>

<script>
    /*/sending post to server*/
    function restart() {
        $.ajax({
            url: '/restart',
            type: 'POST',
            contentType: 'application/json;charset=UTF-8',
            async: false,
            success: function () {
                location.reload();
            }
        });
    }

</script>

</body>
</html>