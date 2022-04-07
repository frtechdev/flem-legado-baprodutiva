package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.dto.DevolucaoDTO;
import br.org.flem.baprodutiva.negocio.Internalizacao;
import br.org.flem.baprodutiva.negocio.LoteDespesaAplicacao;
import br.org.flem.baprodutiva.negocio.Pedido;
import br.org.flem.baprodutiva.negocio.TransferenciaBancaria;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import br.org.flem.baprodutiva.util.IFReceita;
import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fw.persistencia.dto.LancamentoInterface;
import java.io.IOException;
//import java.time.Clock;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author essantos
 */
public abstract class RelatorioFinanceiroTemplate implements BuilderSOE {

    private Collection<String> centrosCusto = new ArrayList();
    private Collection<IFReceita> internalizacoes = new ArrayList();
    private Collection<LoteDespesaAplicacao> tarifas = new ArrayList();
    private Collection<LancamentoInterface> compromissos = new ArrayList();
    private Collection<LancamentoInterface> devolucoes = new ArrayList();
    private Collection<LancamentoInterface> avulsos = new ArrayList();
    private List<LancamentoInterface> valorAcumulado = new ArrayList();
    private Collection<TransferenciaBancaria> transferencias = new ArrayList();
    private Pedido pedido = new Pedido();

    @Override
    public void adicionaListaInternalizacoes(Collection<IFReceita> internalizacoes) {
        this.internalizacoes = internalizacoes;
    }

    @Override
    public void adicionarListaTarifas(Collection<LoteDespesaAplicacao> tarifas) {
        this.tarifas = tarifas;
    }

    @Override
    public void adicionaListaCompromissos(Collection<LancamentoInterface> compromissos) {
        this.compromissos = compromissos;
    }

    @Override
    public void adicionaListaDevolucoes(Collection<LancamentoInterface> devolucoes) {
        this.devolucoes = devolucoes;
    }

    @Override
    public void adicionaListaAvulsos(Collection<LancamentoInterface> avulsos) {
        this.avulsos = avulsos;
    }

    @Override
    public void adicionaListaCentrosCusto(Collection<String> centrosCusto) {
        this.centrosCusto = centrosCusto;
    }

    public Collection<String> obterListaCentrosCusto() {
        return this.centrosCusto;
    }

    @Override
    public Pedido getPedido() {
        return this.pedido;
    }

    @Override
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public void adicionarListaTransferencias(Collection<TransferenciaBancaria> transferencias) {
        this.transferencias = transferencias;
    }

    public abstract List<SoeDTO> filtrar(List<SoeDTO> lista);

    private SoeDTO criaSoeDTO(LancamentoInterface compromisso, IFReceita internalizacao, Double valor) {

        SoeDTO soe = new SoeDTO();
        soe.setCodigoItem(compromisso.getId());
        soe.setDataPagamento(compromisso.getData());
        soe.setDescricao(compromisso.getDescricao());
        soe.setNomeEntidadeFornecedor(compromisso.getNomeFornecedor());
        soe.setCodigoEntidadeFornecedor(compromisso.getCodigoFornecedor());
        soe.setValorOriginalContrato(compromisso.getValor().doubleValue());
        soe.setParcela(valor);
        soe.setValorFinanciado(valor);
        soe.setValorAcumuladoContrato(compromisso.getValor().doubleValue());
        soe.setNumeroContrato(compromisso.getNumeroContrato());
        soe.setCentroCusto(compromisso.getCentroCusto());
        soe.setApdId(compromisso.getApdId());
        soe.setApdTp(compromisso.getApdTp());
        soe.setSeqLinha(compromisso.getSeqLinha());
        soe.setMoeda("R$");
        soe.setNumeroNotaFiscal(compromisso.getRecibo());
        soe.setIdInternalizacao(internalizacao.getId());
        soe.setTipoInternalizacao(internalizacao.getTipoReceita());
        soe.setDataExibicao(compromisso.getDataExibicao());
        soe.setOrdem(compromisso.getOrdem());
        return soe;
    }

