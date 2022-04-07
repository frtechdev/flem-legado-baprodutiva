/*
 * Created on 17/09/2007, 15:54:34
 */
package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.baprodutiva.bo.OrgaoResponsavelBO;
import br.org.flem.baprodutiva.negocio.OrgaoResponsavel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

/**
 * @author fcsilva
 */
public class OrgaoResponsavelAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("lista", new OrgaoResponsavelBO().obterTodos());
        } catch (AplicacaoException e) {
            e.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("lista");
    }

    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        return mapping.findForward("novo");
    }

    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        
            DynaActionForm dyna = (DynaActionForm) form;
            try {
                OrgaoResponsavel orgaoResponsavel = new OrgaoResponsavel();
                BeanUtils.copyProperties(orgaoResponsavel, dyna);
                
                HibernateUtil.beginTransaction();
                new OrgaoResponsavelBO().inserir(orgaoResponsavel);
                
                List<String> mensagens = new ArrayList<String>();
                mensagens.add("Orgão Responsável inserido com sucesso.");
                request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
            } catch (Exception ex) {
                try {
                    HibernateUtil.rollbackTransaction();
                } catch (AcessoDadosException ex1) {
                    Logger.getLogger(OrgaoResponsavelAction.class.getName()).log(Level.SEVERE, null, ex1);
                }
                List<String> mensagens = new ArrayList<String>();
                mensagens.add("Ocorreu um erro ao tentar inserir Orgão Responsável, verifique se já está cadastrado");
                request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
            }
        try {
            HibernateUtil.commitTransaction();
        } catch (AcessoDadosException ex) {
            Logger.getLogger(OrgaoResponsavelAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("acao", "OrgaoResponsavel.do");
        return mapping.findForward("redirect");        
        //return unspecified(mapping, form, request, response);
    }

    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            OrgaoResponsavel orgaoResponsavel = new OrgaoResponsavelBO().obterPorPk(Integer.valueOf(dyna.getString("id")));
            orgaoResponsavel.setDescricao(dyna.getString("descricao"));
            new OrgaoResponsavelBO().alterar(orgaoResponsavel);

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ã“rgão Responsável alterada com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } catch (Exception ex) {
            ex.printStackTrace();

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar alterar o Ã“rgão responsável, verifique se já está cadastrado");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        request.setAttribute("acao", "OrgaoResponsavel.do");
        return mapping.findForward("redirect");
    }

    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        try {
            if (GenericValidator.isInt(id)) {

                OrgaoResponsavel orgaoResponsavel = new OrgaoResponsavelBO().obterPorPk(Integer.valueOf(id));
                BeanUtils.copyProperties(dyna, orgaoResponsavel);
            }
            return mapping.findForward("alterar");
        } catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar selecionar o órgão responsável.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return unspecified(mapping, form, request, response);
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
                OrgaoResponsavelBO orgaoResponsavelBO = new OrgaoResponsavelBO();
                OrgaoResponsavel orgaoResponsavel = orgaoResponsavelBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    // Verifica se o Ã“rgão Responsável está associado, nesse caso não exclui o registro
                    if (orgaoResponsavel.getCompositeFolhas().size() > 0) {
                        HibernateUtil.rollbackTransaction();
                        erros.add("O Orgão Responsável \"" + orgaoResponsavel.getDescricao() + "\" está associado. Não pode ser excluído!");
                        request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                        break;
                    }
                    orgaoResponsavelBO.excluir(orgaoResponsavel);
                } catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    ex.printStackTrace();
                    erros.add("O Orgão Responsável \"" + orgaoResponsavel.getDescricao() + "\" está associado. Não pode ser excluído!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } catch (AplicacaoException ex) {
            ex.printStackTrace();
        }
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        return unspecified(mapping, form, request, response);
    }
}