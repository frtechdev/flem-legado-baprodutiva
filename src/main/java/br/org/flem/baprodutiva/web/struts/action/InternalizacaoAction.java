/*
 * AditivoAction.java
 *
 * Created on 17/09/2007, 15:54:34
 */
package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.baprodutiva.bo.InternalizacaoBO;
import br.org.flem.baprodutiva.negocio.Internalizacao;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
 * @author dbbarreto
 */
public class InternalizacaoAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            Collection internalizacoes = new InternalizacaoBO().obterTodos();
            
            if (internalizacoes != null && !internalizacoes.isEmpty()) {
            
                request.setAttribute("lista", internalizacoes);
            }
            else {
                request.setAttribute("lista", new ArrayList());
            }
            
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
            Internalizacao internalizacao = new Internalizacao();
            //BeanUtils.copyProperties(internalizacao, dyna);
            internalizacao.setDescricao(dyna.getString("descricao"));
            internalizacao.setEntrada(new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("entrada")));
            internalizacao.setIdCompromisso("");
            internalizacao.setParcela("");
            internalizacao.setValor(Double.valueOf(dyna.getString("valor")));

            HibernateUtil.beginTransaction();
            new InternalizacaoBO().inserir(internalizacao);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Internalizacao inserida com sucesso.");
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
            mensagens.add("Ocorreu um erro ao tentar inserir a Internalizacao.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        try {
            HibernateUtil.commitTransaction();
        } catch (AcessoDadosException ex) {
            Logger.getLogger(CategoriaAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("acao", "Internalizacao.do");
        return mapping.findForward("redirect");
    }
    
    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {        
            Internalizacao internalizacao = new InternalizacaoBO().obterPorPk(Integer.valueOf(dyna.getString("id")));
            internalizacao.setDescricao(dyna.getString("descricao"));
            internalizacao.setEntrada(new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("entrada")));
            internalizacao.setIdCompromisso("");
            internalizacao.setParcela("");
            internalizacao.setValor(Double.valueOf(dyna.getString("valor")));

            new InternalizacaoBO().alterar(internalizacao);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Internalizacao alterada com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar alterar a Internalizacao");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }        
        request.setAttribute("acao", "Internalizacao.do");
        return mapping.findForward("redirect");
    }
    
    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        try {
            if (GenericValidator.isInt(id)) {

                Internalizacao internalizacao = new InternalizacaoBO().obterPorPk(Integer.valueOf(id));
                dyna.set("descricao", internalizacao.getDescricao());
                dyna.set("entrada", new SimpleDateFormat("dd/MM/yyyy").format(internalizacao.getEntrada()));
                dyna.set("valor", internalizacao.getValor().toString());
            }
            
            return mapping.findForward("alterar");
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar selecionar a Internalizacao.");
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
                InternalizacaoBO internalizacaoBO = new InternalizacaoBO();
                Internalizacao internalizacao = internalizacaoBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    internalizacaoBO.excluir(internalizacao);
                } 
                catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    erros.add("A internalização \"" + internalizacao.getDescricao() + "\" está associada. Não pode ser excluído!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } 
        catch (AcessoDadosException ex) {
            Logger.getLogger(InternalizacaoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        request.setAttribute("acao", "Internalizacao.do");
        return mapping.findForward("redirect");
    }
    
    /*public ActionForward listarReceitasGEM(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            GEM gem = new GEMImpl();
            
            Collection compromissos = gem.obterReceitasPorCentroCustoTipo(PropertiesUtil.getProperties().getProperty("projeto")+"0000", ReceitaTipoEnum.PROC.getDescricao());
            
            compromissos.removeAll(new InternalizacaoBO().obterCompromissos());
            
            request.setAttribute("lista", compromissos);
            
        }
        catch(Exception e) {
            e.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("listaReceitasGEM");
    }
    
    public ActionForward reconhecer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        
        dyna.set("id", request.getParameter("id"));
        dyna.set("parcela", request.getParameter("parcela"));
        
        return mapping.findForward("reconhecimento");
      
    }
    
    public ActionForward salvarReconhecimento(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {        
            //String id = request.getParameter("id");
            //String parcela = request.getParameter("parcela");
            
            GEM gem = new GEMImpl();
            
            Compromisso compromisso = gem.obterReceitaPorIdParcela(dyna.getString("id"), dyna.getString("parcela"));
            
            Internalizacao internalizacao = new Internalizacao();
            
            internalizacao.setIdCompromisso(compromisso.getId());
            internalizacao.setParcela(compromisso.getParcela());
            internalizacao.setDescricao(compromisso.getDescricao());
            internalizacao.setEntrada(compromisso.getData());
            internalizacao.setValor(compromisso.getValor());
                                 
            new InternalizacaoBO().inserir(internalizacao);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Internalização reconhecida com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar reconhecer a Internalização.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }        
        request.setAttribute("acao", "Internalizacao.do");
        return mapping.findForward("redirect");
    }*/
    
}