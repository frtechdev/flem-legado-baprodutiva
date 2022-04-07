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
        <script type="text/javascript" src="<%=request.getContextPath()%>/dwr/interface/Funcoes.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/dwr/engine.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/dwr/util.js"></script>
        <html:javascript formName="subAtividadeForm" method="validar" />        
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
        <script language="JavaScript" type="text/javascript">
             function atualizaComponente(){
                Funcoes.obterSubcategoriaPorCategoria(DWRUtil.getValue("categoriaId"),populaSubcategoria);
            }
            
            var tmp;
            function populaSubcategoria(dados){
                 DWRUtil.removeAllOptions("subcategoriaId");
                 DWRUtil.addOptions("subcategoriaId", [{id:"", descricao:"Selecione:"}], "id", "descricao"); 
                 DWRUtil.addOptions("subcategoriaId", dados, "id", "descricao");
                 tmp = dados;
 
            }
        </script>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">

                <h2>Lista de Atividades</h2>
                <html:form action="/SubAtividade.do" method="post">
                    <div style="width:100%; text-align:right;">
                        <table>                     
                        <tr>
                            <td>Categoria:</td>
                            <td align="left">
                                <html:select styleClass="requerido" property="categoriaId" onchange="atualizaComponente()">
                                    <html:option value="" >Selecione:</html:option>
                                    <html:optionsCollection name="categorias" value="id" label="descricao" />
                                </html:select>
                            </td>
                        </tr>
                        
                       <tr>
                            <td>SubCategoria:</td>
                            <td align="left">
                                <html:select  styleId="subcategoriaId" styleClass="requerido" property="subcategoriaId" >
                                    <html:option value="" >Selecione:</html:option>
                                    <html:optionsCollection name="subcategorias" value="id" label="descricao" />
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td>Descrição:</td>
                            
                            <td><html:text property="descricao" size="100" maxlength="100" /></td>
                        </tr>
                        
                        
                            <tr>
                                <td colspan="4">
                                    <html:submit property="metodo" value="filtrar" styleClass="botao" />
                                    &nbsp;
                                    <acesso:verificaPermissao funcionalidade="mtb_proj">
                                        <html:button property="" value="novo" onclick="location.href='SubAtividade.do?metodo=novo'" styleClass="botao" />
                                        &nbsp;
                                        <html:submit property="metodo" value="excluir"  styleClass="botao" onclick="return confirmarExclusao();"/>
                                        &nbsp;
                                    </acesso:verificaPermissao>
                                    <html:button property="" value="imprimir" onclick="location.href='Relatorio.do?metodo=relatorioSubatividade'" styleClass="botao" />
                                    &nbsp;

                                </td>
                            </tr>
                        </table>
                    </div>

                    <display:table id="sub" name="lista" defaultsort="4" sort="list" export="false" excludedParams="metodo" requestURI="./SubAtividade.do" pagesize="30" class="tabelaDisplay">
                        <display:column style="width: 1%" ><input type="checkbox" name="ids_exclusao" value="${sub.id}" /></display:column>     
                        <display:column property="categoria.descricao" title="Categoria" />
                        <display:column property="subCategoria.descricao" title="SubCategoria" />    
                        <display:column property="descricao" title="Descrição" />    
                        <display:column title="" href="./SubAtividade.do?metodo=selecionar" paramId="id" paramProperty="id" >
                            <img align="middle" src="img/edit.png" width="22" height="22" border="0" alt="Alterar" title="Alterar"/>
                        </display:column>
                        <display:column title="" href="./SubAtividadeFinanceiroPrevisto.do?metodo=selecionarSubAtividade" paramId="id" paramProperty="id" >
                            <img align="middle" src="img/planejamento.png" width="22" height="22" border="0" alt="Planejamento Financeiro" title="Planejamento Financeiro"/>
                        </display:column>
                    </display:table>                                      
                </html:form>
            </div> 
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
