package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.CorrecaoCC;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

/**
 *
 * @author essantos
 */
public class CorrecaoCCDAO extends BaseDAOAb<CorrecaoCC> {

    public CorrecaoCCDAO() throws AplicacaoException {
    }

    public CorrecaoCC obterPorTipoIdSeqLinha(String apdTp, String apdId, String seqLinha) {
        Criteria criteria = session.createCriteria(CorrecaoCC.class);

        criteria.add(Expression.eq("apdTp", apdTp));
        criteria.add(Expression.eq("apdId", apdId));

        if (seqLinha != null){
            criteria.add(Expression.eq("seqLinha", seqLinha));
        }

        return (CorrecaoCC) criteria.list().iterator().next();
    }

    @Override
    protected Class<CorrecaoCC> getClasseDto() {
        return CorrecaoCC.class;
    }
}
