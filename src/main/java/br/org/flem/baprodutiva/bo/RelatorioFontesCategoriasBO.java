package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.bo.util.OrganizadorLancamentosBO;
import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.DespesaAplicacaoFinanceira;
import br.org.flem.baprodutiva.negocio.FinanceiroPrevisto;
import br.org.flem.baprodutiva.negocio.Fonte;
import br.org.flem.baprodutiva.negocio.Internalizacao;
import br.org.flem.baprodutiva.negocio.InternalizacaoAplicacaoFinanceira;
import br.org.flem.baprodutiva.negocio.InternalizacaoDevolucao;
import br.org.flem.baprodutiva.negocio.LancamentoAvulso;
import br.org.flem.baprodutiva.negocio.LoteDespesaAplicacao;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.baprodutiva.negocio.TipoFonte;
import br.org.flem.baprodutiva.relatorio.FonteCategoriaCompletoDTO;
import br.org.flem.baprodutiva.relatorio.FonteCategoriaDTO;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 *
 * Esta classe contém as regras da criação do Relatório de Fontes e Usos
 *
 * @author dbbarreto
 */
public class RelatorioFontesCategoriasBO {

    private static final int TRIMESTRE = 0;
    private static final int ACUMULADO = 1;
    private static final int SALDO_INICIAL = 2;
    private static final int ANO = 3;
    private static final int SALDO_INICIAL_ANO = 4;
    private DespesaAplicacaoFinanceiraBO despesaAplicacaoFinanceiraBO = null;
    private LancamentoAvulsoBO lancamentoAvulsoBO = null;


    @SuppressWarnings("empty-statement")
    public FonteCategoriaCompletoDTO geraDados(Planejamento planejamento) throws AplicacaoException, ParseException, IOException {
        FonteCategoriaCompletoDTO retorno = new FonteCategoriaCompletoDTO();
        despesaAplicacaoFinanceiraBO = new DespesaAplicacaoFinanceiraBO();
        lancamentoAvulsoBO = new LancamentoAvulsoBO();
        /*
         * CALCULANDO FONTES
         */
        Collection<TipoFonte> tipoFontes = new TipoFonteBO().obterTodos();

        //Este método calcula para cada tipo, os valores acumulado, do trimestre e o saldo inicial.
        /*
         * o tipo de fonte é cadastrado na parte de lançamento de recursos
         * 
         */
        for (TipoFonte tipoFonte : tipoFontes) {
            calculaFontesTotal(tipoFonte, retorno, planejamento, FonteCategoriaDTO.TIPO_FONTE);
        }

        /*for (TipoFonte tipoFonte : tipoFontes) {
            calculaFontesTotal(tipoFonte, retorno, periodo, moeda, FonteCategoriaDTO.TIPO_RENDIMENTO);
        }*/

        /*
         * CALCULANDO USOS
         */
        for (Categoria categoria : new CategoriaBO().obterTodos()) {
            calculaUsosTotal(categoria, retorno, planejamento);
        }

        return retorno;
    }

