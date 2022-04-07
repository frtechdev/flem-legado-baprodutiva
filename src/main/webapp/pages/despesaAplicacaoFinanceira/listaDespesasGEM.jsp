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
                <h2>Lista de Despesas Elegíveis</h2>
                <display:table id="comp"  name="lista" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./DespesaAplicacaoFinanceira.do?metodo=listarDespesasGEM" pagesize="30" class="tabelaDisplay">
                    <display:column property="data" title="Data" format="{0,date,dd/MM/yyyy}"/>
                    <display:column property="descricao" title="Descrição" />
                    <display:column property="valor" title="Valor" format="R$ {0,number,#,##0.00}" />
                    <display:column title="" >
                        <acesso:verificaPermissao funcionalidade="mtb_fin">
                            <a href="./DespesaAplicacaoFinanceira.do?metodo=pagar&apdId=${comp.apdId}&apdTp=${comp.apdTp}"><img align="middle" src="img/calc.png" width="22" height="22" border="0" alt="Salvar Despesa de APlicação Financeira"/></a>
                        </acesso:verificaPermissao>
                    </display:column>
                </display:table>

                <div style="width:100%; text-align:center;">
                    <html:button property="" value="Voltar" onclick="location.href='DespesaAplicacaoFinanceira.do'" styleClass="botao" /> 
                </div>
            </div> 
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
