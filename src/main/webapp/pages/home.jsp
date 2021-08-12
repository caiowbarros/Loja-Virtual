<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="br.uff.loja.core.dtos.ProdutoHomeDTO"%>
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

<!-- Slideshow container -->
<div class="slideshow-container">

    <!-- Full-width images with caption text -->

    <%
    List<ProdutoHomeDTO> jogosVitrine = new ArrayList<>();
    if (request.getAttribute("jogosVitrine") != null) {
        jogosVitrine = (List<ProdutoHomeDTO>) request.getAttribute("jogosVitrine");
    }

    for (ProdutoHomeDTO produto : jogosVitrine) {
    %>
        <div class="mySlides fade">
            <img src="<%= produto.getImagem() %>" alt="<%= produto.getNome() %>" style="width:100%">
            <div class="text" onclick="location.href='produto?id=<%= produto.getId() %>';">Adquira o Jogo</div>
        </div>
    <%
    }
    %>

    <!-- Next and previous buttons -->
    <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
    <a class="next" onclick="plusSlides(1)">&#10095;</a>
</div>
<br>

<!-- The dots/circles -->
<div style="text-align:center">
    <span class="dot" onclick="currentSlide(1)"></span>
    <span class="dot" onclick="currentSlide(2)"></span>
    <span class="dot" onclick="currentSlide(3)"></span>
</div>

<script>
var slideIndex = 1;
showSlides(slideIndex);

// Next/previous controls
function plusSlides(n) {
    showSlides(slideIndex += n);
}

// Thumbnail image controls
function currentSlide(n) {
    showSlides(slideIndex = n);
}

function showSlides(n) {
    var i;
    var slides = document.getElementsByClassName("mySlides");
    var dots = document.getElementsByClassName("dot");
    if (n > slides.length) {slideIndex = 1}
    if (n < 1) {slideIndex = slides.length}
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" active", "");
    }
    slides[slideIndex-1].style.display = "block";
    dots[slideIndex-1].className += " active";
}    
</script>

<div class="sec-container">
    <div class="sec-img">
        <img alt="Playstation" src="img/ps4.jpg">
        <div class="img-btn"><a href="ProdutosController?categoryId=1">Playstation</a></div>
    </div>
    <div class="sec-img">
        <img alt="Xbox" src="img/xone.jpg">
        <div class="img-btn"><a href="ProdutosController?categoryId=2">Xbox</a></div>
    </div>
    <div class="sec-img">
        <img alt="Wii" src="img/wii.jpg">
        <div class="img-btn"><a href="ProdutosController?categoryId=3">Wii</a></div>
    </div>
</div>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>