/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.web.struts.action;



import br.org.flem.baprodutiva.bo.CategoriaBO;


import br.org.flem.baprodutiva.bo.SubCategoriaBO;
import br.org.flem.baprodutiva.bo.TipoFonteBO;
import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.SubCategoria;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.web.tag.MensagemTag;
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
 *
 * @author AJLima
 */
public class SubCategoriaAction  extends DispatchAction{
    
     public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("lista", new SubCategoriaBO().obterTodos());
            
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
            request.setAttribute("categorias", new CategoriaBO().obterTodos());
        }
        catch(AcessoDadosException e) {
            e.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("novo");
      
    }
     public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {        
            
            SubCategoria subcategoria = new SubCategoria();
            
            subcategoria.setDescricao(dyna.getString("descricao"));
            subcategoria.setCategoria(new CategoriaBO().obterPorPk(Integer.valueOf(dyna.getString("categoriaId"))));
            new SubCategoriaBO().inserir(subcategoria);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Categoria inserida com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            try {
                HibernateUtil.rollbackTransaction();
            } catch (AcessoDadosException ex1) {
                Logger.getLogger(CategoriaAction.class.getName()).log(Level.SEVERE, null, ex1);
            }
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar inserir a Categoria, verifique se já está cadastrada.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        try {
            HibernateUtil.commitTransaction();
        } catch (AcessoDadosException ex) {
            Logger.getLogger(CategoriaAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("acao", "Categoria.do");
        return mapping.findForward("redirect");
    }
     public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        
        String id = request.getParameter("id");
        
        try {
            if (GenericValidator.isInt(id)) {
                SubCategoria subcategoria = new SubCategoriaBO().obterPorPk((Integer.valueOf(id)));
                dyna.set("descricao", subcategoria.getDescricao());
                dyna.set("categoriaId", subcategoria.getId().toString());
                dyna.set("categoriaId",subcategoria.getCategoria().getId().toString());
            }
            request.setAttribute("categorias", new CategoriaBO().obterTodos());
            return mapping.findForward("alterar");
        } catch (Exception ex) {
            
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar selecionar a SubCategoria.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return unspecified(mapping, form, request, response);
    }
     public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
       
        
        String id = request.getParameter("id");
        
        try {    
            
            SubCategoria subcategoria = new SubCategoriaBO().obterPorPk(Integer.valueOf(dyna.getString("id")));
            subcategoria.setDescricao(dyna.getString("descricao"));
            Categoria cat = new CategoriaBO().obterPorPk(subcategoria.getCategoria().getId());
            subcategoria.setCategoria(cat);
            new SubCategoriaBO().alterar(subcategoria);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("SubCategoria alterado com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar alterar a SubCategoria.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }        
        request.setAttribute("acao", "SubCategoria.do");
        return mapping.findForward("redirect");
    }
}
