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

        <html:javascript formName="periodoForm" method="validar"/>
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
        <script>

            function validaData() {
                var a = null;
                
                a =  document.getElementById("dataInicial").value.split("/");
                var dtInicial = new Date( a[2] + "/" + a[1] + "/" + a[0] );
                
                a =  document.getElementById("dataFinal").value.split("/");
                var dtFinal   = new Date( a[2] + "/" + a[1] + "/" + a[0] );

                if ( dtInicial > dtFinal ) {
                    alert( "A data inicial não pode ser maior do que a data final!" );
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Alterar Período</h2>
                <html:form action="/Periodo.do?metodo=alterar" onsubmit="return validar(this);" >
                    <html:hidden property="id" />
                    <table>
                        <tr>
                            <td>POA:</td>
                            <td align="left">
                                <html:select styleClass="requerido" property="poaId">
                                    <html:option value="" >Selecione:</html:option>
                                    <html:optionsCollection name="poas" value="id" label="descricao" />
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td>Data Inicial: </td>
                            <td><html:text property="dataInicial" styleId="dataInicial" readonly="true" size="10" maxlength="10" styleClass="requerido" /><img  alt="Selecione uma data" src="img/seletorData.png" onclick="return showCalendar('dataInicial', '%d/%m/%Y');"/></td>
                        </tr>
                        <tr>
                            <td>Data Final: </td>
                            <td><html:text property="dataFinal" styleId="dataFinal" readonly="true" size="10" maxlength="100" styleClass="requerido" /><img  alt="Selecione uma data" src="img/seletorData.png" onclick="return showCalendar('dataFinal', '%d/%m/%Y');"/></td>
                        </tr>                        
                        <tr>
                            <td>Descrição: </td>
                            <td><html:text property="descricao" size="50" maxlength="100" styleClass="requerido" /> </td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center">
                                <acesso:verificaPermissao funcionalidade="mtb_proj">
                                    <html:submit value="alterar" onclick="return validaData();" styleClass="botao" /> &nbsp;
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
