/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.Contrato;
import br.org.flem.baprodutiva.negocio.Parcela;
import java.util.Collection;


/**
 *
 * @author ilfernandes
 */

public class ParcelaDAO extends BaseDAOAb<Parcela> {
    
    public ParcelaDAO() throws AcessoDadosException {
    }
    
    @Override
    protected Class<Parcela> getClasseDto() {
        return Parcela.class;
    }
    
    public Collection<Parcela> obterPorContrato(Contrato contrato) {
        return session.createQuery("from Parcela where contrato = :contrato").setEntity("contrato", contrato).list();
    }
}
