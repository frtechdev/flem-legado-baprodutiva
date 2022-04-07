/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.Aditivo;
import br.org.flem.baprodutiva.negocio.Contrato;
import java.util.Collection;


/**
 *
 * @author ilfernandes
 */

public class AditivoDAO extends BaseDAOAb<Aditivo> {
    
    public AditivoDAO() throws AcessoDadosException {
    }
    
    @Override
    protected Class<Aditivo> getClasseDto() {
        return Aditivo.class;
    }
    
     public Collection<Aditivo> obterPorContrato(Contrato contrato) {
        return session.createQuery("from Aditivo where contrato = :contrato").setEntity("contrato", contrato).list();
    }
}
