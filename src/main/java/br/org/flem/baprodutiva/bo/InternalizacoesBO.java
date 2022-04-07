package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.negocio.Internalizacao;
import br.org.flem.baprodutiva.negocio.InternalizacaoLoteBancario;
import br.org.flem.baprodutiva.util.IFReceita;
import br.org.flem.fwe.exception.AcessoDadosException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
public class InternalizacoesBO {

    private InternalizacaoBO internalizacaoBO;
    private InternalizacaoDevolucaoBO internalizacaoDevolucaoBO;
    private InternalizacaoAplicacaoFinanceiraBO internalizacaoAplicacaoFinanceiraBO;
    private InternalizacaoLoteBancarioBO internalizacaoLoteBancarioBO;

    public InternalizacoesBO() throws AcessoDadosException {
        internalizacaoBO = new InternalizacaoBO();
        internalizacaoDevolucaoBO = new InternalizacaoDevolucaoBO();
        internalizacaoAplicacaoFinanceiraBO = new InternalizacaoAplicacaoFinanceiraBO();
        internalizacaoLoteBancarioBO = new InternalizacaoLoteBancarioBO();
    }

    /**
     * Este método deve ser refatorado, melhorando o código que possui muitos
     * ifs.
     *
     * @return
     * @throws br.org.flem.fwe.exception.AcessoDadosException
     */
    public Collection<IFReceita> obterTodosTiposInternalizacaoAgrupadasPorTaxa() throws AcessoDadosException, IOException {
        List<IFReceita> internalizacoes = new ArrayList();
        List<IFReceita> internalizacoesAgrupadas = new ArrayList();
        internalizacoes.addAll(internalizacaoBO.obterTodos());
        internalizacoes.addAll(internalizacaoDevolucaoBO.obterDevolucoesExcetoCCOperacional());
        internalizacoes.addAll(internalizacaoLoteBancarioBO.obterTodos());
        Collections.sort(internalizacoes, new Comparator<IFReceita>() {

            @Override
            public int compare(IFReceita o1, IFReceita o2) {
                //Se for internalizacao de lote considerar a data de exibição;
                Date data1 = (o1 instanceof InternalizacaoLoteBancario ? ((InternalizacaoLoteBancario) o1).getDataExibicao() : o1.getEntrada());
                Date data2 = (o2 instanceof InternalizacaoLoteBancario ? ((InternalizacaoLoteBancario) o2).getDataExibicao() : o2.getEntrada());
                return data1.compareTo(data2);
            }
        });

        Internalizacao internalizacao = null;
        //int index = 0;
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

    public Collection<IFReceita> obterTodosTiposInternalizacaoSemAgrupar() throws AcessoDadosException, IOException {

        List<IFReceita> internalizacoes = new ArrayList();
        internalizacoes.addAll(internalizacaoBO.obterTodos());
        internalizacoes.addAll(internalizacaoDevolucaoBO.obterDevolucoesExcetoCCOperacional());
        internalizacoes.addAll(internalizacaoLoteBancarioBO.obterTodos());

        Collections.sort(internalizacoes, new Comparator<IFReceita>() {

            @Override
            public int compare(IFReceita o1, IFReceita o2) {
                Date data1 = (o1 instanceof InternalizacaoLoteBancario ? ((InternalizacaoLoteBancario) o1).getDataExibicao() : o1.getEntrada());
                Date data2 = (o2 instanceof InternalizacaoLoteBancario ? ((InternalizacaoLoteBancario) o2).getDataExibicao() : o2.getEntrada());
                return data1.compareTo(data2);
            }
        });

        return internalizacoes;
    }
}
