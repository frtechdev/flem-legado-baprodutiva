package br.org.flem.baprodutiva.dao;

import br.org.flem.baprodutiva.negocio.InternalizacaoDevolucao;
import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import java.io.IOException;
import java.util.Collection;
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
public class InternalizacaoDevolucaoDAO extends BaseDAOAb<InternalizacaoDevolucao> {

    public InternalizacaoDevolucaoDAO() throws AcessoDadosException {
    }

    @Override
    protected Class getClasseDto() {
        return InternalizacaoDevolucao.class;
    }

    @Override
    protected void adicionarAgregacoesCriteria(Criteria c, InternalizacaoDevolucao objeto) {
    }

    public List<InternalizacaoDevolucao> obterPorPeriodo(Date inicio, Date fim) {

        Criteria internalizacaoDevolucao = session.createCriteria(InternalizacaoDevolucao.class);

        internalizacaoDevolucao.add(Expression.between("entrada", inicio, fim));

        return internalizacaoDevolucao.list();
    }

    public InternalizacaoDevolucao obterPorIdCompromisso(String idCompromisso) {

        return (InternalizacaoDevolucao) session.createQuery("FROM InternalizacaoDevolucao id WHERE id.idCompromisso = :idCompromisso ")
                .setString("idCompromisso", idCompromisso).uniqueResult();
    }

    public Collection<InternalizacaoDevolucao> obterDevolucoesSubDoacao() {
        Criteria criteria = session.createCriteria(InternalizacaoDevolucao.class);
        return criteria.list();
    }

    public List<InternalizacaoDevolucao> obterDevolucoesCCOperacionalPorPeriodo(Date inicio, Date fim) throws IOException {
        Criteria devolucao = session.createCriteria(InternalizacaoDevolucao.class);
        if (inicio != null) {
            devolucao.add(Expression.ge("entrada", inicio));
        }
        if (fim != null) {
            devolucao.add(Expression.le("entrada", fim));
        }
        devolucao.add(Expression.eq("centroCusto", PropertiesUtil.getProperties().getProperty("ccOperacional")));

        return devolucao.list();
    }

    @Override
    public List<InternalizacaoDevolucao> obterTodos(){
        Criteria devolucao = session.createCriteria(InternalizacaoDevolucao.class);
        return devolucao.list();
    }
    
    public InternalizacaoDevolucao obterDevolucao(String idCompromisso, String idComp, String seqLinha) {
        Criteria devolucao = session.createCriteria(InternalizacaoDevolucao.class);
        devolucao.add(Expression.eq("idCompromisso", idCompromisso));
        if( idComp != null ){
            devolucao.add(Expression.le("idComp", idComp));
        }
        devolucao.add(Expression.eq("seqLinha", seqLinha));

        return (InternalizacaoDevolucao) devolucao.uniqueResult();
    }
}
