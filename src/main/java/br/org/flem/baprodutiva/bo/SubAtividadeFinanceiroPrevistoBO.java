package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.dto.PlanejamentoFinanceiroDTO;
import br.org.flem.baprodutiva.negocio.FinanceiroPrevisto;
import br.org.flem.fwe.exception.AcessoDadosException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author essantos
 */
public class SubAtividadeFinanceiroPrevistoBO {

    public List<PlanejamentoFinanceiroDTO> converterLista(Collection<FinanceiroPrevisto> lista) throws AcessoDadosException {
        List<PlanejamentoFinanceiroDTO> listaConvertida = new ArrayList<PlanejamentoFinanceiroDTO>();

        for (FinanceiroPrevisto financeiro : lista) {
            PlanejamentoFinanceiroDTO planejamento = new PlanejamentoFinanceiroDTO();

            planejamento.setId(financeiro.getPlanejamento().getId().toString());
            planejamento.setPlanejamento(financeiro.getPlanejamento().getDescricao());
            planejamento.setValorGef(financeiro.getValor());

            listaConvertida.add(planejamento);
        }

        return calculaTotais(listaConvertida);
    }

    private List<PlanejamentoFinanceiroDTO> calculaTotais(List<PlanejamentoFinanceiroDTO> listaConvertida) {

        List<PlanejamentoFinanceiroDTO> listaTotais = new ArrayList<PlanejamentoFinanceiroDTO>();

        // para que o último poa seja computado corretamente
        listaConvertida.add(new PlanejamentoFinanceiroDTO());

        PlanejamentoFinanceiroDTO ultimo = null;
        for (PlanejamentoFinanceiroDTO periodo : listaConvertida) {
            if (ultimo != null && (!ultimo.getPlanejamento().equals(periodo.getPlanejamento()))) {
                PlanejamentoFinanceiroDTO total = new PlanejamentoFinanceiroDTO();

                total.setId(ultimo.getId());
                total.setPlanejamento(ultimo.getPlanejamento());
                //total.setPeriodo("Totalização de todos períodos");
                total.setValorGef(0d);

                for (PlanejamentoFinanceiroDTO item : listaConvertida) {
                    if ((ultimo.getPlanejamento().equals(item.getPlanejamento()))) {
                        total.setValorGef(total.getValorGef() + item.getValorGef());
                    }
                }

                listaTotais.add(total);
            }
            ultimo = periodo;
        }

        Collections.sort(listaTotais, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                PlanejamentoFinanceiroDTO fin1 = (PlanejamentoFinanceiroDTO) o1;
                PlanejamentoFinanceiroDTO fin2 = (PlanejamentoFinanceiroDTO) o2;
                return fin1.getPlanejamento().compareTo(fin2.getPlanejamento());
            }
        });

        return listaTotais;
    }
}
