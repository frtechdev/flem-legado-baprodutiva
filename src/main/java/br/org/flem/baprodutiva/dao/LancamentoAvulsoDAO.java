package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.LancamentoAvulso;
import java.util.Collection;
import java.util.Date;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author essantos
 */
public class LancamentoAvulsoDAO extends BaseDAOAb<LancamentoAvulso> {

    public LancamentoAvulsoDAO() throws AplicacaoException {
    }

    @Override
    protected Class<LancamentoAvulso> getClasseDto() {
        return LancamentoAvulso.class;
    }

    public Collection<LancamentoAvulso> obterPorPeriodo(Date inicio, Date fim) {
        Criteria loteCriteria = session.createCriteria(this.getClasseDto());
        loteCriteria.add(Restrictions.between("dataPagamento", inicio, fim));
        return loteCriteria.list();
    }
}
