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
        <script src="<%=request.getContextPath()%>/dwr/interface/Funcoes.js"></script>
        <script src="<%=request.getContextPath()%>/dwr/engine.js"></script>
        <script src="<%=request.getContextPath()%>/dwr/util.js"></script>
        <html:javascript formName="fonteForm" method="validar"/>
        <title><fmt:message key="aplicacao.nome" /> vers�o: <fmt:message key="aplicacao.versao" /></title>

        <script language="JavaScript" type="text/javascript">
            function atualizaProjeto(){
                Funcoes.obterGrupoTipoFontePorProjeto(DWRUtil.getValue("projetoId"),populaGrupoTipoFonte);
            }
            
            function populaGrupoTipoFonte(dados){                
                DWRUtil.removeAllOptions("grupoTipoFonteId");
                DWRUtil.addOptions("grupoTipoFonteId", [{id:"", descricao:"Selecione:"}], "id", "descricao");        
                DWRUtil.addOptions("grupoTipoFonteId", dados, "id", "descricao");
            }
            
            function atualizaGrupoTipoFonte(){
                Funcoes.obterTipoFonteSemGefPorGrupoTipoFonte(DWRUtil.getValue("grupoTipoFonteId"),populaTipoFonte);
            }
            
            function populaTipoFonte(dados){                
                DWRUtil.removeAllOptions("tipoFonteId");
                DWRUtil.addOptions("tipoFonteId", [{id:"", descricao:"Selecione:"}], "id", "descricao");        
                DWRUtil.addOptions("tipoFonteId", dados, "id", "descricao");
            }
        </script>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Nova Receita</h2>
                <jsp:include flush="false" page="/inc/msg_dolar.jsp" />
                <html:form action="/Fonte.do?metodo=adicionar" onsubmit="return validar(this);" >
                    <table>
                        
                        <tr>
                            <td>Plaejamento: </td>
                            <td align="left">      
                                <html:select styleId="planejamentoId" styleClass="requerido" property="planejamentoId" >
                                    <html:option value="" >Selecione: </html:option>
                                    <html:optionsCollection name="planejamentos" value="id" label="descricao" />                   
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td>Grupo: </td>
                            <td align="left">      
                                <html:select styleId="grupoTipoFonteId" styleClass="requerido" property="grupoTipoFonteId" onchange="atualizaGrupoTipoFonte()">
                                    <html:option value="" >Selecione: </html:option>
                                    <html:optionsCollection name="grupoTipoFontes" value="id" label="descricao" />                   
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td>Subgrupo: </td>
                            <td align="left">      
                                <html:select styleId="tipoFonteId" styleClass="requerido" property="tipoFonteId">
                                    <html:option value="" >Selecione: </html:option>                                      
                                </html:select>
                            </td>
                        </tr> 
                        <tr>
                            <td>Origem da Receita: </td>
                            <td align="left">      
                                <html:select styleId="origemId" styleClass="requerido" property="origemId">
                                    <%--html:option value="" >Selecione: </html:option--%>
                                    <html:option value="FLEM" >FLEM</html:option>
                                </html:select>
                            </td>
                        </tr>

                        <tr>
                            <td>Valor em Real: </td>
                            <td><html:text property="valor" size="20" maxlength="30" styleClass="requerido" /> (Ex.: 1500.50)</td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center">
                                <acesso:verificaPermissao funcionalidade="mtb_proj">
                                    <html:submit value="adicionar" styleClass="botao" /> &nbsp;
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
