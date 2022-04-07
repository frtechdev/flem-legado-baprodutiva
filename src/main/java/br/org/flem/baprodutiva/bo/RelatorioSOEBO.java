package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.bo.util.OrganizadorLancamentosBO;
import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.DespesaDataExibicao;
import br.org.flem.baprodutiva.negocio.Pedido;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author essantos
 */
public class RelatorioSOEBO {

    public Collection<SoeDTO> geraDados(Collection<DespesaDataExibicao> despesasProblematicas, Categoria categoria, Pedido pedido) {

        BuilderSOE builder = new SOE();

        try {

            builder.setPedido(pedido); 
            builder.adicionarListaTarifas(new LoteDespesaAplicacaoBO().obterPorPeriodo(pedido.getInicio(), pedido.getFim()));
            builder.adicionaListaCompromissos(OrganizadorLancamentosBO.getInstancia().obterCompromissos());
            builder.adicionaListaDevolucoes(OrganizadorLancamentosBO.getInstancia().obterDevolucoes());
            builder.adicionaListaAvulsos(OrganizadorLancamentosBO.getInstancia().obterAvulsos());
            builder.adicionaListaInternalizacoes(new InternalizacoesBO().obterTodosTiposInternalizacaoAgrupadasPorTaxa());
            builder.adicionaListaCentrosCusto(new CompositeFolhaBO().obterTodosCentrosCustoPorCategoria(categoria));
            builder.adicionarListaTransferencias(new TransferenciaBancariaBO().obterTodos());
            
                    


        } catch (Exception ex) {
            Logger.getLogger(RelatorioSOEBO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return builder.resultado();
    }
}
