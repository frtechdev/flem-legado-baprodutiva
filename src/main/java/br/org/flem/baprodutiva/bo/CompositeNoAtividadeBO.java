package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.baprodutiva.dao.CompositeNoAtividadeDAO;
import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.CompositeNoAtividade;

import java.util.Collection;
import java.util.List;

/**
 *
 * Esta classe possui regras de neg�cios, referentes a entidade Componente
 *
 * @author Gerador de C�digo da FLEM
 *
 */
public class CompositeNoAtividadeBO {

    CompositeNoAtividadeDAO dao;

    public CompositeNoAtividadeBO() throws AcessoDadosException {

        dao = new CompositeNoAtividadeDAO();
    }

    public void inserirOuAlterar(CompositeNoAtividade objeto) throws AcessoDadosException {
        dao.inserirOuAlterar(objeto);
    }

    public void inserir(CompositeNoAtividade objeto) throws AcessoDadosException {
        dao.inserir(objeto);
    }

    public void alterar(CompositeNoAtividade objeto) throws AcessoDadosException {
        dao.alterar(objeto);
    }

    public void excluir(CompositeNoAtividade objeto) throws AcessoDadosException {
        dao.excluir(objeto);
    }

    public void excluir(Collection<CompositeNoAtividade> objetos) throws AcessoDadosException {
        dao.excluir(objetos);
    }

    public CompositeNoAtividade obterPorPk(CompositeNoAtividade objeto) throws AcessoDadosException {
        return dao.obterPorPk(objeto);
    }

    public CompositeNoAtividade obterPorPk(Integer id) throws AcessoDadosException {
        return dao.obterPorPk(id);
    }

    public Collection<CompositeNoAtividade> obterTodos() throws AcessoDadosException {
        return dao.obterTodos();
    }
    
    public Collection<CompositeNoAtividade> obterTodosOrdenadoPorCampo(String nomeCampo) throws AcessoDadosException {
        return dao.obterTodosOrdenadoPorCampo(nomeCampo);
    }  
    
    public Collection<CompositeNoAtividade> obterTodosOrdenadoPorCampo(String[] campos) throws AcessoDadosException {
        return dao.obterTodosOrdenadoPorCampo(campos);
    }

    public Collection<CompositeNoAtividade> obterPorFiltro(CompositeNoAtividade objeto) throws AcessoDadosException {
        return dao.obterPorFiltro(objeto);
    }



    /**
     * Verifica se a Atividade (CompositeNoAtividade) passada por parâmetro já 
     * existe no Banco de dados. Retorna true se já exitir e false em caso contrátrio.
     * @param obj
     * @return
     * @throws br.org.flem.fwe.exception.AcessoDadosException
     */
    public Boolean existeAtividade(CompositeNoAtividade obj) throws AcessoDadosException {
        if ( this.obterPorFiltro(obj).size() > 0 )
            return true;
        return false;
    }
    
    /* MÉTODO PARA REALIZAÇÃO DE TESTES
    public static void main(String[] args) throws AcessoDadosException {

        CompositeNoAtividade atividade = new CompositeNoAtividade();
        
        Collection<CompositeNoAtividade> atividades = null;
        
        CompositeNoAtividadeBO atividadeBO = new CompositeNoAtividadeBO();

       
        atividade.setDescricao("1.1. Estrutura Participativa Legal e Pol�tica para Apoio Institucional à Gest�o Integrada do Ecossistema");
        atividade.setCategoria( new CategoriaBO().obterPorPk(1) );

        
        atividades = new CompositeNoAtividadeBO().obterPorFiltro(atividade);

        System.out.println("existe: " + atividades.size() );
            
        for ( CompositeNoAtividade c : atividades ) {
            System.out.println("Desc: " + c.getDescricao());
        }        
    }    
     */
}
