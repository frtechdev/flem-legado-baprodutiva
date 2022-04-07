package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.dao.TipoFonteDAO;
import br.org.flem.baprodutiva.negocio.TipoFonte;
import br.org.flem.fwe.exception.AcessoDadosException;
import java.util.Collection;

/**
*
* Esta classe possui regras de negócios, referentes a entidade TipoFonte
*
* @author Gerador de Código da FLEM
*
*/
public class TipoFonteBO {

        TipoFonteDAO dao;

        public TipoFonteBO() throws AcessoDadosException {

                dao = new TipoFonteDAO();
        }

        public void inserirOuAlterar(TipoFonte objeto) throws AcessoDadosException {
                dao.inserirOuAlterar(objeto);
        }

        public void inserir(TipoFonte objeto) throws AcessoDadosException {
                dao.inserir(objeto);
        }

        public void alterar(TipoFonte objeto) throws AcessoDadosException {
                dao.alterar(objeto);
        }

        public void excluir(TipoFonte objeto) throws AcessoDadosException {
                dao.excluir(objeto);
        }

        public void excluir(Collection<TipoFonte> objetos) throws AcessoDadosException {
                dao.excluir(objetos);
        }

        public TipoFonte obterPorPk(TipoFonte objeto) throws AcessoDadosException {
                return dao.obterPorPk(objeto);
        }

        public TipoFonte obterPorPk(Integer id) throws AcessoDadosException {
                return dao.obterPorPk(id);
        }

        public Collection<TipoFonte> obterTodos() throws AcessoDadosException {
                return dao.obterTodos();
        }
        
        public Collection<TipoFonte> obterPorFiltro(TipoFonte objeto) throws AcessoDadosException {
                return dao.obterPorFiltro(objeto);
        }
}