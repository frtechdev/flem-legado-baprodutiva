/*


 */
package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.util.Data;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mjpereira
 */
public class IFR extends RelatorioIFRTemplate {

    @Override
    public List<SoeDTO> filtrar(List<SoeDTO> lista) {
        List<SoeDTO> retorno = new ArrayList<SoeDTO>();
        for (SoeDTO soe : lista) {
            soe.setCentroCusto("20257333001");
            
            if (!this.obterListaCentrosCusto().contains(soe.getCentroCusto())) {
                continue;
            }
            
            if(!Data.dentroDoPerido(soe.getDataPagamento(), this.getDataInicio(), this.getDataFim())){
                continue;
            }
            
            retorno.add(soe);
        }
        return retorno;
    }
}
