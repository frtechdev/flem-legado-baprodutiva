package br.org.flem.baprodutiva.bo;


import br.org.flem.baprodutiva.dao.SubCategoriaDAO;
import br.org.flem.baprodutiva.negocio.SubCategoria;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import java.util.Collection;

/**
 *
 * @author AJLima
 */
public class SubCategoriaBO {
    
    
     SubCategoriaDAO dao;

    public SubCategoriaBO() throws AcessoDadosException {

        dao = new SubCategoriaDAO();
    }
    
        public void inserirOuAlterar(SubCategoria objeto) throws AcessoDadosException {
        dao.inserirOuAlterar(objeto);
    }
        
        
   
    public void inserir(SubCategoria objeto) throws AcessoDadosException {
        dao.inserir(objeto);
    }

    public void alterar(SubCategoria objeto) throws AcessoDadosException {
        dao.alterar(objeto);
    }

    public void excluir(SubCategoria objeto) throws AcessoDadosException {
        dao.excluir(objeto);
    }

    public void excluir(Collection<SubCategoria> objetos) throws AcessoDadosException {
        dao.excluir(objetos);
    }
    
        public Collection<SubCategoria> obterTodos() throws AcessoDadosException {
        return dao.obterTodos();
    }
        
       public SubCategoria obterPorPk(Integer id) throws AplicacaoException {
                return (SubCategoria) dao.obterPorPk(id);
        }

}