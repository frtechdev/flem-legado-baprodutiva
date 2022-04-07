package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.baprodutiva.bo.CategoriaBO;
import br.org.flem.baprodutiva.bo.DespesaDataExibicaoBO;
import br.org.flem.baprodutiva.bo.RelatorioCustoOperacionalBO;
import br.org.flem.baprodutiva.bo.RelatorioSOEBO;
import br.org.flem.baprodutiva.bo.RelatorioSOEListaoBO;
import br.org.flem.baprodutiva.bo.RelatorioSSBO;
import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.DespesaDataExibicao;
import br.org.flem.baprodutiva.negocio.Pedido;
import br.org.flem.baprodutiva.relatorio.MTBCriadorRelatorio;
import br.org.flem.baprodutiva.relatorio.SOECollection;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import br.org.flem.fwe.web.tag.MensagemTag;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class SimuladorAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        return mapping.findForward("filtro");
    }

    public ActionForward soe(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;

        Collection<SOECollection> dados = new ArrayList();
        try {
            Date dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("dataInicio"));
            Date dataFim = new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("dataFim"));
            //Pedido pedido = new PedidoBO().obterPorPk(Integer.valueOf(id));
            Collection<DespesaDataExibicao> despesasDataExibicao = new DespesaDataExibicaoBO().obterTodos();
            for (Categoria categoria : new CategoriaBO().obterTodosOrdenadoPorCampo("descricao")) {
                SOECollection soe = new SOECollection();
                soe.setCategoria(categoria.getDescricao());

                Pedido pedido = new Pedido();
                pedido.setInicio(dataInicio);
                pedido.setFim(dataFim);
                
                soe.setSoes(new RelatorioSOEBO().geraDados(despesasDataExibicao, categoria, pedido));

                if (soe.getSoes().size() > 0) {
                    dados.add(soe);
                    //System.out.println("Categoria -> "+soe.getCategoria()+" <-> "+soe.getSoes().size());
                }

            }
            //System.out.println(" ==> "+dados.size());
            if (!dados.isEmpty()) {
                String arquivoRelatorio = new String("/relatorio/soe.jasper");

                Map parametros = new HashMap();

                parametros.put("numeroSaque", "X");
                parametros.put("subRelatorio", request.getSession().getServletContext().getRealPath("/relatorio/soe_subreport.jasper"));

                MTBCriadorRelatorio criadorRelatorio = new MTBCriadorRelatorio();

                criadorRelatorio.exportaRelatorioXLS(request, response, arquivoRelatorio, parametros, dados);
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

    public ActionForward folha_resumo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;

        Collection<SOECollection> dados = new ArrayList();
        try {
            //Pedido pedido = new PedidoBO().obterPorPk(Integer.valueOf(id));

            Date dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("dataInicio"));
            Date dataFim = new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("dataFim"));

            for (Categoria categoria : new CategoriaBO().obterTodosOrdenadoPorCampo("descricao")) {
                SOECollection soe = new SOECollection();
                soe.setCategoria(categoria.getDescricao());

                Pedido pedido = new Pedido();
                pedido.setInicio(dataInicio);
                pedido.setFim(dataFim);

                soe.setFolhasResumo(new RelatorioSSBO().geraDados(null, categoria, pedido));

                if (soe.getFolhasResumo().size() > 0) {
                    dados.add(soe);
                    //System.out.println("Categoria -> "+soe.getCategoria()+" <-> "+soe.getSoes().size());
                }

            }
            //System.out.println(" ==> "+dados.size());
            if (!dados.isEmpty()) {
                String arquivoRelatorio = new String("/relatorio/folharesumo.jasper");

                Map parametros = new HashMap();

                parametros.put("numeroSaque", "X");
                parametros.put("subRelatorio", request.getSession().getServletContext().getRealPath("/relatorio/folharesumo_subreport.jasper"));

                MTBCriadorRelatorio criadorRelatorio = new MTBCriadorRelatorio();

                criadorRelatorio.exportaRelatorioXLS(request, response, arquivoRelatorio, parametros, dados);
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

    public ActionForward resumo_gastos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;

        Collection<SOECollection> dados = new ArrayList();
        try {
            //Pedido pedido = new PedidoBO().obterPorPk(Integer.valueOf(id));

            Date dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("dataInicio"));
            Date dataFim = new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("dataFim"));

            for (Categoria categoria : new CategoriaBO().obterTodosOrdenadoPorCampo("descricao")) {
                SOECollection soe = new SOECollection();
                soe.setCategoria(categoria.getDescricao());

                Pedido pedido = new Pedido();
                pedido.setInicio(dataInicio);
                pedido.setFim(dataFim);

                soe.setSoes(new RelatorioSOEBO().geraDados(null, categoria, pedido));
                soe.setFolhasResumo(new RelatorioSSBO().geraDados(null, categoria, pedido));


                if (soe.getSoes().size() > 0 || soe.getFolhasResumo().size() > 0) {
                    dados.add(soe);
                    //System.out.println("Categoria -> "+soe.getCategoria()+" <-> "+soe.getSoes().size());
                }
            }

            //System.out.println(" ==> "+dados.size());
            if (!dados.isEmpty()) {
                String arquivoRelatorio = new String("/relatorio/resumodespesas.jasper");

                Map parametros = new HashMap();

                parametros.put("numeroSaque", "X");

                MTBCriadorRelatorio criadorRelatorio = new MTBCriadorRelatorio();

                criadorRelatorio.exportaRelatorioXLS(request, response, arquivoRelatorio, parametros, dados);
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

    public ActionForward soe_listao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;

        Collection<SOECollection> dados = new ArrayList();
        try {
            Date dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("dataInicio"));
            Date dataFim = new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("dataFim"));

            SOECollection soe = new SOECollection();
            soe.setCategoria("");
            soe.setSoes(new RelatorioSOEListaoBO().geraDados(dataInicio, dataFim));

            if (soe.getSoes().size() > 0) {
                dados.add(soe);
            }

            if (!dados.isEmpty()) {
                String arquivoRelatorio = "/relatorio/soe.jasper";

                Map parametros = new HashMap();

                parametros.put("numeroSaque", "X");
                parametros.put("subRelatorio", request.getSession().getServletContext().getRealPath("/relatorio/soe_listao.jasper"));

                MTBCriadorRelatorio criadorRelatorio = new MTBCriadorRelatorio();

                criadorRelatorio.exportaRelatorioXLS(request, response, arquivoRelatorio, parametros, dados);
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

    public ActionForward gastos_operacionais(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;

        Collection<SoeDTO> dados = new ArrayList();
        try {
            Date dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("dataInicio"));
            Date dataFim = new SimpleDateFormat("dd/MM/yyyy").parse(dyna.getString("dataFim"));

            Pedido pedido = new Pedido();
            pedido.setInicio(dataInicio);
            pedido.setFim(dataFim);
            dados = new RelatorioCustoOperacionalBO().geraDados(pedido);

            if (!dados.isEmpty()) {
                String arquivoRelatorio = "/relatorio/custoOperacional.jasper";
                Map parametros = new HashMap();
                MTBCriadorRelatorio criadorRelatorio = new MTBCriadorRelatorio();
                criadorRelatorio.exportaRelatorioXLS(request, response, arquivoRelatorio, parametros, dados);
                
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