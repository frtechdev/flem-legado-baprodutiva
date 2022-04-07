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
        <html:javascript formName="produtoForm" method="validar"/>
        <title><fmt:message key="aplicacao.nome" /> vers�o: <fmt:message key="aplicacao.versao" /></title>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Novo Produto</h2>
                <br/>
                <p>Subatividade: <strong><c:out value="${planejamento.descricaoAtividade}"/></strong></p>
                <html:form action="/Produto.do?metodo=adicionar" onsubmit="return validar(this);" >
                    <table>
                        <tr>
                            <td>Descri��o:</td>
                            <td><html:text property="descricao" size="50" maxlength="100" styleClass="requerido" /> </td>
                        </tr>
                        <tr>
                            <td>Unidade de Medida:</td>           
                            <td>
                                <html:select styleId="unidadeMedidaId" styleClass="requerido" property="unidadeMedidaId">
                                    <html:option value="" >Selecione: </html:option>
                                    <html:optionsCollection name="unidadesMedida" value="id" label="descricao" />                                       
                                </html:select>                        
                            </td>
                        </tr> 
                        <tr>
                            <td>Meta:</td>
                            <td><html:text property="meta" size="20" maxlength="15" styleClass="requerido" /> </td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center"><html:submit value="adicionar" styleClass="botao" /> &nbsp; <html:button property="" value="voltar" onclick="javascript: history.go(-1);" styleClass="botao" /></td>
                        </tr>
                    </table>
                </html:form>
            </div> 
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
    </body>
</html>
