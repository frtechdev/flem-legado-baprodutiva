package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.InternalizacaoAplicacaoFinanceira;
import java.util.Date;
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
public class InternalizacaoAplicacaoFinanceiraDAO extends BaseDAOAb<InternalizacaoAplicacaoFinanceira> {

    public InternalizacaoAplicacaoFinanceiraDAO() throws AcessoDadosException {
    }

    @Override
    protected Class getClasseDto() {
        return InternalizacaoAplicacaoFinanceira.class;
    }

    @Override
    protected void adicionarAgregacoesCriteria(Criteria c, InternalizacaoAplicacaoFinanceira objeto) {
    }
    
    public List<InternalizacaoAplicacaoFinanceira> obterPorPeriodo(Date inicio, Date fim) {
              
        Criteria internalizacaoAplicacaoFinanceira = session.createCriteria(InternalizacaoAplicacaoFinanceira.class);

        internalizacaoAplicacaoFinanceira.add( Expression.between("entrada", inicio, fim));

        return internalizacaoAplicacaoFinanceira.list();
    }
    
}