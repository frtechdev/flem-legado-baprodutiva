<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%--<%@page pageEncoding="ISO-8859-1"%> --%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
                <h2>Centro de Custos sem Atividades Associadas</h2>
                <display:table id="cc" name="listaCC" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./ValidacaoProjeto.do" pagesize="100" class="tabelaDisplay">
                    <display:column style="width: 20%" property="id" title="Número" />
                    <display:column style="width: 80%" property="descricao" title="Descrição" />
                </display:table>
                <br><br>
                <h2>Atividades Inconsistentes</h2>
                ${listaInconsistencias.size()}
                <display:table id="consistencia" name="listaInconsistencias" sort="list" export="false" excludedParams="metodo" requestURI="./ValidacaoProjeto.do" pagesize="100" class="tabelaDisplay">

                    <display:column style="width: 20%" property="subatividade.descricao" title="Atividade" />
                    <display:column style="width: 12%" title="POA">${consistencia.POA.descricao}</display:column>
                    <display:column style="width: 8%" title="Corrigir problema">
                        <c:if test="${consistencia.financeiro eq false}"><a href="./SubAtividadeFinanceiroPrevisto.do?id=${consistencia.subatividade.id}&metodo=selecionarSubAtividade">Adicionar planejamento financeiro</a></c:if>
                    </display:column>
                </display:table>
                <br><br>
            </div>
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>

        <msg:alert escopo="session"/>

    </body>
</html>
