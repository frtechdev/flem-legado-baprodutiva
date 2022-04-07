package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.baprodutiva.bo.CategoriaBO;
import br.org.flem.baprodutiva.bo.DespesaDataExibicaoBO;
import br.org.flem.baprodutiva.bo.PedidoBO;
import br.org.flem.baprodutiva.bo.RelatorioCustoOperacionalBO;
import br.org.flem.baprodutiva.bo.RelatorioSOEBO;
import br.org.flem.baprodutiva.bo.RelatorioSSBO;
import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.DespesaDataExibicao;
import br.org.flem.baprodutiva.negocio.Pedido;
import br.org.flem.baprodutiva.relatorio.MTBCriadorRelatorio;
import br.org.flem.baprodutiva.relatorio.SOECollection;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.web.tag.MensagemTag;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class PedidoAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        try {
            request.setAttribute("lista", new PedidoBO().obterTodosOrdenadoPorCampo("numero"));
        } catch (AcessoDadosException e) {
            e.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("lista");
    }

    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        return mapping.findForward("novo");

    }

    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;

        try {
            Pedido pedido = new Pedido();
            BeanUtils.copyProperties(pedido, dyna);

            pedido.setInicio(new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("dataInicio")));

            pedido.setFim(new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("dataFim")));

            new PedidoBO().inserir(pedido);

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Pedido inserido com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } catch (Exception ex) {
            ex.printStackTrace();

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar inserir o Pedido.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        request.setAttribute("acao", "Pedido.do");
        return mapping.findForward("redirect");
    }

    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;

        try {
            Pedido pedido = new PedidoBO().obterPorPk(Integer.valueOf(dyna.getString("id")));

            BeanUtils.copyProperties(pedido, dyna);

            pedido.setInicio(new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("dataInicio")));

            pedido.setFim(new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("dataFim")));

            new PedidoBO().alterar(pedido);

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Pedido alterado com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } catch (Exception ex) {
            ex.printStackTrace();

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar alterar o Pedido.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        request.setAttribute("acao", "Pedido.do");
        return mapping.findForward("redirect");
    }

    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");

        try {
            if (GenericValidator.isInt(id)) {

                Pedido pedido = new PedidoBO().obterPorPk(Integer.valueOf(id));
                BeanUtils.copyProperties(dyna, pedido);

                dyna.set("dataInicio", new SimpleDateFormat("dd/MM/yyyy").format(pedido.getInicio()));
                dyna.set("dataFim", new SimpleDateFormat("dd/MM/yyyy").format(pedido.getFim()));

                return mapping.findForward("alterar");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar selecionar o Pedido.");
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
                PedidoBO pedidoBO = new PedidoBO();
                Pedido pedido = pedidoBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    pedidoBO.excluir(pedido);
                } catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    erros.add("O pedido \"" + pedido.getNumero() + "\" está associado. Não pode ser excluído!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } catch (AcessoDadosException ex) {
            Logger.getLogger(PedidoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        return unspecified(mapping, form, request, response);
    }

    public ActionForward imprimirSOE(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        Collection<SOECollection> dados = new ArrayList();

        try {

            Pedido pedido = new PedidoBO().obterPorPk(Integer.valueOf(id));
            Collection<DespesaDataExibicao> despesasDataExibicao = new DespesaDataExibicaoBO().obterTodos();
            

            for (Categoria categoria : new CategoriaBO().obterTodosOrdenadoPorCampo("descricao")) {
                SOECollection soe = new SOECollection();
                soe.setCategoria(categoria.getDescricao());
                soe.setSoes(new RelatorioSOEBO().geraDados(despesasDataExibicao, categoria, pedido));

                if (soe.getSoes().size() > 0) {
                    dados.add(soe);
                }
            }

            if (!dados.isEmpty()) {
                String arquivoRelatorio = new String("/relatorio/soe.jasper");

                Map parametros = new HashMap();
                parametros.put("N_CONTRATO", pedido.getNumero());
                parametros.put("subRelatorio", request.getSession().getServletContext().getRealPath("/relatorio/soe_subreport.jasper"));
                parametros.put("logoBIRD", request.getSession().getServletContext().getRealPath("/img/logoBIRD.jpeg"));

                new MTBCriadorRelatorio().exportaRelatorioXLS(request, response, arquivoRelatorio, parametros, dados);
                return null;
            } else {
                List<String> mensagens = new ArrayList<String>();
                mensagens.add("O relatório não possui dados a serem exibidos.");
                request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro na geração do relatório.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return unspecified(mapping, form, request, response);
    }

    public ActionForward imprimirFolhaResumo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        Collection<SOECollection> dados = new ArrayList();

        try {
            Pedido pedido = new PedidoBO().obterPorPk(Integer.valueOf(id));
            Collection<DespesaDataExibicao> despesasProblematicas = new DespesaDataExibicaoBO().obterTodos();

            for (Categoria categoria : new CategoriaBO().obterTodosOrdenadoPorCampo("descricao")) {
                SOECollection soe = new SOECollection();
                soe.setCategoria(categoria.getDescricao());
                soe.setFolhasResumo(new RelatorioSSBO().geraDados(despesasProblematicas, categoria, pedido));

                if (soe.getFolhasResumo().size() > 0) {
                    dados.add(soe);
                }

            }

            if (!dados.isEmpty()) {
                String arquivoRelatorio = new String("/relatorio/folharesumo.jasper");

                Map parametros = new HashMap();
                parametros.put("numeroSaque", pedido.getNumero());
                parametros.put("subRelatorio", request.getSession().getServletContext().getRealPath("/relatorio/folharesumo_subreport.jasper"));

                new MTBCriadorRelatorio().exportaRelatorioXLS(request, response, arquivoRelatorio, parametros, dados);
                return null;
            } else {
                List<String> mensagens = new ArrayList<String>();
                mensagens.add("O relatório não possui dados a serem exibidos.");
                request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro na geração do relatório.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return unspecified(mapping, form, request, response);
    }

    public ActionForward imprimirResumoGasto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        Collection<SOECollection> dados = new ArrayList();

        try {
            Pedido pedido = new PedidoBO().obterPorPk(Integer.valueOf(id));
            Collection<DespesaDataExibicao> despesasProblematicas = new DespesaDataExibicaoBO().obterTodos();

            for (Categoria categoria : new CategoriaBO().obterTodosOrdenadoPorCampo("descricao")) {
                SOECollection soe = new SOECollection();
                soe.setCategoria(categoria.getDescricao());
                soe.setSoes(new RelatorioSOEBO().geraDados(despesasProblematicas, categoria, pedido));
                soe.setFolhasResumo(new RelatorioSSBO().geraDados(despesasProblematicas, categoria, pedido));

                if (soe.getSoes().size() > 0 || soe.getFolhasResumo().size() > 0) {
                    dados.add(soe);
                }
            }

            if (!dados.isEmpty()) {
                String arquivoRelatorio = new String("/relatorio/resumodespesas.jasper");

                Map parametros = new HashMap();
                parametros.put("numeroSaque", pedido.getNumero());

                new MTBCriadorRelatorio().exportaRelatorioXLS(request, response, arquivoRelatorio, parametros, dados);
                return null;
            } else {
                List<String> mensagens = new ArrayList<String>();
                mensagens.add("O relatório não possui dados a serem exibidos.");
                request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro na geração do relatório.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return unspecified(mapping, form, request, response);
    }

    public ActionForward gastosOperacionais(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        Collection<SoeDTO> dados = new ArrayList();

        try {
            Categoria categoria = new Categoria();

            for (Categoria cat : new CategoriaBO().obterTodos()) {
                if (cat.getDescricao().equals("Categoria 3A")) {
                    categoria = cat;
                }
            }

            Pedido pedido = new PedidoBO().obterPorPk(Integer.valueOf(id));

            dados = new RelatorioCustoOperacionalBO().geraDados(pedido);



            if (!dados.isEmpty()) {
                String arquivoRelatorio = "/relatorio/custoOperacional.jasper";

                Map parametros = new HashMap();
                parametros.put("numeroSaque", pedido.getNumero());
                //parametros.put("subRelatorio", request.getSession().getServletContext().getRealPath("/relatorio/soe_subreport.jasper"));
                parametros.put("logoBIRD", request.getSession().getServletContext().getRealPath("/img/logoBIRD.jpeg"));

                new MTBCriadorRelatorio().exportaRelatorioXLS(request, response, arquivoRelatorio, parametros, dados);
                return null;
            } else {
                List<String> mensagens = new ArrayList<String>();
                mensagens.add("O relatório não possui dados a serem exibidos.");
                request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro na geração do relatório.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return unspecified(mapping, form, request, response);
    }
}
