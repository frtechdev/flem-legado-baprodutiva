package br.org.flem.baprodutiva.dao;

import br.org.flem.baprodutiva.negocio.CompositeFolha;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import java.util.Collection;
import java.util.Date;
import org.hibernate.Query;

/**
 *
 * @author mgsilva
 */
public class PlanejamentoDAO extends BaseDAOAb<Planejamento> {

    public PlanejamentoDAO() throws AcessoDadosException {
    }

    @Override
    protected Class getClasseDto() {
        return Planejamento.class;
    }

    public Collection<Planejamento> obterPoaNaoPlanejadoPorSubAtividade(CompositeFolha subAtividade) {
        String sql = "from Planejamento p where p not in (select plan.poa from Planejamento plan where plan.subAtividade = :subAtividade)";
        Query query = session.createQuery(sql).setEntity("subAtividade", subAtividade);
        return query.list();
    }

    public Collection<Planejamento> obterPoaSemFinanceiroPrevistoPorSubAtividade(CompositeFolha subAtividade) {
        String sql = "from Planejamento p where p not in (select fin.periodo.poa from FinanceiroPrevisto fin where fin.composite = :subAtividade)";
        Query query = session.createQuery(sql).setEntity("subAtividade", subAtividade);
        return query.list();
    }
    
    public Collection<Planejamento> obterPendentesDeTaxa() {
        return session.createQuery("from Planejamento p where p.dataFinal < :data ").setDate("data", new Date()).list();
    }
    
    
    public Collection<Planejamento> obterTodosAtivos() {
        return session.createQuery("from Planejamento p where p.ativo=1 ").list();
    }
    
    public Planejamento obterPlanejamentoAtivoPorPk(int id) {
        return (Planejamento) session.createQuery("from Planejamento p where p.ativo=1 ").uniqueResult();
    }
    
    public Planejamento obterPlanejamentoEntreDatas(Date dataInicio, Date dataFinal){
        
        return (Planejamento) session.createQuery("from Planejamento p where p.dataFinal >= :dataInicial and p.dataInicial <= :dataFinal and p.ativo=1 ").setDate("dataInicial", dataInicio).setDate("dataFinal", dataFinal).uniqueResult();
    }
    

}
