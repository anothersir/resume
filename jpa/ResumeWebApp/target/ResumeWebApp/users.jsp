<%-- 
    Document   : user
    Created on : 11 мая 2020 г., 21:49:14
    Author     : User
--%>

<%@page import="com.mycompany.main.Context" %>
<%@page import="com.mycompany.dao.inter.UserDaoInter" %>
<%@page import="com.mycompany.entity.User" %>
<%@page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/css/users.css" type="text/css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
        <script src="https://kit.fontawesome.com/0a155c6a7f.js" crossorigin="anonymous"></script>
        <script src="assets/js/users.js"></script>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </head>
    <body>
    <%
        User user = (User) session.getAttribute("loggedInUser");

        List<User> list = (List<User>) request.getAttribute("userlist");

    %>

    <%="Welcome " + user.getName()%>

    <div class="container mycontainer">
        <div class="row">
           <div class="col-4">
               <form action="users" method="GET">
                   <div class="form-group">
                       <label>name:</label>
                       <input id="whatImTypeing" onkeyup="writeMyText()" class="form-control" type="text" name="name" />
                   </div>
                   <div class="form-group">
                       <label>surname:</label>
                       <input class="form-control" type="text" name="surname" value=""/>
                   </div>
                   <input id="btnSearch" class="btn btn-primary" type="submit" name="search" value="Search"/>
               </form>
           </div>
        </div>
        <div>
            <table class="table">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Nationality</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <%
                    for (User u : list) {
                %>
                <tr>
                    <td><%=u.getName()%></td>
                    <td><%=u.getSurname()%></td>
                    <td><%=u.getNationality().getName() == null ? "N/A" : u.getNationality().getName()%></td>
                    <td style="width: 5px">
                            <input type="hidden" name="id" value="<%=u.getId()%>">
                            <input type="hidden" name="action" value="delete">
                            <button class="btn btn-danger" value="delete" data-toggle="modal" data-target="#exampleModal"
                            onclick="setIdForDelete(<%=u.getId()%>)">
                                <i  class="fas fa-trash-alt"></i>
                            </button>
                    </td>
                    <td style="width: 5px">
                        <form action="userdetail" method="GET">
                            <input type="hidden" name="id" value="<%=u.getId()%>">
                            <input type="hidden" name="action" value="update">
                            <button class="btn btn-secondary" value="update">
                                <i class="fas fa-pen-alt"></i>
                            </button>
                        </form>
                    </td>
                </tr>
                <%}%>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Delete process</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Are you sure?
                </div>
                <div class="modal-footer">
                    <form action="userdetail" method="POST">
                        <input type="hidden" name="id" value="" id="idForDelete">
                        <input type="hidden" name="action" value="delete">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button class="btn btn-danger">Delete</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>
