package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.baprodutiva.dao.CompositeNoDAO;
import br.org.flem.baprodutiva.negocio.CompositeNo;
import java.util.Collection;

/**
 *
 * Esta classe possui regras de negócios, referentes a entidade Componente
 *
 * @author Gerador de Código da FLEM
 *
 */
public class CompositeNoBO {

    CompositeNoDAO dao;

    public CompositeNoBO() throws AcessoDadosException {

        dao = new CompositeNoDAO();
    }

    public CompositeNo obterPorPk(CompositeNo objeto) throws AcessoDadosException {
        return dao.obterPorPk(objeto);
    }

    public CompositeNo obterPorPk(Integer id) throws AcessoDadosException {
        return dao.obterPorPk(id);
    }

    public Collection<CompositeNo> obterTodos() throws AcessoDadosException {
        return dao.obterTodos();
    }
    
    public Collection<CompositeNo> obterTodosOrdenadoPorCampo(String nomeCampo) throws AcessoDadosException {
        return dao.obterTodosOrdenadoPorCampo(nomeCampo);
    }    

    public Collection<CompositeNo> obterPorFiltro(CompositeNo objeto) throws AcessoDadosException {
        return dao.obterPorFiltro(objeto);
    }
    
}
