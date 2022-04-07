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

        <html:javascript formName="subAtividadeForm"  page="1"/>
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
        <script language="JavaScript" type="text/javascript">
             function atualizaComponente(){
                Funcoes.obterSubcategoriaPorCategoria(DWRUtil.getValue("categoriaId"),populaSubcategoria);
            }
            
            
            function populaSubcategoria(dados){
                DWRUtil.removeAllOptions("subcategoriaId");
                DWRUtil.addOptions("subcategoriaId", [{id:"", descricao:"Selecione:"}], "id", "descricao"); 
                DWRUtil.addOptions("subcategoriaId", dados, "id", "descricao");
                tmp = dados;
                DWRUtil.removeAllOptions("centroCusto");
            }
            
            function poaChanged(){
                Funcoes.obterCCPorSubCategoria(DWRUtil.getValue("subcategoriaId"), carregarCentrosDeCusto);
            }
            
            function carregarCentrosDeCusto(dados){   
                DWRUtil.removeAllOptions("centroCusto");
                DWRUtil.addOptions("centroCusto", dados, "id", "descricaoCompleta");
            }
        </script>
      
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Nova Atividade</h2>
                <html:form action="/SubAtividade.do?metodo=adicionar"  >
                    <table>
    
                        <tr>
                            <td>Categoria: </td>
                            <td align="left">
                                <html:select styleId="categoriaId" styleClass="requerido" property="categoriaId" onchange="atualizaComponente();">
                                    <html:option value="" >Selecione: </html:option>
                                    <html:optionsCollection name="categorias" value="id" label="descricao" />
                                </html:select>
                            </td>
                        </tr>
                        
                       <tr>
                            <td>SubCategoria: </td>
                            <td align="left">
                                <html:select styleId="subcategoriaId" styleClass="requerido" property="subcategoriaId" onchange="poaChanged();" >
                                    <html:option value="" >Selecione: </html:option>
                                    <html:optionsCollection name="subcategorias" value="id" label="descricao" />
                                </html:select>
                            </td>
                        </tr>

                        <tr>
                            <td>Descrição: </td>
                            <td><html:text property="descricao" size="100" maxlength="150" styleClass="requerido" /> </td>
                        </tr>
                        <tr>
                            <td>Observação: </td>
                            <td><html:text property="observacao" size="80" maxlength="100" styleClass="requerido" /> </td>
                        </tr>
                         <tr>
                            <td colspan="2">&nbsp;</td>
                        </tr>
                        <tr>
                            <td colspan="2">(As despesas desta subatividade são alocadas neste centro de custo)</td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                &nbsp;Centros de Custo disponíveis<br>
                                &nbsp;<html:select multiple="true" style="width: 400" size="8" property="centroCusto" value="${centroCustoMarcados}" styleId="centroCusto" styleClass="requerido" >
                                     <html:optionsCollection name="centroCustos" value="id" label="descricaoCompleta" /> 
                                </html:select>                                
                            </td>
                        </tr>   
                        <tr>
                            <td colspan="2">
                                <table width="100%">
                                    <tr>
                                        <td width="50%">
                                            Órgãos Responsáveis: <br>
                                            <html:select multiple="true" style="width: 290" size="8" property="orgaos" styleClass="requerido" >
                                                <html:optionsCollection name="orgaosResponsaveis" value="id" label="descricao" />
                                            </html:select>
                                        </td>
                                        <td>
                                            Entidades Executoras: <br>
                                            <html:select multiple="true" style="width: 290" size="8" property="entidades" styleClass="requerido" >
                                                <html:optionsCollection name="entidadesExecutoras" value="id" label="descricao" />
                                            </html:select>
                                        </td>
                                    </tr>
                                </table>   
                            </td>
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
