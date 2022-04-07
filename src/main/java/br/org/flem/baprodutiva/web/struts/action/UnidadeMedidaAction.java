/*
 *
 * Created on 17/09/2007, 15:54:34
 */
package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.baprodutiva.bo.UnidadeMedidaBO;
import br.org.flem.baprodutiva.negocio.UnidadeMedida;
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
public class UnidadeMedidaAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("lista", new UnidadeMedidaBO().obterTodos());
        }
        catch(AplicacaoException e) {
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
            UnidadeMedida unidadeMedida = new UnidadeMedida();
            BeanUtils.copyProperties(unidadeMedida, dyna);
            
            HibernateUtil.beginTransaction();
            new UnidadeMedidaBO().inserir(unidadeMedida);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Unidade de medida inserida com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            try {
                HibernateUtil.rollbackTransaction();
            } catch (AcessoDadosException ex1) {
                Logger.getLogger(UnidadeMedidaAction.class.getName()).log(Level.SEVERE, null, ex1);
            }
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar inserir a unidade de medida, verifique se j� est� cadastrada.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        try {
            HibernateUtil.commitTransaction();
        } catch (AcessoDadosException ex) {
            Logger.getLogger(UnidadeMedidaAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("acao", "UnidadeMedida.do");
        return mapping.findForward("redirect");
    }
    
    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {        
            UnidadeMedida unidadeMedida = new UnidadeMedidaBO().
            obterPorPk(Integer.valueOf(dyna.getString("id")));
            unidadeMedida.setDescricao(dyna.getString("descricao"));
            new UnidadeMedidaBO().alterar(unidadeMedida);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Unidade de medida alterada com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar alterar a unidade de medida, verifique se j� est� cadastrada.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }        
        request.setAttribute("acao", "UnidadeMedida.do");
        return mapping.findForward("redirect");
    }
    
    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        try {
            if (GenericValidator.isInt(id)) {

                UnidadeMedida unidadeMedida = new UnidadeMedidaBO().obterPorPk(Integer.valueOf(id));
                BeanUtils.copyProperties(dyna, unidadeMedida);                
            }
            return mapping.findForward("alterar");
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar selecionar a unidade de medida.");
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
                UnidadeMedidaBO UnidadeMedidaBO = new UnidadeMedidaBO();
                UnidadeMedida unidadeMedida = UnidadeMedidaBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    UnidadeMedidaBO.excluir(unidadeMedida);
                } 
                catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    ex.printStackTrace();
                    erros.add("A unidade de medida \"" + unidadeMedida.getDescricao() + "\" est� associada. N�o pode ser exclu�da!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } 
        catch (AplicacaoException ex) {
            ex.printStackTrace();
        }
        if (erros.size() <= 0) {
            erros.add("Exclus�o realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        return unspecified(mapping, form, request, response);
    }

}