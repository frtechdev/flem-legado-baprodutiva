package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.DespesaDataExibicao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

/**
 *
 * @author essantos
 */
public class DespesaDataExibicaoDAO extends BaseDAOAb<DespesaDataExibicao> {

    public DespesaDataExibicaoDAO() throws AplicacaoException {
    }

    public DespesaDataExibicao obterPorTipoIdSeqLinha(String apdTp, String apdId, String seqLinha) {
        Criteria criteria = session.createCriteria(DespesaDataExibicao.class);

        criteria.add(Expression.eq("apdTp", apdTp));
        criteria.add(Expression.eq("apdId", apdId));

        if (seqLinha != null){
            criteria.add(Expression.eq("seqLinha", seqLinha));
        }

        return (DespesaDataExibicao) criteria.list().iterator().next();
    }

    @Override
    protected Class<DespesaDataExibicao> getClasseDto() {
        return DespesaDataExibicao.class;
    }
}
