/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.dao.TransferenciaBancariaDAO;
import br.org.flem.baprodutiva.dto.DevolucaoDTO;
import br.org.flem.baprodutiva.negocio.TransferenciaBancaria;
import br.org.flem.fw.persistencia.dto.Compromisso;
import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author AJLima
 */
public class TransferenciaBancariaBO extends BaseBOAb<TransferenciaBancaria> {
    
    public TransferenciaBancariaBO() throws AplicacaoException {
        super(new TransferenciaBancariaDAO());
    }

    public TransferenciaBancaria obterPorTipoId(String apdid, String apdtp) throws AplicacaoException{
        TransferenciaBancaria  tran = new TransferenciaBancariaDAO().obterPorApdIdEApdTp(apdid, apdtp);
        return tran;
    }
    
    public Collection<Compromisso> obterCompromissos() throws AcessoDadosException {
        Collection<TransferenciaBancaria> transferencias = dao.obterTodos();
        
        Collection<Compromisso> compromissos = new ArrayList<Compromisso>();
        
        for (TransferenciaBancaria transferencia : transferencias) {
            Compromisso compromisso = new Compromisso();
            
            compromisso.setApdId(transferencia.getApdId());
            compromisso.setApdTp(transferencia.getApdTp());
            compromisso.setData(transferencia.getDataPagamento());
            compromisso.setDataExibicao(transferencia.getDataPagamento());
            compromisso.setDescricao(transferencia.getDescricao());
            compromisso.setCentroCusto(transferencia.getCentroCusto());
            compromisso.setValor(BigDecimal.valueOf(transferencia.getValor()));
            
            compromissos.add(compromisso);
        }
        return compromissos;
    }

    public Collection<DevolucaoDTO> obterCompromissosSoe() throws AcessoDadosException {
        Collection<TransferenciaBancaria> transferencias = dao.obterTodos();

        Collection<DevolucaoDTO> compromissos = new ArrayList<DevolucaoDTO>();

        for (TransferenciaBancaria transf : transferencias) {
            DevolucaoDTO compromisso = new DevolucaoDTO();

            compromisso.setId(transf.getId().toString());
            compromisso.setTipo("TRANSFERENCIA");
            compromisso.setApdId(transf.getApdId());
            compromisso.setApdTp(transf.getApdTp());
            compromisso.setDescricao(transf.getDescricao());
            compromisso.setData(transf.getDataPagamento());
            compromisso.setEntrada(transf.getDataPagamento());
            compromisso.setValor(new BigDecimal(transf.getValor()));
            compromisso.setNomeFornecedor(transf.getNomeFornecedor());
            compromisso.setCentroCusto(transf.getCentroCusto());
            
            compromissos.add(compromisso);
        }

        return compromissos;
    }
    
    public Collection<TransferenciaBancaria> obterPorPeriodo(Date inicio, Date fim) {
        return ((TransferenciaBancariaDAO)this.dao).obterPorPeriodo(inicio, fim);
    }
    
    public Collection<TransferenciaBancaria> obterTodosOrdemData(){
         
        return ((TransferenciaBancariaDAO)this.dao).obterTodosOrdemData();
    }
    
    public TransferenciaBancaria obterPorApdIdEApdTp(String apdId, String apdTp){
        return ((TransferenciaBancariaDAO)this.dao).obterPorApdIdEApdTp(apdId, apdTp);
    }
    
    
}
