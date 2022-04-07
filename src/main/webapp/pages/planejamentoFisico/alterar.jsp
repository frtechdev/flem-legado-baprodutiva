<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://flem.org.br/acesso-tag" prefix="acesso"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
        <!--html:javascript formName="planejamentoFisicoForm" method="validar" page="1"/-->
        
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
        <script language="JavaScript">
            var totalJaPlanejado =  ${totalPrevisto};

            function alterar() {

                var adicional = 0;
                if ( document.planejamentoFisicoForm.porcentagem.value != "" ) 
                    adicional = parseFloat( document.planejamentoFisicoForm.porcentagem.value);
                
                total = parseFloat(totalJaPlanejado) + adicional ;
                if ( total > 100 ) {
                    alert("O total planejado não pode ultrapassar os 100%, o saldo a planejar é " + (100 - parseFloat(totalJaPlanejado) ) + "%" );
                    return false;
                }
                    
                document.planejamentoFisicoForm.submit();
                return true;
            }
            
        </script>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Alterar Planejamento Físico</h2>
                <br/>
                <p>Subatividade: <strong><c:out value="${subAtividade.descricao}"/></strong></p>
                <p>Produto: <strong><c:out value="${produto.descricao}"/></strong></p>
                <html:form action="/ProdutoFisicoPrevisto.do?metodo=alterar" onsubmit="return validar(this);" >
                    <html:hidden property="id"/>
                    <table>
                        <tr>
                            <td>Período: </td>           
                            <td>
                                <html:select styleId="periodoId" styleClass="requerido" property="periodoId">
                                    <html:option value="" >Selecione: </html:option>
                                    <html:optionsCollection name="periodos" value="id" label="descricao" />                                       
                                </html:select>                        
                            </td>
                        </tr>
                        
                        <tr>
                            <td>Descrição: </td>
                            <td><html:text property="descricao" size="70" maxlength="200" styleClass="requerido" /> </td>
                        </tr>
                        
                        <tr>
                            <td>Porcentagem: </td>
                            <td><html:text property="porcentagem" style="text-align: right" size="5" maxlength="5" styleClass="requerido" /> (Ex.: 52.00)</td>
                        </tr>
                        
                        <tr>
                            <td colspan="2" align="center"><html:button property="" value="Salvar" onclick="return alterar();" styleClass="botao" /> &nbsp; <html:button property="" value="Voltar" onclick="javascript: history.go(-1);" styleClass="botao" /></td>
                        </tr>
                    </table>
                </html:form>
            </div> 
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
    </body>
</html>
