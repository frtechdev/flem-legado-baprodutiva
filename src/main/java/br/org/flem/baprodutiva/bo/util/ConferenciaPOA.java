package br.org.flem.baprodutiva.bo.util;

import br.org.flem.baprodutiva.bo.CompositeFolhaBO;
import br.org.flem.baprodutiva.bo.FinanceiroPrevistoBO;
import br.org.flem.baprodutiva.bo.PlanejamentoBO;
import br.org.flem.baprodutiva.dto.ConsistenciaPOA;
import br.org.flem.baprodutiva.negocio.CompositeFolha;
import br.org.flem.baprodutiva.negocio.FinanceiroPrevisto;
import br.org.flem.baprodutiva.negocio.Planejamento;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author essantos
 */
public class ConferenciaPOA {

    public List<ConsistenciaPOA> obterInconsistencia() throws Exception {
        List<ConsistenciaPOA> lista = new ArrayList<ConsistenciaPOA>();

        Collection<Planejamento> poas = new PlanejamentoBO().obterTodos();
        Collection<CompositeFolha> subatividades = new CompositeFolhaBO().obterTodos();
        Collection<FinanceiroPrevisto> previstos = new FinanceiroPrevistoBO().obterTodos();
        //Collection<Produto> produtos = new ProdutoBO().obterTodos();

        for (CompositeFolha subatividade : subatividades) {
            for (Planejamento poa : poas) {
              //  if(subatividade.getPlanejamento().getId().equals(poa.getId())){
                    ConsistenciaPOA consistencia = new ConsistenciaPOA();
                    consistencia.setPOA(poa);
                    consistencia.setSubatividade(subatividade);

                    for (FinanceiroPrevisto previsto : previstos) {
                        if (subatividade.getId().equals(previsto.getSubatividade().getId())) {
                            if (poa.getId().equals(previsto.getPlanejamento().getId())) {
                                consistencia.setFinanceiro(true);
                                break;
                            }
                        }
                    }
                    if (!consistencia.isFinanceiro()) {
                        lista.add(consistencia);
                    }
              //  }
            }
        }

        Collections.sort(lista, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                ConsistenciaPOA comp1 = (ConsistenciaPOA) o1;
                ConsistenciaPOA comp2 = (ConsistenciaPOA) o2;
                int comparacao = comp1.getSubatividade().getDescricao().compareTo(comp2.getSubatividade().getDescricao());
                if (comparacao == 0) {
                    comparacao = comp1.getPOA().getDescricao().compareTo(comp2.getPOA().getDescricao());
                }
                return comparacao;
            }
        });

        return lista;
    }
}
