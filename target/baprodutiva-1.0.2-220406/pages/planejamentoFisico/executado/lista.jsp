<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://flem.org.br/mensagem-tag" prefix="msg"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
        <script type='text/javascript' src="<%=request.getContextPath()%>/dwr/util.js"></script>
        <script type='text/javascript' src="<%=request.getContextPath()%>/dwr/interface/Funcoes.js"></script>
        <script type='text/javascript' src="<%=request.getContextPath()%>/dwr/engine.js"></script>

        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
        <script type="text/javascript">
            function adicionar() {
                var oForm = document.planejamentoFisicoForm;
                var msg = "";
                var total = 0;
            <c:forEach items="${periodosDto}" var="grupo">
                    if (!parseFloat(oForm.porcentagem${grupo.periodo.id}.value) &&
                        oForm.porcentagem${grupo.periodo.id}.value != 0) {
                        msg += "Insira apenas números no campo porcentagem;"
                        oForm.porcentagem${grupo.periodo.id}.value ="";
                        oForm.porcentagem${grupo.periodo.id}.focus();
                    } else if (oForm.porcentagem${grupo.periodo.id}.value == "" ) {
                        oForm.porcentagem${grupo.periodo.id}.value = "0.0";
                    }

                    if ( msg != "") {
                        alert(msg);
                        return false;
                    }
                    total += parseFloat(oForm.porcentagem${grupo.periodo.id}.value);

                    if ( total > 100 ) {
                        total = total - oForm.porcentagem${grupo.periodo.id}.value;
                        oForm.porcentagem${grupo.periodo.id}.focus();
                        oForm.porcentagem${grupo.periodo.id}.value = "";
                        alert( "O planejamento do ${produto.planejamento.poa.descricao} não pode ultrapassar os 100%.\nO saldo a planejar é " + ( 100 - total ) + "%" );
                        oForm.porcentagem${grupo.periodo.id}.focus();
                        return false;
                    }
                    oForm.percentualTotal.value = total +'%';
            </c:forEach>

                    return true;
                }
        </script>
    </head>
    <body onload="adicionar();">
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />

            <div id="content">
                <h2> Físico Executado</h2>
                <br/>
                <p>Subatividade: <strong><c:out value="${produto.planejamento.descricaoAtividade}"/></strong></p>
                <p>Produto: <strong><c:out value="${produto.descricao}"/></strong></p>
                <p>POA: <strong>${produto.planejamento.poa.descricao}</strong></p>
                <div id="dadosPanejamento" style="display: '';">
                    <html:form action="/ProdutoFisicoExecutado.do?metodo=adicionar"  onsubmit="return adicionar(this);" >
                        <html:hidden property="id"  />
                        <table>
                            <tr>
                                <td>
                                    <table cellpadding="0" cellspacing="0" style="border-style:  solid; border-width: 1px">
                                        <thead>
                                            <tr>
                                                <th style="border-bottom-width: 1px; border-bottom-style: solid; width: 300px">Período</th>
                                                <th style="border-bottom-width: 1px; border-bottom-style: solid;">Porcentagem Executada</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${periodosDto}" var="indicePeriodo" varStatus="contador">
                                                <tr>
                                                    <td>
                                                        ${indicePeriodo.periodo.descricao}
                                                        <input type="hidden" name="idFisico${indicePeriodo.periodo.id}" value="${indicePeriodo.idFisico}" />
                                                    </td>
                                                    <td>
                                                        <input type="text" name="porcentagem${indicePeriodo.periodo.id}" id="porcentagem${indicePeriodo.periodo.id}" style="text-align: right" size="5" maxlength="5" onblur="return adicionar();" value="${indicePeriodo.porcentagem}" onfocus="javascript: if (this.value == 0) { this.value = ''; }" /> (Ex.: 25.00)
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td></td>
                                                <td align="left" style="text-align: left;">
                                                    <input readonly="true" id="percentualTotal" size="4" style="background-color: #C1CDCD; font-weight: bold; text-align: center;" /> Total Percentual
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" align="center">
                                    <html:submit value="adicionar" styleClass="botao"/> &nbsp; <html:button property="" value="voltar" onclick="javascript: history.go(-1);" styleClass="botao" />
                                </td>
                            </tr>
                        </table>
                    </div>
                </html:form>
            </div>
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
