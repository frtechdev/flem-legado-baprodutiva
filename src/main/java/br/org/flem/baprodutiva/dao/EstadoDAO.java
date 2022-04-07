/*
 * FrequenciaDAO.java
 * 
 * Created on 23/10/2007, 15:46:19
 * 


 */
package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.Fonte;

/**
 *
 * @author dbbarreto
 */
public class EstadoDAO extends BaseDAOAb<Fonte> {

    public EstadoDAO() throws AcessoDadosException {
    }

    protected Class getClasseDto() {
        return Fonte.class;
    }

}