<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 5/25/2020
  Time: 8:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Admin Login</title>
        <link rel="stylesheet" href="assets/css/AdminLogin.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
              integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    </head>
    <body class="login_background">
    <form action="login" method="POST">
        <div class="col-4 container login_fix_">
            <center>
                <h1>Login:</h1>
            </center>
            <div class="form-group">
                <label>Email adress</label>
                <input type="email" class="form-control" placeholder="email@example.com" name="email">
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" class="form-control" placeholder="Password" name="password">
            </div>
            <button class="btn btn-primary" name="login">Login</button>
        </div>
    </form>
    </body>
</html>
