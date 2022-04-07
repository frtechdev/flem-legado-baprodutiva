package br.org.flem.baprodutiva.bo.util;

import br.org.flem.baprodutiva.bo.CorrecaoCCBO;
import br.org.flem.baprodutiva.bo.CorrecaoNomeBO;
import br.org.flem.baprodutiva.bo.CorrecaoValorBO;
import br.org.flem.baprodutiva.bo.DespesaAplicacaoFinanceiraBO;
import br.org.flem.baprodutiva.bo.DespesaDataExibicaoBO;
import br.org.flem.baprodutiva.bo.DespesaInelegivelBO;
import br.org.flem.baprodutiva.bo.DespesaOrdenadaBO;
import br.org.flem.baprodutiva.bo.InternalizacaoDevolucaoBO;
import br.org.flem.baprodutiva.bo.InternalizacaoLoteBancarioBO;
import br.org.flem.baprodutiva.bo.LancamentoAvulsoBO;
import br.org.flem.baprodutiva.bo.RateioCentroCustoBO;
import br.org.flem.baprodutiva.dto.DevolucaoDTO;
import br.org.flem.baprodutiva.negocio.CorrecaoCC;
import br.org.flem.baprodutiva.negocio.CorrecaoNome;
import br.org.flem.baprodutiva.negocio.CorrecaoValor;
import br.org.flem.baprodutiva.negocio.DespesaDataExibicao;
import br.org.flem.baprodutiva.negocio.RateioCentroCusto;
import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fw.persistencia.dto.Compromisso;
import br.org.flem.fw.persistencia.dto.LancamentoInterface;
import br.org.flem.fw.service.GEM;
import br.org.flem.fw.service.SAV;
import br.org.flem.fw.service.impl.GEMImpl;
import br.org.flem.fw.service.impl.SAVImpl;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.util.Data;
import br.org.flem.fwe.util.StringUtil;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author dbbarreto
 */
public class OrganizadorLancamentosBO implements Serializable {

    private Date ultimaAtualizacaoLinhasCompromissos = null;
    private boolean validaLinhasCompromissos = true;
    private Collection<LancamentoInterface> linhasCompromissos = null;
    private Date ultimaAtualizacaoDevolucoes = null;
    private boolean validaDevolucoes = true;
    private Collection<LancamentoInterface> devolucoes = null;
    private Date ultimaAtualizacaoAvulsos = null;
    private boolean validaAvulsos = true;
    private Collection<LancamentoInterface> avulsos = null;
    private Date ultimaAtualizacaoTransferencias = null;
    private boolean validaTransferencias = true;
    //private Collection<LancamentoInterface> transferencias = null;
    private static OrganizadorLancamentosBO instancia = new OrganizadorLancamentosBO();
    private GEM gem = new GEMImpl();
    private SAV sav = new SAVImpl();

    /*
     * Contrutor privado por causa do padrão de projetos Singleton. Utilize o
     * método getInstancia para acessar a instancia da classe e utilizar os seus
     * recursos.
     */

    private OrganizadorLancamentosBO() {
    }

    /**
     * Método que deve utilizado para acessar a instÃ¢ncia da classe.
     *
     * @return Ãºnica instÃ¢ncia da classe.
     */
    public static OrganizadorLancamentosBO getInstancia() {
        return instancia;
    }

    public Collection<LancamentoInterface> obterCompromissos() throws IOException, AplicacaoException {
        long tempoRestante = 0;

        if (this.ultimaAtualizacaoLinhasCompromissos != null) {
            tempoRestante = (Calendar.getInstance().getTime().getTime() - this.ultimaAtualizacaoLinhasCompromissos.getTime()) / 60000;
        }

        if (this.linhasCompromissos == null || tempoRestante >= 5 || this.validaLinhasCompromissos == false) {
            this.atualizaLinhasCompromissos();
            this.ultimaAtualizacaoLinhasCompromissos = Calendar.getInstance().getTime();
            this.validaLinhasCompromissos = true;
        }

        return this.linhasCompromissos;
    }

    public LancamentoInterface obterCompromissoTipoId(String apdTp, String apdId) throws IOException, AplicacaoException {

        for (LancamentoInterface compromisso : this.obterCompromissos()) {
            if (compromisso.getApdId().equals(apdId) && compromisso.getApdTp().equals(apdTp)) {
                return compromisso;
            }
        }

        return null;
    }

