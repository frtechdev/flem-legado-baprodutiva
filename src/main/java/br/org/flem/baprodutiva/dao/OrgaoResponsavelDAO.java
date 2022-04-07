package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.OrgaoResponsavel;

/**
 *
 * @author dbbarreto
 */
public class OrgaoResponsavelDAO extends BaseDAOAb<OrgaoResponsavel> {

    public OrgaoResponsavelDAO() throws AcessoDadosException {
    }

    protected Class getClasseDto() {
        return OrgaoResponsavel.class;
    }

}