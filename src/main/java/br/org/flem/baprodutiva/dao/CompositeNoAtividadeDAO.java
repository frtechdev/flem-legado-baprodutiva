package br.org.flem.baprodutiva.dao;

import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.CompositeNoAtividade;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

/**
 *
 * Esta classe possui a lógica de acesso a dados, relativo a entidade Categoria
 *
 * @author Gerador de Código da FLEM
 *
 */
public class CompositeNoAtividadeDAO extends BaseDAOAb<CompositeNoAtividade> {

    public CompositeNoAtividadeDAO() throws AcessoDadosException {
    }

    @Override
    protected Class getClasseDto() {
        return CompositeNoAtividade.class;
    }
    
}