package br.org.flem.baprodutiva.dao;

import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.CompositeFolha;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.baprodutiva.negocio.SubCategoria;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

/**
 *
 * Esta classe possui a lógica de acesso a dados, relativo a entidade Categoria
 *
 * @author Gerador de Código da FLEM
 *
 */
public class CompositeFolhaDAO extends BaseDAOAb<CompositeFolha> {

    public CompositeFolhaDAO() throws AcessoDadosException {
    }

    @Override
    protected Class getClasseDto() {
        return CompositeFolha.class;
    }

    public List<CompositeFolha> obterPorCategoria(Categoria categoria) {
        Criteria compositeFolha = session.createCriteria(CompositeFolha.class);

        compositeFolha.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        compositeFolha.add(Expression.eq("categoria", categoria));

        return compositeFolha.list();
    }

    public void excluirOrgaoResponsavelAssociado(CompositeFolha objeto) {

        String sql = "delete from OrgaoResponsavel_CompositeFolha "
                + "where compositeFolhas_id_composite = :subatividade";

        SQLQuery query = session.createSQLQuery(sql);

        query.setInteger("subatividade", objeto.getId());

        query.executeUpdate();
    }

    public List<CompositeFolha> obterTodosComCCCompartilhado() {

        Criteria compositeFolha = session.createCriteria(CompositeFolha.class);

        compositeFolha.add(Expression.eq("ccCompartilhado", true));

        return compositeFolha.list();
    }

    public void excluirEntidadeExecutoraAssociada(CompositeFolha objeto) {

        String sql = "delete from EntidadeExecutora_CompositeFolha "
                + "where compositeFolhas_id_composite = :subatividade";

        SQLQuery query = session.createSQLQuery(sql);

        query.setInteger("subatividade", objeto.getId());

        query.executeUpdate();
        
    }

    public List<CompositeFolha> obterPorAno(Integer ano) {

        Criteria compositeFolha = session.createCriteria(CompositeFolha.class);

        compositeFolha.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (ano != null) {
            compositeFolha.add(Expression.eq("anoOcorrencia", ano));
        }

        Criteria compositeNoAtividade = compositeFolha.createCriteria("pai");

        return compositeNoAtividade.list();

    }
    
    public List<CompositeFolha> obterPorPlanejamento(Planejamento planejamento) {
        Criteria  criteria = session.createCriteria(CompositeFolha.class);
        criteria.add(Restrictions.eq("planejamento", planejamento));
        return criteria.list();
    }
    
  public CompositeFolha obterPorFinanceiroPrevito(int f){

     return (CompositeFolha) session.createQuery("from CompositeFolha c where c.id_composite = :id_composite").setInteger("id_composite", f).uniqueResult(); 
  }
    
    public List<CompositeFolha> obterPorCategoriaPlanejamentoSubcategoria(Planejamento planejamento, Categoria categoria, SubCategoria subcategoria){
        


        Criteria criteria = session.createCriteria(CompositeFolha.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        
        if (planejamento != null) {
            criteria.add(Restrictions.eq("planejamento", planejamento));
        }
        
        if(categoria != null){
            criteria.add(Restrictions.eq("categoria", categoria));
        }
        
        if (subcategoria != null){
            criteria.add(Restrictions.eq("subcategoria", subcategoria));
        }
     


        return criteria.list();
        
    }
    
    public List<CompositeFolha> obterPorSubCategoria(SubCategoria subCategoria) {
//        Criteria compositeFolha = session.createCriteria(CompositeFolha.class);
        Query compositeFolha = session.createQuery("from CompositeFolha c where c.subcategoria.id = :id_subcategoria");
        compositeFolha.setInteger("id_subcategoria", subCategoria.getId());
        
        return compositeFolha.list(); 
        //return compositeFolha.list();
    }
    
//    public List<CompositeFolha> obterPorSubCategoria(SubCategoria subCategoria) {
//        Criteria compositeFolha = session.createCriteria(CompositeFolha.class);
//
//        compositeFolha.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//
//        compositeFolha.add(Expression.eq("subcategoria", subCategoria));
//
//        return compositeFolha.list();
//    }

}
