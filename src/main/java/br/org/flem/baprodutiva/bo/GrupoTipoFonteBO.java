package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.exception.AcessoDadosException;
import java.util.Collection;
import br.org.flem.baprodutiva.dao.GrupoTipoFonteDAO;

import br.org.flem.baprodutiva.negocio.GrupoTipoFonte;

/**
*
* Esta classe possui regras de negócios, referentes a entidade GrupoTipoFonte
*
* @author Gerador de Código da FLEM
*
*/
public class GrupoTipoFonteBO {

        GrupoTipoFonteDAO dao;

        public GrupoTipoFonteBO() throws AcessoDadosException {

                dao = new GrupoTipoFonteDAO();
        }

        public void inserirOuAlterar(GrupoTipoFonte objeto) throws AcessoDadosException {
                dao.inserirOuAlterar(objeto);
        }

        public void inserir(GrupoTipoFonte objeto) throws AcessoDadosException {
                dao.inserir(objeto);
        }

        public void alterar(GrupoTipoFonte objeto) throws AcessoDadosException {
                dao.alterar(objeto);
        }

        public void excluir(GrupoTipoFonte objeto) throws AcessoDadosException {
                dao.excluir(objeto);
        }

        public void excluir(Collection<GrupoTipoFonte> objetos) throws AcessoDadosException {
                dao.excluir(objetos);
        }

        public GrupoTipoFonte obterPorPk(GrupoTipoFonte objeto) throws AcessoDadosException {
                return dao.obterPorPk(objeto);
        }

        public GrupoTipoFonte obterPorPk(Integer id) throws AcessoDadosException {
                return dao.obterPorPk(id);
        }

        public Collection<GrupoTipoFonte> obterTodos() throws AcessoDadosException {
                return dao.obterTodos();
        }
        
        public Collection<GrupoTipoFonte> obterPorFiltro(GrupoTipoFonte objeto) throws AcessoDadosException {
                return dao.obterPorFiltro(objeto);
        }   
        
        

}