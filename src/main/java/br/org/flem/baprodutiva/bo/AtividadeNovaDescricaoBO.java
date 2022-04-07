/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.baprodutiva.negocio.AtividadeNovaDescricao;
import br.org.flem.baprodutiva.dao.AtividadeNovaDescricaoDAO;
import br.org.flem.baprodutiva.negocio.CompositeNoAtividade;
import br.org.flem.baprodutiva.negocio.Planejamento;
import java.util.Collection;


/**
 *
 * @author ILFernandes
 */

public class AtividadeNovaDescricaoBO extends BaseBOAb<AtividadeNovaDescricao> {
    
    public AtividadeNovaDescricaoBO() throws AplicacaoException {
        super(new AtividadeNovaDescricaoDAO());
    }
    
    public Collection<AtividadeNovaDescricao> obterPorAtividade(CompositeNoAtividade atividade) {
        return this.getDAO().obterPorAtividade(atividade);
    }
    
    private AtividadeNovaDescricaoDAO getDAO() {
        return (AtividadeNovaDescricaoDAO)dao;
    }
    
    public String obterDescricaoAtividadePorPoa(CompositeNoAtividade atividade, Planejamento poa) throws AplicacaoException {
        String retorno = atividade.getDescricao();
        for (AtividadeNovaDescricao atividadeNovaDescricao : this.obterTodos()) {
            if (atividadeNovaDescricao.getAtividade() == atividade && atividadeNovaDescricao.getPoa() == poa) {
                retorno = atividadeNovaDescricao.getNovaDescricao();
                break;
            }
        }
        return retorno;
    }
}
