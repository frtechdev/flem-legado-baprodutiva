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
        <html:javascript formName="internalizacaoLoteBancarioForm" method="validar"/>
        <title><fmt:message key="aplicacao.nome" /> vers�o: <fmt:message key="aplicacao.versao" /></title>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Reconhecer Internaliza��o de Lote Banc�rio</h2>
                <jsp:include flush="false" page="/inc/msg_real.jsp" />
                <html:form action="/InternalizacaoLoteBancario.do?metodo=salvarReconhecimento" onsubmit="return validar(this);" >
                    <html:hidden property="id"/>
                    <html:hidden property="tipo"/>
                    <html:hidden property="seqLinha"/>
                    <html:hidden property="nomeFornecedor"/>

                    <table>
                        <tr>
                            <td>Classifica��o: </td>
                            <td>
                                <html:select styleId="classificacao" styleClass="requerido" property="classificacao">
                                    <html:option value="">-- Selecione --</html:option>
                                    <html:option value="CW">CW</html:option>
                                    <html:option value="GO">GO</html:option>
                                    <html:option value="CS">CS</html:option>
                                    <html:option value="TR">TR</html:option>
                                    <html:option value="CO">CO</html:option>
                                     <html:option value="NCS">NCS</html:option>
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
