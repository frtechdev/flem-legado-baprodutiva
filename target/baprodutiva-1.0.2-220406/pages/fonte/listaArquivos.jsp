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
                
                <h2>Arquivos da Contrapartida ${fonte.id}</h2>
                <html:form action="/Fonte.do?metodo=excluirArquivo" method="post">
                    <div style="width:100%; text-align:right;">
                        <acesso:verificaPermissao funcionalidade="mtb_proj">
                            <html:button property="" value="Novo" onclick="location.href='Fonte.do?metodo=novoArquivo&fonte=${fonte.id}'" styleClass="botao" />
                            &nbsp;
                            <html:submit value="excluir" styleClass="botao" onclick="if(confirm('Deseja realmente excluir o(s) registro(s) selecionado(s)?')){return true;} return false;"/>
                        </acesso:verificaPermissao>
                    </div>
                    
                    <display:table id="arquivo" name="${fonte.fonteArquivos}" defaultsort="2" sort="list" export="false" excludedParams="metodo" class="tabelaDisplay">
                        <display:column > <input type="checkbox" name="ids_exclusao" value="${arquivo.id}"/></display:column>  
                        <display:column property="descricao" title="Descrição" />
                        <display:column title="" href="http://10.33.0.31/ged/Documento.do?metodo=download&id=${arquivo.id}" paramId="id" paramProperty="id" ><img align="middle" src="img/download.png" width="22" height="22" border="0" alt="Baixar Arquivo"/></display:column>
                    </display:table>
                </html:form>
            </div> 
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>
