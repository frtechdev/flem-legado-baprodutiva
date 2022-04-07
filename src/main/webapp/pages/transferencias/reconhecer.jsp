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
        <link href="<%=request.getContextPath()%>/css/calendar-blue.css" rel="stylesheet" type="text/css" />
        <script language="JavaScript" src="<%=request.getContextPath()%>/js/calendar.js"  type="text/javascript" ></script>
        <script language="JavaScript" src="<%=request.getContextPath()%>/js/calendar-pt.js"  type="text/javascript" ></script>
        <script language="JavaScript" src="<%=request.getContextPath()%>/js/calendar-setup.js"  type="text/javascript" ></script>        


        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>

    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Reconhecer Transferencia</h2>
                <html:form action="/Transferencia.do?metodo=associar" onsubmit="return validar(this);" >
                    <html:hidden property="id" />
                    <html:hidden property="apdId" />
                    <html:hidden property="apdTp" />
                    <html:hidden property="seqLinha" />
                    <table>
                        <tr>
                            <td>Entidade Fornecedor</td>
                            <td align="left">
                                <td><html:text property="nomeFornecedor" size="50" maxlength="100"  /> 
                            </td>
                        </tr>
                        <tr>
                            <td>Código Fornecedor</td>
                            <td align="left">
                                <td><html:text property="codigoFornecedor" size="15" maxlength="100"  /> 
                            </td>
                        </tr>
                        <tr>
                            <td>Centro de Custo </td>
                            <td align="left">
                             <td>
                                <html:text property="centroCusto" size="15" maxlength="100"  />
                            </td>
                        </tr>      
                        <tr>
                            <td>Descrição  </td>
                            <td align="left">
                             <td>
                                <html:text property="descricao" size="120" maxlength="100"  />
                            </td>
                        </tr> 
                        <tr>
                            <td>Valor: </td>
                            <td align="left">
                            <td><html:text property="valor" size="15" maxlength="100"  disabled="true" /> 
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center">
                                <acesso:verificaPermissao funcionalidade="mtb_proj">
                                    <html:submit value="Associar" styleClass="botao" /> &nbsp;
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
