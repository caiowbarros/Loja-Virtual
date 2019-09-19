<%-- 
    Document   : test
    Created on : Sep 19, 2019, 7:37:44 PM
    Author     : felipe
--%>

<%@page import="br.uff.models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test Page</title>
    </head>
    <body>
        <h1>Hello World! Uff</h1>
        <% User user = (User) request.getAttribute("user"); %>
        <% System.out.println("User: " + String.valueOf(user)); %>
        <h4><%= user.getEmail() %></h4>
    </body>
</html>
