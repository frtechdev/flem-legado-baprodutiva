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
        <title><fmt:message key="aplicacao.nome" /> vers?o: <fmt:message key="aplicacao.versao" /></title>
        <script language="JavaScript">
            function confirmarExclusao() {

                var selecao = document.getElementsByName("ids_exclusao");               
                var marcado = false;

                for (i=0; i<selecao.length; i++){
                    if (selecao[i].checked == true) { marcado = true; break; }
                }
                
                if ( marcado )
                    return confirm('Deseja realmente excluir o(s) registro(s) selecionado(s)?');;

                alert("Nenhum registro selecionado para exclus?o!");
                return false;
            }
        </script>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                
                <h2>Lista de Planejamentos F?sico</h2>
                <br/>
                <p>Subatividade: <strong><c:out value="${subAtividade.descricao}"/></strong></p>
                <p>Produto: <strong><c:out value="${produto.descricao}"/></strong></p>
                <html:form action="/ProdutoFisicoPrevisto.do?metodo=excluir" styleId="formulario" method="post">
                    <div style="width:100%; text-align:right;">
                        <html:button property="" value="Novo" onclick="location.href='ProdutoFisicoPrevisto.do?metodo=novo'" styleClass="botao" /> 
                         &nbsp; 
                         <html:submit property="" value="Excluir" styleClass="botao" onclick="return confirmarExclusao();"/>
                        &nbsp;
                        <html:button property="" value="Voltar" onclick="location.href='Produto.do?'" styleClass="botao" />
                    </div>
                        
                        <display:table id="fisico" name="lista" defaultsort="2" sort="list" export="false" excludedParams="metodo" requestURI="./ProdutoFisicoPrevisto.do" pagesize="30" class="tabelaDisplay">
                            <display:column > <input type="checkbox" name="ids_exclusao" value="${fisico.id}"/></display:column>
                            <display:column property="periodo.descricao" title="Per?odo" />
                            <display:column property="descricao" style="width:35%" title="Descri??o"/>
                            <display:column property="porcentagem" title="Porcentagem" style="text-align: right; width:15%" format="{0,number,#,##0.00}%"/>
                            <display:column title="" href="./ProdutoFisicoPrevisto.do?metodo=selecionar" paramId="id" paramProperty="id" ><img align="middle" src="img/edit.png" width="22" height="22" border="0" alt="Alterar" title="Alterar"/></display:column>
                            </display:table>
                        </html:form>
                    </div> 
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
