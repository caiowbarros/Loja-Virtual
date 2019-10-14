<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->

<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Home"/>
</jsp:include>
<%
    // mostra se tiver msg
    if (session.getAttribute("msg") != null) {
        String msg = session.getAttribute("msg").toString();
        session.setAttribute("msg", null);
        out.println("<script>alert('" + msg + "');</script>");
    }
%>
<div class="main-img">
    <figure>
        <img src="https://media.playstation.com/is/image/SCEA/playstation-plus-online-multiplayer-desktop-banner-01-us-22jan19?$native_nt$">
        <figcaption>
            <a href="ProdutosController?esp=lancamentos">Lançamentos</a>
        </figcaption>
    </figure>
</div>

<div class="sec-container">
    <div class="sec-img">
        <img src="https://icdn2.digitaltrends.com/image/digitaltrends/playstation-4-problems-header.jpg">
        <div class="img-btn"><a href="ProdutosController?categoryId=1">Playstation</a></div>
    </div>

    <div class="sec-img">
        <img src="https://media.wired.com/photos/5b22c58811196626aaeb168b/master/w_1536,c_limit/xbox-FA.jpg">
        <div class="img-btn"><a href="ProdutosController?categoryId=2">Xbox</a></div>
    </div>

    <div class="sec-img">
        <img src="https://www.blogcdn.com/www.engadget.com/media/2009/06/black-kuro-wii-juni-4,-2009.jpg">
        <div class="img-btn"><a href="ProdutosController?categoryId=3">Wii</a></div>
    </div>
</div>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>
