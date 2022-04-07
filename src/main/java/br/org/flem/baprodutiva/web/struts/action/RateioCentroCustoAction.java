package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.fw.persistencia.dto.Compromisso;
import br.org.flem.fw.persistencia.dto.LancamentoInterface;
import br.org.flem.fw.service.GEM;
import br.org.flem.fw.service.impl.GEMImpl;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.baprodutiva.bo.RateioCentroCustoBO;
import br.org.flem.baprodutiva.bo.util.OrganizadorLancamentosBO;
import br.org.flem.baprodutiva.dto.CompromissoDTO;
import br.org.flem.baprodutiva.negocio.RateioCentroCusto;
import java.io.IOException;
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
 *
 * @author MCCosta
 */
public class RateioCentroCustoAction extends DispatchAction {
    
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            GEM gem = new GEMImpl();
            Collection<CompromissoDTO> compromissosDTO = new ArrayList<CompromissoDTO>();
            CompromissoDTO compromissoDTO = null;
            Compromisso compromisso = new Compromisso();
            Collection<RateioCentroCusto> rateioCentroCustos = new RateioCentroCustoBO().obterTodos();
            for (RateioCentroCusto rateioCentroCusto : rateioCentroCustos) {
                compromissoDTO = new CompromissoDTO();
                
                compromisso = gem.obterCompromissosPorTipoId(rateioCentroCusto.getApdTp(), rateioCentroCusto.getApdId());
                compromissoDTO.setCompromisso(compromisso);
                compromissoDTO.setRateioCentroCusto(rateioCentroCusto);
                
                compromissosDTO.add(compromissoDTO);
            }
            
            request.setAttribute("lista", compromissosDTO);
                
        } catch (AplicacaoException ex) {
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        
        return mapping.findForward("lista");
    }
    
    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            RateioCentroCustoBO rateioCentroCustoBO = new RateioCentroCustoBO();
            //Collection<LancamentoInterface> lancamentoInterfacesAux = OrganizadorLancamentosBO.getInstancia().obterCompromissos();
            Collection<LancamentoInterface> lancamentoInterfacesAux = OrganizadorLancamentosBO.getInstancia().obterCompromissos();
            Collection<LancamentoInterface> lancamentoInterfaces = new ArrayList<LancamentoInterface>();
            RateioCentroCusto rateioCentroCusto = null;
            
            for (LancamentoInterface lancamentoInterface : lancamentoInterfacesAux) {
                //System.out.println(lancamentoInterface.getApdId() + ", " + lancamentoInterface.getApdTp());
                rateioCentroCusto = rateioCentroCustoBO.obterPorTipoId(lancamentoInterface.getApdTp(), lancamentoInterface.getApdId());
                if(rateioCentroCusto == null){
                    lancamentoInterfaces.add(lancamentoInterface);
                }
                //System.out.println(rateioCentroCusto);
            }
            
            request.setAttribute("compromissos", lancamentoInterfaces);
            
        } catch (IOException ex) {
            Logger.getLogger(RateioCentroCustoAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AplicacaoException ex) {
            Logger.getLogger(RateioCentroCustoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mapping.findForward("novo");
    }
    
    public ActionForward criarRateio(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String apdId = request.getParameter("apdId");
        String apdTp = request.getParameter("apdTp");
           
        request.setAttribute("apdId", apdId);
        request.setAttribute("apdTp", apdTp);
            
        return mapping.findForward("novoRateio");
    }
    
    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            
            RateioCentroCusto rateioCentroCusto = (RateioCentroCusto) dyna.get("rateioCentroCusto");
            new RateioCentroCustoBO().inserir(rateioCentroCusto);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Rateio de Centro de Custo inserido com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar inserir o Rateio de Centro de Custo.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }        
        request.setAttribute("acao", "RateioCentroCusto.do");
        return mapping.findForward("redirect");
    }
    
    public ActionForward alterarRateio(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {  
            RateioCentroCusto rateioCentroCusto = (RateioCentroCusto) dyna.get("rateioCentroCusto");
            new RateioCentroCustoBO().alterar(rateioCentroCusto);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Rateio de Centro de Custo alterado com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar alterar o Rateio de Centro de Custo.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }        
        request.setAttribute("acao", "RateioCentroCusto.do");
        return mapping.findForward("redirect");
    }
    
    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        
        try {
            if (GenericValidator.isInt(id)) {
                RateioCentroCusto rateioCentroCusto = new RateioCentroCustoBO().obterPorPk(Integer.valueOf(id));
                dyna.set("rateioCentroCusto", rateioCentroCusto);
            }
            return mapping.findForward("alterarRateio");
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar selecionar o Rateio de Centro de Custo.");
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
                RateioCentroCustoBO rateioCentroCustoBO = new RateioCentroCustoBO();
                RateioCentroCusto rateioCentroCusto = rateioCentroCustoBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    rateioCentroCustoBO.excluir(rateioCentroCusto);
                } 
                catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    //ex.printStackTrace();
                    erros.add("O Rateio Centrode Custo \"" + rateioCentroCusto.getId() + "\" está associada. Não pode ser excluído!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } catch (AplicacaoException ex) {
            Logger.getLogger(RateioCentroCustoAction.class.getName()).log(Level.SEVERE, null, ex);
        } 
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        return unspecified(mapping, form, request, response);
    }
}
