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
                <h2>Lista de Despesas de Viagens </h2>
                <jsp:include flush="false" page="/inc/msg_real.jsp" />

                <html:form action="/DespesaViagem.do" method="post">
                        <h3>Adiantamentos de viagens com prestação de contas não finalizados </h3>
                        <display:table id="viagem" name="spcontas" defaultsort="2" sort="list" export="false" requestURI="./DespesaViagem.do"  class="tabelaDisplay">
                            <display:column property="data" title="Data" format="{0,date,dd/MM/yyyy}"/>
                            <display:column property="recibo" title="Código de Viagem" />
                            <display:column property="descricao" title="Descrição" />
                            <display:column property="nomeFornecedor" title="Fornecedor" />
                            <display:column property="valor" title="Valor" format="R$ {0,number,#,##0.00}" />
                        </display:table>

                        <h3>Todos adiantamentos de viagens </h3>
                        <display:table id="viagem2" name="todos" defaultsort="2" sort="list" export="false" requestURI="./DespesaViagem.do" class="tabelaDisplay">
                            <display:column property="data" title="Data" format="{0,date,dd/MM/yyyy}"/>
                            <display:column property="recibo" title="Código de Viagem" />
                            <display:column property="descricao" title="Descrição" />
                            <display:column property="nomeFornecedor" title="Fornecedor" />
                            <display:column property="valor" title="Valor" format="R$ {0,number,#,##0.00}" />
                            
                            <display:column title="" href="./DespesaViagem.do?metodo=detalhes_prestacao" paramId="idViagem" paramProperty="recibo" ><img align="middle" src="img/edit.png" width="22" height="22" border="0" alt="Detalhe"/></display:column>
                        </display:table>
                </html:form>
            </div>
            <jsp:include flush="false" page="/inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>
    </body>
</html>