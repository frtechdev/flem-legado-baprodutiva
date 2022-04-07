/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.baprodutiva.negocio.Parcela;
import br.org.flem.baprodutiva.dao.ParcelaDAO;
import br.org.flem.baprodutiva.negocio.Contrato;
import java.math.BigDecimal;
import java.util.Collection;

/**
 *
 * @author ilfernandes
 */
public class ParcelaBO extends BaseBOAb<Parcela> {

    public ParcelaBO() throws AplicacaoException {
        super(new ParcelaDAO());
    }

    public Collection<Parcela> obterPorContrato(Contrato contrato) {
        return ((ParcelaDAO) this.dao).obterPorContrato(contrato);
    }

    public static void main(String[] args) {
        BigDecimal teste = new BigDecimal("19580.85");
        System.out.println(teste);
    }
}
