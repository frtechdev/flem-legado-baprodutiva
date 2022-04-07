package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.baprodutiva.bo.DespesaAplicacaoFinanceiraBO;
import br.org.flem.baprodutiva.bo.InternalizacaoAplicacaoFinanceiraBO;
import br.org.flem.baprodutiva.bo.InternalizacaoDevolucaoBO;
import br.org.flem.baprodutiva.bo.InternalizacaoSaldoBO;
import br.org.flem.baprodutiva.bo.InternalizacoesBO;
import br.org.flem.baprodutiva.bo.LancamentoAvulsoBO;
import br.org.flem.baprodutiva.bo.LoteDespesaAplicacaoBO;
import br.org.flem.baprodutiva.negocio.DespesaAplicacaoFinanceira;
import br.org.flem.baprodutiva.negocio.InternalizacaoAplicacaoFinanceira;
import br.org.flem.baprodutiva.negocio.InternalizacaoDevolucao;
import br.org.flem.baprodutiva.negocio.InternalizacaoLoteBancario;
import br.org.flem.baprodutiva.negocio.LancamentoAvulso;
import br.org.flem.baprodutiva.negocio.LoteDespesaAplicacao;
import br.org.flem.baprodutiva.negocio.Pedido;
import br.org.flem.commons.util.MoedaUtil;
import br.org.flem.fwe.util.Data;
import br.org.flem.fwe.util.Valores;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import br.org.flem.baprodutiva.util.IFReceita;
import br.org.flem.baprodutiva.util.InternalizacaoSaldoDTO;
import br.org.flem.commons.util.PropertiesUtil;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.DynaActionForm;

/**
 *
 * @author mgsilva
 */
