<%-- 
    Document   : test
    Created on : Sep 19, 2019, 7:37:44 PM
    Author     : felipe
--%>

<%@page import="java.util.HashMap"%>
<%@page import="br.uff.models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test Page</title>
    </head>
    <body>
        <div id="select-test" style="border: solid; border-radius: 8px; padding: 8px;" >
            <h2>Select Test:</h2>
            <% User user = (User) request.getAttribute("user"); %>
            <h4>Nome: <%= user.getName() %></h4>
            <h4>Email: <%= user.getEmail() %></h4>
            <h4>Cargo: <%= user.getEmail() %></h4>
        </div>
        <br/>
        <div id="create-test" style="border: solid; border-radius: 8px; padding: 8px;" >
            <h2>Create Test:</h2>
            <jsp:include page="partials/test/form.jsp">
                <jsp:param name="action" value="create" />
            </jsp:include>
        </div>
        <br/>
        <div id="update-test" style="border: solid; border-radius: 8px; padding: 8px;" >
            <h2>Update Test:</h2>
            <jsp:include page="partials/test/form.jsp">
                <jsp:param name="action" value="update" />
            </jsp:include>
        </div>
        <br/>
        <div id="delete-test" style="border: solid; border-radius: 8px; padding: 8px;" >
            <h2>Delete Test:</h2>
            <form id="delete-form">
                
            </form>
        </div>
    </body>
</html>
