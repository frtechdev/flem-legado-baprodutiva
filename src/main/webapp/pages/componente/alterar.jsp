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
        <script src="<%=request.getContextPath()%>/dwr/interface/Funcoes.js"></script>
        <script src="<%=request.getContextPath()%>/dwr/engine.js"></script>
        <script src="<%=request.getContextPath()%>/dwr/util.js"></script>
        <html:javascript formName="componenteForm" method="validar" page="1"/>
        <title><fmt:message key="aplicacao.nome" /> vers�o: <fmt:message key="aplicacao.versao" /></title>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Alterar Componente</h2>
                <%--jsp:include flush="false" page="/inc/msg_dolar.jsp" /--%>
                <html:form action="/Componente.do?metodo=alterar" onsubmit="return validar(this);" >
                    <html:hidden property="id"/>
                    <table>
                        <tr>
                            <td>Descri��o: </td>
                            <td><html:text property="descricao" size="85" maxlength="150" styleClass="requerido" /> </td>
                        </tr>
                        <tr>
                            <td>Este Componente pode conter SubProjetos?: </td>
                            <td>
                                <html:select property="subProjetos" styleClass="requerido"> 
                                    <html:option value="false">N�o</html:option>
                                    <html:option value="true">Sim</html:option>
                                </html:select>
                            </td>
                        </tr>
                       <tr>
                            <td>Planejado em d�lar:</td>
                            <td><html:text property="gefPlanejado" style="text-align: right" size="20" maxlength="30" styleClass="requerido" /> (Ex.: 1600000.00)</td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center">
                                <acesso:verificaPermissao funcionalidade="mtb_proj">
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