    private SoeDTO criaSoeDTO(LancamentoInterface lancamento) {
        DevolucaoDTO devolucao = (DevolucaoDTO) lancamento;
        SoeDTO soe = new SoeDTO();
        soe.setCodigoItem(devolucao.getClassificacao());
        soe.setDataPagamento(devolucao.getEntrada());
        soe.setDescricao(devolucao.getDescricao());
        soe.setNomeEntidadeFornecedor(devolucao.getNomeFornecedorComp());
        soe.setCodigoEntidadeFornecedor(" ");
        soe.setValorOriginalContrato(devolucao.getValor().doubleValue());
        soe.setParcela(devolucao.getValor().doubleValue());
        soe.setValorFinanciado(devolucao.getValor().doubleValue());
        soe.setValorAcumuladoContrato(devolucao.getValor().doubleValue());
        soe.setNumeroContrato("");
        soe.setCentroCusto(devolucao.getCentroCusto());
        soe.setApdId(devolucao.getApdId());
        soe.setApdTp(devolucao.getApdTp());
        soe.setSeqLinha(devolucao.getSeqLinha());
        soe.setMoeda("R$");
        soe.setNumeroNotaFiscal("");
        soe.setIdInternalizacao(new Integer(devolucao.getId()));
        soe.setTipoInternalizacao(devolucao.getTipoReceita());
        soe.setDataExibicao(devolucao.getDataExibicao());
        soe.setOrdem(devolucao.getOrdem());
        return soe;
    }

    private SoeDTO criaSoeDTOTransferencia(TransferenciaBancaria transf) {
        SoeDTO soe = new SoeDTO();

        soe.setDataPagamento(transf.getDataPagamento());
        soe.setSeqLinha(transf.getSeqLinha());
        soe.setNomeEntidadeFornecedor(transf.getNomeFornecedor());
        soe.setCodigoEntidadeFornecedor(transf.getCodigoFornecedor());
        soe.setDataExibicao(transf.getDataPagamento());
        soe.setDescricao(transf.getDescricao());
        soe.setNumeroContrato("");
        soe.setOrdem("0");
        soe.setMoeda("R$");
        soe.setNumeroNotaFiscal("");
        soe.setCentroCusto(transf.getCentroCusto());
        soe.setParcela(transf.getValor());
        soe.setValorAcumuladoContrato(transf.getValor());
        soe.setValorOriginalContrato(transf.getValor());
        soe.setValorFinanciado(transf.getValor());

        return soe;
    }

    private SoeDTO criaSoeDTOTarifas(LoteDespesaAplicacao tarifa) {
        SoeDTO soe = new SoeDTO();

        soe.setDataPagamento(tarifa.getDataPagamento());
        soe.setSeqLinha(tarifa.getSeqLinha());
        soe.setDataExibicao(tarifa.getData());
        soe.setDescricao(tarifa.getDescricao());
        soe.setNumeroContrato("");
        soe.setOrdem("0");
        soe.setMoeda("R$");
        soe.setNumeroNotaFiscal("");
        // soe.setCentroCusto("2032010103");
        soe.setCentroCusto(tarifa.getCentroCusto());
        soe.setParcela(tarifa.getValor());
        soe.setValorAcumuladoContrato(tarifa.getValor());
        soe.setValorOriginalContrato(tarifa.getValor());
        soe.setValorFinanciado(tarifa.getValor());

        return soe;
    }

    private SoeDTO criaSoeDTOAvulso(LancamentoInterface lancamento) {
        DevolucaoDTO devolucao = (DevolucaoDTO) lancamento;
        SoeDTO soe = new SoeDTO();
        soe.setCodigoItem(devolucao.getClassificacao());
        soe.setDataPagamento(devolucao.getEntrada());
        soe.setDescricao(devolucao.getDescricao());
        soe.setNomeEntidadeFornecedor(devolucao.getNomeFornecedor());
        soe.setCodigoEntidadeFornecedor(" ");
        soe.setValorOriginalContrato(devolucao.getValor().doubleValue());
        soe.setParcela(devolucao.getValor().doubleValue());
        soe.setValorFinanciado(devolucao.getValor().doubleValue());
        soe.setValorAcumuladoContrato(devolucao.getValor().doubleValue());
        soe.setNumeroContrato(devolucao.getNumeroContrato());
        soe.setCentroCusto(devolucao.getCentroCusto());
        soe.setApdId(devolucao.getApdId());
        soe.setApdTp(devolucao.getApdTp());
        soe.setSeqLinha(devolucao.getSeqLinha());
        soe.setMoeda("R$");
        soe.setNumeroNotaFiscal("");
        soe.setIdInternalizacao(new Integer(devolucao.getId()));
        soe.setTipoInternalizacao(devolucao.getTipoReceita());
        soe.setDataExibicao(devolucao.getDataExibicao());
        soe.setOrdem(devolucao.getOrdem());
        return soe;
    }

