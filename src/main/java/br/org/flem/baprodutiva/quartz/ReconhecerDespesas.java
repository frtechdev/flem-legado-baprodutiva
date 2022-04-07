/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.quartz;

import br.org.flem.baprodutiva.bo.CategoriaBO;
import br.org.flem.baprodutiva.bo.InternalizacaoDevolucaoBO;
import br.org.flem.baprodutiva.bo.LoteDespesaAplicacaoBO;
import br.org.flem.baprodutiva.dao.LoteDespesaAplicacaoDAO;
import br.org.flem.baprodutiva.negocio.InternalizacaoDevolucao;
import br.org.flem.baprodutiva.negocio.LoteDespesaAplicacao;
import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fw.persistencia.dto.Compromisso;
import br.org.flem.fw.service.GEM;
import br.org.flem.fw.service.impl.GEMImpl;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.util.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author AJLima
 */
public class ReconhecerDespesas implements Job {

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        try {
            this.associarPrestacaoDeContas();
            this.associarTarifasBancarias();
        } catch (Exception e) {
            Logger.getLogger(ReconhecerDespesas.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                HibernateUtil.closeSession();
            } catch (AcessoDadosException e) {
                Logger.getLogger(ReconhecerDespesas.class.getName()).log(Level.SEVERE, null, "Erro ao fechar Sessão");
            }
        }
    }

    /**
     * Rotina de associação de Prestação de Contas. Obtem dados do Sistema GEM e
     * grava no banco do baprodutiva
     *
     * @author AJLima
     * @throws IOException
     * @throws AcessoDadosException
     * @throws Exception
     */
    public void associarPrestacaoDeContas() throws IOException, AcessoDadosException, Exception {
        
        GEM gem = new GEMImpl();
        String banco1, agencia1, conta1, dataInicio, cc;
        banco1 = PropertiesUtil.getProperties().getProperty("banco1");
        agencia1 = PropertiesUtil.getProperties().getProperty("agencia1");
        conta1 = PropertiesUtil.getProperties().getProperty("conta1");
        dataInicio = PropertiesUtil.getProperties().getProperty("projeto.dataInicio");
        cc = PropertiesUtil.getProperties().getProperty("projeto") + "%";
        Collection<Compromisso> todos = gem.obterCompromissosDeViagensPorCC(cc);
        //Todas as devoluções de viagens...
        Collection<Compromisso> compromissos = gem.obterDevolucoesViagemPorContaDataInicio(Integer.valueOf(Data.retornaDataInversa(dataInicio)), banco1, agencia1, conta1);

        Collection<Compromisso> compromissosEstornados = gem.obterDevolucoesViagemPorContaDataInicioEstornadas(Integer.valueOf(Data.retornaDataInversa(dataInicio)), banco1, agencia1, conta1);

        //Internalizações já reconhecidas...
        Collection<Compromisso> internalizacoesJaReconhecidas = new InternalizacaoDevolucaoBO().obterCompromissos();
        Collection<Compromisso> compromissosValidos = new ArrayList();

        //pega somente as devoluções que não estao na tabela do baprodutiva
        for (Compromisso compromisso : compromissos) {
            boolean insere = true;
            for (Compromisso internalizacao : internalizacoesJaReconhecidas) {
                if (compromisso.getId().equals(internalizacao.getId()) && compromisso.getTipo().equals(internalizacao.getTipo()) && compromisso.getSeqLinha().equals(internalizacao.getSeqLinha())) {
                    insere = false;
                }
            }
            if (insere) {
                compromissosValidos.add(compromisso);
            }
        }

        for (Compromisso comp : compromissosValidos) {
            for (Compromisso c : todos) {
                if (comp.getRecibo() != null && comp.getRecibo().trim().equals(c.getRecibo())) {
                    InternalizacaoDevolucaoBO devolucaoBO = new InternalizacaoDevolucaoBO();
                    InternalizacaoDevolucao internalizacao = new InternalizacaoDevolucao();

                    internalizacao.setIdCompromisso(comp.getId());
                    internalizacao.setTipo(comp.getTipo());
                    internalizacao.setSeqLinha(comp.getSeqLinha());
                    internalizacao.setDescricao(comp.getDescricao());
                    internalizacao.setEntrada(comp.getData());
                    internalizacao.setValor(comp.getValor().doubleValue());
                    internalizacao.setClassificacao("CO");
                    internalizacao.setCentroCusto(comp.getCentroCusto());
                    //* Dados do compromisso associado a devolução      
                    internalizacao.setIdComp(c.getApdId());
                    internalizacao.setTipoComp(c.getApdTp());
                    internalizacao.setSeqLinhaComp(c.getSeqLinha());
                    internalizacao.setNomeFornecedorComp(c.getNomeFornecedor());
//                    InternalizacaoDevolucao old = devolucaoBO.obterPorIdCompromisso(comp.getId());
//                    if( old == null ){
                    devolucaoBO.inserir(internalizacao);
//                    }
                }
            }
        }
        removerCompromissosEstornados(compromissosEstornados, todos);

    }

    /**
     * Caso os compromissos sejam estornados no GEM, devem ser removidos da base
     * de dados do ba produtiva
     *
     * @param compromissosEstornados
     * @param internalizacoesJaReconhecidas
     * @throws AcessoDadosException
     */
    private void removerCompromissosEstornados(Collection<Compromisso> compromissosEstornados, Collection<Compromisso> todos) throws AcessoDadosException {

        InternalizacaoDevolucaoBO devolucaoBO = new InternalizacaoDevolucaoBO();
        for (Compromisso comp : todos) {

            for (Compromisso estornada : compromissosEstornados) {
                if (estornada.getRecibo().equals(comp.getRecibo())) {
                    InternalizacaoDevolucao devolucao = devolucaoBO.obterDevolucao(estornada.getId(), comp.getApdId(), estornada.getSeqLinha());
                    if (devolucao != null) {
                        devolucaoBO.excluir(devolucao);
                    }
                }
            }
        }
    }

    /**
     * Caso os compromissos sejam estornados no GEM, devem ser removidos da base
     * de dados do ba produtiva
     *
     * @param compromissosEstornados
     * @param internalizacoesJaReconhecidas
     * @throws AcessoDadosException
     */
    private void removerTarifasEstornados(Collection<Compromisso> compromissosEstornados) throws AcessoDadosException, AplicacaoException {

        LoteDespesaAplicacaoBO loteBO = new LoteDespesaAplicacaoBO();

        for (Compromisso estornada : compromissosEstornados) {
            LoteDespesaAplicacao lote = loteBO.obterPorIdDespesaAndSeqLinha(estornada.getId(), estornada.getSeqLinha());
            if (lote != null) {
                try {
                    loteBO.excluir(lote);
                } catch (AplicacaoException ex) {
                    Logger.getLogger(ReconhecerDespesas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    /**
     * Rotina de associação de Tarifas Bancárias. Obtem dados do Sistema GEM e
     * grava no banco do baprodutiva
     *
     * @author AJLima
     * @throws AcessoDadosException
     * @throws AplicacaoException
     * @throws IOException
     * @throws Exception
     */
    public void associarTarifasBancarias() throws AcessoDadosException, AplicacaoException, IOException, Exception {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "Inicio Associa\u00e7\u00e3o Tarifas: {0}", Data.formataDataHora(new Date()));
        String[] EVENTO_IOF = {"BJE387", "BJE073", "BJE903", "BJE003", "BJE094"};
        GEMImpl gem = new GEMImpl();
        String banco1 = PropertiesUtil.getProperties().getProperty("banco1");
        String agencia1 = PropertiesUtil.getProperties().getProperty("agencia1");
        String conta1 = PropertiesUtil.getProperties().getProperty("conta1");

        String banco2 = PropertiesUtil.getProperties().getProperty("banco2");
        String agencia2 = PropertiesUtil.getProperties().getProperty("agencia2");
        String conta2 = PropertiesUtil.getProperties().getProperty("conta2");
        Integer dataFiltro = Integer.valueOf(Data.retornaDataInversa(PropertiesUtil.getProperties().getProperty("projeto.dataInicio")));

        Integer dataFinal = Data.converteDataParaInteiro(new Date());

        Collection<Compromisso> compromissos = gem.obterLotesBancariosPorDataEvento(dataFiltro, dataFinal, EVENTO_IOF, banco1, agencia1, conta1);
        compromissos.addAll(gem.obterLotesBancariosPorDataEvento(dataFiltro, dataFinal, EVENTO_IOF, banco2, agencia2, conta2));
        
        for (Compromisso compromisso : compromissos) {
            LoteDespesaAplicacao loteOld = new LoteDespesaAplicacaoBO().obterPorIdDespesaSeqLinhaAndTipo(compromisso.getId(), compromisso.getSeqLinha(), compromisso.getTipo());
            if (loteOld == null) {
                LoteDespesaAplicacao lote = new LoteDespesaAplicacao();
                lote.setIdDespesa(compromisso.getId());
                String categoria = "1";
                if( compromisso.getCentroCusto() != null && compromisso.getCentroCusto().length() > 4){
                    categoria = ""+compromisso.getCentroCusto().charAt(5);
                }
                lote.setCategoria(new CategoriaBO().obterPorPk(Integer.parseInt(categoria)));
                lote.setTipo(compromisso.getTipo());
                lote.setSeqLinha(compromisso.getSeqLinha());
                lote.setCentroCusto("2032010103");
                if (compromisso.getEvento() != null && (compromisso.getEvento().equals("BJE387") || compromisso.getEvento().equals("BJE903"))) {
                    if( compromisso.getEvento().equals("BJE387") ){
                        lote.setCentroCusto(compromisso.getCentroCusto());
                    }
                    lote.setValor(-compromisso.getValor().doubleValue());
                } else {
                    lote.setValor(compromisso.getValor().doubleValue());
                }
                
                lote.setDataPagamento(compromisso.getData());
                lote.setDescricao(compromisso.getDescricao());
                new LoteDespesaAplicacaoDAO().inserir(lote);
            }
        }
        Collection<Compromisso> compromissosEstornados = gem.obterDevolucoesViagemPorContaDataInicioEstornadas(dataFiltro, banco1, agencia1, conta1);
        removerTarifasEstornados(compromissosEstornados);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "Fim Associa\u00e7\u00e3o Tarifas: {0}", Data.formataDataHora(new Date()));
    }

}
