/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.baprodutiva.dao.PlanejadoComponenteDAO;
import br.org.flem.baprodutiva.negocio.CompositeIF;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.baprodutiva.negocio.PlanejadoComponente;
import java.util.List;

/**
 *
 * @author ILFernandes
 */
public class PlanejadoComponenteBO extends BaseBOAb<PlanejadoComponente> {

    public PlanejadoComponenteBO() throws AplicacaoException {
        super(new PlanejadoComponenteDAO());
    }

    public PlanejadoComponenteDAO getDao() {
        return (PlanejadoComponenteDAO) this.dao;
    }

    public List<PlanejadoComponente> obterPorPOAComponente(CompositeIF componente, Planejamento poa) {
       return this.getDao().obterPorPOAComponente(componente, poa);
    }

    
}