    @Override
    public List<SoeDTO> resultado() {

        List<SoeDTO> resultado = new ArrayList<SoeDTO>();

        Iterator<IFReceita> itInternalizacao = internalizacoes.iterator();
        Iterator<LancamentoInterface> itCompromissos = compromissos.iterator();

        Double saldo = 0d;
        IFReceita internalizacao = null;

        String ccOperacional = "";
        try {
            ccOperacional = PropertiesUtil.getProperties().getProperty("ccOperacional");
        } catch (IOException ex) {
            Logger.getLogger(RelatorioFinanceiroTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (itCompromissos.hasNext()) {
            LancamentoInterface compromisso = itCompromissos.next();

            if (compromisso.getCentroCusto().equals(ccOperacional)) {
                continue;// desconsidera o centro de custo operacional
            }

            if (compromisso.getNomeFornecedor().equals("TUX NET - MATERIAIS E SERVICOS DE INFORMATICA LTDA")) {
                System.out.print("Entrei");
            }

            // System.out.println("==> linha 168 pegou pr�ximo compromisso: " +
            // compromisso.getValor());
            if (saldo == 0d) {
                // System.out.println("****** linha 170 Saldo igual a zero. Pegando
                // internaliza��o....");
                if (itInternalizacao.hasNext()) {
                    internalizacao = itInternalizacao.next();
                    // System.out.println(" linha 173 Internaliza��o: "+internalizacao.getId() + " -
                    // " + internalizacao.getDescricao() + "( "+internalizacao.getTipoReceita()+" )
                    // - " + internalizacao.getValor());
                    saldo = saldo + internalizacao.getValor();
                    // System.out.println(">>>>>>>> linha 175 Saldo = "+saldo);
                } else {
                    // System.out.println("####### <> linha 177 - n�o tem mais internaliza��o!");
                    internalizacao = getInternalizacaoZerada();
                    resultado.add(this.criaSoeDTO(compromisso, internalizacao, compromisso.getValor().doubleValue()));
                    continue;
                }
            }
            if (saldo >= compromisso.getValor().doubleValue()) { // Se o saldo disponivel for maior igual que o valor do
                                                                 // compromisso
                resultado.add(this.criaSoeDTO(compromisso, internalizacao, compromisso.getValor().doubleValue()));
                saldo = saldo - compromisso.getValor().doubleValue();
                // System.out.println("---> linha 184 Pagou o compromisso todo! Saldo = "+
                // saldo);
            } else { // se saldo n�o for suficiente para pagar compromisso
                // System.out.println("**** linha 187 O valor do saldo n�o paga o compromisso
                // todo.");
                Double sobra = compromisso.getValor().doubleValue() - saldo; // o que falta pagar
                Double parcela = compromisso.getValor().doubleValue() - sobra; // o valor que foi pago
                resultado.add(this.criaSoeDTO(compromisso, internalizacao, parcela.doubleValue()));
                saldo = saldo - compromisso.getValor().doubleValue();
                // System.out.println("**** linha 192 Pagou parte do compromisso. - Saldo = "+
                // saldo);
                while (sobra > 0) {
                    if (saldo <= 0) {
                        if (itInternalizacao.hasNext()) {
                            internalizacao = itInternalizacao.next();
                            // System.out.println(" linha 196 Internaliza��o: "+internalizacao.getId() + " -
                            // " +internalizacao.getDescricao() + "( "+internalizacao.getTipoReceita()+" ) -
                            // " + internalizacao.getValor());
                            saldo = saldo + internalizacao.getValor();
                            // System.out.println(">>>>>>>> linha 198 Saldo = "+saldo);
                        } else {
                            internalizacao = getInternalizacaoZerada();
                            // System.out.println(">>>>> sobra: "+ sobra);
                            // System.out.println(">>>>> saldo: "+ saldo);
                            resultado.add(this.criaSoeDTO(compromisso, internalizacao, sobra.doubleValue()));
                            sobra = 0d;// Como eu paguei o valor total, a sobra � zero.
                            saldo = 0d;
                            break;
                        }
                    }
                    if ((sobra <= saldo) || (saldo >= 0)) {// o valor do saldo (por causa da nova internaliza��o), paga
                                                           // a sobra toda.
                        // System.out.println(">>>>>>>> linha 201 Pagou a sobra : " +sobra+" Saldo =
                        // "+saldo);
                        resultado.add(this.criaSoeDTO(compromisso, internalizacao, sobra.doubleValue()));
                        sobra = 0d;// Como eu paguei o valor total, a sobra � zero.
                    } else {
                        double valorAPagar = sobra - Math.abs(saldo); // Valor a pagar � sempre a diferen�a entre a
                                                                      // sobra (que � o que eu preciso pagar) e saldo.
                        resultado.add(this.criaSoeDTO(compromisso, internalizacao, valorAPagar));
                        sobra = sobra - valorAPagar; // Sobra � o que ainda falta ser pago
                        // System.out.println(">>>>>>>> linha 209 Pagou uma parte da sobra : "
                        // +valorAPagar+". Nova Sobra: " +sobra+ " Saldo = "+saldo);
                    }
                }
            }
        }

        while (itCompromissos.hasNext()) {
            LancamentoInterface compromisso = itCompromissos.next();
            resultado.add(this.criaSoeDTO(compromisso, new Internalizacao(), 0d));
        }

        for (LancamentoInterface devolucao : devolucoes) {
            if (devolucao.getCentroCusto().equals(ccOperacional)) {
                continue;// desconsidera o centro de custo operacional
            }
            resultado.add(this.criaSoeDTO(devolucao));

        }

        for (LoteDespesaAplicacao tarifa : tarifas) {
            resultado.add(this.criaSoeDTOTarifas(tarifa));
        }

        for (LancamentoInterface avulso : avulsos) {
            resultado.add(this.criaSoeDTOAvulso(avulso));
        }

        for (TransferenciaBancaria transf : transferencias) {
            if (transf.getDescricao()
                    .equalsIgnoreCase("Reposi��o Fundo Rotativo, Setaf  Paulo Afonso (NOV/19), Proc.04785/19.")) {
                System.out.println("Entrou aqui");
            }
            resultado.add(this.criaSoeDTOTransferencia(transf));
        }

        Collections.sort(resultado, new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                SoeDTO soe1 = (SoeDTO) o1;
                SoeDTO soe2 = (SoeDTO) o2;
                int comparacao = soe1.getDataPagamento().compareTo(soe2.getDataPagamento());
                if (comparacao == 0) {
                    comparacao = Integer.valueOf(soe1.getOrdem().trim())
                            .compareTo(Integer.valueOf(soe2.getOrdem().trim()));
                    if (comparacao == 0) {
                        comparacao = soe1.getApdId().compareTo(soe2.getApdId());
                    }
                }
                return comparacao;
            }
        });

        return this.filtrar(resultado);
    }

