package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.baprodutiva.dao.InternalizacaoAplicacaoFinanceiraDAO;
import br.org.flem.baprodutiva.negocio.Internalizacao;
import java.util.Collection;
import br.org.flem.baprodutiva.negocio.InternalizacaoAplicacaoFinanceira;
import br.org.flem.baprodutiva.util.IFReceita;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * Esta classe possui regras de negócios, referentes a entidade Categoria
 *
 * @author Gerador de Código da FLEM
 *
 */
public class InternalizacaoAplicacaoFinanceiraBO {

    InternalizacaoAplicacaoFinanceiraDAO dao;

    public InternalizacaoAplicacaoFinanceiraBO() throws AcessoDadosException {

        dao = new InternalizacaoAplicacaoFinanceiraDAO();
    }

    public void inserirOuAlterar(InternalizacaoAplicacaoFinanceira objeto) throws AcessoDadosException {
        dao.inserirOuAlterar(objeto);
    }

    public void inserir(InternalizacaoAplicacaoFinanceira objeto) throws AcessoDadosException {
        dao.inserir(objeto);
    }

    public void alterar(InternalizacaoAplicacaoFinanceira objeto) throws AcessoDadosException {
        dao.alterar(objeto);
    }

    public void excluir(InternalizacaoAplicacaoFinanceira objeto) throws AcessoDadosException {
        dao.excluir(objeto);
    }

    public void excluir(Collection<InternalizacaoAplicacaoFinanceira> objetos) throws AcessoDadosException {
        dao.excluir(objetos);
    }

    public InternalizacaoAplicacaoFinanceira obterPorPk(InternalizacaoAplicacaoFinanceira objeto) throws AcessoDadosException {
        return dao.obterPorPk(objeto);
    }

    public InternalizacaoAplicacaoFinanceira obterPorPk(Integer id) throws AcessoDadosException {
        return dao.obterPorPk(id);
    }

    public Collection<InternalizacaoAplicacaoFinanceira> obterTodos() throws AcessoDadosException {
        return dao.obterTodos();
    }

    public Collection<InternalizacaoAplicacaoFinanceira> obterTodosOrdenadoPorCampo(String campo)
            throws AcessoDadosException {
        return dao.obterTodosOrdenadoPorCampo(campo);
    }

    public Collection<InternalizacaoAplicacaoFinanceira> obterPorFiltro(InternalizacaoAplicacaoFinanceira objeto) throws AcessoDadosException {
        return dao.obterPorFiltro(objeto);
    }

    public List<InternalizacaoAplicacaoFinanceira> obterPorPeriodo(Date inicio, Date fim) {
        return dao.obterPorPeriodo(inicio, fim);
    }

    /**
     * @return
     * @throws br.org.flem.fwe.exception.AcessoDadosException
     */
    public Collection<IFReceita> obterAgrupadasPorTaxa() throws AcessoDadosException {
        List<IFReceita> internalizacoes = new ArrayList();
        List<IFReceita> internalizacoesAgrupadas = new ArrayList();
        internalizacoes.addAll(this.obterTodos());
        
        Collections.sort(internalizacoes, new Comparator<IFReceita>() {

            @Override
            public int compare(IFReceita o1, IFReceita o2) {
                return o1.getEntrada().compareTo(o2.getEntrada());
            }
        });

        Internalizacao internalizacao = null;
        Iterator<IFReceita> iterador = internalizacoes.iterator();
        while (iterador.hasNext()) {
            IFReceita receita = iterador.next();
            //Entra aqui somente na primeira vez.
            if (internalizacao == null) {
                internalizacao = new Internalizacao();
                internalizacao.setDescricao(receita.getDescricao());
                internalizacao.setId(receita.getId());
                internalizacao.setEntrada(receita.getEntrada());
                internalizacao.setValor(receita.getValor());
            } else {
                internalizacoesAgrupadas.add(internalizacao);
                internalizacao = new Internalizacao();
                internalizacao.setDescricao(receita.getDescricao());
                internalizacao.setId(receita.getId());
                internalizacao.setEntrada(receita.getEntrada());
                internalizacao.setValor(receita.getValor());
            }
            //Entra aqui somente na última vez.
            if (!iterador.hasNext()) {
                internalizacoesAgrupadas.add(internalizacao);
            }
        }

        return internalizacoesAgrupadas;
    }
}
