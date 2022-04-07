package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.baprodutiva.bo.DespesaAplicacaoFinanceiraBO;
import br.org.flem.baprodutiva.bo.DespesaInelegivelBO;
import br.org.flem.baprodutiva.bo.util.OrganizadorLancamentosBO;
import br.org.flem.baprodutiva.negocio.DespesaAplicacaoFinanceira;
import br.org.flem.fw.persistencia.dto.Compromisso;
import br.org.flem.fw.persistencia.dto.LancamentoInterface;
import br.org.flem.fw.service.GEM;
import br.org.flem.fw.service.impl.GEMImpl;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.web.tag.MensagemTag;
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
public class DespesaAplicacaoFinanceiraAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            Collection despesas = new DespesaAplicacaoFinanceiraBO().obterTodos();
            
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
            
            Collection<LancamentoInterface> todosCompromissos = OrganizadorLancamentosBO.getInstancia().obterCompromissosCustosOperacionais();
            
            Collection<Compromisso> despesasInelegiveis = new DespesaInelegivelBO().obterCompromissos();
            
            Collection<Compromisso> despesasAplicacaoFinanceira = new DespesaAplicacaoFinanceiraBO().obterCompromissos();
            
            if (despesasInelegiveis.size() > 0) {
            
                Collection<Compromisso> compromissosValidos = new ArrayList<Compromisso>();

                for (LancamentoInterface compromisso : todosCompromissos) {
                    boolean adicionarCompromisso = true;
                    for (Compromisso compromissoUsado : despesasInelegiveis) {
                        if (compromisso.getApdId() != null && compromisso.getApdTp() != null) {
                            if (compromisso.getApdId().equals(compromissoUsado.getApdId()) && compromisso.getApdTp().equals(compromissoUsado.getApdTp())) {
                                adicionarCompromisso = false;
                                break;
                            }
                        }

                    }
                    
                    for (Compromisso compromissoUsado : despesasAplicacaoFinanceira) {
                        if (compromisso.getApdId() != null && compromisso.getApdTp() != null) {
                            if (compromisso.getApdId().equals(compromissoUsado.getApdId()) && compromisso.getApdTp().equals(compromissoUsado.getApdTp())) {
                                adicionarCompromisso = false;
                                break;
                            }
                        }

                    }
                    
                    if (adicionarCompromisso) {
                        compromissosValidos.add((Compromisso)compromisso);
                    }

                }

                request.setAttribute("lista", compromissosValidos);
            }
            else {
                request.setAttribute("lista", todosCompromissos);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("listaDespesasGEM");
    }
    
    public ActionForward pagar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        
        dyna.set("apdId", request.getParameter("apdId"));
        dyna.set("apdTp", request.getParameter("apdTp"));
        //dyna.set("seqLinha", request.getParameter("seqLinha"));
        
        return mapping.findForward("pagamento");
      
    }
    
    public ActionForward salvarPagamento(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {        
            
            GEM gem = new GEMImpl();
            
            Compromisso compromisso = gem.obterCompromissosPorTipoId(dyna.getString("apdTp"), dyna.getString("apdId"));
            
            DespesaAplicacaoFinanceira despesa = new DespesaAplicacaoFinanceira();
            
            despesa.setApdId(compromisso.getApdId());
            despesa.setApdTp(compromisso.getApdTp());
            despesa.setDescricao(compromisso.getDescricao());
            despesa.setEntrada(compromisso.getData());
            despesa.setValor(compromisso.getValor().doubleValue());
                                 
            new DespesaAplicacaoFinanceiraBO().inserir(despesa);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Despesa de Aplicação Financeira salva com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar salvar a Despesa de Aplicação Financeira.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }        
        //request.setAttribute("acao", "DespesaInelegivel.do");
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
                DespesaAplicacaoFinanceiraBO despesaBO = new DespesaAplicacaoFinanceiraBO();
                DespesaAplicacaoFinanceira despesa = despesaBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    despesaBO.excluir(despesa);
                } 
                catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    erros.add("Uma Despesa de Aplicação Financeira está associada. Não pode ser excluído!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } 
        catch (AplicacaoException ex) {
            Logger.getLogger(DespesaAplicacaoFinanceiraAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        return unspecified(mapping, form, request, response);
    }
    
}