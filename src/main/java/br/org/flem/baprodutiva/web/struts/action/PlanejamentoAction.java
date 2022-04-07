package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.baprodutiva.bo.PlanejamentoBO;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.util.Data;
import br.org.flem.fwe.web.tag.MensagemTag;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.taglibs.standard.lang.jpath.adapter.Convert;

/**
 *
 * @author mgsilva
 */
public class PlanejamentoAction extends DispatchAction {
    
    /* forward name="success" path="" */
    private final static String SUCCESS = "success";
    
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @return
     */
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){

     try {
            request.setAttribute("lista", new PlanejamentoBO().obterTodos());

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
    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){

           
        try {
            DynaActionForm dyna =(DynaActionForm)form;
            DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

            Planejamento planejamento = new Planejamento();
            planejamento.setDescricao(dyna.getString("descricao"));            
            planejamento.setDataInicial(formatoData.parse(dyna.getString("dataInicial")));
            planejamento.setDataFinal(formatoData.parse(dyna.getString("dataFinal")));
            planejamento.setAtivo(true);

            HibernateUtil.beginTransaction();
            new PlanejamentoBO().inserir(planejamento);

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Planejamento inserido com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);

        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                HibernateUtil.rollbackTransaction();
            } catch (AcessoDadosException ex1) {
                Logger.getLogger(PlanejamentoAction.class.getName()).log(Level.SEVERE, null, ex1);
            }
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar inserir o Planejamento, verifique se já está cadastrada.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
           }
           try {
                 HibernateUtil.commitTransaction();
            } catch (AcessoDadosException ex) {
                 Logger.getLogger(PlanejamentoAction.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("acao", "Planejamento.do");
            return mapping.findForward("redirect");

    }
    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {        
            DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
            Planejamento planejamento = new PlanejamentoBO().obterPorPk(Integer.valueOf(dyna.getString("id")));
            planejamento.setDescricao(dyna.getString("descricao"));
            planejamento.setDataInicial(formatoData.parse(dyna.getString("dataInicial")));
            planejamento.setDataFinal(formatoData.parse(dyna.getString("dataFinal")));
            planejamento.setAtivo(Convert.toBoolean(dyna.getString("ativo")));
           
            new PlanejamentoBO().alterar(planejamento);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Planejamento alterado com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar alterar o Planejamento, verifique se já está cadastrada.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }        
        request.setAttribute("acao", "Planejamento.do");
        return mapping.findForward("redirect");
    }
    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        try {
            if (GenericValidator.isInt(id)) {
                DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
                Planejamento planejamento = new PlanejamentoBO().obterPorPk(Integer.valueOf(id));
                 dyna.set("descricao", planejamento.getDescricao());
                 dyna.set("dataInicial",new SimpleDateFormat("dd/MM/yyyy").format(planejamento.getDataInicial()));
                 dyna.set("dataFinal", new SimpleDateFormat("dd/MM/yyyy").format(planejamento.getDataFinal()));
                 dyna.set("ativo", Convert.toString(planejamento.isAtivo()));     
            }

            return mapping.findForward("alterar");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar selecionar o Planejamento.");
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
                PlanejamentoBO planejamentoBO = new PlanejamentoBO();
                Planejamento planejamento = planejamentoBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    planejamentoBO.excluir(planejamento);
                }
                catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    erros.add("O Planejamento \"" + planejamento.getDescricao() + "\" está associado. Não pode ser excluído!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        }
        catch (AcessoDadosException ex) {
            Logger.getLogger(PlanejamentoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        return unspecified(mapping, form, request, response);
    }



}