    public List<LancamentoInterface> ordenarCompromissos(Collection<LancamentoInterface> colecaoDesordenada) {
        List<LancamentoInterface> listaOrdenada = new ArrayList<LancamentoInterface>(colecaoDesordenada);

        Collections.sort(listaOrdenada, new Comparator<LancamentoInterface>() {
            public int compare(LancamentoInterface arg0, LancamentoInterface arg1) {
                int comparacao = arg0.getData().compareTo(arg1.getData());
                if (comparacao == 0) {
                    comparacao = Integer.valueOf(arg0.getOrdem()).compareTo(Integer.valueOf(arg1.getOrdem()));
                    if (comparacao == 0) {
                        comparacao = arg0.getApdId().compareTo(arg1.getApdId());
                        if (comparacao == 0) {
                            return arg0.getValor().compareTo(arg1.getValor());
                        }
                    }
                }
                return comparacao;
            }
        });

        return listaOrdenada;
    }

    public Collection<LancamentoInterface> obterDevolucoes() throws IOException, AplicacaoException {
        long tempoRestante = 0;

        if (this.ultimaAtualizacaoDevolucoes != null) {
            tempoRestante = (Calendar.getInstance().getTime().getTime() - this.ultimaAtualizacaoDevolucoes.getTime()) / 60000;
        }

        if (this.devolucoes == null || tempoRestante >= 5 || this.validaDevolucoes == false) {
            this.atualizaDevolucoes();
            this.ultimaAtualizacaoDevolucoes = Calendar.getInstance().getTime();
            this.validaDevolucoes = true;
        }

        return this.devolucoes;
    }

    /*
     public Collection<LancamentoInterface> obterTransferencias() throws IOException, AplicacaoException {
        long tempoRestante = 0;

        if (this.ultimaAtualizacaoTransferencias != null) {
            tempoRestante = (Calendar.getInstance().getTime().getTime() - this.ultimaAtualizacaoTransferencias.getTime()) / 60000;
        }

        if (this.transferencias == null || tempoRestante >= 5 || this.validaTransferencias == false) {
            this.atualizaTransferencias();
            this.ultimaAtualizacaoTransferencias = Calendar.getInstance().getTime();
            this.validaTransferencias = true;
        }

        return this.transferencias;
    
    }*/

    public void forcarAtualizacaoDevolucoes() {
        this.validaDevolucoes = false;
    }

    public void forcarAtualizacaoTransferencias() {
        this.validaTransferencias = false;
    }