    private void calculaFontesTotal(TipoFonte tipoFonte, FonteCategoriaCompletoDTO completo, Planejamento planejamentoRelatorio, String tipoFonteDescricao) throws ParseException, IOException, AcessoDadosException {

        FonteCategoriaDTO fonteDTO = new FonteCategoriaDTO();
        fonteDTO.setCategoria(tipoFonte.getGrupoTipo().getDescricao());
        fonteDTO.setNome(tipoFonte.getDescricao());
        fonteDTO.setTipo(tipoFonteDescricao);

        Date dataInicialdoProjeto = new SimpleDateFormat("dd/MM/yyyy").parse(PropertiesUtil.getProperties().getProperty("projeto.dataInicio"));

        /*
         * VALORES DO TRIMESTRE
         */
        this.calculaFontes(fonteDTO, tipoFonte, completo, planejamentoRelatorio.getDataInicial(), planejamentoRelatorio.getDataFinal(), TRIMESTRE);

        /*
         * VALORES DO ACUMULADO DO ANO
         */
        this.calculaFontes(fonteDTO, tipoFonte, completo, planejamentoRelatorio.getDataInicial(), planejamentoRelatorio.getDataFinal(), ANO);

        /*
         * VALORES DO ACUMULADO TOTAL
         */
        this.calculaFontes(fonteDTO, tipoFonte, completo, dataInicialdoProjeto, planejamentoRelatorio.getDataFinal(), ACUMULADO);

        /*
         * VALORES DO SALDO INICIAL
         */
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(planejamentoRelatorio.getDataInicial());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        this.calculaFontes(fonteDTO, tipoFonte, completo, dataInicialdoProjeto, calendar.getTime(), SALDO_INICIAL);
        /*
         * VALORES DO SALDO INICIAL ANO
         */
        calendar.setTime(planejamentoRelatorio.getDataInicial());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        this.calculaFontes(fonteDTO, tipoFonte, completo, dataInicialdoProjeto, calendar.getTime(), SALDO_INICIAL_ANO);
    }

    private void calculaFontes(FonteCategoriaDTO dto, TipoFonte tipoFonte, FonteCategoriaCompletoDTO completo, Date inicio, Date fim, int etapa) throws AcessoDadosException {
        Double valorGEF = 0d;
        Double valorAplicacao = 0d;

        //calculo total acumulado para cada fonte
        if (tipoFonte.getOrigem() != null) {
            switch (tipoFonte.getOrigem()) {
                case FLEM: {
                    for (Internalizacao internalizacao : new InternalizacaoBO().obterPorPeriodo(inicio, fim)) {
                        valorGEF += internalizacao.getValor();
                    }
                    break;
                }
                case APLICACAO_FINANCEIRA: {
                    for (InternalizacaoAplicacaoFinanceira internalizacao : new InternalizacaoAplicacaoFinanceiraBO().obterPorPeriodo(inicio, fim)) {
                        if (internalizacao.getTipoFonte() == tipoFonte) {
                            valorAplicacao += internalizacao.getValor();
                        }
                    }
                    break;
                }
            }
        }

        //calcula o total por trimestre e por fonte
        for (Fonte fonte : new FonteBO().obterFontesNoPeriodo(tipoFonte, inicio, fim)) {
            switch (fonte.getOrigem()) {
                case FLEM:
                    valorGEF += fonte.getValor();
                    break;
                case APLICACAO_FINANCEIRA:
                    valorAplicacao += fonte.getValor();
            }
        }

        switch (etapa) {
            case TRIMESTRE: {
                dto.setValorGEF(valorGEF);
                dto.setValorRendimento(valorAplicacao);
                completo.setSaldoValorGEF(completo.getSaldoValorGEF() + valorGEF);
                completo.setSaldoAplicacao(completo.getSaldoAplicacao() + valorAplicacao);
                completo.getFonteCategorias().add(dto);
                break;
            }
            case ACUMULADO: {
                dto.setValorGEFAcumulado(valorGEF);
                dto.setValorRendimentoAcumulado(valorAplicacao);
                completo.setSaldoValorGEFAcumulado(completo.getSaldoValorGEFAcumulado() + valorGEF);
                completo.setSaldoAplicacaoAcumulado(completo.getSaldoAplicacaoAcumulado() + valorAplicacao);
                completo.getFonteCategorias().add(dto);
                break;
            }
            case SALDO_INICIAL: {
                completo.setSaldoInicialValorGEF(completo.getSaldoInicialValorGEF() + valorGEF);
                completo.setSaldoInicialAplicacao(completo.getSaldoInicialAplicacao() + valorAplicacao);
                break;
            }
            case ANO: {
                dto.setValorGEFAno(valorGEF);
                dto.setValorRendimentoAno(valorAplicacao);
                completo.setSaldoValorGEFAno(completo.getSaldoValorGEFAno() + valorGEF);
                completo.setSaldoAplicacaoAno(completo.getSaldoAplicacaoAno() + valorAplicacao);
                completo.getFonteCategorias().add(dto);
                break;
            }
            case SALDO_INICIAL_ANO: {
                completo.setSaldoInicialValorGEFAno(completo.getSaldoInicialValorGEFAno() + valorGEF);
                completo.setSaldoInicialAplicacaoAno(completo.getSaldoInicialAplicacaoAno() + valorAplicacao);
                break;
            }
        }
    }

