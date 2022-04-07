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
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
         <script language="JavaScript">
            function submitFiltro(){
                document.forms[0].action = "<%=request.getContextPath()%>/CorrecaoCC.do?metodo=filtrar";
                document.forms[0].submit();
            }
        </script>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Lista de despesas</h2>
                <html:form action="/CorrecaoCC.do" method="post">
                    <div style="width:100%; text-align:right;">
                        
                        <table>
                            <tr>
                                <td>Data: 
                                    <html:text property="data" styleClass="requerido" style="width: 100px" value="${filtro_data}"/>
                                    &nbsp;(Ex.: 01/01/2008)
                                </td>
                                <td>Descrição:
                                    <html:text property="descricao" styleClass="requerido" style="width: 100px" value="${filtro_descricao}"/>
                                    &nbsp;
                                </td>
                                <td>Valor:
                                    <html:text property="valor" styleClass="requerido" style="width: 100px" value="${filtro_valor}"/>
                                    &nbsp;(Ex.: 33039.23 / -1190.54)
                                </td>
                                <td>Centro de Custo:
                                    <html:text property="centroCusto" styleClass="requerido" style="width: 100px" value="${filtro_cCusto}"/>
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                                    <html:submit property="metodo" value="filtrar" onclick="submitFiltro()" styleClass="botao"/>
                                    &nbsp;
                <display:table id="lancamento" name="lista" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./CorrecaoCC.do" pagesize="30" class="tabelaDisplay">
                    <display:column property="data" title="Data" format="{0,date,dd/MM/yyyy}"/>
                    <display:column property="descricao" title="Descrição" />
                    <display:column property="valor" title="Valor" format="R$ {0,number,#,##0.00}" />
                    <display:column property="centroCusto" title="Centro de Custo"/>
                    <display:column title="Centro de Custo Anterior" >
                        <c:if test="${lancamento.centroCustoAnterior ne null}">${lancamento.centroCustoAnterior}</c:if>
                    </display:column>
                    <display:column title="" style="width: 45px">
                        <c:choose>
                            <c:when test="${lancamento.centroCustoAnterior eq null}">
                                <acesso:verificaPermissao funcionalidade="mtb_fin">
                                    <a href="./CorrecaoCC.do?metodo=novo&apdId=${lancamento.apdId}&apdTp=${lancamento.apdTp}&seqLinha=${lancamento.seqLinha}"><img align="middle" src="img/edit.png" width="22" height="22" border="0" alt="Inserir CC"/></a>
                                </acesso:verificaPermissao>
                            </c:when>
                            <c:otherwise><a href="./CorrecaoCC.do?metodo=selecionar&apdId=${lancamento.apdId}&apdTp=${lancamento.apdTp}&seqLinha=${lancamento.seqLinha}"><img align="middle" src="img/edit.png" width="22" height="22" border="0" alt="Alterar CC"/></a><a href="./CorrecaoCC.do?metodo=remover&apdId=${lancamento.apdId}&apdTp=${lancamento.apdTp}&seqLinha=${lancamento.seqLinha}" onclick="if(confirm('Deseja realmente remover a correção de valor do registro selecionado?')){return true;} return false;"><img align="middle" src="img/excluir.png" width="22" height="22" border="0" alt="Remover data"/></a></c:otherwise>
                        </c:choose>
                    </display:column>
                </display:table>
                                    </html:form>
                    </div>
                <div style="width:100%; text-align:center;">
                    <html:button property="" value="Voltar" onclick="history.go(-1)" styleClass="botao" />
                </div>
            </div>
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
