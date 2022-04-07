package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.Internalizacao;
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
public class InternalizacaoDAO extends BaseDAOAb<Internalizacao> {

    public InternalizacaoDAO() throws AcessoDadosException {
    }

    @Override
    protected Class getClasseDto() {
        return Internalizacao.class;
    }
    
    public List<Internalizacao> obterPorPeriodo(Date inicio, Date fim) {
              
        Criteria internalizacao = session.createCriteria(Internalizacao.class);

        internalizacao.add( Expression.between("entrada", inicio, fim));

        return internalizacao.list();
    }
    
    @Override
    protected void adicionarAgregacoesCriteria(Criteria c, Internalizacao objeto) {
    }
    
    
}