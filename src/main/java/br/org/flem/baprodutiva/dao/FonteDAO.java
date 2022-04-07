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
import br.org.flem.baprodutiva.negocio.TipoFonte;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author dbbarreto
 */
public class FonteDAO extends BaseDAOAb<Fonte> {

    public FonteDAO() throws AcessoDadosException {
    }

    protected Class getClasseDto() {
        return Fonte.class;
    }
    
    public List<Fonte> obterFontesNoPeriodo(TipoFonte tipoFonte, Date dataInicial, Date dataFinal) {
        
        String hql = "select f from Fonte as f join f.tipo as tf " +
                "WHERE tf.id = :id and f.periodo.dataInicial >= :dataInicial and f.periodo.dataFinal <= :dataFinal";
        
        Query query = session.createQuery(hql).setInteger("id", tipoFonte.getId()).
                setDate("dataInicial", dataInicial).setDate("dataFinal", dataFinal);        
        
        return query.list();
    }
}