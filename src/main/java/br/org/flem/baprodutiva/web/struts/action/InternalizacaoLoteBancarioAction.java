package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fw.persistencia.dto.Compromisso;
import br.org.flem.fw.service.GEM;
import br.org.flem.fw.service.impl.GEMImpl;
import br.org.flem.fwe.util.Data;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.baprodutiva.bo.InternalizacaoLoteBancarioBO;
import br.org.flem.baprodutiva.negocio.InternalizacaoLoteBancario;
import br.org.flem.commons.util.PropertiesUtil;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

/**
 * @author dbbarreto
 */
public class InternalizacaoLoteBancarioAction extends DispatchAction {

    private static final String[] EVENTOS = new String[]{"BJE029"};
    

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            Collection internalizacoes = new InternalizacaoLoteBancarioBO().obterTodos();
            
            if (internalizacoes != null && !internalizacoes.isEmpty()) {
            
                request.setAttribute("lista", internalizacoes);
            }
            else {
                request.setAttribute("lista", new ArrayList());
            }
            
        }
        catch(AcessoDadosException e) {
            e.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("lista");
    }

    public ActionForward listarLotesGEM(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            GEMImpl gem = new GEMImpl();

            String banco1 = PropertiesUtil.getProperties().getProperty("banco1");
            String agencia1 = PropertiesUtil.getProperties().getProperty("agencia1");
            String conta1 = PropertiesUtil.getProperties().getProperty("conta1");
            
            String banco2 = PropertiesUtil.getProperties().getProperty("banco2");
            String agencia2 = PropertiesUtil.getProperties().getProperty("agencia2");
            String conta2 = PropertiesUtil.getProperties().getProperty("conta2");
            
            
            String dataInicio = PropertiesUtil.getProperties().getProperty("projeto.dataInicio");

            Collection<Compromisso> compromissos = gem.obterLotesBancariosPorDataEvento(Integer.valueOf(Data.retornaDataInversa(dataInicio)), Data.converteDataParaInteiro(new Date()), EVENTOS, banco1, agencia1, conta1);
            compromissos.addAll(gem.obterLotesBancariosPorDataEvento(Integer.valueOf(Data.retornaDataInversa(dataInicio)), Data.converteDataParaInteiro(new Date()), EVENTOS, banco2, agencia2, conta2));

            Collection compromissosValidos = new ArrayList();

            for (Compromisso compromisso : compromissos) {
                boolean insere = true;
                for (Compromisso internalizacao : new InternalizacaoLoteBancarioBO().obterCompromissos()) {
                    if (compromisso.getId().contentEquals(internalizacao.getId()) && compromisso.getTipo().equals(internalizacao.getTipo()) && compromisso.getSeqLinha().equals(internalizacao.getSeqLinha())) {
                        insere = false;
                    }
                }
                if (insere) {
                    compromissosValidos.add(compromisso);
                }
            }
            //System.out.println(compromissosValidos.size());
            //compromissos.removeAll(new InternalizacaoLoteBancarioBO().obterCompromissos());
            
            request.setAttribute("lista", compromissosValidos);
            
        }
        catch(Exception e) {
            e.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("listaLotesGEM");
    }

     public ActionForward associarCompromisso(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {

            DynaActionForm dyna = (DynaActionForm) form;
            GEMImpl gem = new GEMImpl();

            String id = request.getParameter("id");
            String tipo = request.getParameter("tipo");
            String seqLinha = request.getParameter("seqLinha");
            
            String banco = PropertiesUtil.getProperties().getProperty("banco1");
            String agencia = PropertiesUtil.getProperties().getProperty("agencia1");
            String conta = PropertiesUtil.getProperties().getProperty("conta1");

            Compromisso lote = gem.obterLotePorIdTipoSeqLinhaEventos(id, tipo, seqLinha, EVENTOS, banco, agencia, conta);
            List<Compromisso> compromissos = gem.obterCompromissosPorFiltroGrupoCC(PropertiesUtil.getProperties().getProperty("projeto") + "%");


            /*dyna.set("id", id);
              dyna.set("tipo", tipo);
              dyna.set("seqLinha", seqLinha);*/


            request.getSession().setAttribute("idDev", id);
            request.getSession().setAttribute("tipoDev",tipo);
            request.getSession().setAttribute("seqLinhaDev", seqLinha);

            request.setAttribute("dataDev", new SimpleDateFormat("dd/MM/yyyy").format(lote.getData()));
            request.setAttribute("valorDev", lote.getValor().doubleValue());
            request.setAttribute("devolucao",lote);
            request.setAttribute("lista", compromissos);

        } catch (IOException ex) {
            Logger.getLogger(InternalizacaoLoteBancarioAction.class.getName()).log(Level.SEVERE, null, ex);
        }

         return mapping.findForward("associarCompromisso");
    }

    public ActionForward reconhecer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;

        String idComp = (String) request.getParameter("idComp");
        String tipoComp = (String) request.getParameter("tipoComp");
        String seqLinhaComp = (String) request.getParameter("seqLinhaComp");
        /*String nomeFornecedorComp = (String) request.getParameter("nomeFornecedorComp");*/

        request.setAttribute("compromisso", log);

        dyna.set("id", idComp);
        dyna.set("tipo", tipoComp);
        dyna.set("seqLinha", seqLinhaComp);
       /*dyna.set("nomeFornecedor", URLDecoder.decode(request.getParameter("nomeFornecedorComp"), "UTF-8"));*/

        return mapping.findForward("reconhecimento");
    }

    public ActionForward salvarReconhecimento(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {        
            //String id = request.getParameter("id");
            //String parcela = request.getParameter("parcela");
            //System.out.println(dyna.getString("id")+dyna.getString("tipo"));
            GEMImpl gem = new GEMImpl();

            /*Obtem da sessão atributos da devolução */
            String idDev = (String)request.getSession().getAttribute("idDev");
            String tipoDev = (String)request.getSession().getAttribute("tipoDev");
            String seqLinhaDev = (String)request.getSession().getAttribute("seqLinhaDev");

            String banco1 = PropertiesUtil.getProperties().getProperty("banco1");
            String agencia1 = PropertiesUtil.getProperties().getProperty("agencia1");
            String conta1 = PropertiesUtil.getProperties().getProperty("conta1");

            Compromisso devolucao = gem.obterLotePorIdTipoSeqLinhaEventos(idDev, tipoDev, seqLinhaDev, EVENTOS, banco1, agencia1, conta1);
            
            InternalizacaoLoteBancario internalizacao = new InternalizacaoLoteBancario();
            
            internalizacao.setIdCompromisso(devolucao.getId());
            internalizacao.setTipo(devolucao.getTipo());
            internalizacao.setSeqLinha(devolucao.getSeqLinha());
            internalizacao.setDescricao(devolucao.getDescricao());
            internalizacao.setEntrada(devolucao.getData());
            
            /**
             * garantir que a data de exibição não venha antes da data da ultima internalizacao 
             * problema descrito em http://flemagil.flem.org.br/issues/2386
             */
            if (devolucao.getData().before(getDataMinimaExibicao())) {
                internalizacao.setDataExibicao(getDataMinimaExibicao());
            } else {
                internalizacao.setDataExibicao(devolucao.getData());
            }
            internalizacao.setValor(devolucao.getValor().doubleValue());
            internalizacao.setClassificacao(dyna.getString("classificacao"));
            internalizacao.setCentroCusto(devolucao.getCentroCusto());

            /* Dados do compromisso associado a devolução */
            Compromisso compromisso = gem.obterCompromissosPorTipoId(dyna.getString("tipo"), dyna.getString("id"));
            internalizacao.setIdComp(dyna.getString("id"));
            internalizacao.setTipoComp(dyna.getString("tipo"));
            internalizacao.setSeqLinhaComp(dyna.getString("seqLinha"));
            internalizacao.setNomeFornecedorComp(compromisso.getNomeFornecedor());
            new InternalizacaoLoteBancarioBO().inserir(internalizacao);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Internalização reconhecida com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar reconhecer a Internalização.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }        
        request.setAttribute("acao", "InternalizacaoLoteBancario.do");
        return mapping.findForward("redirect");
    }

    public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ArrayList erros = new ArrayList();
        try {
            String[] id = new String[0];
            if (request.getParameterValues("ids_exclusao") != null) {
                id = request.getParameterValues("ids_exclusao");
            }
            HibernateUtil.beginTransaction();
            for (int i = 0; i < id.length; i++) {
                InternalizacaoLoteBancarioBO internalizacaoBO = new InternalizacaoLoteBancarioBO();
                InternalizacaoLoteBancario internalizacao = internalizacaoBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    internalizacaoBO.excluir(internalizacao);
                } 
                catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    erros.add("A internalização \"" + internalizacao.getDescricao() + "\" está associada. Não pode ser excluído!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } 
        catch (AcessoDadosException ex) {
            Logger.getLogger(InternalizacaoLoteBancarioAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        request.setAttribute("acao", "InternalizacaoLoteBancario.do");
        return mapping.findForward("redirect");
    }


    public ActionForward filtrarCompromisso(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            DynaActionForm dyna = (DynaActionForm) form;
            GEM gem = new GEMImpl();
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

            String descricao = dyna.getString("descricao");
            String centroCusto = dyna.getString("centroCusto");
            Date dataInicial = !dyna.getString("dataInicial").trim().isEmpty() ? fmt.parse(dyna.getString("dataInicial")) : null;
            Date dataFinal = !dyna.getString("dataFinal").trim().isEmpty() ? fmt.parse(dyna.getString("dataFinal")) : null;
            //String valor = dyna.getString("valor");

            /*Obtem da sessão atributos da devolução */
            String idDev = (String)request.getSession().getAttribute("idDev");
            String tipoDev = (String)request.getSession().getAttribute("tipoDev");
            String seqLinhaDev = (String)request.getSession().getAttribute("seqLinhaDev");

            String banco1 = PropertiesUtil.getProperties().getProperty("banco1");
            String agencia1 = PropertiesUtil.getProperties().getProperty("agencia1");
            String conta1 = PropertiesUtil.getProperties().getProperty("conta1");

            Compromisso devolucao = gem.obterDevolucoesViagemPorIdTipoSeqLinha(idDev, tipoDev, seqLinhaDev, banco1, agencia1, conta1);

            request.setAttribute("dataDev", new SimpleDateFormat("dd/MM/yyyy").format(devolucao.getData()));
            request.setAttribute("valorDev", devolucao.getValor().doubleValue());
            request.setAttribute("devolucao",devolucao);
           /*dyna.set("id", devolucao.getApdId());
             dyna.set("tipo", devolucao.getApdTp());
             dyna.set("seqLinha", devolucao.getSeqLinha());*/
            request.setAttribute("lista", gem.obterCompromissosPorFiltros(descricao, centroCusto, dataInicial, dataFinal));

        } catch (Exception ex) {
            Logger.getLogger(InternalizacaoLoteBancarioAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapping.findForward("associarCompromisso");
    }
    
    
    /**
     * Conforme problema descrito no ticket http://flemagil.flem.org.br/issues/2386
     * Implementou-se a data de exibição para os lotes bancários para serem exibidos
     * depois da ultima internalização antes da detecção do problema.
     * @return data de internalizacao de lote superior a ultima 
     * @throws ParseException 
     */
    private Date getDataMinimaExibicao() throws ParseException {
        return  Data.formataData("17/01/2012");
    }
  
}