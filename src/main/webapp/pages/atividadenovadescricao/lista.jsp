<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://flem.org.br/mensagem-tag" prefix="msg"%>
<%@taglib uri="http://flem.org.br/acesso-tag" prefix="acesso"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
        <title><fmt:message key="aplicacao.nome" /> vers�o: <fmt:message key="aplicacao.versao" /></title>
        <script language="JavaScript">
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
                
                alert("Nenhum registro selecionado para exclus�o!");
                return false;
            }            
            
        </script>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                
                <h2>Atividade: ${atividade.descricao}</h2>
                
                <html:form action="/AtividadeNovaDescricao.do" method="post">
                    <div style="width:100%; text-align:right;">
                        <acesso:verificaPermissao funcionalidade="mtb_proj">
                            <html:button property="" value="Novo" onclick="location.href='AtividadeNovaDescricao.do?metodo=novo'" styleClass="botao" />
                            &nbsp;
                            <html:submit property="metodo" value="excluir" styleClass="botao" onclick="return confirmarExclusao();"/>
                        </acesso:verificaPermissao>
                    </div>
                        
                        <display:table id="atividadeNovaDescricao" name="lista" defaultsort="2" defaultorder="descending" sort="list" export="false" excludedParams="metodo" requestURI="./AtividadeNovaDescricao.do" pagesize="30" class="tabelaDisplay">
                            <display:column > 
                            <input type="checkbox" name="ids_exclusao" value="${atividadeNovaDescricao.id}"/></display:column>
                            <display:column style="width: 20%" property="poa.descricao" title="POA" />
                            <display:column property="novaDescricao" title="Descri��o" />
                            <display:column title="" href="./AtividadeNovaDescricao.do?metodo=selecionar" paramId="id" paramProperty="id" ><img align="middle" src="img/edit.png" width="22" height="22" border="0" alt="Alterar"/></display:column>
                            </display:table>
                        </html:form>
                    </div> 
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
