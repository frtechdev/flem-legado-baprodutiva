package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.bo.util.OrganizadorLancamentosBO;
import br.org.flem.baprodutiva.negocio.CompositeFolha;
import br.org.flem.baprodutiva.negocio.FinanceiroPrevisto;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.baprodutiva.negocio.SubCategoria;
import br.org.flem.baprodutiva.relatorio.GerencialFinanceiroDTO;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.util.Data;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * Esta classe contém as regras da criação do Relatório de Fontes e Usos
 *
 * @author dbbarreto
 */
public class RelatorioGerencialFinanceiroBO {

    private static final int PERIODO = 0;
    private static final int ANO = 1;
    private static final int ACUMULADO = 2;

    private Planejamento planejamentoRelatorio = null;
    Collection<Planejamento> planejamentosAtivos = null;
    private Date dataInicialSelecionada;
    private Date dataFinalSelecionada;

    public Collection<GerencialFinanceiroDTO> geraDados( Date dataInicial, Date dataFinal) throws AplicacaoException, ParseException, IOException {
        Collection<GerencialFinanceiroDTO> retorno = new ArrayList<GerencialFinanceiroDTO>();
        planejamentoRelatorio = new PlanejamentoBO().obterPlanejamentoEntreDatas(dataInicial, dataFinal);
        planejamentosAtivos =  new PlanejamentoBO().obterTodosAtivos();
        dataInicialSelecionada = dataInicial;
        dataFinalSelecionada = dataFinal;

        for (SubCategoria subcategoria : new SubCategoriaBO().obterTodos()) {
            for(CompositeFolha atividade : new CompositeFolhaBO().obterPorSubcategoria(subcategoria)){
                boolean quebraFor = false;

                GerencialFinanceiroDTO dto = new GerencialFinanceiroDTO();
                dto.setCategoria("Categoria " + subcategoria.getCategoria().getDescricao());
                dto.setSubcategoria(subcategoria.getDescricao());
                dto.setAtividade(atividade.getDescricao());

                for (GerencialFinanceiroDTO dtoAdicionados : retorno) {
                    if (dtoAdicionados.getAtividade().equals(dto.getAtividade())){
                        dto = dtoAdicionados;
                        quebraFor = true;
                        break;
                    }
                }

                this.calculaPrevistoTotal(atividade, dto);

                this.calculaAplicadoTotal(atividade, dto);
                if (quebraFor) {
                    continue;
                }

                retorno.add(dto);
            }
        }

        return retorno;
    }

    private void calculaPrevistoTotal(CompositeFolha atividade, GerencialFinanceiroDTO dto) throws ParseException, AcessoDadosException, IOException, AplicacaoException {

        Date dataInicialDoProjeto = new SimpleDateFormat("dd/MM/yyyy").parse(PropertiesUtil.getProperties().getProperty("projeto.dataInicio"));

        this.calculaPrevisto(atividade, dto, planejamentoRelatorio.getDataInicial(), planejamentoRelatorio.getDataFinal(), ANO);

        this.calculaPrevisto(atividade, dto, dataInicialDoProjeto, planejamentoRelatorio.getDataFinal(), ACUMULADO);

    }

    private void calculaPrevisto(CompositeFolha atividade, GerencialFinanceiroDTO dto, Date inicio, Date fim, int tipo) throws AcessoDadosException, AplicacaoException {

        Double valorGEF = 0d;
        //Se for acumulado, 
        if(tipo == 2){
            for(Planejamento p : planejamentosAtivos){
                Collection<FinanceiroPrevisto> previstos = new FinanceiroPrevistoBO().obterPorPlanejamento(p);
                for (FinanceiroPrevisto previsto : previstos) {
                    if (!(previsto.getComposite() instanceof CompositeFolha && previsto.getSubatividade().getId().equals(atividade.getId()))) {
                        continue;
                    }

                    valorGEF +=  previsto.getValor();
                    dto.setDataPeriodo(Data.formataData(planejamentoRelatorio.getDataFinal(), "dd/MM/yyyy"));
                }
            }
        } else {
                Collection<FinanceiroPrevisto> previstos = new FinanceiroPrevistoBO().obterFinanceiroNoPeriodo(planejamentoRelatorio, inicio, fim);
                for (FinanceiroPrevisto previsto : previstos) {
                    if (!(previsto.getComposite() instanceof CompositeFolha && previsto.getSubatividade().getId().equals(atividade.getId()))) {
                        continue;
                    }
                    valorGEF +=  previsto.getValor();
                dto.setDataPeriodo(Data.formataData(planejamentoRelatorio.getDataFinal(), "dd/MM/yyyy"));
                }
            }
        
        switch (tipo) {
            case PERIODO: {
                dto.setPlanejadoPeriodo(valorGEF);
                break;
            }
            case ACUMULADO: {
                dto.setPlanejadoAcumulado(valorGEF);
                dto.setPlanejadoAcumuladoCategoria(valorGEF);
                break;
            }
            case ANO: {
                dto.setPlanejadoAno(dto.getPlanejadoAno() + valorGEF);
                break;
            }
        }

    }

