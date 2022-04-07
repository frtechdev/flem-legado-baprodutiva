package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.baprodutiva.dao.PedidoDAO;
import java.util.Collection;
import br.org.flem.baprodutiva.negocio.Pedido;
import java.io.Serializable;

/**
 *
 * Esta classe possui regras de negócios, referentes a entidade Categoria
 *
 * @author Gerador de Código da FLEM
 *
 */
public class PedidoBO {

    PedidoDAO dao;

    public PedidoBO() throws AcessoDadosException {

        dao = new PedidoDAO();
    }

    public void inserirOuAlterar(Pedido objeto) throws AcessoDadosException {
        dao.inserirOuAlterar(objeto);
    }

    public void inserir(Pedido objeto) throws AcessoDadosException {
        dao.inserir(objeto);
    }

    public void alterar(Pedido objeto) throws AcessoDadosException {
        dao.alterar(objeto);
    }

    public void excluir(Pedido objeto) throws AcessoDadosException {
        dao.excluir(objeto);
    }

    public void excluir(Collection<Pedido> objetos) throws AcessoDadosException {
        dao.excluir(objetos);
    }

    public Pedido obterPorPk(Serializable objeto) throws AcessoDadosException {
        return dao.obterPorPk(objeto);
    }

    public Pedido obterPorPk(Integer id) throws AcessoDadosException {
        return dao.obterPorPk(id);
    }

    public Collection<Pedido> obterTodos() throws AcessoDadosException {
        return dao.obterTodos();
    }
    
    public Collection<Pedido> obterTodosOrdenadoPorCampo(String campo)
            throws AcessoDadosException {
        return dao.obterTodosOrdenadoPorCampo(campo);
    }

    public Collection<Pedido> obterPorFiltro(Pedido objeto) throws AcessoDadosException {
        return dao.obterPorFiltro(objeto);
    }

}
