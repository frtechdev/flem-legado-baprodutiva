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
        <html:javascript formName="contratoForm" method="validar" />
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Alterar Contrato</h2>
                <html:form action="/Contrato.do?metodo=alterar" onsubmit="return validar(this);" >
                    <html:hidden property="id"/>
                    <table>
                        <tr>
                            <td>Início da Vigência: </td>
                            <td><html:text property="dataInicioVigencia" size="10" maxlength="100" styleClass="requerido" /> Ex.: 01/01/2008</td>
                        </tr>
                        <tr>
                            <td>Fim da Vigência: </td>
                            <td><html:text property="dataFimVigencia" size="10" maxlength="10" styleClass="requerido" /> Ex.: 01/01/2008</td>
                        </tr>
                        <tr>
                            <td>Fornecedor: </td>
                            <td align="left">
                                <html:select styleId="idFornecedor" styleClass="requerido" property="idFornecedor">
                                    <html:option value="">-- Selecione --</html:option>
                                    <c:forEach items="${fornecedores}" var="fornecedor">
                                        <html:option value="${fornecedor.codigo}">${fornecedor.nomeAbreviado} - ${fornecedor.codigo}</html:option>
                                    </c:forEach>
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td>Número: </td>
                            <td><html:text property="numero" size="50" maxlength="100" styleClass="requerido" /> (Este é o número do contrato que deve ser utilizado no documento a pagar do GEM)</td>
                        </tr>
                        <tr>
                            <td>Client Connection:</td>
                            <td><html:text property="clientConnection" size="25"/></td>
                        </tr>
                        <tr>
                            <td>Classificação: </td>
                            <td align="left">      
                                <html:select styleId="tipo" styleClass="requerido" property="tipo">
                                    <html:option value="">-- Selecione --</html:option>
                                    <html:option value="CW">CW - Obras</html:option>
                                    <html:option value="GO">GO - Bens</html:option>
                                    <html:option value="CS">CS - Serviços de Consultorias</html:option>
                                    <html:option value="TR">TR - Treinamento</html:option>
                                    <html:option value="CO">CO - Custos Operacionais</html:option>
                                </html:select>
                            </td>                            
                        </tr>
                        <tr>
                            <td>Código da Moeda: </td>
                            <td><html:text property="moeda" size="15" maxlength="15" /> </td>
                        </tr>
                        <tr>
                            <td>Valor: </td>
                            <td><html:text property="valor" size="15" maxlength="100" styleClass="requerido" /> Ex.: 2500.50 (Dois mil e quinhentos reais e cinquenta centavos)</td>
                        </tr>
                        <tr>
                            <td>Valor em Dólar: </td>
                            <td><html:text property="valorUSD" size="15" maxlength="100" styleClass="requerido" /> Ex.: 2500.50 (Dois mil quinhentos e cinquenta )</td>
                        </tr>
                        <tr>
                            <td>Objeto: </td>
                            <td><html:text property="objeto" size="100" maxlength="500" /> </td>
                        </tr>
                        <tr>
                            <td>Observação:</td>
                            <td><html:textarea property="observacao"  cols="30" rows="2"/></td>
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
