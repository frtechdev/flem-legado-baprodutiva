package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.exception.AcessoDadosException;
import java.util.Collection;
import br.org.flem.baprodutiva.dao.FonteDAO;

import br.org.flem.baprodutiva.negocio.Fonte;
import br.org.flem.baprodutiva.negocio.TipoFonte;
import java.util.Date;
import java.util.List;

/**
*
* Esta classe possui regras de negócios, referentes a entidade Fonte
*
* @author Gerador de Código da FLEM
*
*/
public class FonteBO {

        FonteDAO dao;

        public FonteBO() throws AcessoDadosException {

                dao = new FonteDAO();
        }

        public void inserirOuAlterar(Fonte objeto) throws AcessoDadosException {
                dao.inserirOuAlterar(objeto);
        }

        public void inserir(Fonte objeto) throws AcessoDadosException {
                dao.inserir(objeto);
        }

        public void alterar(Fonte objeto) throws AcessoDadosException {
                dao.alterar(objeto);
        }

        public void excluir(Fonte objeto) throws AcessoDadosException {
                dao.excluir(objeto);
        }

        public void excluir(Collection<Fonte> objetos) throws AcessoDadosException {
                dao.excluir(objetos);
        }

        public Fonte obterPorPk(Fonte objeto) throws AcessoDadosException {
                return dao.obterPorPk(objeto);
        }

        public Fonte obterPorPk(Integer id) throws AcessoDadosException {
                return dao.obterPorPk(id);
        }

        public Collection<Fonte> obterTodos() throws AcessoDadosException {
                return dao.obterTodos();
        }
        
        public List<Fonte> obterFontesNoPeriodo(TipoFonte tipoFonte, Date inicio, Date fim) {
                return dao.obterFontesNoPeriodo(tipoFonte, inicio, fim);
        }
}