    public void criarListaValorAcumulado() {
        if (valorAcumulado.isEmpty()) {
            valorAcumulado.addAll(compromissos);
            valorAcumulado.addAll(avulsos);

            Collections.sort(valorAcumulado, new Comparator() {

                @Override
                public int compare(Object o1, Object o2) {
                    LancamentoInterface compromisso1 = (LancamentoInterface) o1;
                    LancamentoInterface compromisso2 = (LancamentoInterface) o2;
                    int comparacao = compromisso1.getData().compareTo(compromisso2.getData());
                    if (comparacao == 0) {
                        comparacao = Integer.valueOf(compromisso1.getOrdem().trim())
                                .compareTo(Integer.valueOf(compromisso2.getOrdem().trim()));
                        if (comparacao == 0) {
                            comparacao = compromisso1.getApdId().compareTo(compromisso2.getApdId());
                        }
                    }
                    return comparacao;
                }
            });
        }
    }

    public static void main(String[] args) {
    }

    public Double getValorAcumuladoContrato(String numeroContrato, SoeDTO atual) {
        double retorno = 0;

        criarListaValorAcumulado();

        for (LancamentoInterface compromisso : valorAcumulado) {
            if (compromisso.getNumeroContrato().equals(numeroContrato)) {
                retorno += compromisso.getValor().doubleValue();
            }
            if (compromisso.getApdId() != null && compromisso.getApdTp() != null) {
                if (compromisso.getApdId().equals(atual.getApdId())
                        && compromisso.getApdTp().equals(atual.getApdTp())) {
                    break;
                }
            }
        }

        return retorno;
    }

    public Internalizacao getInternalizacaoZerada() {
        Internalizacao internalizacaoZerInternalizacao = new Internalizacao();

        internalizacaoZerInternalizacao.setId(-1);
        internalizacaoZerInternalizacao.setEntrada(Calendar.getInstance().getTime());
        internalizacaoZerInternalizacao.setParcela("0");
        internalizacaoZerInternalizacao.setDescricao("Internaliza��o Zerada");
        internalizacaoZerInternalizacao.setValor(0d);

        return internalizacaoZerInternalizacao;
    }
}
