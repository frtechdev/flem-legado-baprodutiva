<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://flem.org.br/mensagem-tag" prefix="msg"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
        <script type="text/javascript">
            function confirmarExclusao() {

                var selecao = document.getElementsByName("ids_exclusao");               
                var marcado = false;

                for (i=0; i<selecao.length; i++){
                    if (selecao[i].checked == true) { marcado = true; break; }
                }

                if ( marcado ) {
                    var confirmado = confirm('Deseja realmente excluir o(s) registro(s) selecionado(s)?');
                    return confirmado;
                }
                
                alert("Nenhum registro selecionado para exclusão!");
                return false;
            }
        </script>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Lista de Produtos</h2>
                <br/>
                <p>Subatividade: <strong><c:out value="${planejamento.descricaoAtividade}"/></strong></p>
                <html:form action="/Produto.do" method="post">
                    <div style="width:100%; text-align:right;">
                        <html:button property="" value="novo" onclick="location.href='Produto.do?metodo=novo'" styleClass="botao" />
                        &nbsp; 
                        <html:submit property="metodo" value="excluir" styleClass="botao" onclick="return confirmarExclusao();"/>
                        &nbsp;
                        <html:button property="" value="voltar" onclick="javascript: history.go(-1);" styleClass="botao" />
                    </div>
                        
                    <display:table id="produto" name="lista" defaultsort="2" sort="list" export="false" excludedParams="metodo" requestURI="./Produto.do" pagesize="30" class="tabelaDisplay">
                        <display:column style="width:8"> <input type="checkbox" name="ids_exclusao" value="${produto.id}"/></display:column>
                        <display:column property="descricao" style="width:40%" title="Descrição" />
                        <display:column property="unidadeMedida" style="width:40%" title="Unidade de Medida" />
                        <display:column property="meta" style="width:15%" title="Meta" />
                        <display:column title="" style="width:23" href="./ProdutoFisicoPrevisto.do?metodo=selecionarProduto" paramId="id" paramProperty="id" ><img align="middle" src="img/pda.png" width="22" height="22" border="0" alt="FisicoPrevisto" title="Fisico Previsto"/></display:column>
                        <display:column title="" style="width:23" href="./ProdutoFisicoExecutado.do?metodo=selecionarProduto" paramId="id" paramProperty="id" ><img align="middle" src="img/pda_black.png" width="22" height="22" border="0" alt="FisicoExecutado" title="Fisico Executado"/></display:column>
                        <display:column title="" style="width:23" href="./Produto.do?metodo=selecionar" paramId="id" paramProperty="id" ><img align="middle" src="img/edit.png" width="22" height="22" border="0" alt="Alterar" title="Alterar"/></display:column>
                        </display:table>
                    </html:form>
            </div> 
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
