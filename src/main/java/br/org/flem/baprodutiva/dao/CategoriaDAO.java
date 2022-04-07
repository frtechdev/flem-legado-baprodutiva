package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.Origem;
import java.util.Collection;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
*
* Esta classe possui a lógica de acesso a dados, relativo a entidade Categoria
*
* @author Gerador de Código da FLEM
*
*/
public class CategoriaDAO extends BaseDAOAb<Categoria> {

        public CategoriaDAO() throws AcessoDadosException {}

        protected Class getClasseDto() {
                return Categoria.class;
        }
        
        @Override
        protected void adicionarAgregacoesCriteria(Criteria c, Categoria objeto) {

        }
        
        public Collection<Categoria> obterTodosOrigemFLEM() throws AcessoDadosException {
            Criteria criteria = session.createCriteria(getClasseDto());
            criteria.add(Restrictions.eq("origem", Origem.FLEM));
            return criteria.list();
        }

}