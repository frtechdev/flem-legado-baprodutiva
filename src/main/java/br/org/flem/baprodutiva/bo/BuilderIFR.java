/*


 */

package br.org.flem.baprodutiva.bo;

import br.org.flem.fw.persistencia.dto.Compromisso;
import br.org.flem.baprodutiva.negocio.DespesaOrdenada;
import br.org.flem.baprodutiva.negocio.InternalizacaoAplicacaoFinanceira;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author mjpereira
 * --
 */
public interface BuilderIFR {

    public void setDataInicio(Date dataInicio);
    public void setDataFim(Date dataFim);
    public Date getDataFim();
    public Date getDataInicio();
    
    public void adicionaListaCompromissos(Collection<Compromisso> compromissos);
    public List<SoeDTO> resultado();
    public void adicionaAplicacoesFinanceiras(Collection<InternalizacaoAplicacaoFinanceira> aplicacoesFinanceiras);
    public void adicionaListaCentrosCusto(Collection<String> centrosCusto);
    public void adicionaListaDespesasOrdenadas(Collection<DespesaOrdenada> despesasOrdenadas);
}
