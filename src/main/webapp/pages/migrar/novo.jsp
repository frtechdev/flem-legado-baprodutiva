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
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">

                <h2>Migração de atividades</h2>
                <html:form action="/Migrar.do?metodo=migrar" method="post">
                    <table>
                        <tr>
                            <td>Migrar</td>
                            <td>Planejamento:</td>
                            <td align="left">
                                <html:select styleClass="requerido" property="planejamentoId1">
                                    <html:option value="" >Selecione:</html:option>
                                    <html:optionsCollection name="planejamentos" value="id" label="descricao" />
                                </html:select>
                            </td> 
                            
                            <td>Para:</td>
                            <td>Planejamento:</td>
                            <td align="left">
                                <html:select styleClass="requerido" property="planejamentoId2">
                                    <html:option value="" >Selecione:</html:option>
                                    <html:optionsCollection name="planejamentos" value="id" label="descricao" />
                                </html:select>
                        </tr>
                            <td colspan="2" align="center">
                                <acesso:verificaPermissao funcionalidade="mtb_proj">
                                    <html:submit property="" value="migrar" styleClass="botao" /> &nbsp;
                                </acesso:verificaPermissao>
                                <html:button property="" value="voltar" onclick="javascript: history.go(-1);" styleClass="botao" />
                            </td>
                    </table> 

                                    
                </html:form>
            </div> 
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
