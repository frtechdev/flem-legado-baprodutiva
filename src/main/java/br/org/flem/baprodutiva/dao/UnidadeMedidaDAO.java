package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.UnidadeMedida;

/**
 *
 * @author dbbarreto
 */
public class UnidadeMedidaDAO extends BaseDAOAb<UnidadeMedida> {

    public UnidadeMedidaDAO() throws AcessoDadosException {
    }

    protected Class getClasseDto() {
        return UnidadeMedida.class;
    }

}