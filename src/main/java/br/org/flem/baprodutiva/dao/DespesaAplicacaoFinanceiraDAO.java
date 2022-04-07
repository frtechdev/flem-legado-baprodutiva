package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.DespesaAplicacaoFinanceira;
import br.org.flem.baprodutiva.negocio.LoteDespesaAplicacao;
import java.beans.Expression;
import java.util.Collection;
import java.util.Date;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Gerador de Código da FLEM
 *
 */
public class DespesaAplicacaoFinanceiraDAO extends BaseDAOAb<DespesaAplicacaoFinanceira> {

    public DespesaAplicacaoFinanceiraDAO() throws AcessoDadosException {
    }

    @Override
    protected Class getClasseDto() {
        return DespesaAplicacaoFinanceira.class;
    }

    @Override
    protected void adicionarAgregacoesCriteria(Criteria c, DespesaAplicacaoFinanceira objeto) {
    }

    public Collection<DespesaAplicacaoFinanceira> obterPorPeriodo(Date inicio, Date fim) {
        Criteria loteCriteria = session.createCriteria(DespesaAplicacaoFinanceira.class);
        loteCriteria.add(Restrictions.between("entrada", inicio, fim));
        return loteCriteria.list();
    }
}