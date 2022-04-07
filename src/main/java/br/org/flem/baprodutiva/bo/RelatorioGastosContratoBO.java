/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.bo;

import br.org.flem.fw.persistencia.dto.LancamentoInterface;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.util.Data;
import br.org.flem.baprodutiva.bo.util.OrganizadorLancamentosBO;
import br.org.flem.baprodutiva.dto.RelatorioGastosContratoDTO;
import br.org.flem.baprodutiva.negocio.CodigoItem;
import br.org.flem.baprodutiva.negocio.Contrato;
import br.org.flem.baprodutiva.negocio.InternalizacaoDevolucao;
import br.org.flem.baprodutiva.negocio.Parcela;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author ilfernandes
 */
public class RelatorioGastosContratoBO {

    private Double totalAcordos = 0d;
    private Double totalParcelas = 0d;
    private Double totalPagamentos = 0d;
    private Double totalPagamentosAcumulados = 0d;
    private Double totalSaldoAPagar = 0d;
    private Double totalDevolucao = 0d;

    public Collection<RelatorioGastosContratoDTO> geraDados(CodigoItem tipo) throws AplicacaoException, IOException {
        Collection<Contrato> contratos = new ContratoBO().obterTodosOrdenadoPorCampo("numero");
        Collection<RelatorioGastosContratoDTO> retorno = new ArrayList<RelatorioGastosContratoDTO>();
        Collection<InternalizacaoDevolucao> devolucoes = new InternalizacaoDevolucaoBO().obterDevolucoesSubDoacao();
        for (Contrato contrato : contratos) { //INICIO FOR CONTRATO

            if (!contrato.getTipo().equals(tipo.getSigla())) {
                continue;
            }

            RelatorioGastosContratoDTO dto = new RelatorioGastosContratoDTO();
            dto.setNumeroContrato(contrato.getNumero());
            dto.setVigencia(formatarVigencia(contrato));
            dto.setEntidade(contrato.getNomeFornecedor());
            dto.setValorAcordo(contrato.getValor());

            dto.setObjeto(contrato.getObjeto());
            dto.setParcelas(contrato.getParcelas());
            dto.setAditivos(contrato.getAditivos());
            dto.setObservacao(contrato.getObservacao());
            Double pagamentoAcumulado = 0d;

            for (Parcela parcela : contrato.getParcelas()) {
                totalParcelas += parcela.getValor().doubleValue();
            }


            for (LancamentoInterface lancamento : OrganizadorLancamentosBO.getInstancia().obterCompromissos()) {//INICIO FOR LANCAMENTOS
                if (lancamento.getNumeroContrato() != null && !lancamento.getNumeroContrato().isEmpty()
                        && lancamento.getNumeroContrato().equals(contrato.getNumero())) {
                    dto.setNumeroProcesso(lancamento.getProcesso());
                    dto.getLancamentos().add(lancamento);
                    pagamentoAcumulado += lancamento.getValor().doubleValue();
                    somaTotalPagamentos(lancamento.getValor().doubleValue());

                    for (InternalizacaoDevolucao devolucao : devolucoes) {
                        if (devolucao.getCentroCusto().equals(lancamento.getCentroCusto())
                                && devolucao.getIdComp().equals(lancamento.getApdId())
                                && devolucao.getTipoComp().equals(lancamento.getApdTp())) {
                            dto.setDevolucao(devolucao.getValor());
                            totalDevolucao += devolucao.getValor();
                        } else {
                            dto.setDevolucao(null);
                        }
                    }
                }


            }//FIM FOR LANCAMENTOS

            Double saldoApagar = contrato.getValor() - pagamentoAcumulado;
            dto.setPagamentoAcumulado(pagamentoAcumulado);
            dto.setSaldoAPagar(saldoApagar);
            
            retorno.add(dto);

            somaTotalAcordos(contrato.getValor());
            somaTotalPagamentosAcumulados(pagamentoAcumulado);
            somaTotalSaldoAPagar(saldoApagar);
        }//FIM FOR CONTRATO
        return ordenarRelatorio(retorno);
    }

    public Double getTotalAcordos() {
        return totalAcordos;
    }

    private void somaTotalAcordos(Double valorAcordo) {
        this.totalAcordos += valorAcordo;
    }

    public Double getTotalPagamentosAcumulados() {
        return totalPagamentosAcumulados;
    }

    private void somaTotalPagamentosAcumulados(Double pagamentosAcumulados) {
        this.totalPagamentosAcumulados += pagamentosAcumulados;
    }

    public Double getTotalParcelas() {
        return totalParcelas;
    }

    private void somaTotalParcelas(Double parcelas) {
        this.totalParcelas += parcelas;
    }

    public Double getTotalSaldoAPagar() {
        return totalSaldoAPagar;
    }

    private void somaTotalSaldoAPagar(Double saldoAPagar) {
        this.totalSaldoAPagar += saldoAPagar;
    }

    public Double getTotalPagamentos() {
        return totalPagamentos;
    }

    private void somaTotalPagamentos(Double pagaMentos) {
        this.totalPagamentos += pagaMentos;
    }

    public Double getTotalDevolucao() {
        return totalDevolucao;
    }

    public void setTotalDevolucao(Double totalDevolucao) {
        this.totalDevolucao = totalDevolucao;
    }

    private String formatarVigencia(Contrato contrato) {
        String dataInicial = Data.formataData(contrato.getInicioVigencia());
        String dataFinal = Data.formataData(contrato.getFimVigencia());
        return dataInicial + " a " + dataFinal;
    }

    private List<RelatorioGastosContratoDTO> ordenarRelatorio(Collection<RelatorioGastosContratoDTO> listaDesordenada) {
        List<RelatorioGastosContratoDTO> listaOrdenada = new ArrayList<RelatorioGastosContratoDTO>(listaDesordenada);
        Collections.sort(listaOrdenada, new Comparator<RelatorioGastosContratoDTO>() {
            @Override
            public int compare(RelatorioGastosContratoDTO arg0, RelatorioGastosContratoDTO arg1) {
                int comparacao = arg0.getNumeroContrato().substring(3).compareTo(arg1.getNumeroContrato().substring(3));
                if (comparacao == 0) {
                    comparacao = arg0.getNumeroContrato().substring(0, 2).compareTo(arg1.getNumeroContrato().substring(0, 2));
                }
                return comparacao;
            }
        });
        return listaOrdenada;
    }
}
