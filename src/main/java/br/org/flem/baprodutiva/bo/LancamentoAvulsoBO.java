package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.dao.LancamentoAvulsoDAO;
import br.org.flem.baprodutiva.dto.DevolucaoDTO;
import br.org.flem.baprodutiva.negocio.LancamentoAvulso;
import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author essantos
 */
public class LancamentoAvulsoBO extends BaseBOAb<LancamentoAvulso> {

    public LancamentoAvulsoBO() throws AplicacaoException {
        super(new LancamentoAvulsoDAO());
    }

    public Collection<DevolucaoDTO> obterCompromissos() throws AcessoDadosException {
        Collection<LancamentoAvulso> avulsos = dao.obterTodos();

        Collection<DevolucaoDTO> compromissos = new ArrayList<DevolucaoDTO>();

        for (LancamentoAvulso avulso : avulsos) {
            DevolucaoDTO compromisso = new DevolucaoDTO();

            compromisso.setId(avulso.getId().toString());
            compromisso.setTipo("LANCAVUL");
            compromisso.setApdId(avulso.getId().toString());
            compromisso.setApdTp("LANCAVUL");
            compromisso.setSeqLinha(null);
            compromisso.setDescricao(avulso.getDescricao());
            compromisso.setData(avulso.getDataPagamento());
            compromisso.setEntrada(avulso.getDataPagamento());
            compromisso.setValor(new BigDecimal(avulso.getValorPagamento()));
            compromisso.setNomeFornecedor(avulso.getNomeForn());
            compromisso.setCentroCusto(avulso.getCentroCusto());
            compromisso.setClassificacao(avulso.getCodigoItem());
            compromisso.setNumeroContrato(avulso.getNumeroContrato());

            compromissos.add(compromisso);
        }

        return compromissos;
    }
    
    public Collection<LancamentoAvulso> obterPorPeriodo(Date inicio, Date fim) {
        return ((LancamentoAvulsoDAO)this.dao).obterPorPeriodo(inicio, fim);
    }
}
