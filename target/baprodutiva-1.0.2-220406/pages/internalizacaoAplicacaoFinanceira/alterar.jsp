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
        <html:javascript formName="internalizacaoAplicacaoFinanceiraForm" method="validar" />
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Alterar Aplicação Financeira</h2>
                <html:form action="/InternalizacaoAplicacaoFinanceira.do?metodo=alterar" onsubmit="return validar(this);" >
                    <html:hidden property="id"/>
                    <table>
                        <tr>
                            <td>Descrição: </td>
                            <td><html:text property="descricao" size="50" maxlength="100" styleClass="requerido" /> </td>
                        </tr>
                        <tr>
                            <td>Data da Entrada: </td>
                            <td><html:text property="entrada" size="10" maxlength="10" styleClass="requerido" /> </td>
                        </tr>
                        <tr>
                            <td>Valor em real: </td>
                            <td><html:text property="valor" size="15" maxlength="15" styleClass="requerido" /> (Ex.: 1500.50)</td>
                        </tr>
                        <tr>
                            <td>Tipo Fonte: </td>
                            <td>
                                <html:select property="tipoFonteId">
                                    <html:option value="">Selecione:</html:option>
                                    <html:optionsCollection value="id" label="descricao" name="tiposFonte" />
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center">
                                <acesso:verificaPermissao funcionalidade="mtb_fin">
                                    <html:submit value="salvar" styleClass="botao" /> &nbsp;
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
