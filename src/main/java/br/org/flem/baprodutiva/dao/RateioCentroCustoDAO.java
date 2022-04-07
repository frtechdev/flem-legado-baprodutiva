/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.RateioCentroCusto;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;


/**
 *
 * @author ILFernandes
 */

public class RateioCentroCustoDAO extends BaseDAOAb<RateioCentroCusto> {
    
    public RateioCentroCustoDAO() throws AcessoDadosException {
    }
    
    @Override
    protected Class<RateioCentroCusto> getClasseDto() {
        return RateioCentroCusto.class;
    }
    
    public RateioCentroCusto obterPorTipoId(String apdTp, String apdId) {
        Criteria criteria = this.session.createCriteria(this.getClasseDto());
        criteria.add(Restrictions.eq("apdTp", apdTp));
        criteria.add(Restrictions.eq("apdId", apdId));
        return (RateioCentroCusto) criteria.uniqueResult();
    }
}
