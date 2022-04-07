package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.bo.util.OrganizadorLancamentosBO;
import br.org.flem.baprodutiva.dto.DevolucaoDTO;
import br.org.flem.baprodutiva.negocio.DespesaAplicacaoFinanceira;
import br.org.flem.baprodutiva.negocio.InternalizacaoDevolucao;
import br.org.flem.baprodutiva.negocio.Pedido;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import br.org.flem.baprodutiva.util.IFReceita;
import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fw.persistencia.dto.LancamentoInterface;
import br.org.flem.fwe.util.Valores;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ILFernandes
 */
public class RelatorioCustoOperacionalBO {

    private SoeDTO criaSoeDTO(LancamentoInterface compromisso, DespesaAplicacaoFinanceira despesa) {
        SoeDTO soe = new SoeDTO();
        soe.setCodigoItem(compromisso.getId());
        soe.setDataPagamento(compromisso.getData());
        soe.setDescricao(compromisso.getDescricao());
        soe.setNomeEntidadeFornecedor(compromisso.getNomeFornecedor());
        soe.setCodigoEntidadeFornecedor(compromisso.getCodigoFornecedor());
        soe.setValorOriginalContrato(compromisso.getValor().doubleValue());
        soe.setParcela(compromisso.getValor().doubleValue());
        soe.setValorFinanciado(compromisso.getValor().doubleValue());
        soe.setValorAcumuladoContrato(compromisso.getValor().doubleValue());
        soe.setNumeroContrato(compromisso.getNumeroContrato());
        soe.setCentroCusto(compromisso.getCentroCusto());
        soe.setApdId(compromisso.getApdId());
        soe.setApdTp(compromisso.getApdTp());
        soe.setSeqLinha(compromisso.getSeqLinha());
        soe.setMoeda("R$");
        soe.setNumeroNotaFiscal(compromisso.getRecibo());
        soe.setDataExibicao(compromisso.getDataExibicao());
        soe.setOrdem(compromisso.getOrdem());
        return soe;
    }

    private SoeDTO criaSoeDTO(LancamentoInterface compromisso, IFReceita internalizacao) {

        SoeDTO soe = new SoeDTO();
        soe.setCodigoItem(compromisso.getId());
        soe.setDataPagamento(compromisso.getData());
        soe.setDescricao(internalizacao.getDescricao());
        soe.setNomeEntidadeFornecedor(compromisso.getNomeFornecedor());
        soe.setCodigoEntidadeFornecedor(compromisso.getCodigoFornecedor());
        soe.setValorOriginalContrato(compromisso.getValor().doubleValue());
        soe.setParcela(internalizacao.getValor() * -1);
        soe.setValorFinanciado(internalizacao.getValor() * -1);
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

    private SoeDTO criaSoeDTO(DevolucaoDTO compromisso) {

        SoeDTO soe = new SoeDTO();
        soe.setCodigoItem(compromisso.getId());
        soe.setDataPagamento(compromisso.getData());
        soe.setDescricao(compromisso.getDescricao());
        soe.setNomeEntidadeFornecedor(compromisso.getNomeFornecedor());
        soe.setCodigoEntidadeFornecedor(compromisso.getCodigoFornecedor());
        soe.setValorOriginalContrato(compromisso.getValor().doubleValue());
        soe.setValorAcumuladoContrato(compromisso.getValor().doubleValue());
        soe.setParcela(compromisso.getValor().doubleValue());
        soe.setValorFinanciado(compromisso.getValor().doubleValue());
        soe.setNumeroContrato(compromisso.getNumeroContrato());
        soe.setCentroCusto(compromisso.getCentroCusto());
        soe.setApdId(compromisso.getApdId());
        soe.setApdTp(compromisso.getApdTp());
        soe.setSeqLinha(compromisso.getSeqLinha());
        soe.setMoeda("R$");
        soe.setNumeroNotaFiscal(compromisso.getRecibo());
        soe.setDataExibicao(compromisso.getDataExibicao());
        soe.setOrdem(compromisso.getOrdem());
        return soe;
    }

    public Collection<SoeDTO> geraDados(Pedido pedido) {
        Collection<SoeDTO> resultado = new ArrayList<SoeDTO>();


        try {
            Collection<DespesaAplicacaoFinanceira> despesasAplicacaoFinanceira = new DespesaAplicacaoFinanceiraBO().obterPorPeriodo(pedido.getInicio(), pedido.getFim());
            for (LancamentoInterface lancamento : OrganizadorLancamentosBO.getInstancia().obterCompromissosCustosOperacionais()) {
                for (DespesaAplicacaoFinanceira despesa : despesasAplicacaoFinanceira) {
                    if (lancamento.getApdId().equals(despesa.getApdId()) && lancamento.getApdTp().equals(despesa.getApdTp())) {
                        resultado.add(this.criaSoeDTO(lancamento, despesa));
                        break;
                    }
                }

                for (InternalizacaoDevolucao internalizacaoDevolucao : new InternalizacaoDevolucaoBO().obterPorPeriodo(pedido.getInicio(), pedido.getFim())) {
                    if (internalizacaoDevolucao.getCentroCusto().equals(PropertiesUtil.getProperties().getProperty("ccOperacional"))) {
                        if (lancamento.getApdId().equals(internalizacaoDevolucao.getIdComp()) && lancamento.getApdTp().equals(internalizacaoDevolucao.getTipoComp())) {
                            resultado.add(this.criaSoeDTO(lancamento, internalizacaoDevolucao));
                            break;
                        }
                    }
                }
            }
            for (DevolucaoDTO lancamentoAvulso : new LancamentoAvulsoBO().obterCompromissos()) {
                if (lancamentoAvulso.getCentroCusto().startsWith("202574")) {
                    if ((lancamentoAvulso.getData().equals(pedido.getInicio()) || lancamentoAvulso.getData().after(pedido.getInicio()))
                            && (lancamentoAvulso.getData().equals(pedido.getFim()) || lancamentoAvulso.getData().before(pedido.getFim()))) {
                        resultado.add(this.criaSoeDTO(lancamentoAvulso));
                    }
                }
            }



        } catch (Exception ex) {
            Logger.getLogger(RelatorioSOEBO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;
    }
}
