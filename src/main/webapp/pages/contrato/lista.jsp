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
            function confirmarExclusao() {

                var selecao = document.getElementsByName("ids_exclusao");               
                var marcado = false;

                for (i=0; i<selecao.length; i++){
                    if (selecao[i].checked == true) { marcado = true; break; }
                }

                if ( marcado ) {
                    var confirmado = confirm('A exclusão de um contrato pode implicar diretamente na geração dos relatórios financeiros.\nCertifique-se que de os contratos que estão sendo excluídos não comprometerão a integridade das informações.\n\nDeseja realmente realizar esta exclusão?');
                    return confirmado;
                }
                
                alert("Nenhum registro selecionado para exclusão!");
                return false;
            }            
            
        </script>
        <script language="JavaScript">
            function submitFiltro(){
                document.forms[0].action = "<%=request.getContextPath()%>/Contrato.do?metodo=filtrar";
                document.forms[0].submit();
            }
        </script>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">

                <h2>Lista de Contratos</h2>
                <html:form action="/Contrato.do" method="post">
                    <div style="width:100%; text-align:right;">
                        
                        <table>
                            <tr>
                                <td>Contrato:
                                    <html:text property="contrato" styleClass="requerido" style="width: 100px" value="${filtro_contrato}"/>
                                    &nbsp;
                                </td>
                                <td>Fornecedor:
                                    <html:text property="fornecedor" styleClass="requerido" style="width: 200px" value="${filtro_fornecedor}"/>
                                    &nbsp;
                                </td>
                                
                            </tr>
                        </table>
                        <html:submit property="metodo" value="filtrar" onclick="submitFiltro()" styleClass="botao"/>
                        &nbsp;
                        <html:button property="" value="rel. acordos" onclick="location.href='Relatorio.do?metodo=filtroAcordoSubdoacoes'" styleClass="botao"/>
                        &nbsp;
                        <html:button property="" value="rel. consult." onclick="location.href='Relatorio.do?metodo=relatorioConsultoria'" styleClass="botao"/>
                        &nbsp;
                        <html:button property="" value="novo" onclick="location.href='Contrato.do?metodo=novo'" styleClass="botao" /> 
                        &nbsp;
                        <html:submit property="metodo" value="excluir" styleClass="botao" onclick="return confirmarExclusao();"/>
                    </div>

                    <display:table id="contrato" name="lista" defaultsort="2" sort="list" export="false" excludedParams="metodo" requestURI="./Contrato.do" pagesize="30" class="tabelaDisplay">
                        <display:column > <input type="checkbox" name="ids_exclusao" value="${contrato.id}"/></display:column>
                        <display:column property="numero" title="No. do Contrato" />
                        <display:column property="tipo" title="Classificação" />
                        <display:column property="nomeFornecedor" title="Fonecedor" />
                        <display:column property="valor" title="Valor" format="{0,number,#,##0.00}"/>
                        <display:column property="moeda" title="Código da Moeda" />
                        <acesso:verificaPermissao funcionalidade="mtb_fin">
                            <display:column title="Editar" href="./Contrato.do?metodo=selecionar" paramId="id" paramProperty="id" ><img align="middle" src="img/edit.png" width="22" height="22" border="0" alt="Alterar"/></display:column>
                            <display:column title="Parcelas" href="./Parcela.do" paramId="idContrato" paramProperty="id" ><img align="middle" src="img/currency.png" width="22" height="22" border="0" alt="Alterar"/></display:column>
                            <display:column title="Aditivos" href="./Aditivo.do" paramId="idContrato" paramProperty="id" ><img align="middle" src="img/seletorData.png" width="22" height="22" border="0" alt="Alterar"/></display:column>
                        </acesso:verificaPermissao>
                    </display:table>
                </html:form>
            </div>
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
