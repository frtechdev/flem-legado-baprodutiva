package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.baprodutiva.bo.AtividadeNovaDescricaoBO;
import br.org.flem.baprodutiva.bo.CategoriaBO;
import br.org.flem.baprodutiva.bo.PlanejamentoBO;
import br.org.flem.baprodutiva.bo.RelatorioGerencialFinanceiroBO;
import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.baprodutiva.relatorio.GerencialFinanceiroDTO;
import br.org.flem.baprodutiva.relatorio.MTBCriadorRelatorio;
import br.org.flem.fwe.util.Data;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.fwe.web.util.MensagemTagUtil;
import java.util.*;
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
public class RelatorioAction extends DispatchAction {

    public ActionForward filtroRelatorioGerencialFinanceiro(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("planejamentos", new PlanejamentoBO().obterTodos());
        } catch (Exception ex) {
            ArrayList mensagens = new ArrayList();
            mensagens.add("Ocorreu um erro na geração do relatório.\n" + ex.getMessage());
            request.setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }

        return mapping.findForward("filtroRelatorioGerencialFinanceiro");
    }

    public ActionForward relatorioGerencialFinanceiro(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;

        try {
            Collection<GerencialFinanceiroDTO> componenteSubcomponente;

            Date dataInicial = Data.formataData(dyna.getString("dataInicial"));
            Date dataFinal = Data.formataData(dyna.getString("dataFinal"));


            if((dataInicial==null && dataFinal==null)||(dataInicial.equals("") && dataFinal.equals(""))){
     
            }

            double gefPlanejadoProjeto = 0d;
            
            for (Categoria categoria : new CategoriaBO().obterTodos()) {
                gefPlanejadoProjeto += categoria.getPlanejado();
            }

            componenteSubcomponente = new RelatorioGerencialFinanceiroBO().geraDados(dataInicial, dataFinal);

            String arquivoRelatorio = "/relatorio/relGerencialFinanceiro.jasper";

            Map parametros = new HashMap();

            //parametros.put("trimestre", planejamento.getDescricao());
            parametros.put("planejadoProjeto", gefPlanejadoProjeto);
            parametros.put("dataInicialSelecionada", dyna.getString("dataInicial"));
            parametros.put("dataFinalSelecionada", dyna.getString("dataFinal"));
            MTBCriadorRelatorio criadorRelatorio = new MTBCriadorRelatorio();

            criadorRelatorio.exportaRelatorioXLS(request, response, arquivoRelatorio, parametros, componenteSubcomponente);
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            MensagemTagUtil.adicionarMensagem(request.getSession(), ex.getMessage(), "erro", this.getClass().getName(), ex);
        }
        return filtroRelatorioGerencialFinanceiro(mapping, form, request, response);
    }

}
