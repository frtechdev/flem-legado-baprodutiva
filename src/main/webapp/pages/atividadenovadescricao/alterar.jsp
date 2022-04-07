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
        <link href="<%=request.getContextPath()%>/css/calendar-blue.css" rel="stylesheet" type="text/css" />
        <script language="JavaScript" src="<%=request.getContextPath()%>/js/calendar.js"  type="text/javascript" ></script>
        <script language="JavaScript" src="<%=request.getContextPath()%>/js/calendar-pt.js"  type="text/javascript" ></script>
        <script language="JavaScript" src="<%=request.getContextPath()%>/js/calendar-setup.js"  type="text/javascript" ></script>        
         <html:javascript formName="atividadeNovaDescricaoForm" method="validar"/>
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>

    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Alterar descrição da atividade</h2>
                                <fieldset>
                    <legend>Dados da atividade</legend>
                    <table>
                        <tr>
                            <td>Componente: </td>
                            <td>${atividade.subComponente.componente.descricao}</td>
                        </tr>
                        <tr>
                            <td>Subcomponente: </td>
                            <td>${atividade.subComponente.descricao}</td>
                        </tr>
                        <tr>
                            <td>Atividade: </td>
                            <td>${atividade.descricao}</td>
                        </tr>
                    </table>
                </fieldset>
                <html:form action="/AtividadeNovaDescricao.do?metodo=alterar" onsubmit="return validar(this);" >
                    <html:hidden property="novaDescricao.id" />
                    <html:hidden property="novaDescricao.atividade.id"/>
                    <table>
                        <tr>
                            <td>POA:</td>
                            <td align="left">
                                <html:select styleClass="requerido" property="novaDescricao.poa.id">
                                    <html:option value="" >Selecione:</html:option>
                                    <html:optionsCollection name="poas" value="id" label="descricao" />
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td>Descrição: </td>
                            <td><html:text property="novaDescricao.novaDescricao" size="50" maxlength="100" styleClass="requerido" /> </td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center">
                                <acesso:verificaPermissao funcionalidade="mtb_proj">
                                    <html:submit value="adicionar" onclick="return validaData();" styleClass="botao" /> &nbsp;
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
