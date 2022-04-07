/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.baprodutiva.negocio.RateioCentroCusto;
import br.org.flem.baprodutiva.dao.RateioCentroCustoDAO;


/**
 *
 * @author ILFernandes
 */

public class RateioCentroCustoBO extends BaseBOAb<RateioCentroCusto> {
    
    public RateioCentroCustoBO() throws AplicacaoException {
        super(new RateioCentroCustoDAO());
    }
    
    public RateioCentroCusto obterPorTipoId(String apdTp, String apdId) throws AcessoDadosException {
        return ((RateioCentroCustoDAO)dao).obterPorTipoId(apdTp, apdId);
    }
}
