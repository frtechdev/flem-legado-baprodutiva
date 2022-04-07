<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://flem.org.br/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://flem.org.br/acesso-tag" prefix="acesso"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
        <html:recursos/>
        <html:javascript formName="perdaCambialForm" method="validar"/>
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Nova perda cambial</h2>
                <html:form action="/PerdaCambial.do?metodo=adicionar" onsubmit="return validar(this);" >
                    <table>
                        <tr>
                            <td>Valor em dólar </td>
                            <td><html:real property="perdaCambial.valor" size="15" maxlength="15" styleClass="requerido" /></td>
                        </tr>
                        <tr>
                            <td>Data de exibição </td>
                            <td><html:data property="perdaCambial.data" styleId="data" styleClass="requerido" size="10" maxlength="10" onfocus="return(mascaraFocus(this,'data'))" onblur="return(mascaraBlur(this))" onkeyup="return(mascaraData(this,'/',event))"/></td>
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
