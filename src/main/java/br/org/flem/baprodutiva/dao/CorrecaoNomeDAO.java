/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.dao;


import br.org.flem.baprodutiva.negocio.CorrecaoCC;
import br.org.flem.baprodutiva.negocio.CorrecaoNome;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

/**
 *
 * @author AJLima
 */
public class CorrecaoNomeDAO extends BaseDAOAb<CorrecaoNome>{
    
    public CorrecaoNomeDAO() throws AplicacaoException{}
    
   public CorrecaoNome obterPorTipoIdSeqLinha(String apdTp, String apdId, String seqLinha) {
        Criteria criteria = session.createCriteria(CorrecaoNome.class);

        criteria.add(Expression.eq("apdTp", apdTp));
        criteria.add(Expression.eq("apdId", apdId));

        if (seqLinha != null){
            criteria.add(Expression.eq("seqLinha", seqLinha));
        }

        return (CorrecaoNome) criteria.setMaxResults(1).uniqueResult();
    }
        @Override
    protected Class<CorrecaoNome> getClasseDto() {
        return CorrecaoNome.class;
    }
    
}
