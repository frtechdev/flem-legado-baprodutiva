package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.baprodutiva.bo.CentroCustoBO;
import br.org.flem.baprodutiva.bo.CompositeFolhaBO;
import br.org.flem.baprodutiva.bo.PlanejamentoBO;
import br.org.flem.baprodutiva.bo.util.ConferenciaPOA;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fw.service.CentroResponsabilidade;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author mgsilva
 */
public class ValidacaoProjetoAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {

            String ccProjeto = PropertiesUtil.getProperties().getProperty("projeto");
            Collection<CentroResponsabilidade> lista = new CentroCustoBO().obterFilhosAnaliticos(ccProjeto);
            CentroResponsabilidade cr = new CentroResponsabilidade();
            CentroResponsabilidade cr2 = new CentroResponsabilidade();

            for (CentroResponsabilidade centroResponsabilidade : lista) {
                if (centroResponsabilidade.getId().equals(ccProjeto+"0000")) {
                    cr = centroResponsabilidade;
                }
                if (centroResponsabilidade.getId().equals(PropertiesUtil.getProperties().getProperty("ccOperacional"))) {
                    cr2 = centroResponsabilidade;
                }
            }
            lista.remove(cr);
            lista.remove(cr2); 
            request.setAttribute("listaCC", lista);
            List a = new ConferenciaPOA().obterInconsistencia();
           request.setAttribute("listaInconsistencias", new ConferenciaPOA().obterInconsistencia());
           request.setAttribute("listaPlanejamentos", new PlanejamentoBO().obterPendentesDeTaxa());

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return mapping.findForward("lista");
    }
}
