package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.negocio.CompositeFolha;
import br.org.flem.baprodutiva.negocio.EntidadeExecutora;
import br.org.flem.baprodutiva.negocio.FinanceiroPrevisto;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.baprodutiva.relatorio.PlanejamentoDTO;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 * Esta classe contém as regras da criação do Relatório de Planejamento
 * 
 * @author essantos
 */
public class RelatorioPlanejamentoBO {

    public Collection<PlanejamentoDTO> geraDados(Planejamento planejamento) throws AplicacaoException, ParseException, IOException {
        List<PlanejamentoDTO> retorno = new ArrayList<PlanejamentoDTO>();

        for(CompositeFolha subAtividade : new CompositeFolhaBO().obterPorCategoriaPlanejamentoSubcategoria(planejamento, null, null)){
            PlanejamentoDTO dto = new PlanejamentoDTO();

            dto.setCategoria(subAtividade.getCategoria().getDescricao());
            dto.setSubcategoria(subAtividade.getSubCategoria().getDescricao());
            dto.setAtividade(subAtividade.getDescricao());
            StringBuilder strBuilder = new StringBuilder();
            if(subAtividade.getEntidadesExecutoras() != null && subAtividade.getEntidadesExecutoras().size() > 0){
                for(EntidadeExecutora entidade : subAtividade.getEntidadesExecutoras()){
                    strBuilder.append(entidade.getDescricao()).append("/");
                }
                strBuilder.deleteCharAt(strBuilder.length()-1);
                dto.setEntidadesExecutoras(strBuilder.toString());
            }

            this.calculaPrevisto(subAtividade, dto, planejamento.getDataInicial(), planejamento.getDataFinal());

            retorno.add(dto);
        }

        Collections.sort(retorno, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                PlanejamentoDTO comp1 = (PlanejamentoDTO) o1;
                PlanejamentoDTO comp2 = (PlanejamentoDTO) o2;
                int comparacao = comp1.getCategoria().compareTo(comp2.getCategoria());
                if (comparacao == 0) {
                    comparacao = comp1.getSubcategoria().compareTo(comp2.getSubcategoria());
                    if (comparacao == 0) {
                        comparacao = comp1.getAtividade().compareTo(comp2.getAtividade());
                    }
                }
                return comparacao;
            }
        });

        return removeBrancos(retorno);
    }

    private List<PlanejamentoDTO> removeBrancos(List<PlanejamentoDTO> retorno){
        List<PlanejamentoDTO> corrigida = new ArrayList<PlanejamentoDTO>();
        String subatividadeAnterior = "";

        for (PlanejamentoDTO planejamento : retorno){
            if (planejamento.getValor() == 0){
                continue;
            }
            if (subatividadeAnterior.equals(planejamento.getSubcategoria() + planejamento.getAtividade())){
                planejamento.setMostrar(false);
                planejamento.setValor(0D);
            }
            corrigida.add(planejamento);
            subatividadeAnterior = planejamento.getSubcategoria() + planejamento.getAtividade();
        }

        return corrigida;
    }

    private void calculaPrevisto(CompositeFolha subAtividade,PlanejamentoDTO dto, Date inicio, Date fim) throws AcessoDadosException {

        Double valorGEF = 0d;
        Collection<FinanceiroPrevisto> previstos = new FinanceiroPrevistoBO().obterFinanceiroNoPeriodo(subAtividade, inicio, fim);

        for (FinanceiroPrevisto previsto : previstos) {
            if (!(previsto.getComposite() instanceof CompositeFolha && previsto.getSubatividade().getId().equals(subAtividade.getId()))) {
                continue;
            }
            valorGEF += previsto.getValor();
        }

        
        dto.setValor(valorGEF);
    }
}
