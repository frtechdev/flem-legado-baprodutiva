/*
 * AditivoAction.java
 *
 * Created on 17/09/2007, 15:54:34
 */
package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fw.persistencia.dto.Compromisso;
import br.org.flem.fw.service.GEM;
import br.org.flem.fw.service.impl.GEMImpl;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.baprodutiva.bo.DespesaAplicacaoFinanceiraBO;
import br.org.flem.baprodutiva.bo.DespesaInelegivelBO;
import br.org.flem.baprodutiva.bo.util.OrganizadorLancamentosBO;
import br.org.flem.baprodutiva.negocio.DespesaInelegivel;
import br.org.flem.commons.util.PropertiesUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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
public class DespesaInelegivelAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            Collection despesas = new DespesaInelegivelBO().obterTodos();
            
            if (despesas != null && !despesas.isEmpty()) {
            
                request.setAttribute("lista", despesas);
            }
            else {
                request.setAttribute("lista", new ArrayList());
            }
            
        }
        catch(AplicacaoException e) {
            e.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("lista");
    }
    
    public ActionForward listarDespesasGEM(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            
            String data = (String) request.getSession().getAttribute("filtro_data");
            String descricao = (String) request.getSession().getAttribute("filtro_descricao");
            String valor = (String) request.getSession().getAttribute("filtro_valor");

            request.setAttribute("lista", OrganizadorLancamentosBO.getInstancia().obterCompromissosValidos(data, descricao, valor));
        }
        catch(Exception e) {
            e.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("listaDespesasGEM");
    }
    
    public ActionForward filtrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        String data = (String) dyna.get("data");
        request.getSession().setAttribute("filtro_data", data);
        String descricao = (String) dyna.get("descricao");
        request.getSession().setAttribute("filtro_descricao", descricao);
        String valor = (String) dyna.get("valor");
        request.getSession().setAttribute("filtro_valor", valor);
        return listarDespesasGEM(mapping, form, request, response);
    }
    
    public ActionForward ineleger(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        
        dyna.set("apdId", request.getParameter("apdId"));
        dyna.set("apdTp", request.getParameter("apdTp"));
        //dyna.set("seqLinha", request.getParameter("seqLinha"));
        
        return mapping.findForward("ineleicao");
      
    }
    
    public ActionForward salvarIneleicao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {        
            //String id = request.getParameter("id");
            //String parcela = request.getParameter("parcela");
            
            GEM gem = new GEMImpl();
            
            Compromisso compromisso = gem.obterCompromissosPorTipoId(dyna.getString("apdTp"), dyna.getString("apdId"));
            
            DespesaInelegivel despesa = new DespesaInelegivel();
            
            despesa.setApdId(compromisso.getApdId());
            despesa.setApdTp(compromisso.getApdTp());
            //despesa.setSeqLinha(compromisso.getSeqLinha());
            despesa.setDescricao(compromisso.getDescricao());
            despesa.setEntrada(compromisso.getData());
            despesa.setValor(compromisso.getValor().doubleValue());
            despesa.setMotivo(dyna.getString("motivo"));
                                 
            new DespesaInelegivelBO().inserir(despesa);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Despesa Inelegível reconhecida com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar reconhecer a Despesa Inelegível.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }        
        request.setAttribute("acao", "DespesaInelegivel.do");
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
                DespesaInelegivelBO despesaBO = new DespesaInelegivelBO();
                DespesaInelegivel despesa = despesaBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    despesaBO.excluir(despesa);
                } 
                catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    erros.add("ums despesa inelegível está associada. Não pode ser excluído!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } 
        catch (AplicacaoException ex) {
            Logger.getLogger(DespesaInelegivelAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        return unspecified(mapping, form, request, response);
    }
    
}