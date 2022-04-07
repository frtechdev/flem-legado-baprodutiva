/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.dao.CorrecaoNomeDAO;
import br.org.flem.baprodutiva.negocio.CorrecaoNome;
import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AplicacaoException;

/**
 *
 * @author AJLima
 */
public class CorrecaoNomeBO extends BaseBOAb<CorrecaoNome> {
    
    public CorrecaoNomeBO() throws AplicacaoException{
    
        super(new CorrecaoNomeDAO());
    }
    
    public CorrecaoNome obterPorTipoIdSeqLinha(String apdTp, String apdId, String seqLinha){
    
        return ((CorrecaoNomeDAO) dao).obterPorTipoIdSeqLinha(apdTp, apdId, seqLinha);
    }
    
}
