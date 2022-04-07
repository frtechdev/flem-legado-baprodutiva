<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib uri="http://flem.org.br/mensagem-tag" prefix="msg"%>
<%@taglib uri="http://flem.org.br/acesso-tag" prefix="acesso"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
        <link href="<%=request.getContextPath()%>/css/calendar-blue.css" rel="stylesheet" type="text/css" />
        <script language="JavaScript" src="<%=request.getContextPath()%>/js/calendar.js"  type="text/javascript" ></script>
        <script language="JavaScript" src="<%=request.getContextPath()%>/js/calendar-pt.js"  type="text/javascript" ></script>
        <script language="JavaScript" src="<%=request.getContextPath()%>/js/calendar-setup.js"  type="text/javascript" ></script>
        <html:javascript formName="correcaoValorForm" method="validar" />
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Correção de valor do lançamento</h2>
                <html:form action="/CorrecaoValor.do?metodo=inserir" method="post" onsubmit="return validar(this)">
                    <html:hidden property="lancamento.apdId"/>
                    <html:hidden property="lancamento.apdTp"/>
                    <html:hidden property="lancamento.seqLinha"/>
                    <table>
                        <tr>
                            <td>Data:</td>
                            <td>${data}</td>
                        </tr>
                        <tr>
                            <td>Descrição:</td>
                            <td>${descricao}</td>
                        </tr>
                        <tr>
                            <td>Valor em real:</td>
                            <td>${valor}</td>
                        </tr>
                        <tr>
                            <td>Correção do valor:</td>
                            <td><html:text property="valorLancamento" value="" size="20" maxlength="10" styleClass="requerido" /> (Ex.: 1500.50)</td>
                        </tr>
                        <tr>
                            <td><html:button property="" value="Voltar" onclick="history.go(-1)" styleClass="botao" /></td>
                            <acesso:verificaPermissao funcionalidade="mtb_fin">
                                <td><html:submit property="" value="Salvar" styleClass="botao" /></td>
                            </acesso:verificaPermissao>
                        </tr>
                    </table>

                </html:form>
            </div>
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
