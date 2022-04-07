/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.fwe.web.util.MensagemTagUtil;
import br.org.flem.baprodutiva.bo.AtividadeNovaDescricaoBO;
import br.org.flem.baprodutiva.bo.CompositeNoAtividadeBO;
import br.org.flem.baprodutiva.bo.PlanejamentoBO;
import br.org.flem.baprodutiva.negocio.AtividadeNovaDescricao;
import br.org.flem.baprodutiva.negocio.CompositeNoAtividade;
import java.util.ArrayList;
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
 * @author ILFernandes
 */
public class AtividadeNovaDescricaoAction extends DispatchAction {

    private static final String REQUEST_ATTRIBUTE = "atividadenovadescricaoaction_atividade_id";

    public ActionForward selecionarAtividade(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(REQUEST_ATTRIBUTE, request.getParameter("id"));

        return mapping.findForward("redirect");
    }

    @Override
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id_atividade");

        if (!GenericValidator.isBlankOrNull(id) || GenericValidator.isInt(id)) {
            request.getSession().setAttribute(REQUEST_ATTRIBUTE, id);
        } else if (GenericValidator.isBlankOrNull((String) request.getSession().getAttribute(REQUEST_ATTRIBUTE))
                || !GenericValidator.isInt((String) request.getSession().getAttribute(REQUEST_ATTRIBUTE))) {
            return mapping.findForward("redirectAtividade");
        }
        return listar(mapping, form, request, response);
    }

    public ActionForward listar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            CompositeNoAtividade atividade = new CompositeNoAtividadeBO().obterPorPk(Integer.valueOf((String) request.getSession().getAttribute(REQUEST_ATTRIBUTE)));
            request.setAttribute("atividade", atividade);
            request.setAttribute("lista", new AtividadeNovaDescricaoBO().obterPorAtividade(atividade));
        } catch (AplicacaoException ex) {
            Logger.getLogger(AtividadeNovaDescricaoAction.class.getName()).log(Level.SEVERE, null, ex);
            return mapping.findForward("redirectAtividade");
        }
        return mapping.findForward("lista");
    }

    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            CompositeNoAtividade atividade = new CompositeNoAtividadeBO().obterPorPk(Integer.valueOf((String) request.getSession().getAttribute(REQUEST_ATTRIBUTE)));
            request.setAttribute("atividade", atividade);
            request.setAttribute("poas", new PlanejamentoBO().obterTodos());
        } catch (AplicacaoException ex) {
            Logger.getLogger(AtividadeNovaDescricaoAction.class.getName()).log(Level.SEVERE, null, ex);
            return mapping.findForward("redirectAtividade");
        }
        return mapping.findForward("novo");
    }

    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            AtividadeNovaDescricao atividadeNovaDescricao = (AtividadeNovaDescricao) dyna.get("novaDescricao");
            new AtividadeNovaDescricaoBO().inserir(atividadeNovaDescricao);
        } catch (AplicacaoException ex) {
            Logger.getLogger(ContratoAction.class.getName()).log(Level.SEVERE, null, ex);
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Não foi possível salvar o registro: " + ex.getMessage(), "erro", AtividadeNovaDescricaoAction.class.getName(), ex);
        }
        return mapping.findForward("redirect");
    }

    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            if (!GenericValidator.isBlankOrNull(id) || GenericValidator.isInt(id)) {
                AtividadeNovaDescricao atividadeNovaDescricao = new AtividadeNovaDescricaoBO().obterPorPk(Integer.valueOf(id));
                CompositeNoAtividade atividade = new CompositeNoAtividadeBO().obterPorPk(Integer.valueOf((String) request.getSession().getAttribute(REQUEST_ATTRIBUTE)));
                request.setAttribute("atividade", atividade);
                request.setAttribute("poas", new PlanejamentoBO().obterTodos());
                dyna.set("novaDescricao", atividadeNovaDescricao);
            }
        } catch (AplicacaoException ex) {
            Logger.getLogger(AtividadeNovaDescricaoAction.class.getName()).log(Level.SEVERE, null, ex);
            return unspecified(mapping, form, request, response);
        }
        return mapping.findForward("alterar");
    }

    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            AtividadeNovaDescricao atividadeNovaDescricao = (AtividadeNovaDescricao) dyna.get("novaDescricao");
            new AtividadeNovaDescricaoBO().alterar(atividadeNovaDescricao);
        } catch (AplicacaoException ex) {
            Logger.getLogger(ContratoAction.class.getName()).log(Level.SEVERE, null, ex);
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Não foi possível salvar o registro: " + ex.getMessage(), "erro", AtividadeNovaDescricaoAction.class.getName(), ex);
        }
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
                AtividadeNovaDescricaoBO atividadeNovaDescricaoBO = new AtividadeNovaDescricaoBO();
                AtividadeNovaDescricao atividadeNovaDescricao = atividadeNovaDescricaoBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    atividadeNovaDescricaoBO.excluir(atividadeNovaDescricao);
                } catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    erros.add("O aditivo não pode ser excluído!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } catch (AplicacaoException ex) {
            Logger.getLogger(AtividadeNovaDescricaoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        return mapping.findForward("redirect");
    }
}