    /**
     * Método utilizado para atualizar as devoluçÃµes.
     */
    private void atualizaDevolucoes() throws IOException, AplicacaoException {
        Collection<LancamentoInterface> listaDevolucoesCorretas = new ArrayList<LancamentoInterface>();

        Collection<DevolucaoDTO> listaDevolucoes = new InternalizacaoDevolucaoBO().obterDevolucoes();

        //OBtém também as devoluções por lote bancario que não são de viagem.
        listaDevolucoes.addAll(new InternalizacaoLoteBancarioBO().obterLotesBancarios());
        Collection<Compromisso> despesasOrdenadas = new DespesaOrdenadaBO().obterCompromissos();
        Collection<CorrecaoValor> despesasCorrigidasValor = new CorrecaoValorBO().obterTodos();
        Collection<CorrecaoCC> despesasCorrigidasCC = new CorrecaoCCBO().obterTodos();
        Collection<DespesaDataExibicao> despesasDataExibicao = new DespesaDataExibicaoBO().obterTodos();
        Collection<CorrecaoNome> despesasCorrigidasNome = new CorrecaoNomeBO().obterTodos();

        for (DevolucaoDTO devolucao : listaDevolucoes) {
            // Para evitar problemas de exceção na ordenação por ordem
            devolucao.setOrdem("0");
            if (devolucao.getDescricao().equalsIgnoreCase("AQUISICAO DE 30 AR CONDICIONADOS P.2741/18")) {
                System.out.println("Aqui");
            }

            // Adicionar o atributo 'ordem' Ã s devoluçÃµes
            for (Compromisso despesaOrdenada : despesasOrdenadas) {
                if (devolucao.getApdId().equals(despesaOrdenada.getApdId()) && devolucao.getApdTp().equals(despesaOrdenada.getApdTp()) && (devolucao.getSeqLinha() == null || devolucao.getSeqLinha().equals(despesaOrdenada.getSeqLinha()))) {
                    if ((despesaOrdenada.getOrdem() != null) && (!despesaOrdenada.getOrdem().isEmpty())) {
                        devolucao.setOrdem(despesaOrdenada.getOrdem().trim());
                    }
                    break;
                }
            }

            // Adicionar o atributo 'valor' Ã s devoluçÃµes (apenas para as que tiveram correção de valor)
            for (CorrecaoValor despesasCorrigidaValor : despesasCorrigidasValor) {
                if (devolucao.getApdId().equals(despesasCorrigidaValor.getApdId()) && devolucao.getApdTp().equals(despesasCorrigidaValor.getApdTp()) && (devolucao.getSeqLinha() == null || devolucao.getSeqLinha().equals(despesasCorrigidaValor.getSeqLinha()))) {
                    devolucao.setValorAnterior(devolucao.getValor());
                    devolucao.setValor(new BigDecimal(despesasCorrigidaValor.getValor()));
                    break;
                }
            }

            // Altera o atributo 'centroCusto' Ã s devoluçÃµes (apenas para as que tiveram correção de valor)
            for (CorrecaoCC despesasCorrigidaCC : despesasCorrigidasCC) {
                if (devolucao.getApdId().equals(despesasCorrigidaCC.getApdId()) && devolucao.getApdTp().equals(despesasCorrigidaCC.getApdTp()) && (devolucao.getSeqLinha() == null || devolucao.getSeqLinha().equals(despesasCorrigidaCC.getSeqLinha()))) {
                    devolucao.setCentroCustoAnterior(devolucao.getCentroCusto());
                    devolucao.setCentroCusto(despesasCorrigidaCC.getCentroCusto());
                    break;
                }
            }

            for (CorrecaoNome nomesCorrigido : despesasCorrigidasNome) {
                if (devolucao.getApdId().equals(nomesCorrigido.getApdId()) && devolucao.getApdTp().equals(nomesCorrigido.getApdTp()) && (devolucao.getSeqLinha() == null || devolucao.getSeqLinha().equals(nomesCorrigido.getSeqLinha()))) {
                    devolucao.setDescricaoAnterior(devolucao.getDescricao());
                    devolucao.setDescricao(nomesCorrigido.getDescricao());
                    break;
                }
            }

            // Adicionar o atributo 'dataExibicao' Ã s devoluçÃµes
            for (DespesaDataExibicao despesaDataExibicao : despesasDataExibicao) {
                if (devolucao.getApdId().equals(despesaDataExibicao.getApdId()) && devolucao.getApdTp().equals(despesaDataExibicao.getApdTp()) && (devolucao.getSeqLinha() == null || devolucao.getSeqLinha().equals(despesaDataExibicao.getSeqLinha()))) {
                    devolucao.setDataExibicao(despesaDataExibicao.getDataExibicao());
                    break;
                }
            }

            listaDevolucoesCorretas.add(devolucao);
        }

        this.devolucoes = this.ordenarDevolucoes(listaDevolucoesCorretas);

    }

    public LancamentoInterface obterDevolucaoTipoId(String apdTp, String apdId) throws IOException, AplicacaoException {

        for (LancamentoInterface compromisso : this.obterDevolucoes()) {
            if (compromisso.getApdId().equals(apdId) && compromisso.getApdTp().equals(apdTp)) {
                return compromisso;
            }
        }

        return null;
    }

    public List<LancamentoInterface> ordenarDevolucoes(Collection<LancamentoInterface> colecaoDesordenada) {
        List<LancamentoInterface> listaOrdenada = new ArrayList<LancamentoInterface>(colecaoDesordenada);

        Collections.sort(listaOrdenada, new Comparator<LancamentoInterface>() {
            public int compare(LancamentoInterface arg0, LancamentoInterface arg1) {
                return new Integer(arg0.getId()).compareTo(new Integer(arg1.getId()));
            }
        });

        return listaOrdenada;
    }

    public Collection<LancamentoInterface> obterAvulsos() throws IOException, AplicacaoException {
        long tempoRestante = 0;

        if (this.ultimaAtualizacaoAvulsos != null) {
            tempoRestante = (Calendar.getInstance().getTime().getTime() - this.ultimaAtualizacaoAvulsos.getTime()) / 60000;
        }

        if (this.avulsos == null || tempoRestante >= 5 || this.validaAvulsos == false) {
            this.atualizaAvulsos();
            this.ultimaAtualizacaoAvulsos = Calendar.getInstance().getTime();
            this.validaAvulsos = true;
        }

        return this.avulsos;
    }

    public void forcarAtualizacaoAvulsos() {
        this.validaAvulsos = false;
    }