    private void calculaUsosTotal(Categoria categoria, FonteCategoriaCompletoDTO completo, Planejamento periodoRelatorio) throws ParseException, IOException, AplicacaoException {

        FontesCategorias geradorSOE = null;
        Collection<SoeDTO> resultado = null;

        FonteCategoriaDTO fonteDTO = new FonteCategoriaDTO();
        fonteDTO.setCategoria("Categorias");
        fonteDTO.setNome(categoria.getDescricao());
        fonteDTO.setTipo(FonteCategoriaDTO.TIPO_USOS);

        Date dataInicialdoProjeto = new SimpleDateFormat("dd/MM/yyyy").parse(PropertiesUtil.getProperties().getProperty("projeto.dataInicio"));

        /*
         * VALORES DO TRIMESTRE
         */
        geradorSOE = new FontesCategorias();
        geradorSOE.getPedido().setInicio(periodoRelatorio.getDataInicial());
        geradorSOE.getPedido().setFim(periodoRelatorio.getDataFinal());
        geradorSOE.adicionaListaAvulsos(OrganizadorLancamentosBO.getInstancia().obterAvulsos());
//        geradorSOE.adicionaListaCentrosCusto(new CompositeFolhaBO().obterCentrosCustoPorCategoria(categoria));
        geradorSOE.adicionaListaInternalizacoes(new InternalizacoesBO().obterTodosTiposInternalizacaoAgrupadasPorTaxa());
        geradorSOE.adicionaListaCompromissos(OrganizadorLancamentosBO.getInstancia().obterCompromissos());
        geradorSOE.adicionaListaDevolucoes(OrganizadorLancamentosBO.getInstancia().obterDevolucoes());

        resultado = geradorSOE.resultado();

        this.calculaUsos(fonteDTO, completo, categoria, resultado, periodoRelatorio, periodoRelatorio.getDataInicial(), periodoRelatorio.getDataFinal(), TRIMESTRE);

        /*
         * VALORES DO ACUMULADO DO ANO
         */
        geradorSOE = new FontesCategorias();
        geradorSOE.getPedido().setInicio(periodoRelatorio.getDataInicial());
        geradorSOE.getPedido().setFim(periodoRelatorio.getDataFinal());
        geradorSOE.adicionaListaAvulsos(OrganizadorLancamentosBO.getInstancia().obterAvulsos());
      //  geradorSOE.adicionaListaCentrosCusto(new CompositeFolhaBO().obterCentrosCustoPorCategoria(categoria));
        geradorSOE.adicionaListaInternalizacoes(new InternalizacoesBO().obterTodosTiposInternalizacaoAgrupadasPorTaxa());
        geradorSOE.adicionaListaCompromissos(OrganizadorLancamentosBO.getInstancia().obterCompromissos());
        geradorSOE.adicionaListaDevolucoes(OrganizadorLancamentosBO.getInstancia().obterDevolucoes());

        resultado = geradorSOE.resultado();

        this.calculaUsos(fonteDTO, completo, categoria, resultado, periodoRelatorio, periodoRelatorio.getDataInicial(), periodoRelatorio.getDataFinal(), ANO);

        /*
         * VALORES DO ACUMULADO
         */
        geradorSOE = new FontesCategorias();
        geradorSOE.getPedido().setInicio(dataInicialdoProjeto);
        geradorSOE.getPedido().setFim(periodoRelatorio.getDataFinal());
        geradorSOE.adicionaListaAvulsos(OrganizadorLancamentosBO.getInstancia().obterAvulsos());
   //     geradorSOE.adicionaListaCentrosCusto(new CompositeFolhaBO().obterCentrosCustoPorCategoria(categoria));
        geradorSOE.adicionaListaInternalizacoes(new InternalizacoesBO().obterTodosTiposInternalizacaoAgrupadasPorTaxa());
        geradorSOE.adicionaListaCompromissos(OrganizadorLancamentosBO.getInstancia().obterCompromissos());
        geradorSOE.adicionaListaDevolucoes(OrganizadorLancamentosBO.getInstancia().obterDevolucoes());

        resultado = geradorSOE.resultado();

        this.calculaUsos(fonteDTO, completo, categoria, resultado, periodoRelatorio, dataInicialdoProjeto, periodoRelatorio.getDataFinal(), ACUMULADO);

        /*
         * VALORES DO SALDO INICIAL
         */
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(periodoRelatorio.getDataInicial());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        geradorSOE = new FontesCategorias();
        geradorSOE.getPedido().setInicio(dataInicialdoProjeto);
        geradorSOE.getPedido().setFim(calendar.getTime());
        geradorSOE.adicionaListaAvulsos(OrganizadorLancamentosBO.getInstancia().obterAvulsos());
//        geradorSOE.adicionaListaCentrosCusto(new CompositeFolhaBO().obterCentrosCustoPorCategoria(categoria));
        geradorSOE.adicionaListaInternalizacoes(new InternalizacoesBO().obterTodosTiposInternalizacaoAgrupadasPorTaxa());
        geradorSOE.adicionaListaCompromissos(OrganizadorLancamentosBO.getInstancia().obterCompromissos());
        geradorSOE.adicionaListaDevolucoes(OrganizadorLancamentosBO.getInstancia().obterDevolucoes());

        resultado = geradorSOE.resultado();

        this.calculaUsos(fonteDTO, completo, categoria, resultado, periodoRelatorio, dataInicialdoProjeto, calendar.getTime(), SALDO_INICIAL);

        /*
         * VALORES DO SALDO INICIAL ANO
         */
        calendar.setTime(periodoRelatorio.getDataInicial());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        geradorSOE = new FontesCategorias();
        geradorSOE.getPedido().setInicio(dataInicialdoProjeto);
        geradorSOE.getPedido().setFim(calendar.getTime());
        geradorSOE.adicionaListaAvulsos(OrganizadorLancamentosBO.getInstancia().obterAvulsos());
    //    geradorSOE.adicionaListaCentrosCusto(new CompositeFolhaBO().obterCentrosCustoPorCategoria(categoria));
        geradorSOE.adicionaListaInternalizacoes(new InternalizacoesBO().obterTodosTiposInternalizacaoAgrupadasPorTaxa());
        geradorSOE.adicionaListaCompromissos(OrganizadorLancamentosBO.getInstancia().obterCompromissos());
        geradorSOE.adicionaListaDevolucoes(OrganizadorLancamentosBO.getInstancia().obterDevolucoes());

        resultado = geradorSOE.resultado();

        this.calculaUsos(fonteDTO, completo, categoria, resultado, periodoRelatorio, dataInicialdoProjeto, calendar.getTime(), SALDO_INICIAL_ANO);
    }

