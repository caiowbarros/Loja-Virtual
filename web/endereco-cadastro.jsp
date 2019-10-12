<%-- 
    Document   : endereco
    Created on : 28/09/2019, 23:42:18
    Author     : HP
--%>
<%@page import="br.uff.models.Address"%>
<%
    // se n tiver um usuario logado retorna p controller de usuario com redirect p EnderecoController
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("UserController?redirect=EnderecoController");
    }
    // mostra msg se tiver
    if (session.getAttribute("msg") != null) {
        String msg = session.getAttribute("msg").toString();
        session.setAttribute("msg", null);
        out.println("<script>alert('" + msg + "');</script>");
    }

    Address endereco = null;
    if (request.getAttribute("endereco") != null) {
        endereco = (Address) request.getAttribute("endereco");
    } else {
        response.sendRedirect("EnderecoController");
        return;
    }

    String sel = request.getParameter("sel");
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Cadastro de Endereços"/>
</jsp:include>
<form action="EnderecoController" method="post">
    <button name="action" formnovalidate value="unsel">Voltar</button>
    <fieldset>
        <legend>Endereço</legend>
        <!--onblur ocorre qnd o objeto perde o foco-->
        <input value="${endereco.getZipcode()}" name="zipcode" required type="number" placeholder="CEP" maxlength="11" id="cep" onblur="pesquisacep(this.value);"/>
        <input value="${endereco.getName()}" name="name" required type="text" placeholder="Apelido Curto para o Endereço" maxlength="255" />
        <input value="${endereco.getAddress()}" name="address" required type="text" id="rua" placeholder="Endereço" maxlength="255" />
        <input value="${endereco.getCity()}" name="city" required type="text" id="cidade"  placeholder="Cidade" maxlength="255" />
        <input value="${endereco.getState()}" name="state" required type="text" id="uf" placeholder="Estado" maxlength="255" />
        <input name="country" required type="text" placeholder="País" readonly value="Brasil" maxlength="255" />
        <% if (!sel.equals("")) { %>
        <button type="submit" name="action" value="del" formnovalidate onclick="return confirm('Tem certeza que deseja excluir esse endereço?');false;">Apagar</button>
        <% }%>
        <button name="action" value="grava" type="submit">Salvar</button>
    </fieldset>
</form>
<script type="text/javascript" >

    function limpa_formulário_cep() {
        //Limpa valores do formulário de cep.
        document.getElementById('rua').value = ("");
        document.getElementById('cidade').value = ("");
        document.getElementById('uf').value = ("");
    }

    function meu_callback(conteudo) {
        if (!("erro" in conteudo)) {
            //Atualiza os campos com os valores.
            document.getElementById('rua').value = (conteudo.logradouro);
            document.getElementById('cidade').value = (conteudo.localidade);
            document.getElementById('uf').value = (conteudo.uf);
        } //end if.
        else {
            //CEP não Encontrado.
            limpa_formulário_cep();
            alert("CEP não encontrado.");
        }
    }

    function pesquisacep(valor) {
        //Nova variável "cep" somente com dígitos.
        var cep = valor.replace(/\D/g, '');

        //Verifica se campo cep possui valor informado.
        if (cep != "") {
            //Expressão regular para validar o CEP.
            var validacep = /^[0-9]{8}$/;

            //Valida o formato do CEP.
            if (validacep.test(cep)) {
                //Preenche os campos com "..." enquanto consulta webservice.
                document.getElementById('rua').value = "...";
                document.getElementById('cidade').value = "...";
                document.getElementById('uf').value = "...";

                //Cria um elemento javascript.
                var script = document.createElement('script');

                //Sincroniza com o callback.
                script.src = 'https://viacep.com.br/ws/' + cep + '/json/?callback=meu_callback';

                //Insere script no documento e carrega o conteúdo.
                document.body.appendChild(script);

            } //end if.
            else {
                //cep é inválido.
                limpa_formulário_cep();
                alert("Formato de CEP inválido.");
            }
        } //end if.
        else {
            //cep sem valor, limpa formulário.
            limpa_formulário_cep();
        }
    }
    ;
</script>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>