    /**
     * Método utilizado para atualizar as devoluçÃµes.
     */
    private void atualizaAvulsos() throws IOException, AplicacaoException {
        Collection<LancamentoInterface> listaAvulsosCorretos = new ArrayList<LancamentoInterface>();

        Collection<DevolucaoDTO> listaAvulsos = new LancamentoAvulsoBO().obterCompromissos();

        Collection<Compromisso> despesasOrdenadas = new DespesaOrdenadaBO().obterCompromissos();
        Collection<CorrecaoValor> despesasCorrigidas = new CorrecaoValorBO().obterTodos();
        Collection<CorrecaoCC> despesasCorrigidasCC = new CorrecaoCCBO().obterTodos();
        Collection<DespesaDataExibicao> despesasDataExibicao = new DespesaDataExibicaoBO().obterTodos();
        Collection<CorrecaoNome> despesasCorrigidasNome = new CorrecaoNomeBO().obterTodos();

        for (DevolucaoDTO compromisso : listaAvulsos) {
            // Para evitar problemas de exceção na ordenação por ordem
            compromisso.setOrdem("0");

            // Adicionar o atributo 'ordem' Ã s devoluçÃµes
            for (Compromisso despesaOrdenada : despesasOrdenadas) {
                if (compromisso.getApdId().equals(despesaOrdenada.getApdId()) && compromisso.getApdTp().equals(despesaOrdenada.getApdTp()) && (compromisso.getSeqLinha() == null || compromisso.getSeqLinha().equals(despesaOrdenada.getSeqLinha()))) {
                    if ((despesaOrdenada.getOrdem() != null) && (!despesaOrdenada.getOrdem().isEmpty())) {
                        compromisso.setOrdem(despesaOrdenada.getOrdem().trim());
                    }
                    break;
                }
            }

            // Adicionar o atributo 'valor' Ã s devoluçÃµes (apenas para as que tiveram correção de valor)
            for (CorrecaoValor despesasCorrigida : despesasCorrigidas) {
                if (compromisso.getApdId().equals(despesasCorrigida.getApdId()) && compromisso.getApdTp().equals(despesasCorrigida.getApdTp()) && (compromisso.getSeqLinha() == null || compromisso.getSeqLinha().equals(despesasCorrigida.getSeqLinha()))) {
                    compromisso.setValorAnterior(compromisso.getValor());
                    compromisso.setValor(new BigDecimal(despesasCorrigida.getValor()));
                    break;
                }
            }

            // Altera o atributo 'centroCusto' Ã s devoluçÃµes (apenas para as que tiveram correção de valor)
            for (CorrecaoCC despesasCorrigidaCC : despesasCorrigidasCC) {
                if (compromisso.getApdId().equals(despesasCorrigidaCC.getApdId()) && compromisso.getApdTp().equals(despesasCorrigidaCC.getApdTp()) && (compromisso.getSeqLinha() == null || compromisso.getSeqLinha().equals(despesasCorrigidaCC.getSeqLinha()))) {
                    compromisso.setCentroCustoAnterior(compromisso.getCentroCusto());
                    compromisso.setCentroCusto(despesasCorrigidaCC.getCentroCusto());
                    break;
                }
            }

            for (CorrecaoNome nomesCorrigido : despesasCorrigidasNome) {
                if (compromisso.getApdId().equals(nomesCorrigido.getApdId()) && compromisso.getApdTp().equals(nomesCorrigido.getApdTp()) && (compromisso.getSeqLinha() == null || compromisso.getSeqLinha().equals(nomesCorrigido.getSeqLinha()))) {
                    compromisso.setDescricaoAnterior(compromisso.getDescricao());
                    compromisso.setDescricao(nomesCorrigido.getDescricao());
                    break;
                }
            }

            // Adicionar o atributo 'dataExibicao' Ã s devoluçÃµes
            for (DespesaDataExibicao despesaDataExibicao : despesasDataExibicao) {
                if (compromisso.getApdId().equals(despesaDataExibicao.getApdId()) && compromisso.getApdTp().equals(despesaDataExibicao.getApdTp()) && (compromisso.getSeqLinha() == null || compromisso.getSeqLinha().equals(despesaDataExibicao.getSeqLinha()))) {
                    compromisso.setDataExibicao(despesaDataExibicao.getDataExibicao());
                    break;
                }
            }

            listaAvulsosCorretos.add(compromisso);
        }

        this.avulsos = this.ordenarAvulsos(listaAvulsosCorretos);

    }

    public LancamentoInterface obterAvulsoTipoId(String apdTp, String apdId) throws IOException, AplicacaoException {

        for (LancamentoInterface compromisso : this.obterAvulsos()) {
            if (compromisso.getApdId().equals(apdId) && compromisso.getApdTp().equals(apdTp)) {
                return compromisso;
            }
        }

        return null;
    }

