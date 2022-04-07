/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.baprodutiva.bo.CategoriaBO;
import br.org.flem.baprodutiva.bo.CompositeFolhaBO;
import br.org.flem.baprodutiva.bo.EntidadeExecutoraBO;
import br.org.flem.baprodutiva.bo.FinanceiroPrevistoBO;
import br.org.flem.baprodutiva.bo.OrgaoResponsavelBO;
import br.org.flem.baprodutiva.bo.PlanejamentoBO;
import br.org.flem.baprodutiva.bo.SubCategoriaBO;
import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.CompositeFolha;
import br.org.flem.baprodutiva.negocio.CompositeIF;
import br.org.flem.baprodutiva.negocio.EntidadeExecutora;
import br.org.flem.baprodutiva.negocio.FinanceiroPrevisto;
import br.org.flem.baprodutiva.negocio.OrgaoResponsavel;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.baprodutiva.negocio.SubCategoria;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.util.Lista;
import br.org.flem.fwe.web.tag.MensagemTag;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author AJLima
 */
public class MigrarAction extends DispatchAction{
    
    
    public ActionForward unspecified (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws AplicacaoException {
        try {

            request.setAttribute("planejamentos", new PlanejamentoBO().obterTodos());


        } catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("novo");
    }
    
    public ActionForward migrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws AplicacaoException{
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            
             Planejamento planejamento1 = new PlanejamentoBO().obterPorPk(Integer.valueOf(dyna.getString("planejamentoId1")));
             Planejamento planejamento2 = new PlanejamentoBO().obterPorPk(Integer.valueOf(dyna.getString("planejamentoId2")));
             planejamento1.setAtivo(false);
             new PlanejamentoBO().alterar(planejamento1);

             Collection<FinanceiroPrevisto> financeiros = new FinanceiroPrevistoBO().obterPorPlanejamento(planejamento1);
             
             for(FinanceiroPrevisto f : financeiros){
                 FinanceiroPrevisto fin = new FinanceiroPrevisto();
                 fin.setComposite(f.getComposite());
                 fin.setData(f.getData());
                 fin.setPlanejamento(planejamento2);
                 fin.setValor(f.getValor());
                 new FinanceiroPrevistoBO().inserir(fin);
             }

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Migração feita com Sucesso");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } catch (Exception ex) {
            ex.printStackTrace();

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar Migrar as atividades.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        request.setAttribute("acao", "Migrar.do");
        return mapping.findForward("redirect");

     }
    
}
