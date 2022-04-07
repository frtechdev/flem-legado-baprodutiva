/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.baprodutiva.negocio.Aditivo;
import br.org.flem.baprodutiva.dao.AditivoDAO;
import br.org.flem.baprodutiva.negocio.Contrato;
import java.util.Collection;


/**
 *
 * @author ilfernandes
 */

public class AditivoBO extends BaseBOAb<Aditivo> {
    
    public AditivoBO() throws AplicacaoException {
        super(new AditivoDAO());
    }
    
    public Collection<Aditivo> obterPorContrato(Contrato contrato) {
        return ((AditivoDAO) this.dao).obterPorContrato(contrato);
    }
}
