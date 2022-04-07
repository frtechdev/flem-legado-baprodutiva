/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.LoteDespesaAplicacao;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

/**
 *
 * @author ilfernandes
 */
public class LoteDespesaAplicacaoDAO extends BaseDAOAb<LoteDespesaAplicacao> {

    public LoteDespesaAplicacaoDAO() throws AcessoDadosException {
    }

    @Override
    protected Class<LoteDespesaAplicacao> getClasseDto() {
        return LoteDespesaAplicacao.class;
    }

    public Collection<LoteDespesaAplicacao> obterPorPeriodo(Date inicio, Date fim) {

        Criteria loteCriteria = session.createCriteria(LoteDespesaAplicacao.class);

        loteCriteria.add(Expression.between("dataPagamento", inicio, fim));

        return loteCriteria.list();
    }

    public List<LoteDespesaAplicacao> obterPorIdDespesaAndSeqLinha(String idDespesa, String seqLinha) {

        Criteria loteCriteria = session.createCriteria(LoteDespesaAplicacao.class);

        loteCriteria.add(Expression.eq("idDespesa", idDespesa));
        if (seqLinha != null) {
            loteCriteria.add(Expression.eq("seqLinha", seqLinha));
        }

        return loteCriteria.list();
    }

    public LoteDespesaAplicacao obterPorIdDespesaSeqLinhaAndTipo(String idDespesa, String seqLinha, String tipo) {

        Criteria loteCriteria = session.createCriteria(LoteDespesaAplicacao.class);

        loteCriteria.add(Expression.eq("idDespesa", idDespesa));
        if (seqLinha != null) {
            loteCriteria.add(Expression.eq("seqLinha", seqLinha));
        }
        if (tipo != null) {
            loteCriteria.add(Expression.eq("tipo", tipo));
        }
        List<LoteDespesaAplicacao> lista = loteCriteria.list();
        if( !lista.isEmpty() ){
            return lista.get(0);
        }

        return null;
    }
}
