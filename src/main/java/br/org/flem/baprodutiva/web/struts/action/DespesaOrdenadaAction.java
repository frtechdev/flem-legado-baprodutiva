package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.fw.persistencia.dto.LancamentoInterface;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.util.Data;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.baprodutiva.bo.DespesaOrdenadaBO;
import br.org.flem.baprodutiva.bo.util.OrganizadorLancamentosBO;
import br.org.flem.baprodutiva.negocio.DespesaOrdenada;
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
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author mccosta
 */
public class DespesaOrdenadaAction extends DispatchAction {

    public ActionForward filtrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        String data = (String) dyna.get("data");

        request.getSession().setAttribute("filtro_data", data);

        return unspecified(mapping, form, request, response);
    }

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            Collection<LancamentoInterface> todosCompromissosOrdenados = new ArrayList<LancamentoInterface>();

            Date filtro_data = (request.getSession().getAttribute("filtro_data") != null && !request.getSession().getAttribute("filtro_data").equals("")) ? Data.formataData((String) request.getSession().getAttribute("filtro_data")) : null;

            if (filtro_data != null) {
                Collection<LancamentoInterface> todosCompromissos = OrganizadorLancamentosBO.getInstancia().obterLancamentos();

                for (LancamentoInterface compromisso : todosCompromissos) {
                    if (filtro_data.equals(compromisso.getData())) {
                        todosCompromissosOrdenados.add(compromisso);
                    }
                }
            }

            request.setAttribute("lista", todosCompromissosOrdenados);

        } catch (Exception e) {
            e.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }

        return mapping.findForward("lista");
    }

    public ActionForward salvar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;

        try {

            String[] ordem = (String[]) dyna.get("ordem");
            String[] apdId = (String[]) dyna.get("apdId");
            String[] apdTp = (String[]) dyna.get("apdTp");
            String[] seqLinha = (String[]) dyna.get("seqLinha");

            HibernateUtil.beginTransaction();
            for (int i = 0; i < apdId.length; i++) {
                DespesaOrdenada despesaOrdenada = new DespesaOrdenadaBO().obterPorApdIdEApdTPESeqLinha(apdId[i], apdTp[i], seqLinha[i]);
                if (despesaOrdenada == null) {
                    despesaOrdenada = new DespesaOrdenada();
                    despesaOrdenada.setApdId(apdId[i]);
                    despesaOrdenada.setApdTp(apdTp[i]);
                    despesaOrdenada.setSeqLinha(seqLinha[i]);
                    despesaOrdenada.setOrdem(ordem[i]);
                    new DespesaOrdenadaBO().inserir(despesaOrdenada);
                } else {
                    despesaOrdenada.setOrdem(ordem[i]);
                    new DespesaOrdenadaBO().alterar(despesaOrdenada);
                }
            }
            HibernateUtil.commitTransaction();

            OrganizadorLancamentosBO.getInstancia().forcarAtualizacao();

        } catch (AplicacaoException ex) {
            ex.printStackTrace();
            try {
                HibernateUtil.rollbackTransaction();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return mapping.findForward("redirect");
    }
}
