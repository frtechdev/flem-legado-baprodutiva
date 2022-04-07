/*


 */
package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.util.Data;
import br.org.flem.fwe.util.Valores;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mjpereira
 */
public class FontesCategorias extends RelatorioFinanceiroTemplate {

    @Override
    public List<SoeDTO> filtrar(List<SoeDTO> lista) {
        List<SoeDTO> retorno = new ArrayList<SoeDTO>();
        for (SoeDTO soe : lista) {

            if (soe.getDataExibicao() != null) {
                // Caso a data de exibição não esteja dentro do período do pedido ele não serÃ¡ adicionado na lista
                if (!Data.dentroDoPerido(soe.getDataExibicao(), this.getPedido().getInicio(), this.getPedido().getFim())) {
                    continue;
                }
            } else {
                // Caso a data de pagamento não esteja dentro do período do pedido ele não serÃ¡ adicionado na lista
                if (!Data.dentroDoPerido(soe.getDataPagamento(), this.getPedido().getInicio(), this.getPedido().getFim())) {
                    continue;
                }
            }
            if (!this.obterListaCentrosCusto().contains(soe.getCentroCusto())) {
                continue;
            }
            /*            if(!Data.dentroDoPerido(soe.getDataPagamento(), this.getPedido().getInicio(), this.getPedido().getFim())){
            continue;
            }*/
            //System.out.println("Descrição:\t"+soe.getDescricao()+"\tValor:\t"+MoedaUtil.removePrecisao(soe.getValorOriginalContrato(), 2)+"\tCentro de custo:\t"+soe.getCentroCusto());
            retorno.add(soe);
        }
        return retorno;
    }
}
