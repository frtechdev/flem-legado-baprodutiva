package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.relatorio.SoeDTO;
import br.org.flem.fwe.util.Data;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mjpereira
 */
public class SOEListao extends RelatorioFinanceiroTemplate {

    @Override
    public List<SoeDTO> filtrar(List<SoeDTO> lista) {
        List<SoeDTO> retorno = new ArrayList<SoeDTO>();

        try {
            //Baprodutiva n�o usa.
           // Collection<Contrato> contratos = new ContratoBO().obterTodos();

            for (SoeDTO soe : lista) {
                if(soe.getDescricao().equals("Reposi��o Fundo Rotativo, Setaf  Paulo Afonso (NOV/19), Proc.04785/19.")){
                    System.out.print("Entrei");
                }
                if (!this.obterListaCentrosCusto().contains(soe.getCentroCusto())) {
                    continue;
                }
                
                if( soe.getDataExibicao() != null ){
                    if (!Data.dentroDoPerido(soe.getDataExibicao(), this.getPedido().getInicio(), this.getPedido().getFim())) {
                        continue;
                    }
                }else{
                    if (!Data.dentroDoPerido(soe.getDataPagamento(), this.getPedido().getInicio(), this.getPedido().getFim())) {
                        continue;
                    }
                }
/*              //Baprodutiva n�o usa.
                for (Contrato contrato : contratos) {
                    if (soe.getNumeroContrato().equals(contrato.getNumero())) {
                        soe.setMoeda(contrato.getMoeda());
                        soe.setValorOriginalContrato(contrato.getValor());
                        soe.setValorAcumuladoContrato(getValorAcumuladoContrato(contrato.getNumero(), soe));
                    }
                }
*/
                retorno.add(soe);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return retorno;
    }
}
