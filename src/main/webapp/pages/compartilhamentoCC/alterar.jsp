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
        <html:javascript formName="subAtividadeForm" method="validar" page="1"/>
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
        <script language="JavaScript" type="text/javascript">
            
            function atualizaComponente(){
                Funcoes.obterSubComponentePorComponente(DWRUtil.getValue("componenteId"),populaSubComponente);
            }
            
            function populaSubComponente(dados){          
                DWRUtil.removeAllOptions("subComponenteId");
                DWRUtil.removeAllOptions("atividadeId");
                
                DWRUtil.addOptions("subComponenteId", [{id:"", descricao:"Selecione:"}], "id", "descricao"); 
                DWRUtil.addOptions("atividadeId", [{id:"", descricao:"Selecione:"}], "id", "descricao");
                
                DWRUtil.addOptions("subComponenteId", dados, "id", "descricaoCompleta");
            }
            
            function atualizaSubComponente(){
                if ((DWRUtil.getValue("subComponenteId") == "Selecione:")) {
                    if (DWRUtil.getValue("subComponenteId") == "Selecione:") {
                        alert("Selecione um SubComponente para exibir as atividades.");
                    }
                }          
                else {
                    Funcoes.obterAtividadePorSubComponente(DWRUtil.getValue("subComponenteId"),populaAtividade);
                }
            }
            
            function populaAtividade(dados){          
                DWRUtil.removeAllOptions("atividadeId");
                
                DWRUtil.addOptions("atividadeId", [{id:"", descricao:"Selecione:"}], "id", "descricao"); 
                
                DWRUtil.addOptions("atividadeId", dados, "id", "descricao");
            }
            
            function validaSelecaoCentroCusto() {
                if (DWRUtil.getValue("ccCompartilhado") == "on") {
                    document.getElementById("centroCusto").disabled=true;
                }
                else {
                    document.getElementById("centroCusto").disabled=false;
                }
            }
        </script>
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="false" page="/inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Alterar Subatividade</h2>
                <html:form action="/SubAtividade.do?metodo=alterar" onsubmit="return validar(this);" >
                    <html:hidden property="id"/>
                    <table>
                        <tr>
                            <td>Componente: </td>
                            
                            <td align="left">      
                                <html:select styleId="componenteId" styleClass="requerido" style="width: 430" property="componenteId" onchange="atualizaComponente();">
                                    <html:option value="" >Selecione: </html:option>
                                    <html:optionsCollection name="componentes" value="id" label="descricao" />
                                </html:select>
                            </td>
                            
                        </tr>
                        <tr>
                            <td>Subcomponente: </td>
                            
                            <td align="left">      
                                <html:select styleId="subComponenteId" styleClass="requerido" style="width: 635" property="subComponenteId" onchange="atualizaSubComponente();">
                                    <html:option value="" >Selecione: </html:option>
                                    <html:optionsCollection name="subComponentes" value="id" label="descricao" />
                                </html:select>
                            </td>
                            
                        </tr>
                        <tr>
                            <td>Atividade: </td>
                            
                            <td align="left">      
                                <html:select styleId="atividadeId" style="width: 500px" styleClass="requerido" property="atividadeId">
                                    <html:option value="" >Selecione: </html:option>
                                    <html:optionsCollection name="atividades" value="id" label="descricao" />
                                </html:select>
                            </td>
                            
                        </tr>
                        
                        <tr>
                            <td>Ano de Referência: </td>
                            <td><html:text property="ano" size="4" maxlength="4" styleClass="requerido" /> </td>
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
                            <td colspan="2"><html:checkbox property="ccCompartilhado" styleClass="requerido" onchange="validaSelecaoCentroCusto();"/> Esta subatividade compartilha centro de custo</td>
                        </tr>
                        <tr>
                            <td colspan="2">&nbsp;</td>
                        </tr>
                        <tr>
                            <td colspan="2">&nbsp;(As despesas desta subatividade são alocadas neste(s) centro(s) de custo)</td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                &nbsp;Centros de Custo disponíveis<br>
                                &nbsp;<html:select multiple="true" style="width: 400" size="8" property="centroCusto" value="${centroCustoMarcados}" styleClass="requerido" styleId="centroCusto" >
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
                                            <html:select multiple="true" style="width: 290" size="8" property="orgaos" value="${orgaosMarcados}" styleClass="requerido" >
                                                <html:optionsCollection name="orgaosResponsaveis" value="id" label="descricao" />
                                            </html:select>
                                        </td>
                                        <td>
                                            Entidades Executoras: <br>
                                            <html:select multiple="true" style="width: 290" size="8" property="entidades" value="${entidadesMarcadas}" styleClass="requerido" >
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
        <script language="JavaScript">
            
            validaSelecaoCentroCusto();
            
            selecionarElemento("orgaos", "${orgaosMarcados}");
            selecionarElemento("entidades", "${entidadesMarcadas}");
            selecionarElemento("centroCusto", "${centroCustoMarcados}");
            
               
            function selecionarElemento(idObj, selecao) { 
                var c;

                var lista = selecao.split("," );
                
                for ( c =0 ; c < lista.length ; c++ ) {
                    for(i=0; i <document.getElementById(idObj).length; i++ ) { 
                        if ( document.getElementById(idObj).options[i].value == lista[c] ) {
                           document.getElementById(idObj).options[i].selected = true; 
                           break;
                       }
                   } 
                }
           }
           
           
                      
        </script>
    </body>
</html>
