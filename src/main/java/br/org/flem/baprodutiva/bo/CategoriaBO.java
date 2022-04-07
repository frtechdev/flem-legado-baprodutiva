package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.exception.AcessoDadosException;
import java.util.Collection;
import br.org.flem.baprodutiva.dao.CategoriaDAO;

import br.org.flem.baprodutiva.negocio.Categoria;

/**
 *
 * Esta classe possui regras de negócios, referentes a entidade Categoria
 *
 * @author Gerador de Código da FLEM
 *
 */
public class CategoriaBO {

    CategoriaDAO dao;

    public CategoriaBO() throws AcessoDadosException {

        dao = new CategoriaDAO();
    }

    public void inserirOuAlterar(Categoria objeto) throws AcessoDadosException {
        dao.inserirOuAlterar(objeto);
    }

    public void inserir(Categoria objeto) throws AcessoDadosException {
        dao.inserir(objeto);
    }

    public void alterar(Categoria objeto) throws AcessoDadosException {
        dao.alterar(objeto);
    }

    public void excluir(Categoria objeto) throws AcessoDadosException {
        dao.excluir(objeto);
    }

    public void excluir(Collection<Categoria> objetos) throws AcessoDadosException {
        dao.excluir(objetos);
    }

    public Categoria obterPorPk(Categoria objeto) throws AcessoDadosException {
        return dao.obterPorPk(objeto);
    }

    public Categoria obterPorPk(Integer id) throws AcessoDadosException {
        return dao.obterPorPk(id);
    }

    public Collection<Categoria> obterTodos() throws AcessoDadosException {
        return dao.obterTodos();
    }

    public Collection<Categoria> obterTodosOrdenadoPorCampo(String campo)
            throws AcessoDadosException {
        return dao.obterTodosOrdenadoPorCampo(campo);
    }

    public Collection<Categoria> obterPorFiltro(Categoria objeto) throws AcessoDadosException {
        return dao.obterPorFiltro(objeto);
    }
    
    public Collection<Categoria> obterTodosOrigemFLEM() throws AcessoDadosException {
        return dao.obterTodosOrigemFLEM();
    }
}
