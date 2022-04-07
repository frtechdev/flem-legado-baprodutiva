package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.fwe.web.util.MensagemTagUtil;
import br.org.flem.baprodutiva.bo.InternalizacaoAplicacaoFinanceiraBO;
import br.org.flem.baprodutiva.bo.TipoFonteBO;
import br.org.flem.baprodutiva.negocio.InternalizacaoAplicacaoFinanceira;
import br.org.flem.baprodutiva.negocio.TipoFonte;
import java.text.SimpleDateFormat;
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
 * @author dbbarreto
 */
public class InternalizacaoAplicacaoFinanceiraAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            Collection internalizacoes = new InternalizacaoAplicacaoFinanceiraBO().obterTodos();

            if (internalizacoes != null && !internalizacoes.isEmpty()) {

                request.setAttribute("lista", internalizacoes);
            } else {
                request.setAttribute("lista", new ArrayList());
            }

        } catch (AcessoDadosException e) {
            e.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("lista");
    }

    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("tiposFonte", new TipoFonteBO().obterTodos());
        } catch (AcessoDadosException ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), ex.getMessage(), "erro", this.getClass().getName(), ex);
            Logger.getLogger(InternalizacaoAplicacaoFinanceiraAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapping.findForward("novo");

    }

    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            InternalizacaoAplicacaoFinanceira internalizacao = new InternalizacaoAplicacaoFinanceira();
            internalizacao.setDescricao(dyna.getString("descricao"));
            internalizacao.setEntrada(new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("entrada")));
            internalizacao.setValor(Double.valueOf(dyna.getString("valor")));
            TipoFonte tipoFonte = new TipoFonte();
            tipoFonte.setId(Integer.valueOf(dyna.getString("tipoFonteId")));
            internalizacao.setTipoFonte(tipoFonte);
            HibernateUtil.beginTransaction();
            new InternalizacaoAplicacaoFinanceiraBO().inserir(internalizacao);

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Aplicação Financeira inserida com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                HibernateUtil.rollbackTransaction();
            } catch (AcessoDadosException ex1) {
                Logger.getLogger(CategoriaAction.class.getName()).log(Level.SEVERE, null, ex1);
            }
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar inserir a Aplicação Financeira.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        try {
            HibernateUtil.commitTransaction();
        } catch (AcessoDadosException ex) {
            Logger.getLogger(CategoriaAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapping.findForward("redirect");
    }

    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            InternalizacaoAplicacaoFinanceira internalizacao = new InternalizacaoAplicacaoFinanceiraBO().obterPorPk(Integer.valueOf(dyna.getString("id")));
            internalizacao.setDescricao(dyna.getString("descricao"));
            internalizacao.setEntrada(new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("entrada")));
            internalizacao.setValor(Double.valueOf(dyna.getString("valor")));
            TipoFonte tipoFonte = new TipoFonte();
            tipoFonte.setId(Integer.valueOf(dyna.getString("tipoFonteId")));
            internalizacao.setTipoFonte(tipoFonte);

            new InternalizacaoAplicacaoFinanceiraBO().alterar(internalizacao);

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Aplicação Financeira alterada com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } catch (Exception ex) {
            ex.printStackTrace();

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar alterar a Aplicação Financeira");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("redirect");
    }

    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        try {
            if (GenericValidator.isInt(id)) {
                request.setAttribute("tiposFonte", new TipoFonteBO().obterTodos());
                InternalizacaoAplicacaoFinanceira internalizacao = new InternalizacaoAplicacaoFinanceiraBO().obterPorPk(Integer.valueOf(id));
                dyna.set("descricao", internalizacao.getDescricao());
                dyna.set("entrada", new SimpleDateFormat("dd/MM/yyyy").format(internalizacao.getEntrada()));
                dyna.set("valor", internalizacao.getValor().toString());
                dyna.set("tipoFonteId", internalizacao.getTipoFonte().getId().toString());
            }

            return mapping.findForward("alterar");
        } catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar selecionar a Aplicação Financeira.");
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
                InternalizacaoAplicacaoFinanceiraBO internalizacaoBO = new InternalizacaoAplicacaoFinanceiraBO();
                InternalizacaoAplicacaoFinanceira internalizacao = internalizacaoBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    internalizacaoBO.excluir(internalizacao);
                } catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    erros.add("A Aplicação Financeira \"" + internalizacao.getDescricao() + "\" está associada. Não pode ser excluído!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } catch (AcessoDadosException ex) {
            Logger.getLogger(InternalizacaoAplicacaoFinanceiraAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }
        return mapping.findForward("redirect");
    }
}
