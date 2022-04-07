package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.util.CNPJ;
import br.org.flem.fwe.util.Data;
import br.org.flem.baprodutiva.negocio.Contrato;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author essantos
 */
public class SOE extends RelatorioFinanceiroTemplate {

    @Override
    public List<SoeDTO> filtrar(List<SoeDTO> lista) {

        List<SoeDTO> retorno = new ArrayList<SoeDTO>();

        try {

            Collection<Contrato> contratos = new ContratoBO().obterTodos();

            for (SoeDTO soe : lista) {
                
                if(soe.getNomeEntidadeFornecedor().equals("INGRAM MICRO BRASIL LTDA")){
                                System.out.print("Entrei");
                            }
                
                boolean quebraFor = false;

                if (soe.getDataExibicao() != null) {
                    // Caso a data de exibi��o n�o esteja dentro do per�odo do pedido ele n�o será adicionado na lista
                    if (!Data.dentroDoPerido(soe.getDataExibicao(), this.getPedido().getInicio(), this.getPedido().getFim())) {
                        continue;
                    }
                } else {
                    // Caso a data de pagamento n�o esteja dentro do per�odo do pedido ele n�o será adicionado na lista
                    if (!Data.dentroDoPerido(soe.getDataPagamento(), this.getPedido().getInicio(), this.getPedido().getFim())) {
                        continue;
                    }
                }

                // Caso n�o possua centro de custo ele n�o será adicionado na lista
                if (!this.obterListaCentrosCusto().contains(soe.getCentroCusto())) {
                    continue;
                }

                /* Regra no CERRADO para aparecer n�o SOE : Despesa n�o ter um n�mero de Contrato cadastrado com n�mero de Client Connection
                // Caso o valor do item seja superior ao permitido para o seu tipo ele n�o será adicionado na lista
                if (soe.getCodigoItem().equals("CW") && (soe.getValorFinanciadoUS() >= 1000000.00)) {
                    continue;
                } else if (soe.getCodigoItem().equals("GO") && (soe.getValorFinanciadoUS() >= 350000.00)) {
                    continue;
                } else if (soe.getCodigoItem().equals("CS")) {
                    if ((soe.getCodigoEntidadeFornecedor().charAt(0) == 'E' 
                            ||  CNPJ.validarCNPJ(soe.getCodigoEntidadeFornecedor())) && (soe.getValorFinanciadoUS() >= 100000.00)) {
                        continue;
                    } else if (soe.getValorFinanciadoUS() >= 50000.00) {
                        continue;
                    }
                }*/

                // Caso o valor do contrato seja superior ao permitido para o seu tipo ele n�o será adicionado na lista
                for (Contrato contrato : contratos) {
                    if (soe.getNumeroContrato().equals(contrato.getNumero())) {
                        quebraFor = true;
                        break;
                        /* Regra no CERRADO para aparecer n�o SOE : Despesa n�o ter um n�mero de Contrato cadastrado com n�mero de Client Connection
                        if (contrato.getTipo().equals("CW") && (contrato.getValorUSD() >= 1000000.00)) {
                            quebraFor = true;
                            break;
                        } else if (contrato.getTipo().equals("GO") && (contrato.getValorUSD() >= 350000.00)) {
                            quebraFor = true;
                            break;
                        } else if (contrato.getTipo().equals("CS") && (contrato.getValorUSD() >= 50000.00)) {
                            quebraFor = true;
                            break;
                        } else if (contrato.getTipo().equals("TR")) {
                            quebraFor = true;
                            break;
                        } else if (contrato.getTipo().equals("NCS")) {
                            quebraFor = true;
                            break;
                        }

                        soe.setMoeda(contrato.getMoeda());
                        soe.setValorOriginalContrato(contrato.getValor());
                        soe.setValorAcumuladoContrato(getValorAcumuladoContrato(contrato.getNumero(), soe));*/
                    }
                }

                if (quebraFor){
                    continue;
                }

                retorno.add(soe);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return retorno;
    }

}