    public List<LancamentoInterface> ordenarAvulsos(Collection<LancamentoInterface> colecaoDesordenada) {
        List<LancamentoInterface> listaOrdenada = new ArrayList<LancamentoInterface>(colecaoDesordenada);

        Collections.sort(listaOrdenada, new Comparator<LancamentoInterface>() {
            public int compare(LancamentoInterface arg0, LancamentoInterface arg1) {
                return new Integer(arg0.getId()).compareTo(new Integer(arg1.getId()));
            }
        });

        return listaOrdenada;
    }

    public Collection<LancamentoInterface> obterLancamentos() throws IOException, AplicacaoException {
        return obterLancamentos(null, null, null, null, null);
    }

    /*  
    
    private void atualizaTransferencias() throws IOException, AplicacaoException {
        Collection<LancamentoInterface> listaTransferenciasCorretas = new ArrayList<LancamentoInterface>();
        Collection<DevolucaoDTO> transfs = new TransferenciaBancariaBO().obterCompromissosSoe();
       
        for (DevolucaoDTO compromisso : transfs) {
          
            listaTransferenciasCorretas.add(compromisso);
        }

        this.transferencias = listaTransferenciasCorretas;

    }*/
    public Collection<LancamentoInterface> obterLancamentos(String campoData, String campoDescricao, String campoValor, String campoCentroCusto, String campoFornecedor) throws IOException, AplicacaoException {

        Collection<LancamentoInterface> todos = new ArrayList<LancamentoInterface>();

        todos.addAll(this.obterCompromissos());
        todos.addAll(this.obterDevolucoes());
        todos.addAll(this.obterAvulsos());
        // todos.addAll(this.obterTransferencias());

        filtrarCompromissos(todos, campoData, campoDescricao, campoValor, campoCentroCusto, campoFornecedor);

        return todos;
    }

    private void filtrarCompromissos(Collection<LancamentoInterface> compromissos, String campoData, String campoDescricao, String campoValor, String campoCentroCusto, String campoFornecedor) {
        if (StringUtil.stringNotNull(campoData) || StringUtil.stringNotNull(campoDescricao) || StringUtil.stringNotNull(campoValor) || StringUtil.stringNotNull(campoCentroCusto)) {

            BigDecimal bc = StringUtil.stringNotNull(campoValor) ? new BigDecimal(campoValor) : null;
            Date data = null;

            try {
                data = StringUtil.stringNotNull(campoData) ? Data.formataData(campoData) : null;
            } catch (Exception e) {
                e.printStackTrace();
            }

            Iterator<LancamentoInterface> iterator = compromissos.iterator();

            while (iterator.hasNext()) {
                LancamentoInterface lancamento = iterator.next();
                if (data != null && !lancamento.getData().equals(data)) {
                    iterator.remove();
                    continue;
                }
                if (bc != null && lancamento.getValor().compareTo(bc) != 0) {
                    iterator.remove();
                    continue;
                }
                if (StringUtil.stringNotNull(campoDescricao) && !lancamento.getDescricao().contains(campoDescricao.toUpperCase().trim())) {
                    iterator.remove();
                    continue;
                }
                if (StringUtil.stringNotNull(campoCentroCusto) && !lancamento.getCentroCusto().contains(campoCentroCusto.trim())) {
                    iterator.remove();
                    continue;
                }
                if (StringUtil.stringNotNull(campoFornecedor) && !lancamento.getNomeFornecedor().contains(campoFornecedor.toUpperCase().trim())) {
                    iterator.remove();
                    continue;
                }
            }
        }
    }

    public void forcarAtualizacao() {
        this.forcarAtualizacaoLinhasCompromissos();
        this.forcarAtualizacaoDevolucoes();
        this.forcarAtualizacaoAvulsos();
        this.forcarAtualizacaoTransferencias();
    }

    public LancamentoInterface obterLancamentoTipoIdSeqLinha(String apdTp, String apdId, String seqLinha) throws IOException, AplicacaoException {

        for (LancamentoInterface compromisso : this.obterLancamentos()) {
            if (compromisso.getApdId().equals(apdId) && compromisso.getApdTp().equals(apdTp) && (compromisso.getSeqLinha() == null || compromisso.getSeqLinha().equals(seqLinha))) {
                return compromisso;
            }
        }

        return null;
    }