    private void calculaAplicadoTotal(CompositeFolha atividade, GerencialFinanceiroDTO dto) throws ParseException, AplicacaoException, IOException {

        FontesCategorias geradorSOE = null;
        Collection<SoeDTO> resultado = null;

        Date dataInicialDoProjeto = new SimpleDateFormat("dd/MM/yyyy").parse(PropertiesUtil.getProperties().getProperty("projeto.dataInicio"));
        
        geradorSOE = new FontesCategorias();
        geradorSOE.getPedido().setInicio(dataInicialSelecionada);
        geradorSOE.getPedido().setFim(dataFinalSelecionada);
        geradorSOE.adicionaListaCentrosCusto(atividade.getCentrosCusto());
        geradorSOE.adicionaListaCompromissos(OrganizadorLancamentosBO.getInstancia().obterCompromissos());
        geradorSOE.adicionaListaInternalizacoes(new InternalizacoesBO().obterTodosTiposInternalizacaoAgrupadasPorTaxa());
        geradorSOE.adicionaListaDevolucoes(OrganizadorLancamentosBO.getInstancia().obterDevolucoes());

        resultado = geradorSOE.resultado();

        this.calculaAplicado(atividade, dto, resultado, planejamentoRelatorio, PERIODO);

        geradorSOE = new FontesCategorias();
        geradorSOE.getPedido().setInicio(planejamentoRelatorio.getDataInicial());
        geradorSOE.getPedido().setFim(planejamentoRelatorio.getDataFinal());
        geradorSOE.adicionaListaCentrosCusto(atividade.getCentrosCusto());
        geradorSOE.adicionaListaCompromissos(OrganizadorLancamentosBO.getInstancia().obterCompromissos());
        geradorSOE.adicionaListaInternalizacoes(new InternalizacoesBO().obterTodosTiposInternalizacaoAgrupadasPorTaxa());
        geradorSOE.adicionaListaDevolucoes(OrganizadorLancamentosBO.getInstancia().obterDevolucoes());

        resultado = geradorSOE.resultado();

        this.calculaAplicado(atividade, dto, resultado, planejamentoRelatorio, ANO);

        geradorSOE = new FontesCategorias();
        geradorSOE.getPedido().setInicio(dataInicialDoProjeto);
        geradorSOE.getPedido().setFim(new Date());
        geradorSOE.adicionaListaCentrosCusto(atividade.getCentrosCusto());
        geradorSOE.adicionaListaCompromissos(OrganizadorLancamentosBO.getInstancia().obterCompromissos());
        geradorSOE.adicionaListaInternalizacoes(new InternalizacoesBO().obterTodosTiposInternalizacaoAgrupadasPorTaxa());
        geradorSOE.adicionaListaDevolucoes(OrganizadorLancamentosBO.getInstancia().obterDevolucoes());

        resultado = geradorSOE.resultado();

        this.calculaAplicado(atividade, dto, resultado, planejamentoRelatorio, ACUMULADO);

    }

    private void calculaAplicado(CompositeFolha atividade, GerencialFinanceiroDTO dto, Collection<SoeDTO> compromissos, Planejamento planejamento, int tipo) throws AcessoDadosException, ParseException {

        Double valorGEF = 0d;

        Collection<String> ccCompartilhados = new CompositeFolhaBO().obterTodosCentrosCustoCompartilhados();

        /**
         * Calcula o valor do GEF.
         */
        for (SoeDTO despesa : compromissos) {
            //Caso o centro de custo seja compartilhado, somente metade do valor dele é informado no relatório.
            if (ccCompartilhados.contains(despesa.getCentroCusto())) {
                valorGEF += despesa.getParcela() / 2;
            } else {
                valorGEF += despesa.getParcela();
            }
        }

        switch (tipo) {
            case PERIODO: {
                dto.setAplicadoPeriodo(dto.getAplicadoPeriodo() + valorGEF);
                break;
            }
            case ANO: {
                dto.setAplicadoAno(dto.getAplicadoAno() + valorGEF);
                break;
            }
            case ACUMULADO: {
                dto.setAplicadoAcumulado(dto.getAplicadoAcumulado() + valorGEF);
                break;
            }
        }
    }

}
