package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.Contrato;
import br.org.flem.baprodutiva.negocio.DespesaInelegivel;
import org.hibernate.Criteria;

/**
 *
 * Esta classe possui a lógica de acesso a dados, relativo a entidade Categoria
 *
 * @author Gerador de Código da FLEM
 *
 */
public class DespesaInelegivelDAO extends BaseDAOAb<DespesaInelegivel> {

    public DespesaInelegivelDAO() throws AcessoDadosException {
    }

    @Override
    protected Class getClasseDto() {
        return DespesaInelegivel.class;
    }

    @Override
    protected void adicionarAgregacoesCriteria(Criteria c, DespesaInelegivel objeto) {
    }
}