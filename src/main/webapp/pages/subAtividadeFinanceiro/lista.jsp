<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://flem.org.br/mensagem-tag" prefix="msg"%>
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

            function mudaDestino(){
                document.forms[0].action = "<%=request.getContextPath()%>/SubAtividadeFinanceiroPrevisto.do?metodo=novo";
                document.forms[0].submit();
            }
        </script>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Lista de Planejamentos Financeiros</h2>


                <p>Atividade: <strong><c:out value="${subAtividade.descricao}"/></strong></p>

                <p>Valor Atual: <strong><c:out value="${subAtividade.valorFinanceiroPrevisto}"/></strong></p>
                <html:form action="/SubAtividadeFinanceiroPrevisto.do" method="post">
                    <div style="width:100%; text-align:right; font: 76%/1.2em Verdana,Tahoma,Arial,sans-serif">
                        <%--Adicionar:
                        <html:select property="id" styleId="id" styleClass="requerido" onchange="mudaDestino();">
                            <html:option value="">Selecione:</html:option>
                            <html:optionsCollection name="poas" value="id" label="descricao" />
                        </html:select>&nbsp;--%>
                        <html:button property="" value="novo" styleClass="botao" onclick="mudaDestino();"/>&nbsp;
                        <html:submit property="metodo" value="excluir" styleClass="botao" onclick="return confirmarExclusao();"/>&nbsp; 
                        <html:button property="" value="voltar" onclick="javascript: history.go(-1);" styleClass="botao" />
                    </div>

                    <display:table id="financeiro" name="lista"  defaultsort="2" export="false" excludedParams="metodo" requestURI="./SubAtividadeFinanceiroPrevisto.do" pagesize="30" class="tabelaDisplay">
                        <display:column><input type="checkbox" name="ids_exclusao" value="${financeiro.id}"/></display:column> 
                        <display:column property="planejamento.descricao" title="Planejamento" style="text-align:right; width:30%"  />                      
                        <display:column property="valor" title="Valor FLEM" style="text-align:right; width:30%" format="{0,number,#,##0.00}"/>
                        <display:column style="text-align:right; width:30%" property="data" title="Data" format="{0,date,dd/MM/yyyy}"/>
                        <display:column><c:if test="${financeiro.id ne null}"><a href="./SubAtividadeFinanceiroPrevisto.do?metodo=selecionar&id=${financeiro.id}"><img align="middle" src="img/edit.png" width="22" height="22" border="0" alt="Alterar" title="Alterar"/></a></c:if></display:column> 
                        </display:table>
                    </html:form>
            </div>
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
