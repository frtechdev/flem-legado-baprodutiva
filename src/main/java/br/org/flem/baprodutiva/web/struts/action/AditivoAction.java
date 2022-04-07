/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.util.Data;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.fwe.web.util.MensagemTagUtil;
import br.org.flem.baprodutiva.bo.ContratoBO;
import br.org.flem.baprodutiva.bo.AditivoBO;
import br.org.flem.baprodutiva.negocio.Contrato;
import br.org.flem.baprodutiva.negocio.Aditivo;
import java.util.ArrayList;
import java.util.Collection;
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
public class AditivoAction extends DispatchAction {

    private final String STRING_CONTRATO_SESSAO = "aditivoAction_contrato_sessao";

    @Override
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String idContrato = request.getParameter("idContrato");

        if (idContrato == null || idContrato.isEmpty()) {
            idContrato = (String) request.getSession().getAttribute(STRING_CONTRATO_SESSAO);
        }
        try {
            if (GenericValidator.isInt(idContrato)) {
                Contrato contrato = new ContratoBO().obterPorPk(Integer.valueOf(idContrato));
                Collection<Aditivo> aditivos = new AditivoBO().obterPorContrato(contrato);
                request.setAttribute("aditivos", aditivos);
                request.setAttribute("contrato", contrato);
                request.getSession().setAttribute(STRING_CONTRATO_SESSAO, idContrato);
            }
        } catch (Exception ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), ex.getMessage(), "erro", this.getClass().getName(), ex);
            Logger.getLogger(AditivoAction.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return mapping.findForward("lista");
    }

    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        String idContrato = request.getParameter("idContrato");
        dyna.set("idContrato", idContrato);
        return mapping.findForward("novo");
    }

    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            if (GenericValidator.isInt(dyna.getString("idContrato")) && GenericValidator.isDate(dyna.getString("novaData"), "dd/MM/yyyy", true)) {
                Contrato contrato = new Contrato();
                contrato.setId(Integer.valueOf(dyna.getString("idContrato")));
                Aditivo aditivo = new Aditivo();
                aditivo.setNovaData(Data.formataData(dyna.getString("novaData")));
                aditivo.setContrato(contrato);
                new AditivoBO().inserir(aditivo);
                MensagemTagUtil.adicionarMensagem(request.getSession(), "Aditivo adicionado com sucesso");
            }
        } catch (Exception ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), ex.getMessage(), "erro", this.getClass().getName(), ex);
            Logger.getLogger(AditivoAction.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return mapping.findForward("redirect");
    }

    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        try {
            if (GenericValidator.isInt(id)) {
                Aditivo aditivo = new AditivoBO().obterPorPk(Integer.valueOf(id));
                dyna.set("novaData", Data.formataData(aditivo.getNovaData()));
                dyna.set("idContrato", aditivo.getContrato().getId().toString());
                dyna.set("id", aditivo.getId().toString());
            }
        } catch (Exception ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), ex.getMessage(), "erro", this.getClass().getName(), ex);
            Logger.getLogger(AditivoAction.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return mapping.findForward("alterar");
    }

    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            if (GenericValidator.isInt(dyna.getString("idContrato")) && GenericValidator.isInt(dyna.getString("id"))
                    && GenericValidator.isDate(dyna.getString("novaData"), "dd/MM/yyyy", true)) {
                Contrato contrato = new Contrato();
                contrato.setId(Integer.valueOf(dyna.getString("idContrato")));
                Aditivo aditivo = new Aditivo();
                aditivo.setNovaData(Data.formataData(dyna.getString("novaData")));
                aditivo.setContrato(contrato);
                aditivo.setId(Integer.valueOf(dyna.getString("id")));
                new AditivoBO().alterar(aditivo);
                MensagemTagUtil.adicionarMensagem(request.getSession(), "Aditivo alterado com sucesso");
            }
        } catch (Exception ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), ex.getMessage(), "erro", this.getClass().getName(), ex);
            Logger.getLogger(AditivoAction.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
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
                AditivoBO aditivoBO = new AditivoBO();
                Aditivo aditivo = aditivoBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    aditivoBO.excluir(aditivo);
                } catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    erros.add("O aditivo não pode ser excluído!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } catch (AplicacaoException ex) {
            Logger.getLogger(ContratoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        return mapping.findForward("redirect");
    }
}
