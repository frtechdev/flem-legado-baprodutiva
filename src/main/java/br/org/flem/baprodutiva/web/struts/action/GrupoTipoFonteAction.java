/*
 * AditivoAction.java
 *
 * Created on 17/09/2007, 15:54:34
 */
package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.baprodutiva.bo.GrupoTipoFonteBO;
import br.org.flem.baprodutiva.negocio.GrupoTipoFonte;
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
 * @author dbbarreto
 */
public class GrupoTipoFonteAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("lista", new GrupoTipoFonteBO().obterTodos());
        }
        catch(AcessoDadosException e) {
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
            GrupoTipoFonte grupoTipoFonte = new GrupoTipoFonte();
            BeanUtils.copyProperties(grupoTipoFonte, dyna);
            
            new GrupoTipoFonteBO().inserir(grupoTipoFonte);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Grupo inserido com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar inserir o Grupo.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }        
        //request.setAttribute("acao", "GrupoTipoFonte.do");
        return mapping.findForward("redirect");
    }
    
    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {        
            GrupoTipoFonte grupoTipoFonte = new GrupoTipoFonteBO().obterPorPk(Integer.valueOf(dyna.getString("id")));
            grupoTipoFonte.setDescricao(dyna.getString("descricao"));
            new GrupoTipoFonteBO().alterar(grupoTipoFonte);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("GrupoTipoFonte alterado com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar alterar o GrupoTipoFonte.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }        
        //request.setAttribute("acao", "GrupoTipoFonte.do");
        return mapping.findForward("redirect");
    }
    
    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        try {
            if (GenericValidator.isInt(id)) {

                GrupoTipoFonte grupoTipoFonte = new GrupoTipoFonteBO().obterPorPk(Integer.valueOf(id));
                BeanUtils.copyProperties(dyna, grupoTipoFonte);                
            }
            return mapping.findForward("alterar");
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar selecionar o GrupoTipoFonte.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return unspecified(mapping, form, request, response);
    }

    public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ArrayList erros = new ArrayList();
        try {
            DynaActionForm dyna = (DynaActionForm) form;
            String[] id = new String[0];
            if (request.getParameterValues("ids_exclusao") != null) {
                id = request.getParameterValues("ids_exclusao");
            }
            HibernateUtil.beginTransaction();
            for (int i = 0; i < id.length; i++) {
                GrupoTipoFonteBO grupoTipoFonteBO = new GrupoTipoFonteBO();
                GrupoTipoFonte grupoTipoFonte = grupoTipoFonteBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    grupoTipoFonteBO.excluir(grupoTipoFonte);
                } 
                catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    //ex.printStackTrace();
                    erros.add("O Grupo \"" + grupoTipoFonte.getDescricao() + "\" está associado. Não pode ser excluído!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } 
        catch (AcessoDadosException ex) {
            Logger.getLogger(GrupoTipoFonteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        return unspecified(mapping, form, request, response);
    }

}