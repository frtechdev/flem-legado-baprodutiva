<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
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
        <script language="JavaScript">
            function submitFiltro(){
                document.forms[0].action = "<%=request.getContextPath()%>/DespesaOrdenada.do?metodo=filtrar";
                document.forms[0].submit();
                document.forms[0].action = "<%=request.getContextPath()%>/DespesaOrdenada.do?metodo=salvar";
            }
        </script>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Lista de Despesas Ordenadas</h2>
                <jsp:include flush="false" page="/inc/msg_real.jsp" />
                <html:form action="/DespesaOrdenada.do" >
                    <table>
                        <tr>
                            <td>
                                <html:text property="data" size="15" value="${filtro_data}"/> (Ex.: 01/01/2008)
                                &nbsp;
                                <input type="button" value="filtrar" onclick="submitFiltro()" class="botao"/>
                            </td>
                        </tr>
                    </table>
                                            
                    <display:table id="comp" name="lista" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./DespesaOrdenada.do?metodo=listarDespesasGEM" pagesize="30" class="tabelaDisplay">
                        <display:column property="data" title="Data" format="{0,date,dd/MM/yyyy}"/>
                        <display:column property="nomeFornecedor" title="Fornecedor" />
                        <display:column property="descricao" title="Descrição" />
                        <display:column property="valor" title="Valor" format="R$ {0,number,#,##0.00}" />
                        <display:column title="Ordem">
                            <html:text property="ordem" size="3" value="${comp.ordem}"/>
                            <html:hidden property="apdId" value="${comp.apdId}"/>
                            <html:hidden property="apdTp" value="${comp.apdTp}"/>
                            <html:hidden property="seqLinha" value="${comp.seqLinha}"/>
                        </display:column>
                    </display:table>
                    
                    <div style="width:100%; text-align:center;">
                        <acesso:verificaPermissao funcionalidade="mtb_fin">
                            <html:submit property="metodo" value="salvar" styleClass="botao"/>
                        </acesso:verificaPermissao>
                    </div>
                </html:form>
            </div> 
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
