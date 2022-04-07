package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.baprodutiva.bo.CentroCustoBO;
import br.org.flem.baprodutiva.bo.GrupoTipoFonteBO;
import br.org.flem.baprodutiva.bo.TipoFonteBO;
import br.org.flem.baprodutiva.negocio.GrupoTipoFonte;
import br.org.flem.baprodutiva.negocio.OrigemFonte;
import br.org.flem.baprodutiva.negocio.TipoFonte;
import br.org.flem.commons.util.PropertiesUtil;
import java.io.IOException;
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
public class TipoFonteAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("lista", new TipoFonteBO().obterTodos());            
            
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
            request.setAttribute("grupoTipoFontes", new GrupoTipoFonteBO().obterTodos());
            request.setAttribute("origens", OrigemFonte.getLista());

            return mapping.findForward("novo");
        } catch (Exception ex) {
            Logger.getLogger(TipoFonteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return unspecified(mapping, form, request, response);
    }
    
    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {        
            TipoFonte tipoFonte = new TipoFonte();
            //BeanUtils.copyProperties(tipoFonte, dyna);
            
            GrupoTipoFonte grupoTipoFonte = new GrupoTipoFonteBO().obterPorPk(Integer.valueOf(dyna.getString("grupoTipoFonteId")));    
            tipoFonte.setGrupoTipo(grupoTipoFonte);
            
            tipoFonte.setDescricao(dyna.getString("descricao"));
            
            if (!dyna.getString("origemId").isEmpty()) {
                tipoFonte.setOrigem(OrigemFonte.valueOf(dyna.getString("origemId")));
            }
            
            new TipoFonteBO().inserir(tipoFonte);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Tipo inserido com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar inserir o Tipo.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }        
        request.setAttribute("acao", "TipoFonte.do");
        return mapping.findForward("redirect");
    }
    
    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {        
            TipoFonte tipoFonte = new TipoFonteBO().obterPorPk(Integer.valueOf(dyna.getString("id")));
            tipoFonte.setDescricao(dyna.getString("descricao"));
            tipoFonte.setOrigem(null);
            
            if (!dyna.getString("origemId").isEmpty()) {
                tipoFonte.setOrigem(OrigemFonte.valueOf(dyna.getString("origemId")));
            }
            
            GrupoTipoFonte grupoTipoFonte = new GrupoTipoFonteBO().obterPorPk(Integer.valueOf(dyna.getString("grupoTipoFonteId")));    
            tipoFonte.setGrupoTipo(grupoTipoFonte);
            
            new TipoFonteBO().alterar(tipoFonte);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("TipoFonte alterado com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar alterar o TipoFonte.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }        
        request.setAttribute("acao", "TipoFonte.do");
        return mapping.findForward("redirect");
    }
    
    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        request.setAttribute("grupoTipoFontes", new ArrayList());
        try {
            if (GenericValidator.isInt(id)) {

                TipoFonte tipoFonte = new TipoFonteBO().obterPorPk(Integer.valueOf(id));
                //BeanUtils.copyProperties(dyna, tipoFonte);
                
                dyna.set("descricao",tipoFonte.getDescricao());
                
                if(tipoFonte.getOrigem() != null) {
                    dyna.set("origemId",tipoFonte.getOrigem().getConstante());
                }
                
                dyna.set("grupoTipoFonteId",tipoFonte.getGrupoTipo().getId().toString());                
                request.setAttribute("grupoTipoFontes", new GrupoTipoFonteBO().obterTodos());
            }
            request.setAttribute("origens", OrigemFonte.getLista());
            //request.setAttribute("centroCustos", new CentroCustoBO().obterFilhos(PropertiesUtil.getProperties().getProperty("projeto")));
            return mapping.findForward("alterar");
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar selecionar o Tipo.");
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
                TipoFonteBO tipoFonteBO = new TipoFonteBO();
                TipoFonte tipoFonte = tipoFonteBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    tipoFonteBO.excluir(tipoFonte);
                } 
                catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    //ex.printStackTrace();
                    erros.add("O Tipo \"" + tipoFonte.getDescricao() + "\" está associado. Não pode ser excluído!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } 
        catch (AcessoDadosException ex) {
            Logger.getLogger(TipoFonteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        return unspecified(mapping, form, request, response);
    }

}