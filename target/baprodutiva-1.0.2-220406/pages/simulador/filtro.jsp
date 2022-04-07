<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://flem.org.br/acesso-tag" prefix="acesso"%>
<%@ taglib uri="http://flem.org.br/mensagem-tag" prefix="msg"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
        <html:javascript formName="simuladorForm" method="validar"/>
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Simulador de Relatórios Financeiros</h2>
                <html:form action="/Simulador.do" onsubmit="return validar(this);" >
                    <table>
                        <tr>
                            <td>Data Início: </td>
                            <td><html:text property="dataInicio" size="10" maxlength="10" styleClass="requerido" /> Ex.: 01/01/2008</td>
                        </tr>
                        <tr>
                            <td>Data Fim: </td>
                            <td><html:text property="dataFim" size="10" maxlength="10" styleClass="requerido" /> Ex.: 01/01/2008</td>
                        </tr>
                        
                        <tr>
                            <td colspan="2" align="center">
                                <acesso:verificaPermissao funcionalidade="mtb">
                                    <html:submit property="metodo" value="soe" styleClass="botao" /> &nbsp;
                                    <html:submit property="metodo" value="folha_resumo" styleClass="botao" /> &nbsp;
                                    <html:submit property="metodo" value="resumo_gastos" styleClass="botao" />&nbsp;
                                    <html:submit property="metodo" value="soe_listao" styleClass="botao" />&nbsp;
                                    <html:submit property="metodo" value="gastos_operacionais" styleClass="botao" />
                                </acesso:verificaPermissao>
                            </td>
                        </tr>
                    </table>
                </html:form>
            </div> 
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
