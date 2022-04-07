package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fw.persistencia.dto.Pagamento;
import br.org.flem.fw.service.GEM;
import br.org.flem.fw.service.impl.GEMImpl;
import br.org.flem.fwe.exception.AcessoDadosException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author fcsilva
 */
public class RelatorioCertificadoGastosBO {

    //TODO informar centro de custo
    public List<Pagamento> geraDados(Planejamento planejamento)
            throws AcessoDadosException, IOException {
        GEM gem = new GEMImpl();
        List<Pagamento> pagamentos = gem.obterPagamentosNoPeriodo
                (planejamento.getDataInicial(), planejamento.getDataFinal(), 
                PropertiesUtil.getProperties().getProperty("projeto"));
        return pagamentos;
    }
}
