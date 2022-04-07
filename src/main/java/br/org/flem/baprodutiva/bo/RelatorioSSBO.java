package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.baprodutiva.bo.util.OrganizadorLancamentosBO;
import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.DespesaDataExibicao;
import br.org.flem.baprodutiva.negocio.Pedido;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author essantos
 */
public class RelatorioSSBO {

    public Collection<SoeDTO> geraDados(Collection<DespesaDataExibicao> despesasProblematicas, Categoria categoria, Pedido pedido) throws IOException, AcessoDadosException {
        BuilderSOE builder = new SS();

        try {

            builder.setPedido(pedido);
            builder.adicionaListaCompromissos(OrganizadorLancamentosBO.getInstancia().obterCompromissos());
            builder.adicionaListaDevolucoes(OrganizadorLancamentosBO.getInstancia().obterDevolucoes());
            builder.adicionaListaAvulsos(OrganizadorLancamentosBO.getInstancia().obterAvulsos());
          //  builder.adicionaListaCentrosCusto(new CompositeFolhaBO().obterCentrosCustoPorCategoria(categoria));
            builder.adicionaListaInternalizacoes(new InternalizacoesBO().obterTodosTiposInternalizacaoAgrupadasPorTaxa());

        } catch (Exception ex) {
            Logger.getLogger(RelatorioSSBO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return builder.resultado();
    }
}
