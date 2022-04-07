package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fw.persistencia.dto.Compromisso;
import br.org.flem.baprodutiva.dao.DespesaInelegivelDAO;
import br.org.flem.baprodutiva.negocio.DespesaInelegivel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * Esta classe possui regras de negócios, referentes a entidade Categoria
 *
 * @author Gerador de Código da FLEM
 *
 */
public class DespesaInelegivelBO extends BaseBOAb<DespesaInelegivel>{
    
    public DespesaInelegivelBO() throws AplicacaoException {
        super(new DespesaInelegivelDAO());
    }
    
    public Collection<Compromisso> obterCompromissos() throws AcessoDadosException {
        Collection<DespesaInelegivel> despesas = dao.obterTodos();
        
        Collection<Compromisso> compromissos = new ArrayList<Compromisso>();
        
        for (DespesaInelegivel despesa : despesas) {
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
}
