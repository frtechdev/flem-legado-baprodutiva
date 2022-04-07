package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.baprodutiva.dao.CorrecaoValorDAO;
import br.org.flem.baprodutiva.dao.DespesaDataExibicaoDAO;
import br.org.flem.baprodutiva.negocio.CorrecaoValor;

/**
 *
 * @author essantos
 */
public class CorrecaoValorBO extends BaseBOAb<CorrecaoValor> {

    public CorrecaoValorBO() throws AplicacaoException {
        super(new CorrecaoValorDAO());
    }

    public CorrecaoValor obterPorTipoIdSeqLinha(String apdTp, String apdId, String seqLinha) {
        return ((CorrecaoValorDAO) dao).obterPorTipoIdSeqLinha(apdTp, apdId, seqLinha);
    }

}
