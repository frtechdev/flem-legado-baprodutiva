<%-- 
    Document   : listaContratos
    Created on : 12/01/2009, 16:56:30
    Author     : mgsilva
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%--<%@page pageEncoding="ISO-8859-1"%> --%>
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

 <h2>Compromissos com Contratos Irregulares</h2>
 <display:table id="contratoIrregular" name="listaContratoIrregular" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./ValidacoesFinanceira.do" class="tabelaDisplay">
     <display:column style="width: 10%" property="data" title="Data da Entrada" format="{0,date,dd/MM/yyyy}"/>
        <display:column style="width: 40%" property="descricao" title="Descrição" />
        <display:column style="width: 10%" property="valor" title="Valor" format="{0,number,#,##0.00}"/>
        <display:column style="width: 10%" property="numeroContrato" title="Contrato GEM" />
        <display:column value="${contratoIrregular.nomeFornecedor} ${contratoIrregular.codigoFornecedor}" title="Fornecedor"/>
        
        <display:column style="width: 3%"> <a href="./ValidacoesFinanceira.do?metodo=selecionar&id=${contratoIrregular.apdId}&tipo=${contratoIrregular.apdTp}" name="ids_tipo"/><img alt="alterar" align="middle" src="img/edit.png" width="22" height="22" border="0"/> </display:column>
  </display:table>
      <br><br>

<h2>Compromissos com Classificações Irregulares</h2>
  <display:table id="tipoIrregular" name="listaTipoIrregular" defaultsort="2" sort="list" export="false" excludedParams="metodo" requestURI="./ValidacoesFinanceira.do" pagesize="30" class="tabelaDisplay">
     <display:column style="width: 20%" property="data" title="Data da Entrada" format="{0,date,dd/MM/yyyy}"/>
        <display:column style="width: 40%" property="descricao" title="Descrição" />
        <display:column style="width: 20%" property="valor" title="Valor" format="{0,number,#,##0.00}"/>
        <display:column style="width: 10%" property="id" title="Tipo" />
        <display:column style="width: 3%"> <a href="./ValidacoesFinanceira.do?metodo=selecionar&id=${tipoIrregular.apdId}&tipo=${tipoIrregular.apdTp}" name="ids_tipo"/><img alt="alterar" align="middle" src="img/edit.png" width="22" height="22" border="0"/> </display:column>   
  </display:table>
          <br><br>

           <h2>Devoluções Irregulares</h2>
  <display:table id="internalizacaoIrregular" name="listaInternalizacaoIrregular" defaultsort="2" sort="list" export="false" excludedParams="metodo" requestURI="./InternalizacaoDevolucao.do" pagesize="30" class="tabelaDisplay">
     <display:column style="width: 20%" property="data" title="Data da Entrada" format="{0,date,dd/MM/yyyy}"/>
        <display:column style="width: 40%" property="descricao" title="Descrição" />
        <display:column style="width: 10%" property="valor" title="Valor" format="{0,number,#,##0.00}"/>
        <display:column style="width: 3%">
            <acesso:verificaPermissao funcionalidade="mtb_fin">
                <a href="./InternalizacaoDevolucao.do?metodo=associarCompromisso&id=${internalizacaoIrregular.id}&tipo=${internalizacaoIrregular.tipo}&seqLinha=${internalizacaoIrregular.seqLinha}"/><img alt="alterar" align="middle" src="img/edit.png" width="22" height="22" border="0"/>
            </acesso:verificaPermissao>
        </display:column>

  </display:table>
  


  </div>
          <jsp:include flush="false" page="/inc/footer.jsp" />
       </div>

   <msg:alert escopo="session"/>

 </body>
</html>
