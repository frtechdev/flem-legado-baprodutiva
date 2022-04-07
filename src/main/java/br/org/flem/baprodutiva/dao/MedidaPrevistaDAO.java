package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.MedidaPrevista;

/**
 *
 * @author dbbarreto
 */
public class MedidaPrevistaDAO extends BaseDAOAb<MedidaPrevista> {

    public MedidaPrevistaDAO() throws AcessoDadosException {
    }

    protected Class getClasseDto() {
        return MedidaPrevista.class;
    }

}