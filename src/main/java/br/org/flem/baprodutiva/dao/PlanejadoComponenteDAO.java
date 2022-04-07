/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.CompositeIF;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.baprodutiva.negocio.PlanejadoComponente;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ILFernandes
 */
public class PlanejadoComponenteDAO extends BaseDAOAb<PlanejadoComponente> {

    public PlanejadoComponenteDAO() throws AcessoDadosException {
    }

    @Override
    protected Class<PlanejadoComponente> getClasseDto() {
        return PlanejadoComponente.class;
    }

    public List<PlanejadoComponente> obterPorPOAComponente(CompositeIF componente, Planejamento poa) {
        Criteria planejadoCriteria = session.createCriteria(this.getClasseDto());
        planejadoCriteria.add(Restrictions.eq("componente", componente));
        planejadoCriteria.add(Restrictions.eq("poa", poa));

        return planejadoCriteria.list();
    }
}
