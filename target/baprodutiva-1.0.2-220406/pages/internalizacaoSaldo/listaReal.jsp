<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://flem.org.br/mensagem-tag" prefix="msg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3c.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
            <link rel="stylesheet" type="text/css" href="css/800px.css" />
            <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
            <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
            <script language="JavaScript">
                function confirmarExclusao() {

                    var selecao = document.getElementsByName("ids_exclusao");               
                    var marcado = false;

                    for (i=0; i<selecao.length; i++){
                        if (selecao[i].checked == true) { marcado = true; break; }
                    }

                    if ( marcado ) {
                        var confirmado = confirm('Deseja realmente excluir o(s) registro(s) selecionado(s)?');
                        return confirmado;
                    }
                
                    alert("Nenhum registro selecionado para exclusão!");
                    return false;
                }            
            
            </script>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">

                <h2>Saldo das Internalizações em Real</h2>
                <html:form action="/InternalizacaoSaldo.do" method="post">
                    <input type="hidden" name="pagina" value="${pagina}"/>
                    <table>
                        <tr>
                            <td>Data Final: </td>
                            <td><html:text property="dataFim" size="10" maxlength="10"/> </td>
                        </tr>
                        <tr>
                            <td colspan="2"><html:submit property="" value="filtrar" styleClass="botao"/></td>
                        </tr>
                    </table>

                    <display:table id="dto" name="lista"  sort="external"  export="false" excludedParams="metodo" requestURI="./InternalizacaoSaldo.do" class="tabelaDisplay">
                        <display:column style="width: 5%" title="Data da Entrada" format="{0,date,dd/MM/yyyy}">
                            <c:if test="${dto.internalizacao.tipoReceita eq lote}" >
                                <fmt:formatDate pattern="dd/MM/yyyy" value="${dto.internalizacao.dataExibicao}"/>
                            </c:if>
                            <c:if test="${dto.internalizacao.tipoReceita ne lote}" >
                                <fmt:formatDate pattern="dd/MM/yyyy" value="${dto.internalizacao.entrada}"/>
                            </c:if>
                        </display:column>
                        <display:column style="width: 1%" property="internalizacao.tipoReceita" title="Tipo" />
                        <display:column style="width: 35%" property="internalizacao.descricao" title="Descrição" />
                        <display:column style="width: 15%" property="internalizacao.valor" title="Valor" format="R$ {0,number,#,##0.00}"/>
                        <display:column style="width: 12%" value="${dto.debito}" title="Debito" format="R$ {0,number,#,##0.00}"/>
                        <display:column style="width: 15%" value="${dto.internalizacao.valor - (dto.debito < 0.0 ? dto.debito*(-1):dto.debito) }" title="Saldo" format="R$ {0,number,#,##0.00}"/>
                    </display:table>
                </html:form>
            </div>
            <div style="padding: 10px 2px 0px 1000px; text-align: left">
                <strong>Total Internalização (sem devoluções)</strong> : ${totalInterReal}<br/><br/>
                <strong> Total Debitos (deduzindo devoluções):</strong> ${totalDebitosReal}<br/><br/>
                <strong style="color: #FF0000">Valores não Convertidos (R$): ${totalNaoPago}</strong> <br/><br/>
                --------------------------------------------------------------------<br/>
                <strong>Subtotal Internalizações:</strong> ${totalReal} <br/>
                <br/><br/>
                <strong>Total Aplicações Financeiras</strong> : ${totalInterAplicacaoFinanceiraReal}<br/><br/>
                <strong> Total Debitos Aplicações Financeiras:</strong> ${totalDebitosAplicacaoFinanceiraReal}<br/><br/>
                --------------------------------------------------------------------<br/>
                <strong>Subtotal Aplicações Financeiras:</strong> ${totalAplicacaoFinanceiraReal} <br/>
                <br/><br/>
                --------------------------------------------------------------------<br/>
                <strong>Total:</strong> ${totalGeralReal} <br/>

                <br/><br/><br/>
            </div>
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
