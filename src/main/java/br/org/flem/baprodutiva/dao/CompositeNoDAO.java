package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.CompositeNo;

/**
 *
 * Esta classe possui a l�gica de acesso a dados, relativo a entidade Categoria
 *
 * @author Gerador de C�digo da FLEM
 *
 */
public class CompositeNoDAO extends BaseDAOAb<CompositeNo> {

    public CompositeNoDAO() throws AcessoDadosException {
    }

    @Override
    protected Class getClasseDto() {
        return CompositeNo.class;
    }
}