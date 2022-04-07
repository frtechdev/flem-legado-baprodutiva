package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.DespesaOrdenada;
import org.hibernate.Criteria;

/**
 *
 * @author mccosta
 */
public class DespesaOrdenadaDAO extends BaseDAOAb<DespesaOrdenada> {

    public DespesaOrdenadaDAO() throws AcessoDadosException {
    }

    @Override
    protected Class getClasseDto() {
        return DespesaOrdenada.class;
    }

    @Override
    protected void adicionarAgregacoesCriteria(Criteria c, DespesaOrdenada objeto) {
    }

    public DespesaOrdenada obterPorApdIdEApdTPESeqLinha(String apdId, String apdTp, String seqLinha) {

        return (DespesaOrdenada) session.createQuery("from DespesaOrdenada do where do.apdId =:apdId and do.apdTp = :apdTp and do.seqLinha = :seqLinha").setString("apdId", apdId).setString("apdTp", apdTp).setString("seqLinha", seqLinha).uniqueResult();
    }
}