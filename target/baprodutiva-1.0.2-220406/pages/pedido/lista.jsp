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
                
                <h2>Lista de Pedidos</h2>
                <html:form action="/Pedido.do" method="post">
                    <div style="width:100%; text-align:right;">
                        <acesso:verificaPermissao funcionalidade="mtb_fin">
                            <html:button property="" value="Novo" onclick="location.href='Pedido.do?metodo=novo'" styleClass="botao" />
                            &nbsp;
                            <html:submit property="metodo" value="excluir" styleClass="botao" onclick="if(confirm('Deseja realmente excluir o(s) registro(s) selecionado(s)?')){return true;} return false;"/>
                        </acesso:verificaPermissao>
                    </div>
                    
                    <display:table id="pedido" name="lista"  export="false" excludedParams="metodo" requestURI="./Pedido.do" pagesize="30" class="tabelaDisplay">
                        <display:column style="width: 1%" > <input type="checkbox" name="ids_exclusao" value="${pedido.id}"/></display:column>
                        <display:column property="inicio" title="Data Início" format="{0,date,dd/MM/yyyy}"/>
                        <display:column property="fim" title="Data Fim" format="{0,date,dd/MM/yyyy}"/>
                        <display:column property="numero"  title="No. do Pedido" />
                        <display:column style="width: 10%; text-align: center;" title="Editar" href="./Pedido.do?metodo=selecionar" paramId="id" paramProperty="id" ><img align="middle" src="img/edit.png" width="22" height="22" border="0" alt="Alterar"/></display:column>
                        <display:column style="width: 10%; text-align: center;" title="SOE" href="./Pedido.do?metodo=imprimirSOE" paramId="id" paramProperty="id" ><img align="middle" src="img/planilha.png" width="22" height="22" border="0" alt="Imprimir SOE"/></display:column>
                        <display:column style="width: 10%; text-align: center;" title="Folha Resumo" href="./Pedido.do?metodo=imprimirFolhaResumo" paramId="id" paramProperty="id" ><img align="middle" src="img/planilha.png" width="22" height="22" border="0" alt="Imprimir Folha de Resumo"/></display:column>
                        <display:column style="width: 10%; text-align: center;" title="Resumo de Despesas" href="./Pedido.do?metodo=imprimirResumoGasto" paramId="id" paramProperty="id" ><img align="middle" src="img/planilha.png" width="22" height="22" border="0" alt="Imprimir Resumo de Despesas"/></display:column>
                        <display:column style="width: 10%; text-align: center;" title="Gastos Operacionais" href="./Pedido.do?metodo=gastosOperacionais" paramId="id" paramProperty="id" ><img align="middle" src="img/planilha.png" width="22" height="22" border="0" alt="Imprimir Gastos Operacionais"/></display:column>
                    </display:table>
                </html:form>
            </div>
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
