<%@ page contentType="text/html" errorPage="/erro.jsp" %>
<%@ page pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://flem.org.br/acesso-tag" prefix="acesso"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
        <script type="text/javascript" src="<%=request.getContextPath()%>/dwr/interface/Funcoes.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/dwr/engine.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/dwr/util.js"></script>
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Alterar Planejamento Financeiro</h2>


                <p>Subatividade: <strong><c:out value="${subAtividade.descricao}"/></strong></p>
                <p>Planejamento: <strong><c:out value="${planejamento.descricao}"/></strong></p>
                <html:form action="/SubAtividadeFinanceiroPrevisto.do?metodo=adicionar" method="post" >
                    <html:hidden property="id" value="${planejamento.id}"/>

                    <table>
                        <tr>
                            <td colspan="2">
                                <fieldset>
                                    <legend class="tx_titulo_03"><b>Valores </b> (Ex.: 1500.50)</legend>
                                    <table>
                                        <tr>
                                            <td>Planejamento</td>
                                            <td>
                                                <html:select styleId="planejamentoId" styleClass="requerido" property="planejamentoId">
                                                    <html:option value="" >Selecione: </html:option>
                                                    <html:optionsCollection name="planejamentos" value="id" label="descricao" />
                                                </html:select>
                                            </td>
                                        </tr>
                                        <tr>
                                             <td>Valor  FLEM</td>
                                             <td>
                                                  <input type="text" name="valor"     id="valor"     value=""     onblur="ajustaValor(this); calculaTotalGeral();" onkeyup="calculaTotalGeral();" size="20" style="text-align:right" maxlength="9" class="requerido" onfocus="javascript: if (this.value == 0) { this.value = ''; }" />
                                             </td>
                                             <td>
                                                  <input type="hidden" name="financeiroId" id="financeiroPrevisto" value="${financeiroPrevisto.id}"/>
                                             </td>
                                            </tr>
                                   
                                        <tr>
                                            <td colspan="5"><hr></td>
                                        </tr>
                                    </table>
                                </fieldset>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center"><html:submit value="salvar" styleClass="botao" /> &nbsp; <html:button property="" value="voltar" onclick="javascript: history.go(-1);" styleClass="botao" /></td>
                        </tr>
                    </table>
                </html:form>
            </div> 
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
    </body>
</html>