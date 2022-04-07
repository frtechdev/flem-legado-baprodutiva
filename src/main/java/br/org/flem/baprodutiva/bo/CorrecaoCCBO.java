package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.baprodutiva.dao.CorrecaoCCDAO;
import br.org.flem.baprodutiva.negocio.CorrecaoCC;

/**
 *
 * @author essantos
 */
public class CorrecaoCCBO extends BaseBOAb<CorrecaoCC> {

    public CorrecaoCCBO() throws AplicacaoException {
        super(new CorrecaoCCDAO());
    }

    public CorrecaoCC obterPorTipoIdSeqLinha(String apdTp, String apdId, String seqLinha) {
        return ((CorrecaoCCDAO) dao).obterPorTipoIdSeqLinha(apdTp, apdId, seqLinha);
    }

}
