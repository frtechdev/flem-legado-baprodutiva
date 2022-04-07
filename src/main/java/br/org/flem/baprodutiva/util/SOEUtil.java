/*


 */

package br.org.flem.baprodutiva.util;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.baprodutiva.bo.ContratoBO;
import br.org.flem.baprodutiva.negocio.Contrato;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import java.util.Collection;

/**
 *
 * @author dbbarreto
 */
public class SOEUtil {
    public static void adicionaDadosContrato(SoeDTO dto) throws AcessoDadosException {
        if(!dto.getNumeroContrato().equals("")) {
            Contrato contrato = new ContratoBO().obterPorNumero(dto.getNumeroContrato());
            
            if (contrato != null) {
                dto.setValorOriginalContrato(contrato.getValor());
                dto.setMoeda(contrato.getMoeda());
            }
        }
    }
    
    public static void adicionaDadosContrato(Collection<SoeDTO> dtos) throws AcessoDadosException {
        for(SoeDTO dto : dtos) {
            adicionaDadosContrato(dto);
        }
    }
}
