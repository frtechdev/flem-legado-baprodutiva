package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.baprodutiva.dao.ContratoDAO;
import java.util.Collection;
import br.org.flem.baprodutiva.negocio.Contrato;
import java.io.Serializable;
import java.util.List;

/**
 *
 * Esta classe possui regras de negócios, referentes a entidade Categoria
 *
 * @author Gerador de Código da FLEM
 * 
 *
 */
public class ContratoBO {

    ContratoDAO dao;

    public ContratoBO() throws AcessoDadosException {

        dao = new ContratoDAO();
    }

    public void inserirOuAlterar(Contrato objeto) throws AcessoDadosException {
        dao.inserirOuAlterar(objeto);
    }

    public void inserir(Contrato objeto) throws AcessoDadosException {
        dao.inserir(objeto);
    }

    public void alterar(Contrato objeto) throws AcessoDadosException {
        dao.alterar(objeto);
    }

    public void excluir(Contrato objeto) throws AcessoDadosException {
        dao.excluir(objeto);
    }

    public void excluir(Collection<Contrato> objetos) throws AcessoDadosException {
        dao.excluir(objetos);
    }

    public Contrato obterPorPk(Serializable objeto) throws AcessoDadosException {
        return dao.obterPorPk(objeto);
    }

    public Contrato obterPorPk(Integer id) throws AcessoDadosException {
        return dao.obterPorPk(id);
    }

    public Collection<Contrato> obterTodos() throws AcessoDadosException {
        return dao.obterTodos();
    }
    
    public Collection<Contrato> obterTodosOrdenadoPorCampo(String campo)
            throws AcessoDadosException {
        return dao.obterTodosOrdenadoPorCampo(campo);
    }

    public Collection<Contrato> obterPorFiltro(Contrato objeto) throws AcessoDadosException {
        return dao.obterPorFiltro(objeto);
    }
    
    public Contrato obterPorNumero(String numero) {   
        return dao.obterPorNumero(numero);
    }

    public List<String> obterNumerosContratos(){
        return dao.obterNumerosContratos();
    }
    
     public Collection<Contrato> obterPorContratoFornecedor(String contrato, String nomeFornecedor) throws AcessoDadosException {
        return dao.obterPorContratoFornecedor(contrato, nomeFornecedor);
    }


}
