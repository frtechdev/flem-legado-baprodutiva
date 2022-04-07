package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.baprodutiva.bo.CorrecaoNomeBO;
import br.org.flem.baprodutiva.bo.util.OrganizadorLancamentosBO;
import br.org.flem.baprodutiva.negocio.CorrecaoNome;
import br.org.flem.fw.persistencia.dto.LancamentoInterface;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.web.tag.MensagemTag;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
 * @author AJLima
 */
public class CorrecaoNomeAction extends DispatchAction {
    
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
        
        try {
            String data = (String) request.getSession().getAttribute("filtro_data");
            String descricao = (String) request.getSession().getAttribute("filtro_descricao");
            String valor = (String) request.getSession().getAttribute("filtro_valor");
            
            if(data== null || descricao == null || valor == null ){
                 request.setAttribute("lista", OrganizadorLancamentosBO.getInstancia().obterLancamentos());
            }
            else{                  
            request.setAttribute("lista", OrganizadorLancamentosBO.getInstancia().obterLancamentos(data, descricao, valor, null, null));
            }
            
            return mapping.findForward("lista");     
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return mapping.findForward("redirect");
    }
    
    public ActionForward filtrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        String data = (String) dyna.get("data");
        request.getSession().setAttribute("filtro_data", data);
        String descricao = (String) dyna.get("descricao");
        request.getSession().setAttribute("filtro_descricao", descricao);
        String valor = (String) dyna.get("valor");
        request.getSession().setAttribute("filtro_valor", valor);
        
        return unspecified(mapping, form, request, response);
    }
    
    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;

        try {

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "br"));

            String apdId = request.getParameter("apdId");
            String apdTp = request.getParameter("apdTp");
            String seqLinha = request.getParameter("seqLinha");

            LancamentoInterface lancamento = OrganizadorLancamentosBO.getInstancia().obterLancamentoTipoIdSeqLinha(apdTp, apdId, seqLinha);

            dyna.set("lancamento", lancamento);
            request.setAttribute("data", dateFormatter.format(lancamento.getData()));
            request.setAttribute("descricao", lancamento.getDescricao());
            request.setAttribute("valor", numberFormat.format(lancamento.getValor()));
            
            request.setAttribute("descricaoLancamento", lancamento.getDescricao());

            return mapping.findForward("alterar");

        } catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Erro ao requisitar alteração da correção do nome.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return mapping.findForward("redirect");
    }
    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        DynaActionForm dyna = (DynaActionForm) form;

        try {

            LancamentoInterface lancamento = (LancamentoInterface) dyna.get("lancamento");
            String descricaoNome = dyna.getString("descricaoLancamento");

            CorrecaoNome correcaoNome = new CorrecaoNomeBO().obterPorTipoIdSeqLinha(lancamento.getApdTp(), lancamento.getApdId(), lancamento.getSeqLinha());
            correcaoNome.setDescricao(descricaoNome);

            HibernateUtil.beginTransaction();
            new CorrecaoNomeBO().alterar(correcaoNome);
            HibernateUtil.commitTransaction();

            OrganizadorLancamentosBO.getInstancia().forcarAtualizacao();

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("A correção do nome foi alterada com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Erro ao alterar a correção do nome.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }

        return mapping.findForward("redirect");
    }
    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;

        try {

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "br"));

            String apdId = request.getParameter("apdId");
            String apdTp = request.getParameter("apdTp");
            String seqLinha = request.getParameter("seqLinha");

            LancamentoInterface lancamento = OrganizadorLancamentosBO.getInstancia().obterLancamentoTipoIdSeqLinha(apdTp, apdId, seqLinha);

            dyna.set("lancamento", lancamento);
            request.setAttribute("data", dateFormatter.format(lancamento.getData()));
            request.setAttribute("descricao", lancamento.getDescricao());
            request.setAttribute("valor", numberFormat.format(lancamento.getValor()));

            return mapping.findForward("novo");

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Erro ao requisitar inserção da correção do nome.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }

        return mapping.findForward("redirect");
    }
    public ActionForward inserir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        DynaActionForm dyna = (DynaActionForm) form;

        try {

            LancamentoInterface lancamento = (LancamentoInterface) dyna.get("lancamento");
            String descricaoLancamento = dyna.getString("descricaoLancamento");

            CorrecaoNome correcaoNome = new CorrecaoNome();
            correcaoNome.setApdTp(lancamento.getApdTp());
            correcaoNome.setApdId(lancamento.getApdId());
            correcaoNome.setSeqLinha(lancamento.getSeqLinha());
            correcaoNome.setDescricao(descricaoLancamento);


            HibernateUtil.beginTransaction();
            new CorrecaoNomeBO().inserir(correcaoNome);
            HibernateUtil.commitTransaction();

            OrganizadorLancamentosBO.getInstancia().forcarAtualizacao();

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("A correção da descrição foi inserida com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Erro ao inserir a correção do nome.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }

        return mapping.findForward("redirect");
    }
    public ActionForward remover(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {

            String apdId = request.getParameter("apdId");
            String apdTp = request.getParameter("apdTp");
            String seqLinha = request.getParameter("seqLinha");

            CorrecaoNome correcaoNome = new CorrecaoNomeBO().obterPorTipoIdSeqLinha(apdTp, apdId, seqLinha);

            HibernateUtil.beginTransaction();
            new CorrecaoNomeBO().excluir(correcaoNome);
            HibernateUtil.commitTransaction();

            OrganizadorLancamentosBO.getInstancia().forcarAtualizacao();

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("A correção do nome foi removida com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Erro ao remover a correção do nome.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }

        return mapping.findForward("redirect");
    }
}
