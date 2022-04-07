package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.bo.util.OrganizadorLancamentosBO;
import br.org.flem.baprodutiva.negocio.Pedido;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author essantos
 */
public class RelatorioSOEListaoBO {

    public Collection<SoeDTO> geraDados(Date inicio, Date fim) {
        BuilderSOE builder = new SOEListao();
        try {

            Pedido pedido = new Pedido();
            pedido.setInicio(inicio);
            pedido.setFim(fim);

            builder.setPedido(pedido);
            builder.adicionarListaTarifas(new LoteDespesaAplicacaoBO().obterPorPeriodo(pedido.getInicio(), pedido.getFim()));
            builder.adicionaListaCentrosCusto(new CompositeFolhaBO().obterTodosCentrosCusto());
            builder.adicionaListaAvulsos(OrganizadorLancamentosBO.getInstancia().obterAvulsos());
            builder.adicionaListaCompromissos(OrganizadorLancamentosBO.getInstancia().obterCompromissos());
            builder.adicionaListaInternalizacoes(new InternalizacoesBO().obterTodosTiposInternalizacaoAgrupadasPorTaxa());
            builder.adicionaListaDevolucoes(OrganizadorLancamentosBO.getInstancia().obterDevolucoes());
            builder.adicionarListaTransferencias(new TransferenciaBancariaBO().obterTodos());
            
        } catch (Exception ex) {
            Logger.getLogger(RelatorioSOEListaoBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return builder.resultado();
    }

   
}
