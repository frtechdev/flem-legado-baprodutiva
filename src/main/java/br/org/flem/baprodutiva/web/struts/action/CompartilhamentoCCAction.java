/*
 * AditivoAction.java
 *
 * Created on 17/09/2007, 15:54:34
 */
package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.baprodutiva.bo.CentroCustoBO;
import br.org.flem.baprodutiva.bo.CompositeFolhaBO;
import br.org.flem.baprodutiva.negocio.CompositeFolha;
import br.org.flem.commons.util.PropertiesUtil;
import java.util.ArrayList;
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
public class CompartilhamentoCCAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            /*Collection<CompartilhamentoCC> compartilhamentos = new HashSet<CompartilhamentoCC>();
            
            for (CompositeFolha subatividade : new CompositeFolhaBO().obterComCCCompartilhado()) {
                CompartilhamentoCC compartilhamento = new CompartilhamentoCC();
                compartilhamento.setCentroCusto(centroCusto);
            }
            */
            request.setAttribute("lista", new CompositeFolhaBO().obterTodos());
            
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
        try {
            request.setAttribute("centroCustos", new CentroCustoBO().obterFilhosAnaliticos(PropertiesUtil.getProperties().getProperty("projeto")));
            request.setAttribute("subatividades", new CompositeFolhaBO().obterTodosComCCCompartilhado());
        } catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);  
        } 
        return mapping.findForward("novo");
      
    }
    
    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {        
            String cc = dyna.getString("centroCusto");
            String[] subatividades = dyna.getStrings("composites");
           
            CompositeFolhaBO bo = new CompositeFolhaBO();
            
            HibernateUtil.beginTransaction();
            for(String obj : subatividades) {
                CompositeFolha subatividade = bo.obterPorPk(Integer.valueOf(obj));
                
                bo.alterar(subatividade);
            }
            HibernateUtil.commitTransaction();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Compartilhamento realizado com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            try {
                HibernateUtil.rollbackTransaction();
            } catch (AcessoDadosException ex1) {
                Logger.getLogger(CompartilhamentoCCAction.class.getName()).log(Level.SEVERE, null, ex1);
            }
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar Compartilhar um centro de Custo.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        try {
            HibernateUtil.commitTransaction();
        } catch (AcessoDadosException ex) {
            Logger.getLogger(CompartilhamentoCCAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("acao", "CompartilhamentoCC.do");
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
                CompositeFolhaBO compositeFolhaBO = new CompositeFolhaBO();
                CompositeFolha subatividade = compositeFolhaBO.obterPorPk(Integer.valueOf(id[i]));
                try {
             
                    compositeFolhaBO.alterar(subatividade);
                } 
                catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    erros.add("O Compartilhamento de Centros de Custo com a Subatividade '"+subatividade.getDescricao()+"' NÃO foi removido.");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } 
        catch (AcessoDadosException ex) {
            Logger.getLogger(CompartilhamentoCCAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (erros.size() <= 0) {
            erros.add("O Compartilhamento de Centros de Custo com a subatividade foi removido.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        request.setAttribute("acao", "CompartilhamentoCC.do");
        return mapping.findForward("redirect");
    }

}