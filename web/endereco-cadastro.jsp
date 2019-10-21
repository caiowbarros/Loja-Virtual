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
    
<div class="end-add-container">    
    <form action="EnderecoController" method="post">
          
        <div class="end-group-container">
            <div class="group">      
                <input value="${endereco.getName()}" name="name" required type="text" maxlength="255" />
                <span class="highlight"></span>
                <span class="bar"></span>
                <label>Descrição do endereço</label>
            </div>
            
            <div class="group">      
                <input value="${endereco.getZipcode()}" name="zipcode" required type="number" maxlength="11" id="cep" onblur="pesquisacep(this.value);"/>
                <span class="highlight"></span>
                <span class="bar"></span>
                <label>CEP</label>
            </div>
                
            <div class="group">      
                <input value="${endereco.getAddress()}" name="address" required type="text" id="rua" maxlength="255" />
                <span class="highlight"></span>
                <span class="bar"></span>
                <label>Endereço</label>
            </div>
                
            <div class="group">      
                <input value="${endereco.getCity()}" name="city" required type="text" id="cidade"  maxlength="255" />
                <span class="highlight"></span>
                <span class="bar"></span>
                <label>Cidade</label>
            </div>
                
            <div class="group">      
                <input value="${endereco.getState()}" name="state" required type="text" id="uf" maxlength="255" />
                <span class="highlight"></span>
                <span class="bar"></span>
                <label>Estado</label>
            </div>
                
            <div class="group">      
                <input name="country" required type="text" readonly value="Brasil" maxlength="255" />
                <span class="highlight"></span>
                <span class="bar"></span>
                <label></label>
            </div>
        </div>
            
        <button class="standard-btn" name="action" formnovalidate value="unsel">Voltar</button>
        <button class="standard-btn" name="action" value="grava" type="submit">Salvar</button>   
        <% if (!sel.equals("")) { %>
        <button class="standard-btn" type="submit" name="action" value="del" formnovalidate onclick="return confirm('Tem certeza que deseja excluir esse endereço?');false;">Apagar</button>
        <% }%>
            
    </form>
</div>
        
        
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