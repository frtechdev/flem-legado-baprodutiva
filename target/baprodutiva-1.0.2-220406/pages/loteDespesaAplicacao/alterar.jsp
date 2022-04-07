<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://flem.org.br/acesso-tag" prefix="acesso"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
        <html:javascript formName="loteForm" method="validar"/>
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Reconhecer despesa bancárias de aplicação</h2>
                <html:form action="/LoteDespesaAplicacao.do?metodo=alterar" onsubmit="return validar(this);" >
                    <html:hidden property="lote.id" value="${lote.id}"/>
                    <html:hidden property="lote.idDespesa" value="${lote.idDespesa}"/>
                    <html:hidden property="lote.tipo" value="${lote.tipo}"/>
                    <html:hidden property="lote.seqLinha" value="${lote.seqLinha}"/>
                    <input type="hidden" name="dataPagamento" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${lote.dataPagamento}"/>" />
                    <html:hidden property="lote.descricao" value="${lote.descricao}"/>
                    <html:hidden property="lote.valor" value="${lote.valor}"/>

                    <fieldset style="width: 500px;">
                        <legend>Dados do lote</legend>
                        <table>
                            <tr>
                                <td>Data:</td>
                                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${lote.dataPagamento}"/></td>
                            </tr>
                            <tr>
                                <td>Descrição:</td>
                                <td>${lote.descricao}</td>
                            </tr>
                            <tr>
                                <td>Valor em real:</td>
                                <td><fmt:formatNumber type="NUMBER" value="${lote.valor}" minFractionDigits="2" maxFractionDigits="2"/></td>
                            </tr>
                        </table>
                    </fieldset>
                    <br/>
                    <table>
                        <tr>
                            <td>Categoria: </td>
                            <td>
                                <html:select property="lote.categoria.id" value="${lote.categoria.id}">
                                    <html:option value="">Selecione:</html:option>
                                    <html:optionsCollection value="id" label="descricao" name="categorias" />
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center">
                                <acesso:verificaPermissao funcionalidade="mtb_fin">
                                    <html:submit value="salvar" styleClass="botao" /> &nbsp;
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
