<%-- 
    Document   : form
    Created on : Oct 7, 2019, 6:51:00 PM
    Author     : felipe
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="br.uff.models.BaseModel"%>
<%@page import="br.uff.models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <% User user = (User) request.getAttribute("user"); %>
    <body>
        <div>
            <% String userId = user == null ? "new" : String.valueOf(user.getId()); %>
            <form action="test" id=<%= request.getParameter("action") + "form" + userId  %> >
                <label>Nome: </label><input type="text" name="name" value=<%= user.getName() %>><br/>
                <label>Email: </label><input type="text" name="email" value=<%= user.getEmail() %>><br/>
                <% if(request.getParameter("action").equals("create")) { %>
                    <label>Senha: </label><input type="password" name="password"><br/>
                    <label>Confirme a senha: </label><input type="password" name="password-confirm"><br/>
                <% } %>
                <select name="role">
                    <% for (BaseModel role : (ArrayList<BaseModel>) request.getAttribute("roles")) { %>
                    <option value="<%= role.getAttribute("id") %>"><%= role.getAttribute("name") %></option>
                    <% } %>  
                </select>
                <input type="submit" value="Submit">
                <input type="reset">
            </form>
        </div>
    </body>
</html>
