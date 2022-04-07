package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fw.persistencia.dto.Compromisso;
import br.org.flem.baprodutiva.dao.DespesaOrdenadaDAO;
import br.org.flem.baprodutiva.negocio.DespesaOrdenada;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author mccosta
 */
public class DespesaOrdenadaBO extends BaseBOAb<DespesaOrdenada> {

    public DespesaOrdenadaBO() throws AplicacaoException {
        super(new DespesaOrdenadaDAO());
    }

    public Collection<Compromisso> obterCompromissos() throws AplicacaoException {
        Collection<DespesaOrdenada> despesas = dao.obterTodos();

        Collection<Compromisso> compromissos = new ArrayList<Compromisso>();

        for (DespesaOrdenada despesa : despesas) {
            Compromisso compromisso = new Compromisso();

            compromisso.setApdId(despesa.getApdId());
            compromisso.setApdTp(despesa.getApdTp());
            compromisso.setSeqLinha(despesa.getSeqLinha());
            compromisso.setOrdem(despesa.getOrdem());

            compromissos.add(compromisso);
        }
        return compromissos;
    }

    public DespesaOrdenada obterPorApdIdEApdTPESeqLinha(String apdId, String apdTp, String seqLinha) {
        return ((DespesaOrdenadaDAO) dao).obterPorApdIdEApdTPESeqLinha(apdId, apdTp, seqLinha);
    }
}
