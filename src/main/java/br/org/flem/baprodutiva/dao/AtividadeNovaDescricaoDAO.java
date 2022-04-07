/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.AtividadeNovaDescricao;
import br.org.flem.baprodutiva.negocio.CompositeNoAtividade;
import java.util.Collection;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ILFernandes
 */
public class AtividadeNovaDescricaoDAO extends BaseDAOAb<AtividadeNovaDescricao> {

    public AtividadeNovaDescricaoDAO() throws AcessoDadosException {
    }

    @Override
    protected Class<AtividadeNovaDescricao> getClasseDto() {
        return AtividadeNovaDescricao.class;
    }

    public Collection<AtividadeNovaDescricao> obterPorAtividade(CompositeNoAtividade atividade) {
        Criteria atividadeNovaDescricao = this.session.createCriteria(this.getClasseDto());
        atividadeNovaDescricao.createAlias("atividade", "atividade");
        atividadeNovaDescricao.add(Restrictions.eq("atividade", atividade));
        return atividadeNovaDescricao.list();
    }
}
