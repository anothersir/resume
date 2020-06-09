<%-- 
    Document   : user
    Created on : 11 мая 2020 г., 21:49:14
    Author     : User
--%>

<%@page import="com.mycompany.main.Context"%>
<%@page import="com.mycompany.dao.inter.UserDaoInter"%>
<%@page import="com.mycompany.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
//            if(request.getAttribute("owner")==null) {
//                response.sendRedirect("error.jsp?msg=not found");
//                return;
//            }
            User u = (User) request.getAttribute("user");
//            try {
//                u = UserRequestUtil.processRequest(request, response);
//            } catch (Exception e){
//                response.sendRedirect("error.jsp?msg=" + e.getMessage());
//                return;
//            }
        %>
        <div>
            <form action="userdetail" method="POST">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="<%=u.getId()%>"/>
                <label>name:</label>
                <input type="text" name="name" value="<%=u.getName()%>"/>
                <br>
                <label>surname:</label>
                <input type="text" name="surname" value="<%=u.getSurname()%>"/>

                <input type="submit" name="save" value="Save"/>
            </form>
        </div>
    </body>
</html>
