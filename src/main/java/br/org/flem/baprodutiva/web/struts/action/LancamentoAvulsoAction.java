package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.baprodutiva.bo.LancamentoAvulsoBO;
import br.org.flem.baprodutiva.bo.util.OrganizadorLancamentosBO;
import br.org.flem.baprodutiva.negocio.LancamentoAvulso;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.DynaActionForm;

/**
 *
 * @author essantos
 */
public class LancamentoAvulsoAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        try {

            request.setAttribute("lista", new LancamentoAvulsoBO().obterTodos());

            return mapping.findForward("lista");

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return mapping.findForward("redirect");
    }

    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;

        dyna.initialize(mapping);

        return mapping.findForward("novo");
    }

    public ActionForward inserir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        DynaActionForm dyna = (DynaActionForm) form;

        try {

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            LancamentoAvulso lancamentoAvulso = (LancamentoAvulso) dyna.get("lancamentoAvulso");
            String dataPagamento = dyna.getString("dataPagamento");

            lancamentoAvulso.setDataPagamento(formatter.parse(dataPagamento));

            HibernateUtil.beginTransaction();
            new LancamentoAvulsoBO().inserir(lancamentoAvulso);
            HibernateUtil.commitTransaction();

            OrganizadorLancamentosBO.getInstancia().forcarAtualizacaoAvulsos();

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("O lançamento avulso foi inserido com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Erro ao inserir lançamento avulso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }

        return mapping.findForward("redirect");
    }

    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        try {

            DynaActionForm dyna = (DynaActionForm) form;
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

            String id = request.getParameter("id");

            if (GenericValidator.isInt(id)) {
                LancamentoAvulso lancamentoAvulso = new LancamentoAvulsoBO().obterPorPk(new Integer(id));

                dyna.set("lancamentoAvulso", lancamentoAvulso);
                request.setAttribute("dataPagamento", dateFormatter.format(lancamentoAvulso.getDataPagamento()));

                return mapping.findForward("alterar");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Erro ao requisitar alteração da data de exibição.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return mapping.findForward("redirect");
    }

    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        DynaActionForm dyna = (DynaActionForm) form;

        try {

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            LancamentoAvulso lancamentoAvulsoForm = (LancamentoAvulso) dyna.get("lancamentoAvulso");
            String dataPagamento = dyna.getString("dataPagamento");

            LancamentoAvulso lancamentoAvulso = new LancamentoAvulsoBO().obterPorPk(lancamentoAvulsoForm.getId());
            lancamentoAvulso.setDataPagamento(formatter.parse(dataPagamento));
            lancamentoAvulso.setCodigoItem(lancamentoAvulsoForm.getCodigoItem());
            lancamentoAvulso.setNomeForn(lancamentoAvulsoForm.getNomeForn());
            lancamentoAvulso.setDescricao(lancamentoAvulsoForm.getDescricao());
            lancamentoAvulso.setNumeroContrato(lancamentoAvulsoForm.getNumeroContrato());
            lancamentoAvulso.setValorPagamento(lancamentoAvulsoForm.getValorPagamento());
            lancamentoAvulso.setCentroCusto(lancamentoAvulsoForm.getCentroCusto());

            HibernateUtil.beginTransaction();
            new LancamentoAvulsoBO().alterar(lancamentoAvulso);
            HibernateUtil.commitTransaction();

            OrganizadorLancamentosBO.getInstancia().forcarAtualizacaoAvulsos();

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("O lançamento avulso foi alterado com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Erro ao alterar lançamento avulso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }

        return mapping.findForward("redirect");
    }

    public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        try {

            String[] id = new String[0];

            if (request.getParameterValues("ids_exclusao") != null) {
                id = request.getParameterValues("ids_exclusao");
            }

            HibernateUtil.beginTransaction();
            for (int i = 0; i < id.length; i++) {
                LancamentoAvulso lancamentoAvulso = new LancamentoAvulsoBO().obterPorPk(Integer.valueOf(id[i]));
                new LancamentoAvulsoBO().excluir(lancamentoAvulso);
            }
            HibernateUtil.commitTransaction();

            OrganizadorLancamentosBO.getInstancia().forcarAtualizacaoAvulsos();

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("O lançamento avulso foi removido com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);

        } catch (Exception ex) {
            try {
                HibernateUtil.rollbackTransaction();
            } catch (Exception ex1) {
            }
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Erro ao remover lançamento avulso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }

        return unspecified(mapping, form, request, response);
    }
}
