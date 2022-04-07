<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib uri="http://flem.org.br/mensagem-tag" prefix="msg"%>
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
                
                <h2>Lista de Perdas cambiais</h2>
                <html:form action="/PerdaCambial.do" method="post">
                    <div style="width:100%; text-align:right;">
                        <acesso:verificaPermissao funcionalidade="mtb_fin">
                            <html:button property="" value="Novo" onclick="location.href='PerdaCambial.do?metodo=novo'" styleClass="botao" />
                            &nbsp;
                            <html:submit property="metodo" value="excluir" styleClass="botao" onclick="if(confirm('Deseja realmente excluir o(s) registro(s) selecionado(s)?')){return true;} return false;"/>
                        </acesso:verificaPermissao>
                    </div>
                    
                    <display:table id="pc" name="lista" sort="list" export="false" excludedParams="metodo" requestURI="./PerdaCambial.do" pagesize="30" class="tabelaDisplay">
                        <display:column> <input type="checkbox" name="ids_exclusao" value="${pc.id}"/></display:column>
                        <display:column property="valor" title="Valor" />
                        <display:column property="data" title="Data de exibição" format="{0,date,dd/MM/yyyy}"/>
                        <display:column style="width: 10%; text-align: center;" title="" href="./PerdaCambial.do?metodo=selecionar" paramId="id" paramProperty="id" ><img align="middle" src="img/edit.png" width="22" height="22" border="0" alt="Alterar"/></display:column>
                    </display:table>
                </html:form>
            </div>
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
