package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.negocio.DespesaOrdenada;
import br.org.flem.baprodutiva.negocio.InternalizacaoAplicacaoFinanceira;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import br.org.flem.fw.persistencia.dto.Compromisso;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author mjpereira
 */
public abstract class RelatorioIFRTemplate implements BuilderIFR {

    private Collection<InternalizacaoAplicacaoFinanceira> aplicacoesFinanceiras = new ArrayList();
    private Collection<String> centrosCusto = new ArrayList();
    private Collection<Compromisso> compromissos = new ArrayList();
    private Collection<DespesaOrdenada> despesasOrdenadas = new ArrayList<DespesaOrdenada>();
    private Date dataInicio;
    private Date dataFim;

    /**
     * Este método jÃ¡ estÃ¡ removendo as despesas inelegíveis da lista de compromissos.
     * @param compromissos
     */
    @Override
    public void adicionaListaCompromissos(Collection<Compromisso> compromissos) {
        this.compromissos = compromissos;

        try {
            //this.compromissos.addAll(new DespesaAplicacaoFinanceiraBO().obterCompromissos());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (Compromisso compromisso : this.compromissos) {
            for (DespesaOrdenada despesaOrdenada : despesasOrdenadas) {
                if (compromisso.getApdId() != null && compromisso.getApdTp() != null) {
                    if (compromisso.getApdId().equals(despesaOrdenada.getApdId()) && compromisso.getApdTp().equals(despesaOrdenada.getApdTp()) && (compromisso.getSeqLinha() == null || compromisso.getSeqLinha().equals(despesaOrdenada.getSeqLinha()))) {
                        compromisso.setOrdem(despesaOrdenada.getOrdem());
                        break;
                    }
                }
            }

        }

        Collections.sort((List) this.compromissos, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Compromisso comp1 = (Compromisso) o1;
                Compromisso comp2 = (Compromisso) o2;
                int comparacao = comp1.getData().compareTo(comp2.getData());
                if (comparacao == 0) {
                    if (comp1.getOrdem() == null) {
                        comp1.setOrdem("");
                    }
                    if (comp2.getOrdem() == null) {
                        comp2.setOrdem("");
                    }
                    comparacao = Integer.valueOf(comp1.getOrdem().isEmpty() ? "0" : comp1.getOrdem()).compareTo(Integer.valueOf(comp2.getOrdem().isEmpty() ? "0" : comp2.getOrdem()));
                    if (comparacao == 0) {
                        comparacao = comp1.getApdId().compareTo(comp2.getApdId());
                        if (comparacao == 0) {
                            return comp1.getValor().compareTo(comp2.getValor());
                        }
                    }
                }
                return comparacao;
            }
        });
    }

    @Override
    public void adicionaAplicacoesFinanceiras(Collection<InternalizacaoAplicacaoFinanceira> aplicacoesFinanceiras) {
        this.aplicacoesFinanceiras = aplicacoesFinanceiras;
    }

    @Override
    public void adicionaListaCentrosCusto(Collection<String> centrosCusto) {
        this.centrosCusto = centrosCusto;
    }

    public Collection<String> obterListaCentrosCusto() {
        return this.centrosCusto;
    }

    @Override
    public void adicionaListaDespesasOrdenadas(Collection<DespesaOrdenada> despesasOrdenadas) {
        this.despesasOrdenadas = despesasOrdenadas;
    }

    public abstract List<SoeDTO> filtrar(List<SoeDTO> lista);

    private SoeDTO criaSoeDTO(Compromisso compromisso, InternalizacaoAplicacaoFinanceira aplicacaoFinanceira, Double valor) {
        SoeDTO soe = new SoeDTO();
        soe.setCodigoItem(compromisso.getId());
        soe.setDataPagamento(compromisso.getData());
        soe.setDescricao(compromisso.getDescricao());
        soe.setNomeEntidadeFornecedor(compromisso.getNomeFornecedor());
        soe.setCodigoEntidadeFornecedor(compromisso.getCodigoFornecedor());
        soe.setValorOriginalContrato(compromisso.getValor().doubleValue());
        soe.setNumeroNotaFiscal(compromisso.getNumero());
        soe.setParcela(valor);
        soe.setValorAcumuladoContrato(compromisso.getValor().doubleValue());
        soe.setNumeroContrato(compromisso.getNumeroContrato());
        soe.setCentroCusto(compromisso.getCentroCusto());
        soe.setApdId(compromisso.getApdId());
        soe.setApdTp(compromisso.getApdTp());
        soe.setSeqLinha(compromisso.getSeqLinha());
        soe.setMoeda("R$");
        soe.setNumeroNotaFiscal(compromisso.getRecibo());
        soe.setIdInternalizacao(aplicacaoFinanceira.getId());
        soe.setTipoInternalizacao(aplicacaoFinanceira.getTipoReceita());
        soe.setOrdem(compromisso.getOrdem());
        return soe;
    }

    @Override
    public List<SoeDTO> resultado() {
        List<SoeDTO> resultado = new ArrayList<SoeDTO>();
        Iterator<InternalizacaoAplicacaoFinanceira> itAplicacaoFinanceira = aplicacoesFinanceiras.iterator();
        Iterator<Compromisso> itCompromissos = compromissos.iterator();
        Double saldo = 0d;
        InternalizacaoAplicacaoFinanceira internalizacao = null;
        while (itCompromissos.hasNext()) {
            Compromisso compromisso = itCompromissos.next();
            if (saldo == 0d) {
                if (itAplicacaoFinanceira.hasNext()) {
                    internalizacao = itAplicacaoFinanceira.next();
                    saldo = saldo + internalizacao.getValor();
                } else {
                    break;
                }
            }
            if (saldo >= compromisso.getValor().doubleValue()) { // Se o saldo disponivel for maior igual que o valor do compromisso
                resultado.add(this.criaSoeDTO(compromisso, internalizacao, compromisso.getValor().doubleValue()));
                saldo = saldo - compromisso.getValor().doubleValue();
            } else { // se saldo não for suficiente para pagar compromisso
                Double sobra = compromisso.getValor().doubleValue() - saldo;  //o que falta pagar
                Double parcela = compromisso.getValor().doubleValue() - sobra; // o valor que foi pago
                resultado.add(this.criaSoeDTO(compromisso, internalizacao, parcela.doubleValue()));
                saldo = saldo - compromisso.getValor().doubleValue();
                while (sobra > 0) {
                    if (saldo <= 0) {
                        internalizacao = itAplicacaoFinanceira.next();
                        saldo = saldo + internalizacao.getValor();
                    }
                    if ((sobra <= saldo) || (saldo >= 0)) {
                        resultado.add(this.criaSoeDTO(compromisso, internalizacao, sobra.doubleValue()));
                        sobra = 0d;//Como eu paguei o valor total, a sobra é zero.
                    } else {
                        double valorAPagar = sobra - Math.abs(saldo); //Valor a pagar é sempre a diferença entre a sobra (que é o que eu preciso pagar) e saldo.
                        resultado.add(this.criaSoeDTO(compromisso, internalizacao, valorAPagar));
                        sobra = sobra - valorAPagar; //Sobra é o que ainda falta ser pago
                    }
                }
            }
        }

        while (itCompromissos.hasNext()) {
            Compromisso compromisso = itCompromissos.next();
            resultado.add(this.criaSoeDTO(compromisso, new InternalizacaoAplicacaoFinanceira(), 0d));
        }

        Collections.sort(resultado, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                SoeDTO soe1 = (SoeDTO) o1;
                SoeDTO soe2 = (SoeDTO) o2;
                int comparacao = soe1.getDataPagamento().compareTo(soe2.getDataPagamento());
                if (comparacao == 0) {
                    comparacao = Integer.valueOf(soe1.getOrdem().isEmpty() ? "0" : soe1.getOrdem()).compareTo(Integer.valueOf(soe2.getOrdem().isEmpty() ? "0" : soe2.getOrdem()));
                    if (comparacao == 0) {
                        comparacao = soe1.getApdId().compareTo(soe2.getApdId());
                    }
                }
                return comparacao;
            }
        });

        return this.filtrar(resultado);
    }

    @Override
    public Date getDataInicio() {
        return dataInicio;
    }

    @Override
    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    @Override
    public Date getDataFim() {
        return dataFim;
    }

    @Override
    public void setDataFim(Date fim) {
        this.dataFim = fim;
    }

    public Collection<InternalizacaoAplicacaoFinanceira> getAplicacoesFinanceiras() {
        return aplicacoesFinanceiras;
    }

    public void setAplicacoesFinanceiras(Collection<InternalizacaoAplicacaoFinanceira> aplicacoesFinanceiras) {
        this.aplicacoesFinanceiras = aplicacoesFinanceiras;
    }

    public Collection<String> getCentrosCusto() {
        return centrosCusto;
    }

    public void setCentrosCusto(Collection<String> centrosCusto) {
        this.centrosCusto = centrosCusto;
    }

    public Collection<Compromisso> getCompromissos() {
        return compromissos;
    }

    public void setCompromissos(Collection<Compromisso> compromissos) {
        this.compromissos = compromissos;
    }
}
