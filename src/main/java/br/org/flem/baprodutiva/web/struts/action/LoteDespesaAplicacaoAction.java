/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.fw.persistencia.dto.Compromisso;
import br.org.flem.fw.service.GEM;
import br.org.flem.fw.service.impl.GEMImpl;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.util.Data;
import br.org.flem.fwe.web.util.MensagemTagUtil;
import br.org.flem.baprodutiva.bo.CategoriaBO;
import br.org.flem.baprodutiva.bo.LoteDespesaAplicacaoBO;
import br.org.flem.baprodutiva.negocio.LoteDespesaAplicacao;
import br.org.flem.commons.util.PropertiesUtil;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author ilfernandes
 */
public class LoteDespesaAplicacaoAction extends DispatchAction {

    private static final String LISTA = "lista";
    private static final String LISTAR_LOTES_GEM = "listaLotes";
    private static final String REDIRECT = "redirect";
    private static final String ALTERAR = "alterar";
    private static final String RECONHECER = "reconhecer";
    private static final String[] EVENTO_IOF = {"BJE073", "BJE003", "BJE094"};

    @Override
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            Collection<LoteDespesaAplicacao> lista = new LoteDespesaAplicacaoBO().obterTodos();
            request.setAttribute("lista", lista);
        } catch (AplicacaoException ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), ex.getMessage(), "erro", LoteDespesaAplicacaoAction.class.getName(), ex);
            Logger.getLogger(LoteDespesaAplicacaoAction.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return mapping.findForward(LISTA);
    }

    public ActionForward filtrarLotesGem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String dataFiltro = request.getParameter("dataFiltro");
        if (GenericValidator.isDate(dataFiltro, "dd/MM/yyyy", false)) {
            String dataPadraoGem = dataFiltro.substring(6) + dataFiltro.substring(3, 5) + dataFiltro.substring(0, 2);
            request.setAttribute("dataGem", Integer.valueOf(dataPadraoGem));
            request.setAttribute("dataFiltro", dataFiltro);
        }
        return listarLotesGEM(mapping, form, request, response);
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
            
            Integer dataFiltro = (Integer) request.getAttribute("dataGem");
            Integer dataFinal = null;

            if (dataFiltro == null) {
                dataFiltro = Integer.valueOf(Data.retornaDataInversa(PropertiesUtil.getProperties().getProperty("projeto.dataInicio")));
                dataFinal = Data.converteDataParaInteiro(new Date());
            }else{
                dataFinal = dataFiltro;
            }

            Collection<Compromisso> lotes = new ArrayList<Compromisso>();
            Set<String> codigos = new LoteDespesaAplicacaoBO().obterSetCodigos();

            Collection<Compromisso> compromissos = gem.obterLotesBancariosPorDataEvento(dataFiltro.intValue(), dataFinal.intValue(), EVENTO_IOF, banco1, agencia1, conta1);
            compromissos.addAll(gem.obterLotesBancariosPorDataEvento(dataFiltro.intValue(), dataFinal.intValue(), EVENTO_IOF, banco2, agencia2, conta2));

            for (Compromisso compromisso : compromissos) {
                if (!codigos.contains(compromisso.getId() + compromisso.getTipo() + compromisso.getSeqLinha())) {
                    lotes.add(compromisso);
                }
            }
            request.setAttribute("lista", lotes);
        } catch (Exception ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), ex.getMessage(), "erro", LoteDespesaAplicacaoAction.class.getName(), ex);
            Logger.getLogger(LoteDespesaAplicacaoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapping.findForward(LISTAR_LOTES_GEM);
    }

    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String idDespesa = request.getParameter("idDespesa");
        String tipo = request.getParameter("tipo");
        String seqLinha = request.getParameter("seqLinha");
        DynaActionForm dyna = (DynaActionForm) form;
        GEMImpl gem = new GEMImpl();
        
        try {
            String banco1 = PropertiesUtil.getProperties().getProperty("banco1");
            String agencia1 = PropertiesUtil.getProperties().getProperty("agencia1");
            String conta1 = PropertiesUtil.getProperties().getProperty("conta1");
            
            String banco2 = PropertiesUtil.getProperties().getProperty("banco2");
            String agencia2 = PropertiesUtil.getProperties().getProperty("agencia2");
            String conta2 = PropertiesUtil.getProperties().getProperty("conta2");
            
            Compromisso compromisso = gem.obterLotePorIdTipoSeqLinhaEventos(idDespesa, tipo, seqLinha, EVENTO_IOF, banco1, agencia1, conta1);
            if (compromisso == null) {
                compromisso = gem.obterLotePorIdTipoSeqLinhaEventos(idDespesa, tipo, seqLinha, EVENTO_IOF, banco2, agencia2, conta2);
            }
            if (compromisso != null) {
                LoteDespesaAplicacao lote = new LoteDespesaAplicacao();
                lote.setIdDespesa(compromisso.getId());
                lote.setTipo(compromisso.getTipo());
                lote.setSeqLinha(compromisso.getSeqLinha());
                lote.setValor(compromisso.getValor().doubleValue());
                lote.setDataPagamento(compromisso.getData());
                lote.setDescricao(compromisso.getDescricao());
                request.setAttribute("lote", lote);
                request.setAttribute("categorias", new CategoriaBO().obterTodos());
                return mapping.findForward(RECONHECER);
            } else {
                MensagemTagUtil.adicionarMensagem(request.getSession(), "Não foi possivel selecionar o lote");
            }
        } catch (Exception ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), ex.getMessage(), "erro", LoteDespesaAplicacaoAction.class.getName(), ex);
            Logger.getLogger(LoteDespesaAplicacaoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarLotesGEM(mapping, form, request, response);
    }

    public ActionForward salvar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        LoteDespesaAplicacao lote = (LoteDespesaAplicacao) dyna.get("lote");
        try {
            String data = request.getParameter("dataPagamento");
            lote.setDataPagamento(Data.formataData(data));
            new LoteDespesaAplicacaoBO().inserir(lote);
        } catch (Exception ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Não foi possível salvar o lote " + ex.getMessage(), "erro", LoteDespesaAplicacaoAction.class.getName(), ex);
            Logger.getLogger(LoteDespesaAplicacaoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapping.findForward(REDIRECT);
    }

    public ActionForward selecionarLote(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        try {
            if (GenericValidator.isInt(id)) {
                LoteDespesaAplicacao lote = new LoteDespesaAplicacaoBO().obterPorPk(Integer.valueOf(id));
                request.setAttribute("lote", lote);
                request.setAttribute("categorias", new CategoriaBO().obterTodos());

            }
        } catch (AplicacaoException ex) {
            Logger.getLogger(LoteDespesaAplicacaoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapping.findForward(ALTERAR);

    }

    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        LoteDespesaAplicacao lote = (LoteDespesaAplicacao) dyna.get("lote");
        try {
            String data = request.getParameter("dataPagamento");
            lote.setDataPagamento(Data.formataData(data));
            new LoteDespesaAplicacaoBO().alterar(lote);
        } catch (Exception ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Não foi possível salvar o lote " + ex.getMessage(), "erro", LoteDespesaAplicacaoAction.class.getName(), ex);
            Logger.getLogger(LoteDespesaAplicacaoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapping.findForward(REDIRECT);
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
                LoteDespesaAplicacaoBO loteDespesaAplicacaoBO = new LoteDespesaAplicacaoBO();
                LoteDespesaAplicacao loteDespesaAplicacao = loteDespesaAplicacaoBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    loteDespesaAplicacaoBO.excluir(loteDespesaAplicacao);
                } catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    MensagemTagUtil.adicionarMensagem(request.getSession(), "Houve um erro ao tentar excluir o lote: \"" + loteDespesaAplicacao.getDescricao() + "\"!");
                    erros.add("");
                    break;
                }
            }
            HibernateUtil.commitTransaction();
        } catch (AplicacaoException ex) {
            Logger.getLogger(LoteDespesaAplicacaoAction.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        if (erros.size() <= 0) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Exclusão realizada com sucesso!");
        }
        return mapping.findForward("redirect");
    }

}
