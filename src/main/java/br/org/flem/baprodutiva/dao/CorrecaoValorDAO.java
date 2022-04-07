package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.CorrecaoValor;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

/**
 *
 * @author essantos
 */
public class CorrecaoValorDAO extends BaseDAOAb<CorrecaoValor> {

    public CorrecaoValorDAO() throws AplicacaoException {
    }

    public CorrecaoValor obterPorTipoIdSeqLinha(String apdTp, String apdId, String seqLinha) {
        Criteria criteria = session.createCriteria(CorrecaoValor.class);

        criteria.add(Expression.eq("apdTp", apdTp));
        criteria.add(Expression.eq("apdId", apdId));

        if (seqLinha != null){
            criteria.add(Expression.eq("seqLinha", seqLinha));
        }

        return (CorrecaoValor) criteria.list().iterator().next();
    }

    @Override
    protected Class<CorrecaoValor> getClasseDto() {
        return CorrecaoValor.class;
    }
}
