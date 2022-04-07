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
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Lista de Despesas Inelegíveis</h2>
                <jsp:include flush="false" page="/inc/msg_real.jsp" />
                <html:form action="/DespesaInelegivel.do" method="post">
                    <div style="width:100%; text-align:right;">
                        <acesso:verificaPermissao funcionalidade="mtb_fin">
                            <html:button property="" value="Ineleger" onclick="location.href='DespesaInelegivel.do?metodo=listarDespesasGEM'" styleClass="botao" />
                             &nbsp;
                            <html:submit property="metodo" value="excluir" styleClass="botao" onclick="if(confirm('Deseja realmente excluir o(s) registro(s) selecionado(s)?')){return true;} return false;"/>
                        </acesso:verificaPermissao>
                    </div>
                        
                        <display:table id="despesa" name="lista" defaultsort="2" sort="list" export="false" excludedParams="metodo" requestURI="./DespesaInelegivel.do" pagesize="30" class="tabelaDisplay">
                            <display:column > <input type="checkbox" name="ids_exclusao" value="${despesa.id}"/></display:column>
                            <display:column property="entrada" title="Data" format="{0,date,dd/MM/yyyy}"/>
                            <display:column property="descricao" title="Descrição" />
                            <display:column property="motivo" title="Motivo" />
                            <display:column property="valor" title="Valor" format="R$ {0,number,#,##0.00}" />
                        </display:table>
                        
                        </html:form>
                    </div> 
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