public class InternalizacaoSaldoAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        Collection<InternalizacaoSaldoDTO> lista = new ArrayList<InternalizacaoSaldoDTO>();
        double debito;
        Double totalInterReal = 0D;
        Double totalDebitosReal = 0D;
        Double totalInterAplicacaoFinanceiraReal = 0D;
        Double totalDebitosAplicacaoFinanceiraReal = 0D;

        Double totalInterDolar = 0D;
        Double totalDebitosDolar = 0D;
        Double totalInterAplicacaoFinanceiraDolar = 0D;
        Double totalDebitosAplicacaoFinanceiraDolar = 0D;

        Date dataFim = null;

        String pagina = request.getParameter("pagina");
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "br"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        Double gastoTotal = 0d;

        try {
            if (GenericValidator.isDate(dyna.getString("dataFim"), "dd/MM/yyyy", true)) {
                dataFim = Data.formataData(dyna.getString("dataFim"));
            }

            Collection<IFReceita> internalizacoes = new InternalizacoesBO().obterTodosTiposInternalizacaoSemAgrupar();//OrganizadorLancamentosBO.getInstancia().obterInternalizacoes(); /*new InternalizacoesBO().obterTodosTiposInternalizacaoSemAgrupar();*/

            Collection<SoeDTO> soes = obterGastos(dataFim);

            for (SoeDTO soe : soes) {
                //Aqui considera os valores das devolucoes e dos lotes!
                gastoTotal = gastoTotal + soe.getParcela();
            }
            //Para cada internalização - normal ou de devolução


            for (IFReceita inter : internalizacoes) {
                Date entrada = (inter instanceof InternalizacaoLoteBancario ? ((InternalizacaoLoteBancario) inter).getDataExibicao() : inter.getEntrada());

                if (dataFim != null && entrada.after(dataFim)) {
                    continue;
                }

                debito = 0d;
                //System.out.println("- - - - - - - - - - - - - - - - - - - - - - -");
                //System.out.println("- - - - - - - - - - - - - - - - - - - - - - -");
                //System.out.println("- - - - - - - - - - - - - - - - - - - - - - -");
                //System.out.println("- - - - - - - - - - - - - - - - - - - - - - -");
                //System.out.println("Internalização: '"+inter.getId()+"' - '"+inter.getDescricao()+"' - TAXA:'"+inter.getTaxa() + "' - VALOR:'"+inter.getValor() + "' - '" + inter.getTipoReceita()+"'");
                for (SoeDTO soe : soes) {
                    //Nao soma valores negativos (devolucoes e lotes bancarios)
                    if (soe.getParcela() < 0) {
                        continue;
                        //System.out.println("Soe EXCLUIDO: valor - "+soe.getParcela() + " idInternalização - '"+ soe.getIdInternalizacao() + "' tipo - '" + soe.getTipoInternalizacao()+"'");
                    }
                    if (inter.getId().equals(soe.getIdInternalizacao()) && inter.getTipoReceita().equals(soe.getTipoInternalizacao())) {
                        debito += soe.getParcela();
                        //System.out.println("Soe ACEITO: valor - "+soe.getParcela() + " idInternalização - '"+ soe.getIdInternalizacao() + "' tipo - '" + soe.getTipoInternalizacao()+"'");
                    } else {
                        //System.out.println("Soe EXCLUIDO: valor - "+soe.getParcela() + " idInternalização - '"+ soe.getIdInternalizacao() + "' tipo - '" + soe.getTipoInternalizacao()+"'");
                    }
                }


                InternalizacaoSaldoDTO dto = new InternalizacaoSaldoDTO();
                dto.setInternalizacao(inter);
                dto.setDebito(debito);
                //System.out.println("debito - "+debito);
                lista.add(dto);

                if (inter.getTipoReceita().equals("INT")) {
                    totalInterReal = totalInterReal + inter.getValor();

                }
                /*
                 * System.out.println("debito - "+debito); System.out.println("-
                 * - - - - - - - - - - - - - - - - - - - - - -");
                 * System.out.println("- - - - - - - - - - - - - - - - - - - - -
                 * - -"); System.out.println("- - - - - - - - - - - - - - - - -
                 * - - - - - -"); System.out.println("- - - - - - - - - - - - -
                 * - - - - - - - - - -");
                 */
            }
            totalDebitosReal = gastoTotal;
            List<InternalizacaoAplicacaoFinanceira> listaAplicacoes = (List) new InternalizacaoAplicacaoFinanceiraBO().obterTodos();
            for (InternalizacaoAplicacaoFinanceira internalizacaoAplicacaoFinanceira : listaAplicacoes) {
                if (dataFim != null && internalizacaoAplicacaoFinanceira.getEntrada().after(dataFim)) {
                    continue;
                }
                totalInterAplicacaoFinanceiraReal += internalizacaoAplicacaoFinanceira.getValor();
            }


            for (LoteDespesaAplicacao loteDespesaAplicacao : new LoteDespesaAplicacaoBO().obterTodos()) {
                if (dataFim != null && loteDespesaAplicacao.getDataPagamento().after(dataFim)) {
                    continue;
                }
                totalDebitosAplicacaoFinanceiraReal += loteDespesaAplicacao.getValor();
            }

            for (DespesaAplicacaoFinanceira despesaAplicacaoFinanceira : new DespesaAplicacaoFinanceiraBO().obterTodos()) {
                if (dataFim != null && despesaAplicacaoFinanceira.getEntrada().after(dataFim)) {
                    continue;
                }
                totalDebitosAplicacaoFinanceiraReal += despesaAplicacaoFinanceira.getValor();
            }

            for (InternalizacaoDevolucao devolucao : new InternalizacaoDevolucaoBO().obterDevolucoesCCOperacionalPorPeriodo(null, dataFim)) {
                totalDebitosAplicacaoFinanceiraReal -= devolucao.getValor();
            }

            for (LancamentoAvulso lancamentoAvulso : new LancamentoAvulsoBO().obterTodos()) {
                if (dataFim == null) {
                    dataFim = new Date();
                }
                if (lancamentoAvulso.getCentroCusto().startsWith("202574")
                        && (lancamentoAvulso.getDataPagamento().equals(dataFim) || lancamentoAvulso.getDataPagamento().before(dataFim))) {
                    totalDebitosAplicacaoFinanceiraReal += lancamentoAvulso.getValorPagamento();
                }
            }


            double totalReal = totalInterReal - totalDebitosReal;
            double totalAplicacaoFinanceiraReal = totalInterAplicacaoFinanceiraReal - totalDebitosAplicacaoFinanceiraReal;

            request.setAttribute("totalInterReal", nf.format(totalInterReal));
            request.setAttribute("totalDebitosReal", nf.format(totalDebitosReal));
            request.setAttribute("totalReal", nf.format(totalReal));
            request.setAttribute("totalInterAplicacaoFinanceiraReal", nf.format(totalInterAplicacaoFinanceiraReal));
            request.setAttribute("totalDebitosAplicacaoFinanceiraReal", nf.format(totalDebitosAplicacaoFinanceiraReal));
            request.setAttribute("totalAplicacaoFinanceiraReal", nf.format(totalAplicacaoFinanceiraReal));
            request.setAttribute("totalGeralReal", nf.format(totalReal + totalAplicacaoFinanceiraReal));


            request.setAttribute("lote", "LOT");
            request.setAttribute("lista", lista);
            request.setAttribute("gastoTotal", nf.format(gastoTotal));

        } catch (Exception ex) {
            Logger.getLogger(InternalizacaoSaldoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (request.getParameter("pagina").equalsIgnoreCase("real")) {
            request.setAttribute("pagina", pagina);
            return mapping.findForward("listaReal");
        } else {
            request.setAttribute("pagina", pagina);
            return mapping.findForward("listaDolar");
        }
    }

    public Collection<SoeDTO> obterGastos(Date dataFinal) {

        Collection<SoeDTO> soes = new ArrayList<SoeDTO>();
        try {
            //for (Categoria categoria : new CategoriaBO().obterTodosOrdenadoPorCampo("descricao")) {

            Date dataInicial = new SimpleDateFormat("dd/MM/yyyy").parse(PropertiesUtil.getProperties().getProperty("projeto.dataInicio"));

            if (dataFinal == null) {
                dataFinal = Calendar.getInstance().getTime();
            }
            Pedido pedido = new Pedido();

            pedido.setInicio(dataInicial);
            pedido.setFim(dataFinal);

            soes.addAll(new InternalizacaoSaldoBO().geraDados(pedido));

            //}
        } catch (IOException ex) {
            Logger.getLogger(InternalizacaoSaldoAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(InternalizacaoSaldoAction.class.getName()).log(Level.SEVERE, null, ex);
        } /*
         * catch (AcessoDadosException ex) {
         * Logger.getLogger(InternalizacaoSaldoAction.class.getName()).log(Level.SEVERE,
         * null, ex);
         }
         */

        return soes;
    }
    /*
     * private Double converteDespesaAplicacaoFinanceiraParaDollar(Double
     * despesas, List<InternalizacaoAplicacaoFinanceira> listaAplicacoes) {
     * Double valorEmDollar = 0d; Double valorRestante = despesas.doubleValue();
     * Collections.sort(listaAplicacoes, new
     * Comparator<InternalizacaoAplicacaoFinanceira>() {
     *
     * public int compare(InternalizacaoAplicacaoFinanceira o1,
     * InternalizacaoAplicacaoFinanceira o2) { return
     * o1.getEntrada().compareTo(o2.getEntrada()); } });
     *
     * for (InternalizacaoAplicacaoFinanceira internalizacaoAplicacaoFinanceira
     * : listaAplicacoes) {
     *
     * if (valorRestante >= internalizacaoAplicacaoFinanceira.getValor()) {
     * valorEmDollar += internalizacaoAplicacaoFinanceira.getValor() /
     * internalizacaoAplicacaoFinanceira.getTaxa(); valorRestante -=
     * internalizacaoAplicacaoFinanceira.getValor(); continue; } else {
     * valorEmDollar += valorRestante /
     * internalizacaoAplicacaoFinanceira.getTaxa(); break; }
     *
     * }
     *
     * return valorEmDollar;
     }
     */
}
