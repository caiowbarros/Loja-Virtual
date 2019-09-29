<%-- 
    Document   : endereco
    Created on : 28/09/2019, 23:42:18
    Author     : HP
--%>
<%
    // se n tiver um usuario logado retorna p controller
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("UserController?redirect=EnderecoController");
    }
    if (request.getAttribute("msg") != null) {
        out.println("<script>alert('" + request.getAttribute("msg") + "');</script>");
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Cadastro de Endereços"/>
</jsp:include>
<a href="EnderecoController?unsel">Voltar</a>
<form action="EnderecoController">
    <fieldset>
        <legend>Endereço</legend>
        <!--onblur ocorre qnd o objeto perde o foco-->
        <input name="zipcode" required type="number" placeholder="CEP" maxlength="11" id="cep" onblur="pesquisacep(this.value);"/>
        <input name="name" required type="text" placeholder="Apelido Curto para o Endereço" maxlength="255" />
        <input name="address" required type="text" id="rua" placeholder="Endereço" maxlength="255" />
        <input name="city" required type="text" id="cidade"  placeholder="Cidade" maxlength="255" />
        <input name="state" required type="text" id="uf" placeholder="Estado" maxlength="255" />
        <input name="country" required type="text" placeholder="País" readonly value="Brasil" maxlength="255" />
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