package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.EntidadeExecutora;

/**
 *
 * @author dbbarreto
 */
public class EntidadeExecutoraDAO extends BaseDAOAb<EntidadeExecutora> {

    public EntidadeExecutoraDAO() throws AcessoDadosException {
    }

    protected Class getClasseDto() {
        return  EntidadeExecutora.class;
    }

}