package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.EntidadeExecutora;

/**
 *
 * @author dbbarreto
 */
public class UnidadeExecutoraDAO extends BaseDAOAb<EntidadeExecutora> {

    public UnidadeExecutoraDAO() throws AcessoDadosException {
    }

    protected Class getClasseDto() {
        return  EntidadeExecutora.class;
    }

}