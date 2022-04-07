<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://flem.org.br/acesso-tag" prefix="acesso"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
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
                <h2>Associar Devolução à Compromisso</h2>
                <html:form action="/InternalizacaoDevolucao.do?metodo=filtrarCompromisso">
                    <html:hidden property="id"/>
                    <html:hidden property="tipo"/>
                    <html:hidden property="seqLinha"/>
                    <fieldset style="width: 500px;">
                        <legend>Devolução</legend>
                    <table>
                        <tr>
                         <td>Data:</td>
                         <td>${dataDev}</td>
                        </tr>
                        <tr>
                            <td> Descrição:</td>
                            <td>${devolucao.descricao}</td>
                        </tr>
                        <tr>
                            <td>Valor:</td>
                            <td>${valorDev}</td>
                        </tr> 
                    </table>
                    </fieldset>
                    <br><br>
                    <table>
                        <tr>
                            <td>Descrição:</td>
                            <td><html:text property="descricao" size="40" /> </td>
                        </tr>
                        <tr>
                            <td>Centro de Custo:</td>
                            <td><html:text property="centroCusto" size="15"/></td>
                        </tr>
                        <tr>
                            <td>Data Inicial:</td>
                            <td><html:text property="dataInicial" size="9" styleId="dataInicial"/><img  alt="Selecione uma data" src="img/seletorData.png" onclick="return showCalendar('dataInicial', '%d/%m/%Y');"/></td>
                        </tr>
                        <tr>
                            <td>Data Final:</td>
                            <td><html:text property="dataFinal" size="9" styleId="dataFinal"/><img  alt="Selecione uma data" src="img/seletorData.png" onclick="return showCalendar('dataFinal', '%d/%m/%Y');"/></td>
                        </tr>
                        <tr>
                            <td>
                                <html:submit value="filtrar" styleClass="botao" />
                            </td>
                        </tr>
                    </table>
                   <display:table id="compromisso" name="lista" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./InternalizacaoDevolucao.do?metodo=associarCompromisso" pagesize="30" class="tabelaDisplay">
                       <display:column property="data" title="Data" format="{0,date,dd/MM/yyyy}"/>
                        <display:column property="descricao" title="Descrição" />
                        <display:column property="centroCusto" title="Centro de Custo" />
                        <display:column property="numeroContrato" title="Num. Contrato" />
                        <display:column property="nomeFornecedor" title="Nome Fornecedor" />
                        <display:column property="valor" title="Valor" format="R$ {0,number,#,##0.00}" />
                        <display:column title="">
                            <acesso:verificaPermissao funcionalidade="mtb_fin">
                                <a href="./InternalizacaoDevolucao.do?metodo=reconhecer&idComp=${compromisso.apdId}&tipoComp=${compromisso.apdTp}&seqLinhaComp=${compromisso.seqLinha}"><img align="middle" src="img/select.png" width="22" height="22" border="0" alt="Associar devolução"/></a>
                            </acesso:verificaPermissao>
                        </display:column>
                    </display:table>
                </html:form>
            </div>
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
    </body>
</html>
