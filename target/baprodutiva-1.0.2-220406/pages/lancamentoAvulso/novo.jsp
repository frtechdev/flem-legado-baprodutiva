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
        <!--html:javascript formName="lancamentoAvulsoForm" method="validar" /-->
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Data de exibição do lançamento</h2>
                <jsp:include flush="false" page="/inc/msg_real.jsp" />
                <html:form action="/LancamentoAvulso.do?metodo=inserir" method="post" onsubmit="return validar(this)">
                    <table>
                        <tr>
                            <td>Código do item:</td>
                            <td><html:text property="lancamentoAvulso.codigoItem" size="30" maxlength="20" styleClass="requerido" /></td>
                        </tr>
                        <tr>
                            <td>Nome do fornecedor:</td>
                            <td><html:text property="lancamentoAvulso.nomeForn" size="60" maxlength="50" styleClass="requerido" /></td>
                        </tr>
                        <tr>
                            <td>Descrição do lançamento:</td>
                            <td><html:text property="lancamentoAvulso.descricao" size="60" maxlength="50" styleClass="requerido" /></td>
                        </tr>
                        <tr>
                            <td>Número do contrato:</td>
                            <td><html:text property="lancamentoAvulso.numeroContrato" size="30" maxlength="20" styleClass="requerido" /></td>
                        </tr>
                        <tr>
                            <td>Data de pagamento:</td>
                            <td><html:text property="dataPagamento" styleId="dataPagamento" size="20" maxlength="10" styleClass="requerido" readonly="true" /><img alt="Selecione uma data" src="img/seletorData.png" onclick="return showCalendar('dataPagamento', '%d/%m/%Y');"/></td>
                        </tr>
                        <tr>
                            <td>Valor do pagamento (R$):</td>
                            <td><html:text property="lancamentoAvulso.valorPagamento" size="20" maxlength="10" styleClass="requerido" /> (Ex.: 1500.50)</td>
                        </tr>
                        <tr>
                            <td>Centro de custo:</td>
                            <td><html:text property="lancamentoAvulso.centroCusto" size="30" maxlength="20" styleClass="requerido" /></td>
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