    public Collection<LancamentoInterface> obterCompromissosCustosOperacionais() throws IOException, AplicacaoException {
        String ccOperacional = PropertiesUtil.getProperties().getProperty("ccOperacional");
        Collection<LancamentoInterface> lista = new ArrayList<LancamentoInterface>();
        Collection<Compromisso> listaCompromissosGEM = gem.obterCompromissosPorFiltroGrupoCC(ccOperacional);

        //listaCompromissosGEM.addAll(adiantamentoFornecedor);
        for (Compromisso compromisso : listaCompromissosGEM) {
            compromisso.setOrdem("0");
            lista.add(compromisso);
        }
        for (LancamentoInterface compromisso : this.obterCompromissos()) {
            if (compromisso.getCentroCusto().equals(ccOperacional) && !lista.contains(compromisso)) {
                lista.add(compromisso);
            }
        }
        return lista;
    }

    public void forcarAtualizacaoLinhasCompromissos() {
        this.validaLinhasCompromissos = false;
    }

    public void atualizaLinhasCompromissos() throws IOException, AplicacaoException {
        Collection<LancamentoInterface> listaCompromissosCorretos = new ArrayList<LancamentoInterface>();

        //Este método é exclusivo para o Bahia Produtiva
        Collection<Compromisso> listaCompromissosGEM = gem.obterLinhaCompromissosPorCentoCustoDataBaProdutiva(PropertiesUtil.getProperties().getProperty("projeto") + "%");

        Collection<Compromisso> adiantamentoFornecedor = gem.obterAdiantamentoFornecedorPorCc(PropertiesUtil.getProperties().getProperty("projeto") + "%");
        listaCompromissosGEM.addAll(adiantamentoFornecedor);
        Collection<CorrecaoNome> despesasCorrigidasNome = new CorrecaoNomeBO().obterTodos();
        Collection<Compromisso> despesasInelegiveis = new DespesaInelegivelBO().obterCompromissos();
        Collection<Compromisso> despesasOrdenadas = new DespesaOrdenadaBO().obterCompromissos();
        Collection<CorrecaoValor> despesasCorrigidas = new CorrecaoValorBO().obterTodos();
        Collection<CorrecaoCC> despesasCorrigidasCC = new CorrecaoCCBO().obterTodos();
        Collection<DespesaDataExibicao> despesasDataExibicao = new DespesaDataExibicaoBO().obterTodos();
        Collection<Compromisso> despesasRateadas = new ArrayList<Compromisso>();
        Collection<RateioCentroCusto> rateios = new RateioCentroCustoBO().obterTodos();
        for (Compromisso compromisso : listaCompromissosGEM) {

            if (compromisso.getNomeFornecedor().equals("TUX NET - MATERIAIS E SERVICOS DE INFORMATICA LTDA")) {
                System.out.print("Entrei");
            }

            // Utilizado para quebrar o for em despesas que não devem ser consideradas.
            boolean quebraFor = false;
            // Para evitar problemas de exceção na ordenação por ordem
            compromisso.setOrdem("0");
            if (compromisso.getCentroCusto().equals(PropertiesUtil.getProperties().getProperty("ccOperacional"))) {
                continue;//desconsidera o centro de custo operacional
            }

            // Se o compromisso estÃ¡ na lista de inelegíveis, ele não deve ser inserido na lista.
            for (Compromisso despesaInelegivel : despesasInelegiveis) {
                if (compromisso.getApdId().equals(despesaInelegivel.getApdId()) && compromisso.getApdTp().equals(despesaInelegivel.getApdTp())) {
                    quebraFor = true;
                    break;
                }
            }

            // Quebra o for para que a despesa não seja considerada.
            if (quebraFor) {
                continue;
            }

            for (RateioCentroCusto rateio : rateios) {
                if (compromisso.getApdId().equals(rateio.getApdId()) && compromisso.getApdTp().equals(rateio.getApdTp())
                        && (compromisso.getSeqLinha() == null || compromisso.getSeqLinha().equals(rateio.getSeqLinha()))) {
                    quebraFor = true;
                    Compromisso novoCompromisso1 = this.clonarCompromisso(compromisso);
                    Compromisso novoCompromisso2 = this.clonarCompromisso(compromisso);
                    
                    novoCompromisso1.setValor(compromisso.getValor().divide(new BigDecimal(2)));
                    novoCompromisso1.setCentroCusto(rateio.getCentroCusto1());
                    despesasRateadas.add(novoCompromisso1);
                    
                    novoCompromisso2.setValor(compromisso.getValor().divide(new BigDecimal(2)));
                    novoCompromisso2.setCentroCusto(rateio.getCentroCusto2());
                    despesasRateadas.add(novoCompromisso2);
                    break;
                }
            }
            if (quebraFor) {
                continue;
            }

            // Adicionar o atributo 'ordem' aos compromissos
            for (Compromisso despesaOrdenada : despesasOrdenadas) {
                if (compromisso.getApdId().equals(despesaOrdenada.getApdId()) && compromisso.getApdTp().equals(despesaOrdenada.getApdTp())) {
                    if ((despesaOrdenada.getOrdem() != null) && (!despesaOrdenada.getOrdem().isEmpty())) {
                        compromisso.setOrdem(despesaOrdenada.getOrdem().trim());
                    }
                    break;
                }
            }

            Iterator<CorrecaoValor> iterator = despesasCorrigidas.iterator();

            while (iterator.hasNext()) {
                CorrecaoValor despesasCorrigida = iterator.next();
                if (compromisso.getApdId().equals(despesasCorrigida.getApdId()) && compromisso.getApdTp().equals(despesasCorrigida.getApdTp()) && (compromisso.getSeqLinha() == null || compromisso.getSeqLinha().equals(despesasCorrigida.getSeqLinha()))) {
                    compromisso.setValorAnterior(compromisso.getValor());
                    compromisso.setValor(new BigDecimal(despesasCorrigida.getValor()));
                    if (compromisso.getSeqLinha() == null) {
                        compromisso.setSeqLinha(despesasCorrigida.getSeqLinha());
                    }
                    iterator.remove();
                    break;
                }
            }

            // Altera o atributo 'centroCusto' Ã s devoluçÃµes (apenas para as que tiveram correção de valor)
            for (CorrecaoCC despesasCorrigidaCC : despesasCorrigidasCC) {
                if (compromisso.getApdId().equals(despesasCorrigidaCC.getApdId()) && compromisso.getApdTp().equals(despesasCorrigidaCC.getApdTp()) && (compromisso.getSeqLinha() == null || compromisso.getSeqLinha().equals(despesasCorrigidaCC.getSeqLinha()))) {
                    compromisso.setCentroCustoAnterior(compromisso.getCentroCusto());
                    compromisso.setCentroCusto(despesasCorrigidaCC.getCentroCusto());
                    break;
                }
            }

            for (CorrecaoNome nomesCorrigido : despesasCorrigidasNome) {
                if (compromisso.getApdId().equals(nomesCorrigido.getApdId()) && compromisso.getApdTp().equals(nomesCorrigido.getApdTp()) && (compromisso.getSeqLinha() == null || compromisso.getSeqLinha().equals(nomesCorrigido.getSeqLinha()))) {
                    compromisso.setDescricaoAnterior(compromisso.getDescricao());
                    compromisso.setDescricao(nomesCorrigido.getDescricao());
                    break;
                }
            }

            // Adicionar o atributo 'dataExibicao' aos compromissos
            for (DespesaDataExibicao despesaDataExibicao : despesasDataExibicao) {
                if (compromisso.getApdId().equals(despesaDataExibicao.getApdId()) && compromisso.getApdTp().equals(despesaDataExibicao.getApdTp())
                        && (compromisso.getSeqLinha() == null || compromisso.getSeqLinha().equals(despesaDataExibicao.getSeqLinha()))) {
                    compromisso.setDataExibicao(despesaDataExibicao.getDataExibicao());
                    break;
                }
            }

            listaCompromissosCorretos.add(compromisso);
        }
        listaCompromissosCorretos.addAll(despesasRateadas);

        this.linhasCompromissos = this.ordenarCompromissos(listaCompromissosCorretos);
    }

