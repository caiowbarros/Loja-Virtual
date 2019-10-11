<%-- 
    Document   : select
    Created on : 11/10/2019, 16:02:22
    Author     : HP
--%>
<%@page import="br.uff.dao.Components"%>
<%
    Components comp = new Components();
    String nameSelect = request.getParameter("nameSelect");
    boolean required = false;
    if (request.getParameter("required").equals("1")) {
        required = true;
    }
    String consulta = request.getParameter("consulta");
    String[] bind = null;
    if (request.getParameter("bind") != null) {
        bind = request.getParameter("bind").split(",");
    }
    String selectedValue = request.getParameter("selectedValue");
    String cssclass = request.getParameter("cssclass");
    String style = request.getParameter("style");
    String select = comp.mostraSelect(nameSelect, required, consulta, bind, selectedValue, cssclass, style);
%>
<%= select%>
