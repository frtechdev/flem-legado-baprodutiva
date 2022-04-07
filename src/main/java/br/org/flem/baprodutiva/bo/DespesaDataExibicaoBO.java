package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.baprodutiva.dao.DespesaDataExibicaoDAO;
import br.org.flem.baprodutiva.negocio.DespesaDataExibicao;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import java.util.Collection;

/**
 *
 * @author essantos
 */
public class DespesaDataExibicaoBO extends BaseBOAb<DespesaDataExibicao> {

    public DespesaDataExibicaoBO() throws AplicacaoException {
        super(new DespesaDataExibicaoDAO());
    }

    public DespesaDataExibicao obterPorTipoIdSeqLinha(String apdTp, String apdId, String seqLinha) {
        return ((DespesaDataExibicaoDAO) dao).obterPorTipoIdSeqLinha(apdTp, apdId, seqLinha);
    }

}