    public Compromisso clonarCompromisso(Compromisso compromisso) {
        if (compromisso == null) {
            return null;
        }

        Compromisso novoCompromisso = new Compromisso();

        novoCompromisso.setApdId(compromisso.getApdId());
        novoCompromisso.setApdTp(compromisso.getApdTp());
        novoCompromisso.setCentroCusto(compromisso.getCentroCusto());
        novoCompromisso.setCodigoFornecedor(compromisso.getCodigoFornecedor());
        novoCompromisso.setData(compromisso.getData());
        novoCompromisso.setDescricao(compromisso.getDescricao());
        novoCompromisso.setId(compromisso.getId());
        novoCompromisso.setImposto(compromisso.getImposto());
        novoCompromisso.setNomeFornecedor(compromisso.getNomeFornecedor());
        novoCompromisso.setNumero(compromisso.getNumero());
        novoCompromisso.setNumeroClienteConnect(compromisso.getNumeroClienteConnect());
        novoCompromisso.setOrdem(compromisso.getOrdem());
        novoCompromisso.setParcela(compromisso.getParcela());
        novoCompromisso.setRecibo(compromisso.getRecibo());
        novoCompromisso.setSeqLinha(compromisso.getSeqLinha());
        novoCompromisso.setTipo(compromisso.getTipo());
        novoCompromisso.setValor(compromisso.getValor());
        novoCompromisso.setValorAnterior(compromisso.getValorAnterior());

        return novoCompromisso;
    }

