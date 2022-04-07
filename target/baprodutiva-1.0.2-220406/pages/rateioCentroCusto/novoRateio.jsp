<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://flem.org.br/acesso-tag" prefix="acesso"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
        <html:javascript formName="rateioCentroCustoForm" method="validar"/>
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Novo Rateio de Centro de Custo</h2>
                <html:form action="/RateioCentroCusto.do?metodo=adicionar" onsubmit="return validar(this);" >
                    <html:hidden property="rateioCentroCusto.apdId" value="${apdId}" />
                    <html:hidden property="rateioCentroCusto.apdTp" value="${apdTp}" />
                    <table>
                        <tr>
                            <td>Centro de Custo 1:</td>
                            <td><html:text property="rateioCentroCusto.centroCusto1" size="50" value="" maxlength="100" styleClass="requerido" /> </td>
                        </tr>
                        <tr>
                            <td>Centro de Custo 2:</td>
                            <td><html:text property="rateioCentroCusto.centroCusto2" size="50" value="" maxlength="100" styleClass="requerido" /> </td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center">
                                <acesso:verificaPermissao funcionalidade="mtb_fin">
                                    <html:submit value="adicionar" styleClass="botao" /> &nbsp;
                                </acesso:verificaPermissao>
                                <html:button property="" value="voltar" onclick="javascript: history.go(-1);" styleClass="botao" />
                            </td>
                        </tr>
                    </table>
                </html:form>
            </div> 
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
    </body>
</html>
