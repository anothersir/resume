<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 5/20/2020
  Time: 6:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Why are you here?</title>
    </head>
    <body>
    <%
        String msg = request.getParameter("msg");
    %>
    <center>
        <%=msg%>
    </center>
    </body>
</html>
