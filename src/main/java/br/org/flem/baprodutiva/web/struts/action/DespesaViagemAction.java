
package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.baprodutiva.bo.util.OrganizadorLancamentosBO;
import br.org.flem.fw.persistencia.dto.DetalhePrestacaoDeConta;
import br.org.flem.fw.service.impl.SAVImpl;
import br.org.flem.fwe.web.util.MensagemTagUtil;
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
 * @author AJLima
 */

public class DespesaViagemAction extends DispatchAction{
    
        public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

            try { 
                
                request.setAttribute("todos", OrganizadorLancamentosBO.getInstancia().obterDespesasViagens());
                request.setAttribute("spcontas", OrganizadorLancamentosBO.getInstancia().obterdespesasViagensSemPrestacaoContas());
               

                return mapping.findForward("lista");

            } catch (Exception ex) {
                ex.printStackTrace();
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }

            return mapping.findForward("redirect");
        }
        public ActionForward detalhes_prestacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        try {
            List<DetalhePrestacaoDeConta> detalhesPrestacao = new SAVImpl().obterDetalhesPrestacaoContas(Integer.parseInt(request.getParameter("idViagem")));
            request.setAttribute("detalhesPrestacao", detalhesPrestacao);
            //return mapping.findForward("redirect");
            return mapping.findForward("detalhesPrestacaoContas");

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return mapping.findForward("redirect");
    }  
        public ActionForward selecionar (ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response){
             DynaActionForm dyna = (DynaActionForm) form;
             String recibo = request.getParameter("recibo");
            
             try{
                  if (GenericValidator.isInt(recibo)) {
       
            }
             
             
             }
             catch(Exception ex){
                  MensagemTagUtil.adicionarMensagem(request.getSession(), ex.getMessage(), "erro", this.getClass().getName(), ex);
                  Logger.getLogger(AditivoAction.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
             }
            return mapping.findForward("alterar");
        }
  
}
