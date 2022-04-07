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
        <script type="text/javascript">
            function confirmarExclusao() {
                var selecao = document.getElementsByName("ids_exclusao");
                var marcado = false;

                for (i=0; i< selecao.length; i++){
                    if (selecao[i].checked == true) {
                        marcado = true;
                        break;
                    }
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
                <h2>Lista de lançamentos avulsos</h2>
                <html:form action="/LancamentoAvulso.do" method="post">
                    <div style="width:100%; text-align:right;">
                        <table>
                            <tr>
                                <td>
                                    <html:button property="" value="voltar" onclick="history.go(-1)" styleClass="botao" />
                                    <acesso:verificaPermissao funcionalidade="mtb_fin">
                                        <html:button property="" value="novo" onclick="location.href='LancamentoAvulso.do?metodo=novo'" styleClass="botao" />
                                        <html:submit property="metodo" value="excluir" styleClass="botao" onclick="return confirmarExclusao();"/>
                                    </acesso:verificaPermissao>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <display:table id="lancamento" name="lista" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./LancamentoAvulso.do" pagesize="30" class="tabelaDisplay">
                        <display:column title="" style="width: 45px"><input type="checkbox" name="ids_exclusao" value="${lancamento.id}" /></display:column>
                        <display:column property="dataPagamento" title="Data de pagamento" format="{0,date,dd/MM/yyyy}"/>
                        <display:column property="codigoItem" title="Código do item" />
                        <display:column property="nomeForn" title="Nome do fornecedor" />
                        <display:column property="numeroContrato" title="Número do contrato" />
                        <display:column property="descricao" title="Descrição" />
                        <display:column property="valorPagamento" title="Valor de pagamento" format="R$ {0,number,#,##0.00}" />
                        <display:column property="centroCusto" title="Centro de custo" />
                        <display:column title="" style="width: 45px">
                            <a href="./LancamentoAvulso.do?metodo=selecionar&id=${lancamento.id}"><img align="middle" src="img/edit.png" width="22" height="22" border="0" alt="Editar lançamento"/></a>
                        </display:column>
                    </display:table>
                </html:form>
            </div>
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
