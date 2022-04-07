package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.negocio.LoteDespesaAplicacao;
import br.org.flem.fw.persistencia.dto.LancamentoInterface;
import br.org.flem.baprodutiva.negocio.Pedido;
import br.org.flem.baprodutiva.negocio.TransferenciaBancaria;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import br.org.flem.baprodutiva.util.IFReceita;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author essantos
 */
public interface BuilderSOE {

    public Pedido getPedido();
    public void setPedido(Pedido pedido);
    public void adicionarListaTarifas(Collection<LoteDespesaAplicacao> tarifas);
    public void adicionaListaInternalizacoes(Collection<IFReceita> internalizacoes);
    public void adicionaListaCompromissos(Collection<LancamentoInterface> compromissos);
    public void adicionaListaCentrosCusto(Collection<String> centrosCusto);
    public void adicionaListaDevolucoes(Collection<LancamentoInterface> devolucoes);
    public void adicionaListaAvulsos(Collection<LancamentoInterface> avulsos);
    public void adicionarListaTransferencias(Collection<TransferenciaBancaria> transferencias);
    public List<SoeDTO> resultado();
    
    
    



}
