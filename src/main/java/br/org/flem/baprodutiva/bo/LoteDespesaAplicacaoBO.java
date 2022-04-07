/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.baprodutiva.negocio.LoteDespesaAplicacao;
import br.org.flem.baprodutiva.dao.LoteDespesaAplicacaoDAO;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ilfernandes
 */
public class LoteDespesaAplicacaoBO extends BaseBOAb<LoteDespesaAplicacao> {

    public LoteDespesaAplicacaoBO() throws AplicacaoException {
        super(new LoteDespesaAplicacaoDAO());
    }

    public Set<String> obterSetCodigos() throws AplicacaoException {
        Set<String> codigos = new HashSet<String>();
        Collection<LoteDespesaAplicacao> lista = this.obterTodos();
        for (LoteDespesaAplicacao lote : lista) {
            codigos.add(lote.getIdDespesa() + lote.getTipo() + lote.getSeqLinha());
        }
        return codigos;
    }

    public Collection<LoteDespesaAplicacao> obterPorPeriodo(Date inicio, Date fim) {
        return ((LoteDespesaAplicacaoDAO) this.dao).obterPorPeriodo(inicio, fim);
    }

    public LoteDespesaAplicacao obterPorIdDespesaAndSeqLinha(String idDespesa, String seqLinha) {
        List<LoteDespesaAplicacao> lista = ((LoteDespesaAplicacaoDAO) this.dao).obterPorIdDespesaAndSeqLinha(idDespesa, seqLinha);
        if (seqLinha != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }
    /**
     * 
     * @param idDespesa
     * @param seqLinha
     * @param tipo
     * @return 
     */
    public LoteDespesaAplicacao obterPorIdDespesaSeqLinhaAndTipo(String idDespesa, String seqLinha, String tipo) {
        return ((LoteDespesaAplicacaoDAO) this.dao).obterPorIdDespesaSeqLinhaAndTipo(idDespesa, seqLinha, tipo);
    }
}
