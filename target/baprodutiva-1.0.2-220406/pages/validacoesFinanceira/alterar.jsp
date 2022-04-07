<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page contentType="text/html" errorPage="/erro.jsp"%>
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
                <h2>Alterar Compromisso</h2>
                <table>
                    <html:form action="/ValidacoesFinanceira.do?metodo=alterar">
                        <html:hidden property="apdId" />
                        <html:hidden property="apdTp" />
                        <tr><td>Descricao:</td>
                            <td><html:text property="descricao" disabled="true" size="50" maxlength="150"/></td>
                        </tr>
                        <tr><td>Valor: </td>
                            <td>
                                <html:text property="valor" disabled="true" size="11" maxlength="10"/>
                            </td>
                        </tr>
                        <tr>  <td>Classificação:</td>
                            <td>

                                <html:select styleId="tipo" styleClass="requerido" property="id">
                                    <html:option value="">-- Selecione --</html:option>
                                    <html:option value="CW">CW - Obras</html:option>
                                    <html:option value="GO">GO - Bens</html:option>
                                    <html:option value="CS">CS - Serviços de Consultorias</html:option>
                                    <html:option value="TR">TR - Treinamento</html:option>
                                    <html:option value="CO">CO - Custos Operacionais</html:option>
                                </html:select>
                            </td>
                        </tr>

                        <tr><td>Número Contrato:</td>
                            <td>
                                <html:select property="numeroContrato" >
                                    <html:option value="">Selecione: </html:option>
                                    <html:options name="numeroCont"  />
                                </html:select>
                            </td>
                        </tr>
                        <tr><td>Número Cliente Connect:</td>
                            <td><html:text property="numeroClienteConnect" size="09" maxlength="150" styleClass="requerido"/></td>
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

        <msg:alert escopo="session"/>

    </body>
</html>
