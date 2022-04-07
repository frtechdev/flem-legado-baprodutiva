package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.util.Data;
import br.org.flem.baprodutiva.negocio.Contrato;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author mjpereira
 */
public class SS extends RelatorioFinanceiroTemplate {

    @Override
    public List<SoeDTO> filtrar(List<SoeDTO> lista) {
        List<SoeDTO> retorno = new ArrayList();

        try {
            Collection<Contrato> contratos = new ContratoBO().obterTodos();

            for (SoeDTO ss : lista) {
                boolean contratoOk = false;

                if (ss.getDataExibicao() != null) {
                    // Caso a data de exibição não esteja dentro do período do pedido ele não serÃ¡ adicionado na lista
                    if (!Data.dentroDoPerido(ss.getDataExibicao(), this.getPedido().getInicio(), this.getPedido().getFim())) {
                        continue;
                    }
                } else {
                    // Caso a data de pagamento não esteja dentro do período do pedido ele não serÃ¡ adicionado na lista
                    if (!Data.dentroDoPerido(ss.getDataPagamento(), this.getPedido().getInicio(), this.getPedido().getFim())) {
                        continue;
                    }
                }

                //Filtro para contrato.
                for (Contrato contrato : contratos) {
                    if (ss.getNumeroContrato().equals(contrato.getNumero())) {
                        contratoOk = true;
                        /* Regra no CERRADO para aparecer na FOLHA RESUMO: Despesa ter um número de Contrato cadastrado com número de Client Connection
                        /*if (contrato.getTipo().equals("CW") && (contrato.getValorUSD() >= 1000000.00)) {
                            contratoOk = true;
                        } else if (contrato.getTipo().equals("GO") && (contrato.getValorUSD() >= 350000.00)) {
                            contratoOk = true;
                        } else if (contrato.getTipo().equals("CS") && (contrato.getValorUSD() >= 50000.00)) {
                            contratoOk = true;
                        } else if (contrato.getTipo().equals("TR")) {
                            contratoOk = true;
                        }else if (contrato.getTipo().equals("NCS")) {
                            contratoOk = true;
                        }*/

                        ss.setMoeda(contrato.getMoeda());
                        ss.setClienteConnection(contrato.getClientConnection());
                        ss.setValorOriginalContrato(contrato.getValor());
                        ss.setValorAcumuladoContrato(getValorAcumuladoContrato(contrato.getNumero(), ss));
                    }
                }

                if (!contratoOk) {
                    continue;
                    /* Regra no CERRADO para aparecer na FOLHA RESUMO: Despesa ter um número de Contrato cadastrado com número de Client Connection
                    if (ss.getCodigoItem().trim().equals("CW") && (ss.getValorFinanciadoUS() < 1000000.00)) {
                        continue;
                    } else if (ss.getCodigoItem().trim().equals("GO") && (ss.getValorFinanciadoUS() < 350000.00)) {
                        continue;
                    } else if (ss.getCodigoItem().trim().equals("CS")) {
                        if ((ss.getCodigoEntidadeFornecedor().charAt(0) == 'E') && (ss.getValorFinanciadoUS() < 100000.00)) {
                            continue;
                        } else if (ss.getValorFinanciadoUS() < 50000.00) {
                            continue;
                        }
                    } else if (ss.getCodigoItem().trim().equals("SG") && ss.getValorFinanciadoUS() < 1000000.00) {
                        continue;
                    } else if ((ss.getCodigoItem().trim().equals("TR")) || (ss.getCodigoItem().trim().equals("NCS")) || (ss.getCodigoItem().trim().equals("CO") || (ss.getCodigoItem().trim().equals("")))) {
                        continue;
                    }*/
                }

                if (!this.obterListaCentrosCusto().contains(ss.getCentroCusto())) {
                    continue;
                }

                retorno.add(ss);
            }
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }

        return retorno;
    }
}