    public Collection<LancamentoInterface> obterCompromissosValidos(String campoData, String campoDescricao, String campoValor) throws IOException, AplicacaoException {
        GEM gem = new GEMImpl();

        Collection<LancamentoInterface> compromissosValidos = new ArrayList<LancamentoInterface>();

        Collection<Compromisso> todosCompromissos = gem.obterCompromissosPorFiltroGrupoCC(PropertiesUtil.getProperties().getProperty("projeto") + "%");
        Collection<Compromisso> despesasInelegiveis = new DespesaInelegivelBO().obterCompromissos();
        Collection<Compromisso> despesasAplicacaoFinanceira = new DespesaAplicacaoFinanceiraBO().obterCompromissos();

        if (despesasInelegiveis.size() > 0) {

            for (Compromisso compromisso : todosCompromissos) {
                boolean adicionarCompromisso = true;
                for (Compromisso compromissoUsado : despesasInelegiveis) {
                    if (compromisso.getApdId() != null && compromisso.getApdTp() != null) {
                        if (compromisso.getApdId().equals(compromissoUsado.getApdId()) && compromisso.getApdTp().equals(compromissoUsado.getApdTp())) {
                            adicionarCompromisso = false;
                            break;
                        }
                    }

                }

                for (Compromisso compromissoUsado : despesasAplicacaoFinanceira) {
                    if (compromisso.getApdId() != null && compromisso.getApdTp() != null) {
                        if (compromisso.getApdId().equals(compromissoUsado.getApdId()) && compromisso.getApdTp().equals(compromissoUsado.getApdTp())) {
                            adicionarCompromisso = false;
                            break;
                        }
                    }

                }

                if (adicionarCompromisso) {
                    compromissosValidos.add(compromisso);
                }

            }
        } else {
            compromissosValidos.addAll(todosCompromissos);
        }
        filtrarCompromissos(compromissosValidos, campoData, campoDescricao, campoValor, null, null);
        return compromissosValidos;
    }

    public Collection<Compromisso> obterDespesasViagens() throws IOException, AplicacaoException {

        Collection<Compromisso> compromissoViagens = gem.obterCompromissosDeViagens(PropertiesUtil.getProperties().getProperty("projeto") + "%");
        Collection<Compromisso> removerCompromissos = new ArrayList<Compromisso>();
        Date dataCorrecaoDespViagem = null;
        try {
            dataCorrecaoDespViagem = Data.formataData(PropertiesUtil.getProperties().getProperty("dtCorrigirDespViagem"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Compromisso compromisso : compromissoViagens) {

            compromisso.setValor(new BigDecimal(compromisso.getValor().intValue() / 100));
            if (dataCorrecaoDespViagem != null && compromisso.getData().before(dataCorrecaoDespViagem)) {
                removerCompromissos.add(compromisso);
            }
        }
        compromissoViagens.removeAll(removerCompromissos);
        return compromissoViagens;
    }

    public Collection<Compromisso> obterdespesasViagensSemPrestacaoContas() throws IOException, AplicacaoException {

        Collection<Compromisso> retorno = new ArrayList<Compromisso>();
        List<Integer> total = new ArrayList<Integer>();
        Collection<Compromisso> compromissoViagens = this.obterDespesasViagens();

        byte flag = 0;

        //Transforma lista de compromisso em uma lista de inteiros
        for (Compromisso comp : compromissoViagens) {

            total.add(Integer.parseInt(comp.getRecibo().replace("A", "")));

        }

        //Obtem viagens finalizadas a partir da lista de inteiros acima. 15
        List<Integer> finalizadas = sav.obterViagensPrestContasFinalizadas(total);

        List<Integer> naoFinalizadas = new ArrayList<Integer>();

        //Cria uma lista de Viagens não finalizadas. 
        for (Integer comp1 : total) {

            for (Integer f : finalizadas) {
                if (comp1.intValue() == f) {
                    flag = 1;
                }
            }
            if (flag == 0) {
                naoFinalizadas.add(comp1);
            }

            flag = 0;
        }

        //Retorna todos os compromissos de viagens que não foram finalizados
        for (Compromisso c : compromissoViagens) {

            for (Integer i : naoFinalizadas) {

                if (Integer.parseInt(c.getRecibo().replace("A", "")) == i) {

                    retorno.add(c);
                }
            }
        }

        return retorno;
    }
}
