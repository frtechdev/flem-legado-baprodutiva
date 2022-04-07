package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.baprodutiva.bo.CompositeFolhaBO;
import br.org.flem.baprodutiva.bo.FinanceiroPrevistoBO;
import br.org.flem.baprodutiva.bo.PlanejamentoBO;
import br.org.flem.baprodutiva.negocio.CompositeFolha;
import br.org.flem.baprodutiva.negocio.FinanceiroPrevisto;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.web.tag.MensagemTag;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * @author essantos
 */
public class SubAtividadeFinanceiroPrevistoAction extends DispatchAction {

    private static final String REQUEST_ATTRIBUTE = "financeiro_previsto_subatividade_id";

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        Integer subAtividadeId = (Integer) request.getSession().getAttribute(REQUEST_ATTRIBUTE);
        if (subAtividadeId == null) {
            request.setAttribute("acao", "SubAtividade.do");
            return mapping.findForward("redirect");
        } else {
            return listar(mapping, form, request, response);
        }

    }
    public ActionForward selecionarSubAtividade(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws AcessoDadosException {
        Integer id = Integer.valueOf(request.getParameter("id"));
       

        request.getSession().setAttribute(REQUEST_ATTRIBUTE, id);

        request.setAttribute("acao", "SubAtividadeFinanceiroPrevisto.do");

        return mapping.findForward("redirect");
    }
    public ActionForward listar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
                       
            CompositeFolha subatividade = new CompositeFolhaBO().obterPorPk((Integer) request.getSession().getAttribute(REQUEST_ATTRIBUTE));
            request.setAttribute("subAtividade", subatividade);

            Collection<FinanceiroPrevisto> lista = new FinanceiroPrevistoBO().obterTodosPorSubatividadeOrdenadoPorData(subatividade);

            request.setAttribute("lista", lista);
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("lista");
    }
    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("financeiroId");
            CompositeFolha subAtividade = new CompositeFolhaBO().obterPorPk((Integer) request.getSession().getAttribute(REQUEST_ATTRIBUTE));
            if (subAtividade == null) {
                return new SubAtividadeAction().unspecified(mapping, form, request, response);
            }
            //Planejamento planejamento = subAtividade.getPlanejamento();
            FinanceiroPrevisto financeiroPrevisto = new FinanceiroPrevistoBO().obterPorPk(Integer.valueOf(id));
            
            if(financeiroPrevisto ==  null){
                FinanceiroPrevisto f = new FinanceiroPrevisto();
                HibernateUtil.beginTransaction();
                f.setComposite(subAtividade);
                //f.setPlanejamento(planejamento);
                String valor = request.getParameter("valor");
                f.setValor(new Double(valor == null ? "0" : valor));
                new FinanceiroPrevistoBO().alterar(f);
            
            } else {
                
            HibernateUtil.beginTransaction();
            DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
            String  idFinanceiro = financeiroPrevisto.getId().toString();
            if(idFinanceiro.equals("")){
                financeiroPrevisto = new FinanceiroPrevisto();
            } else {
             financeiroPrevisto = new FinanceiroPrevistoBO().obterPorPk(new Integer(idFinanceiro));
            }

            financeiroPrevisto.setComposite(subAtividade);
         //   financeiroPrevisto.setPlanejamento(planejamento);

            String valor = request.getParameter("valor");


            financeiroPrevisto.setValor(new Double(valor == null ? "0" : valor));

            new FinanceiroPrevistoBO().alterar(financeiroPrevisto);
            HibernateUtil.beginTransaction();
            }
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Planejamento Financeiro alterado com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar alterar o Planejamento Financeiro.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        request.setAttribute("acao", "SubAtividadeFinanceiroPrevisto.do");
        return mapping.findForward("redirect");
    }
    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws AcessoDadosException {
        try {
            request.setAttribute("planejamentos", new PlanejamentoBO().obterTodos());
            return mapping.findForward("novo");
        } catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        
        return mapping.findForward("novo");

    }
    public ActionForward adicionar (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
         try {
            CompositeFolha subAtividade = new CompositeFolhaBO().obterPorPk((Integer) request.getSession().getAttribute(REQUEST_ATTRIBUTE));

            Integer PlanejamentoId = Integer.parseInt(request.getParameter("planejamentoId"));
            Planejamento planejamento = new PlanejamentoBO().obterPorPk(PlanejamentoId);
            FinanceiroPrevisto financeiroPrevisto = new FinanceiroPrevisto();
            
            String valor = request.getParameter("valor");
            financeiroPrevisto.setComposite(subAtividade);
            financeiroPrevisto.setPlanejamento(planejamento);
            financeiroPrevisto.setData(new Date());
            financeiroPrevisto.setValor(new Double(valor == null ? "0" : valor));

            new FinanceiroPrevistoBO().inserir(financeiroPrevisto);
            HibernateUtil.beginTransaction();
       
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Planejamento Financeiro Adicionado com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar alterar o Planejamento Financeiro.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        request.setAttribute("acao", "SubAtividadeFinanceiroPrevisto.do");
        return mapping.findForward("redirect");
    }
    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("id");
            CompositeFolha subAtividade = new CompositeFolhaBO().obterPorPk((Integer) request.getSession().getAttribute(REQUEST_ATTRIBUTE));
            if (subAtividade == null) {
                return new SubAtividadeAction().unspecified(mapping, form, request, response);
            }
            
            FinanceiroPrevisto financeiroPrevisto = new FinanceiroPrevistoBO().obterPorPk(Integer.valueOf(id));
            Planejamento planejamento = financeiroPrevisto.getPlanejamento();
            Date data = financeiroPrevisto.getData();

            request.setAttribute("valor", financeiroPrevisto.getValor().toString());
            request.setAttribute("financeiroPrevisto", financeiroPrevisto);
            request.setAttribute("planejamento", planejamento);
            request.setAttribute("subAtividade", subAtividade);
            request.setAttribute("data", new SimpleDateFormat("dd/MM/yyyy").format(financeiroPrevisto.getData()));


            return mapping.findForward("alterar");
        } catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar selecionar o Planejamento Financeiro.");
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
               
                FinanceiroPrevisto financeiroPrevisto = new FinanceiroPrevistoBO().obterPorPk(Integer.parseInt(id[i]));

                try {
                        new FinanceiroPrevistoBO().excluir(financeiroPrevisto);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    HibernateUtil.rollbackTransaction();
                    erros.add("A exclusão não foi realizada!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        request.setAttribute("acao", "SubAtividadeFinanceiroPrevisto.do");
        return mapping.findForward("redirect");
    }
    
}