    private void calculaUsos(FonteCategoriaDTO dto, FonteCategoriaCompletoDTO completo, Categoria categoria, Collection<SoeDTO> compromissos, Planejamento planejamento, Date inicio, Date fim, int etapa) throws AplicacaoException, IOException {
        Double valorGEF = 0d;
        Double valorDespesaAplicacao = 0d;
        Double valorPrevisto = 0d;

        valorPrevisto = calculaPrevisto(completo, categoria, planejamento, inicio, fim);

        switch (categoria.getOrigem()) {

            case APLICACAO_FINANCEIRA: {
                for (LoteDespesaAplicacao loteDespesaAplicacao : new LoteDespesaAplicacaoBO().obterPorPeriodo(inicio, fim)) {
                    if (loteDespesaAplicacao.getCategoria() == categoria) {
                        valorDespesaAplicacao += loteDespesaAplicacao.getValor();
                    }
                }

                for (DespesaAplicacaoFinanceira despesaAplicacao : despesaAplicacaoFinanceiraBO.obterPorPeriodo(inicio, fim)) {
                    valorDespesaAplicacao += despesaAplicacao.getValor();

                }
                
                for (InternalizacaoDevolucao devolucao : new InternalizacaoDevolucaoBO().obterDevolucoesCCOperacionalPorPeriodo(inicio, fim)) {
                    if (devolucao.getCentroCusto().equals(PropertiesUtil.getProperties().getProperty("ccOperacional"))) {
                        valorDespesaAplicacao -= devolucao.getValor();
                    }
                }
                
                for (LancamentoAvulso lancamentoAvulso : lancamentoAvulsoBO.obterPorPeriodo(inicio, fim)) {
                    if (lancamentoAvulso.getCentroCusto().equals(PropertiesUtil.getProperties().getProperty("ccOperacional"))) {
                        valorDespesaAplicacao += lancamentoAvulso.getValorPagamento();
                    }
                }

                break;
            }
            default: {
                for (SoeDTO despesa : compromissos) {
                    valorGEF += despesa.getParcela();
                }
            }
        }

        switch (etapa) {
            case TRIMESTRE: {
                dto.setValorGEF(valorGEF);
                completo.setSaldoValorGEF(completo.getSaldoValorGEF() - valorGEF);
                completo.getFonteCategorias().add(dto);
                completo.setSaldoAplicacao(completo.getSaldoAplicacao() - valorDespesaAplicacao);
                dto.setValorPrevisto(valorPrevisto);
                break;
            }
            case ACUMULADO: {
                dto.setValorGEFAcumulado(valorGEF);
                completo.setSaldoValorGEFAcumulado(completo.getSaldoValorGEFAcumulado() - valorGEF);
                completo.getFonteCategorias().add(dto);
                completo.setSaldoAplicacaoAcumulado(completo.getSaldoAplicacaoAcumulado() - valorDespesaAplicacao);
                dto.setValorPrevistoAcumulado(valorPrevisto);
                break;
            }
            case SALDO_INICIAL: {
                completo.setSaldoInicialValorGEF(completo.getSaldoInicialValorGEF() - valorGEF);
                break;
            }
            case ANO: {
                dto.setValorGEFAno(valorGEF);
                completo.setSaldoValorGEFAno(completo.getSaldoValorGEFAno() - valorGEF);
                completo.getFonteCategorias().add(dto);
                completo.setSaldoAplicacaoAno(completo.getSaldoAplicacaoAno() - valorDespesaAplicacao);
                dto.setValorPrevistoAno(valorPrevisto);
                break;
            }
            case SALDO_INICIAL_ANO: {
                completo.setSaldoInicialValorGEFAno(completo.getSaldoInicialValorGEFAno() - valorGEF);
                break;
            }
        }
    }

    private Double calculaPrevisto(FonteCategoriaCompletoDTO completo, Categoria categoria, Planejamento planejamento, Date inicio, Date fim) throws AcessoDadosException, AplicacaoException {
        Double valorPrevisto = 0d;

        Collection<FinanceiroPrevisto> previstos = new FinanceiroPrevistoBO().obterFinanceiroNoPeriodo(categoria, planejamento, inicio, fim);

        for (FinanceiroPrevisto previsto : previstos) {
            valorPrevisto += previsto.getValor();
        }
        return valorPrevisto;
    }

}
