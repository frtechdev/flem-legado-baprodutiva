<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page contentType="text/html" errorPage="/erro.jsp"%>
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
                <h2>Revisar contrapartida</h2>
                <html:form action="/ValidacaoProjeto.do?metodo=alterarContrapartida" onsubmit="return validar(this);" >
                    <html:hidden property="id" />
                    <table>
                        <tr>
                            <td>Atividade: </td>
                            <td><html:text property="atividade" size="100" readonly="true" /></td>
                        </tr>
                        <tr>
                            <td>SubAtividade: </td>
                            <td><html:text property="subatividade" size="100" readonly="true" /></td>
                        </tr>
                        <tr>
                            <td>Período: </td>
                            <td><html:text property="dataInicial" size="10" readonly="true" /> à <html:text property="dataFinal" size="10" readonly="true" /></td>
                        </tr>
                        <tr>
                            <td>Valor: </td>
                            <td><html:text property="valor" style="text-align: right" size="20" maxlength="30" styleClass="requerido" /> (Ex.: 1500.50)</td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center">
                                <acesso:verificaPermissao funcionalidade="mtb_proj">
                                    <html:submit value="alterar" styleClass="botao" /> &nbsp;
                                </acesso:verificaPermissao>
                                <html:button property="" value="voltar" onclick="javascript: history.go(-1);" styleClass="botao" />
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
