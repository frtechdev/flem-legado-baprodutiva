<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://flem.org.br/mensagem-tag" prefix="msg" %>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
        <html:javascript formName="relFonteCategoriaForm" method="validar" page="1" />
        <title><fmt:message key="aplicacao.nome" /> vers?o: <fmt:message key="aplicacao.versao" /></title>
    </head>
    <body >
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Filtro do Relat?rio Planejamento</h2>
                <html:form action="/Relatorio.do?metodo=relatorioPlanejamento" onsubmit="return validar(this);" >
                    <table>
                        <tr>
                            <td>Planejamento:</td>
                            <td align="left">
                                <html:select styleClass="requerido" styleId="planejamentoId" property="planejamentoId">
                                    <html:option value="">Selecione:</html:option>
                                    <html:optionsCollection name="planejamentos" value="id" label="descricao" />
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center"><html:submit value="visualizar" styleClass="botao" /> &nbsp; <html:button property="" value="voltar" onclick="javascript: history.go(-1);" styleClass="botao" /></td>
                        </tr>
                    </table>
                </html:form>
            </div> 
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
