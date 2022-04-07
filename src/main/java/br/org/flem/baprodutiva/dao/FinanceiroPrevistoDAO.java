package br.org.flem.baprodutiva.dao;

import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.CompositeFolha;
import br.org.flem.baprodutiva.negocio.CompositeIF;
import br.org.flem.baprodutiva.negocio.FinanceiroPrevisto;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;

/**
 *
 * @author dbbarreto
 */
public class FinanceiroPrevistoDAO extends BaseDAOAb<FinanceiroPrevisto> {

    public FinanceiroPrevistoDAO() throws AcessoDadosException {
    }

    protected Class getClasseDto() {
        return FinanceiroPrevisto.class;
    }
    
    public List<FinanceiroPrevisto> obterFinanceiroNoPeriodo(CompositeIF composite, Date inicio, Date fim) {
        
        String hql = "select f from FinanceiroPrevisto as f WHERE f.composite.id = :compositeId and year(f.planejamento.dataFinal) >= year(:dataInicial) and year(f.planejamento.dataFinal) <= year(:dataFinal)";
        
        Query query = session.createQuery(hql).setInteger("compositeId", composite.getId()).setDate("dataInicial", inicio).setDate("dataFinal", fim);        
        
        return query.list();
    }

    public List<FinanceiroPrevisto> obterPorPlanejamento(Planejamento planejamento) {

        String hql = "select f from FinanceiroPrevisto as f WHERE f.planejamento.id = :planejamento";

        Query query = session.createQuery(hql).setInteger("planejamento", planejamento.getId());

        return query.list();
    }

    public FinanceiroPrevisto obterPorSubAtividade(CompositeFolha subAtividade) {

        String hql = "select f from FinanceiroPrevisto as f WHERE f.composite.id = :subAtividade";

        Query query = session.createQuery(hql).setInteger("subAtividade", subAtividade.getId());

        return (FinanceiroPrevisto)query.setMaxResults(1).uniqueResult();
    }

    public List<FinanceiroPrevisto> obterFinanceiroNoPeriodo(Planejamento planejamento, Date inicio, Date fim) {
        
        String hql = "select f from FinanceiroPrevisto as f WHERE f.planejamento.dataInicial >= :dataInicial and f.planejamento.dataFinal <= :dataFinal and f.planejamento.id = :planejamento";
        
        Query query = session.createQuery(hql).setDate("dataInicial", inicio).setDate("dataFinal", fim).setInteger("planejamento", planejamento.getId());
        
        return query.list();
    }
    
        public List<FinanceiroPrevisto> obterFinanceiroNoPeriodoPlanejamentoAtivo(Collection<Planejamento> planejamento, Date inicio, Date fim) {
        
        String hql = "select f from FinanceiroPrevisto as f WHERE f.planejamento.dataInicial >= :dataInicial and f.planejamento.dataFinal <= :dataFinal and f.planejamento.ativo=1";
        
        Query query = session.createQuery(hql).setDate("dataInicial", inicio).setDate("dataFinal", fim);
        
        return query.list();
    }
    
    
    public List<FinanceiroPrevisto> obterFinanceiroNoPeriodo(Categoria categoria, Planejamento planejamento, Date inicio, Date fim) {
        
        String hql = "select f from FinanceiroPrevisto as f WHERE f.composite IN (SELECT c FROM CompositeFolha c WHERE c.categoria = :categoria) AND f.planejamento.dataInicial >= :dataInicial and f.planejamento.dataFinal <= :dataFinal and f.planejamento.id = :planejamento";
        
        Query query = session.createQuery(hql).setDate("dataInicial", inicio).setDate("dataFinal", fim).setParameter("categoria", categoria).setInteger("planejamento", planejamento.getId());

        return query.list();
    }
        public List<FinanceiroPrevisto> obterTodosPorSubatividadeOrdenadoPorData(CompositeFolha subAtividade) {
        
        String hql = "select f from FinanceiroPrevisto as f WHERE f.composite.id = :subAtividade order by data desc";

        Query query = session.createQuery(hql).setInteger("subAtividade", subAtividade.getId());

        return query.list();
    }
    
    
    @Override
    protected void adicionarAgregacoesCriteria(Criteria c, FinanceiroPrevisto objeto) {
        if (objeto.getComposite() != null && objeto.getComposite().getId() != null) {
            c.add(Expression.eq("composite.id", objeto.getComposite().getId()));
        }
    }

}