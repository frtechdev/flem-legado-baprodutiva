package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fw.persistencia.dto.Compromisso;
import br.org.flem.baprodutiva.dao.DespesaAplicacaoFinanceiraDAO;
import br.org.flem.baprodutiva.negocio.DespesaAplicacaoFinanceira;
import br.org.flem.baprodutiva.negocio.LoteDespesaAplicacao;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 *
 * Esta classe possui regras de negócios, referentes a entidade Categoria
 *
 * @author Gerador de Código da FLEM
 *
 */
public class DespesaAplicacaoFinanceiraBO extends BaseBOAb<DespesaAplicacaoFinanceira> {

    public DespesaAplicacaoFinanceiraBO() throws AplicacaoException {
        super(new DespesaAplicacaoFinanceiraDAO());
    }

    public Collection<Compromisso> obterCompromissos() throws AcessoDadosException {
        Collection<DespesaAplicacaoFinanceira> despesas = dao.obterTodos();

        Collection<Compromisso> compromissos = new ArrayList<Compromisso>();

        for (DespesaAplicacaoFinanceira despesa : despesas) {
            Compromisso compromisso = new Compromisso();

            compromisso.setApdId(despesa.getApdId());
            compromisso.setApdTp(despesa.getApdTp());
            compromisso.setData(despesa.getEntrada());
            compromisso.setDescricao(despesa.getDescricao());
            compromisso.setValor(BigDecimal.valueOf(despesa.getValor()));

            compromissos.add(compromisso);
        }
        return compromissos;
    }

    public Collection<DespesaAplicacaoFinanceira> obterPorPeriodo(Date inicio, Date fim) {
        return ((DespesaAplicacaoFinanceiraDAO) this.dao).obterPorPeriodo(inicio, fim);
    }
}
