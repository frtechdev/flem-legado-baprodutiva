package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fw.service.GEM;
import br.org.flem.fw.service.impl.GEMImpl;
import java.util.Collection;
import br.org.flem.baprodutiva.dao.ReceitaDAO;
import br.org.flem.baprodutiva.negocio.Receita;

/**
*
* Esta classe possui regras de negócios, referentes a entidade Receita
*
* @author Gerador de Código da FLEM
*
*/
public class ReceitaBO {

        ReceitaDAO dao;

        public ReceitaBO() throws AcessoDadosException {

                dao = new ReceitaDAO();
        }

        public void inserirOuAlterar(Receita objeto) throws AcessoDadosException {
                dao.inserirOuAlterar(objeto);
        }

        public void inserir(Receita objeto) throws AcessoDadosException {
                dao.inserir(objeto);
        }

        public void alterar(Receita objeto) throws AcessoDadosException {
                dao.alterar(objeto);
        }

        public void excluir(Receita objeto) throws AcessoDadosException {
                dao.excluir(objeto);
        }

        public void excluir(Collection<Receita> objetos) throws AcessoDadosException {
                dao.excluir(objetos);
        }

        public Receita obterPorPk(Receita objeto) throws AcessoDadosException {
                return dao.obterPorPk(objeto);
        }

        public Receita obterPorPk(Integer id) throws AcessoDadosException {
                return dao.obterPorPk(id);
        }

        public Collection<Receita> obterTodos() throws AcessoDadosException {
                return dao.obterTodos();
        }
        
        public Collection<Receita> obterPorFiltro(Receita objeto) throws AcessoDadosException {
                return dao.obterPorFiltro(objeto);
        }
        
        public Collection<br.org.flem.fw.persistencia.dto.Receita> listaReceitasGEM() throws AcessoDadosException {
            String conta = "44118";    
            String centroCusto = "20251"; 
            GEM gem = new GEMImpl();
            return gem.obterReceitaPorCentroCustoContaReceita(centroCusto, conta);
                    
        }
        
}