package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.util.Data;
import br.org.flem.baprodutiva.negocio.Contrato;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author mgsilva
 */
public class InternalizacaoSaldo extends RelatorioFinanceiroTemplate {

 @Override
    public List<SoeDTO> filtrar(List<SoeDTO> lista) {
        List<SoeDTO> retorno = new ArrayList<SoeDTO>();

        try {
            Collection<Contrato> contratos = new ContratoBO().obterTodos();

            for (SoeDTO soe : lista) {
                if (!this.obterListaCentrosCusto().contains(soe.getCentroCusto())) {
                    continue;
                }

                if ((soe.getDataExibicao() != null) && (!Data.dentroDoPerido(soe.getDataExibicao(), this.getPedido().getInicio(), this.getPedido().getFim()))) {
                    continue;
                }

                if (!Data.dentroDoPerido(soe.getDataPagamento(), this.getPedido().getInicio(), this.getPedido().getFim())) {
                    continue;
                }

                for (Contrato contrato : contratos) {
                    if (soe.getNumeroContrato().equals(contrato.getNumero())) {
                        soe.setMoeda(contrato.getMoeda());
                        soe.setValorOriginalContrato(contrato.getValor());
                        soe.setValorAcumuladoContrato(getValorAcumuladoContrato(contrato.getNumero(), soe));
                    }
                }

                retorno.add(soe);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return retorno;
    }
}
