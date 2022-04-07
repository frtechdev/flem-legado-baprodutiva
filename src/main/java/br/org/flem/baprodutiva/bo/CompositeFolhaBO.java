package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.dao.CompositeFolhaDAO;
import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.CompositeFolha;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.baprodutiva.negocio.SubCategoria;
import br.org.flem.fwe.exception.AcessoDadosException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * Esta classe possui regras de negócios, referentes a entidade Componente
 *
 * @author Gerador de Código da FLEM
 *
 */
public class CompositeFolhaBO {

    CompositeFolhaDAO dao;

    public CompositeFolhaBO() throws AcessoDadosException {

        dao = new CompositeFolhaDAO();
    }
    public void inserirOuAlterar(CompositeFolha objeto) throws AcessoDadosException {
        dao.inserirOuAlterar(objeto);
    }
    public void inserir(CompositeFolha objeto) throws AcessoDadosException {
          dao.inserir(objeto);
    }
    public void alterar(CompositeFolha objeto) throws AcessoDadosException {
        dao.alterar(objeto);
    }
    public void excluir(CompositeFolha objeto) throws AcessoDadosException {
        dao.excluir(objeto);


    }
    public void excluir(Collection<CompositeFolha> objetos) throws AcessoDadosException {
        dao.excluir(objetos);
    }
    public void excluirOrgaoResponsavelAssociado(CompositeFolha objeto) {
        dao.excluirOrgaoResponsavelAssociado(objeto);
    }
    public void excluirEntidadeExecutoraAssociada(CompositeFolha objeto) {
        dao.excluirEntidadeExecutoraAssociada(objeto);
    }    
    public CompositeFolha obterPorPk(CompositeFolha objeto) throws AcessoDadosException {
        return dao.obterPorPk(objeto);
    }
    public CompositeFolha obterPorPk(Integer id) throws AcessoDadosException {
        return dao.obterPorPk(id);
    }
    public Collection<CompositeFolha> obterTodos() throws AcessoDadosException {
        return dao.obterTodos();
    }
    public Collection<CompositeFolha> obterTodosOrdenadoPorCampo(String nomeCampo) throws AcessoDadosException {
        return dao.obterTodosOrdenadoPorCampo(nomeCampo);
    }    
    public List<CompositeFolha> obterPorAno(Integer ano) {
        return dao.obterPorAno(ano);
    }
    public List<CompositeFolha> obterPorPlanejamento(Planejamento planejamento){
        return dao.obterPorPlanejamento(planejamento);
    }
    public Collection<CompositeFolha> obterPorFiltro(CompositeFolha objeto) throws AcessoDadosException {
        return dao.obterPorFiltro(objeto);
    }
    public List<CompositeFolha> obterPorCategoriaPlanejamentoSubcategoria(Planejamento planejamento, Categoria categoria, SubCategoria subcategoria){
        return dao.obterPorCategoriaPlanejamentoSubcategoria(planejamento, categoria, subcategoria);
    }  
    /**
     * Retorna todos os centros de custos que jÃ¡ foram associados a Subatividades.
     * @return Retorna um Set<String> com os centros de custo associados.
     * @throws br.org.flem.fwe.exception.AcessoDadosException
     */
     public Set<String> obterTodosCentrosCustoPorPlanejamento(Planejamento planejamento) throws AcessoDadosException {
        Set<String> retorno = new HashSet<String>();
        Collection<CompositeFolha> subAtividades = dao.obterPorPlanejamento(planejamento);
        
        for (CompositeFolha subAtividade : subAtividades) {
            retorno.addAll(subAtividade.getCentrosCusto());
        }
        
        return retorno;
    }
     
     public Set<String> obterTodosCentrosCustoPorCategoriaPlanejamento(Categoria categoria, Planejamento planejamento){
     
         Set <String> retorno = new HashSet<String>();
         Collection<CompositeFolha> subatividades = dao.obterPorCategoria(categoria);
         for(CompositeFolha subatividade : subatividades){
             retorno.addAll(subatividade.getCentrosCusto());
         }
         return retorno;
     }
     
    public Set<String> obterTodosCentrosCustoPorCategoria(Categoria categoria) throws AcessoDadosException {
        Set<String> retorno = new HashSet<String>();
        Collection<CompositeFolha> subAtividades = dao.obterPorCategoria(categoria);
        for (CompositeFolha subAtividade : subAtividades) {
            retorno.addAll(subAtividade.getCentrosCusto());
        }
        
        return retorno;
    }
    
    public Set<String> obterCentrosCustoPorSubCategoria(SubCategoria subCategoria) throws AcessoDadosException {
        Set<String> retorno = new HashSet<String>();
        Collection<CompositeFolha> subAtividades = dao.obterPorSubCategoria(subCategoria);
        for (CompositeFolha subAtividade : subAtividades) {
            retorno.addAll(subAtividade.getCentrosCusto());
        }
        
        return retorno;
    }
    public Collection<String> obterTodosCentrosCustoCompartilhados() {
        //return dao.obterCentrosCustoPorCategoria(categoria);
        Set<String> retorno = new HashSet<String>();
        
        List<CompositeFolha> subAtividades = dao.obterTodosComCCCompartilhado();
        
        for(CompositeFolha subAtividade : subAtividades) {
            retorno.addAll(subAtividade.getCentrosCusto());
        }
        
        return retorno;
    }
    /**
     * Esse método retorna todas as subatividades que possuem o check ccCompartilhado marcado, que possuem ou não centros de custo associado.
     * @return
     */
    public Collection<CompositeFolha> obterTodosComCCCompartilhado() {
        return dao.obterTodosComCCCompartilhado();
    }
    /**
     * Esse método retorna todas as subatividades que possuem o check ccCompartilhado marcado e que tem alguem centro de custo associado.
     * @return
     */
    public static void main(String args[]) throws AcessoDadosException {
        Categoria cat = new Categoria();
        cat.setId(1);

        //System.out.println(new CompositeFolhaBO().obterCentrosCustoPorCategoria(cat));
        
    }
    
    public List<CompositeFolha> obterPorSubcategoria(SubCategoria subCategoria) {
        return dao.obterPorCategoriaPlanejamentoSubcategoria( null, null, subCategoria);
    }

    public Collection<String> obterCentrosCustoPorSubcomponente(SubCategoria subCategoria) {
       
        Set<String> retorno = new HashSet<String>();
        
        List<CompositeFolha> subAtividades = dao.obterPorCategoriaPlanejamentoSubcategoria(null, null, subCategoria);
        
        for(CompositeFolha subAtividade : subAtividades) {
            retorno.addAll(subAtividade.getCentrosCusto());
        }
        
        return retorno;
    }

    public List<CompositeFolha> obterPorPlanejamentoCategoriaComponenteSubcomponente(Planejamento planejamento, Categoria categoria, SubCategoria subCategoria) {
          return dao.obterPorCategoriaPlanejamentoSubcategoria(planejamento, categoria, subCategoria);
    }
    
    /**
     * Retorna todos os centros de custos que jÃ¡ foram associados a Subatividades.
     * @return Retorna um Set<String> com os centros de custo associados.
     * @throws br.org.flem.fwe.exception.AcessoDadosException
     */
    public Set<String> obterTodosCentrosCusto() throws AcessoDadosException {
        Set<String> retorno = new HashSet<String>();
        Collection<CompositeFolha> subAtividades = dao.obterTodos();
        
        for (CompositeFolha subAtividade : subAtividades) {
            retorno.addAll(subAtividade.getCentrosCusto());
        }
        
        return retorno;
    }
}
