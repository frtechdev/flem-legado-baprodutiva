package br.org.flem.baprodutiva.dao;

import br.org.flem.baprodutiva.negocio.InternalizacaoLoteBancario;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * Esta classe possui a lógica de acesso a dados, relativo a entidade Categoria
 *
 * @author Gerador de Código da FLEM
 *
 */
public class InternalizacaoLoteBancarioDAO extends BaseDAOAb<InternalizacaoLoteBancario> {

    public InternalizacaoLoteBancarioDAO() throws AcessoDadosException {
    }

    @Override
    protected Class getClasseDto() {
        return InternalizacaoLoteBancario.class;
    }

    @Override
    protected void adicionarAgregacoesCriteria(Criteria c, InternalizacaoLoteBancario objeto) {
    }
    
    public List<InternalizacaoLoteBancario> obterPorPeriodo(Date inicio, Date fim) {
              
        Criteria internalizacaoDevolucao = session.createCriteria(InternalizacaoLoteBancario.class);

        internalizacaoDevolucao.add( Restrictions.between("entrada", inicio, fim));

        return internalizacaoDevolucao.list();
    }

    public InternalizacaoLoteBancario obterPorIdCompromisso(String idCompromisso){

      return (InternalizacaoLoteBancario) session.createQuery("FROM InternalizacaoLoteBancario id WHERE id.idCompromisso = :idCompromisso ")
              .setString("idCompromisso", idCompromisso).uniqueResult();
    }
    
}