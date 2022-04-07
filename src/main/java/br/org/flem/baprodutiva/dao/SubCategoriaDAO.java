/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.dao;


import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.Origem;
import br.org.flem.baprodutiva.negocio.SubCategoria;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import java.util.Collection;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author AJLima
 */
public class SubCategoriaDAO extends BaseDAOAb<SubCategoria>{     
    

    public SubCategoriaDAO() throws AcessoDadosException {}

        protected Class getClasseDto() {
                return SubCategoria.class;
        }


        

}
