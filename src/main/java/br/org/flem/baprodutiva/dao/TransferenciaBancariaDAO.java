/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.dao;

import br.org.flem.baprodutiva.negocio.TransferenciaBancaria;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import java.util.Collection;
import java.util.Date;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author AJLima
 */
public class TransferenciaBancariaDAO extends BaseDAOAb<TransferenciaBancaria> {
    
    public TransferenciaBancariaDAO() throws AcessoDadosException {
    }

    @Override
    protected Class<TransferenciaBancaria> getClasseDto() {
        return TransferenciaBancaria.class;
    }
    
    public TransferenciaBancaria obterPorApdIdEApdTp(String apdId, String apdTp){

      return  (TransferenciaBancaria) session.createQuery("from TransferenciaBancaria t where t.apdId =:apdId and t.apdTp = :apdTp ").setString("apdId", apdId).setString("apdTp", apdTp).uniqueResult();
        
    }
    
    public Collection<TransferenciaBancaria> obterTodosOrdemData(){
         
        Criteria transferencias = session.createCriteria(TransferenciaBancaria.class);

        transferencias.addOrder(Order.asc("dataPagamento"));
        
        return transferencias.list();
    }
    
    
    
    public TransferenciaBancaria obterPorTipoId(String apdTp, String apdId) {
        Criteria criteria = this.session.createCriteria(this.getClasseDto());
        criteria.add(Restrictions.eq("apdTp", apdTp));
        criteria.add(Restrictions.eq("apdId", apdId));
        return (TransferenciaBancaria) criteria.uniqueResult();
    }
    

    
    public Collection<TransferenciaBancaria> obterPorPeriodo(Date inicio, Date fim) {

        Criteria loteCriteria = session.createCriteria(TransferenciaBancaria.class);

        loteCriteria.add(Expression.between("dataPagamento", inicio, fim));

        return loteCriteria.list();
    }
    
}
