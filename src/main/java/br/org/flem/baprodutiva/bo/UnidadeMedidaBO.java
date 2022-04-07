package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.baprodutiva.dao.UnidadeMedidaDAO;
import br.org.flem.baprodutiva.negocio.UnidadeMedida;
import java.util.Collection;
import java.util.List;

/**
 *
 * Esta classe possui regras de negócios, referentes as unidades de medida
 *
 * @author Gerador de Código da FLEM
 *
 */
public class UnidadeMedidaBO extends BaseBOAb<UnidadeMedida> {

    public UnidadeMedidaBO() throws  AplicacaoException {
        super(new UnidadeMedidaDAO());
    }
    
    public Collection<UnidadeMedida> obterTodosOrdenadoPorCampo(String nomeCampo) throws AcessoDadosException {
        return dao.obterTodosOrdenadoPorCampo(nomeCampo);
        
    }    
}
