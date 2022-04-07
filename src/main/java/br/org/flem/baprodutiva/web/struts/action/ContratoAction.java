package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fw.persistencia.dto.Entidade;
import br.org.flem.fw.service.GEM;
import br.org.flem.fw.service.impl.GEMImpl;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.fwe.web.util.MensagemTagUtil;
import br.org.flem.baprodutiva.bo.ContratoBO;
import br.org.flem.baprodutiva.negocio.Contrato;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

/**
 * @author dbbarreto
 */
public class ContratoAction extends DispatchAction {

    @Override
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            String contrato = (String) request.getSession().getAttribute("filtro_contrato");
            String fornecedor = (String) request.getSession().getAttribute("filtro_fornecedor");
            request.setAttribute("lista", new ContratoBO().obterPorContratoFornecedor(contrato, fornecedor));
        } catch (AcessoDadosException ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Erro ao acessar base de dados", "ERROR", ex.getMessage(), ex);
        }
        return mapping.findForward("lista");
    }

    public ActionForward filtrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        String contrato = (String) dyna.get("contrato");
        request.getSession().setAttribute("filtro_contrato", contrato);
        String fornecedor = (String) dyna.get("fornecedor");
        request.getSession().setAttribute("filtro_fornecedor", fornecedor);
        return unspecified(mapping, form, request, response);
    }

    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            GEM gem = new GEMImpl();
            request.getSession().setAttribute("fornecedores", gem.obterFornecedores());
        } catch (Exception ex) {
            Logger.getLogger(ContratoAction.class.getName()).log(Level.SEVERE, null, ex);
            request.getSession().setAttribute("fornecedores", new ArrayList());
        }

        return mapping.findForward("novo");
    }

    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            Contrato contrato = new Contrato();
            BeanUtils.copyProperties(contrato, dyna);

            contrato.setInicioVigencia(new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("dataInicioVigencia")));

            contrato.setFimVigencia(new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("dataFimVigencia")));

            Entidade entidade = new Entidade();
            entidade.setCodigo(dyna.getString("idFornecedor"));

            entidade = (Entidade) ((List) request.getSession().getAttribute("fornecedores")).get(((List) request.getSession().getAttribute("fornecedores")).indexOf(entidade));

            contrato.setNomeFornecedor(entidade.getNomeAbreviado());
            new ContratoBO().inserir(contrato);

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Contrato inserido com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } catch (Exception ex) {
            ex.printStackTrace();

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar inserir o Contrato.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        request.setAttribute("acao", "Contrato.do");
        return mapping.findForward("redirect");
    }

    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            Contrato contrato = new ContratoBO().obterPorPk(Integer.valueOf(dyna.getString("id")));

            BeanUtils.copyProperties(contrato, dyna);

            contrato.setInicioVigencia(new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("dataInicioVigencia")));

            contrato.setFimVigencia(new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("dataFimVigencia")));

            Entidade entidade = new Entidade();
            entidade.setCodigo(dyna.getString("idFornecedor"));

            entidade = (Entidade) ((List) request.getSession().getAttribute("fornecedores")).get(((List) request.getSession().getAttribute("fornecedores")).indexOf(entidade));

            contrato.setNomeFornecedor(entidade.getNomeAbreviado());
            new ContratoBO().alterar(contrato);

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Contrato alterado com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } catch (Exception ex) {
            ex.printStackTrace();

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar alterar o Contrato.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        request.setAttribute("acao", "Contrato.do");
        return mapping.findForward("redirect");
    }

    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        GEM gem = new GEMImpl();
        try {
            if (GenericValidator.isInt(id)) {
                Contrato contrato = new ContratoBO().obterPorPk(Integer.valueOf(id));
                BeanUtils.copyProperties(dyna, contrato);
                dyna.set("dataInicioVigencia", new SimpleDateFormat("dd/MM/yyyy").format(contrato.getInicioVigencia()));
                dyna.set("dataFimVigencia", new SimpleDateFormat("dd/MM/yyyy").format(contrato.getFimVigencia()));
            }

            request.getSession().setAttribute("fornecedores", gem.obterFornecedores());

            return mapping.findForward("alterar");
        } catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar selecionar o Contrato.");
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
                ContratoBO contratoBO = new ContratoBO();
                Contrato contrato = contratoBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    contratoBO.excluir(contrato);
                } catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    erros.add("O contrato \"" + contrato.getNumero() + "\" está associado. Não pode ser excluído!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } catch (AcessoDadosException ex) {
            Logger.getLogger(ContratoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        return unspecified(mapping, form, request, response);
    }
}