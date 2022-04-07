/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.dto;

import br.org.flem.fw.persistencia.dto.LancamentoInterface;
import br.org.flem.baprodutiva.negocio.Aditivo;
import br.org.flem.baprodutiva.negocio.Parcela;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author ilfernandes
 */
public class RelatorioGastosContratoDTO {
    
    private String numeroProcesso;
    private String entidade;
    private String objeto;
    private String numeroContrato;
    private String vigencia;
    private Collection<Aditivo> aditivos;
    private Double valorAcordo;
    private Collection<Parcela> parcelas;
    private Double pagamentoAcumulado;
    private Double saldoAPagar;
    private String observacao;
    private Collection<LancamentoInterface> lancamentos = new ArrayList<LancamentoInterface>();
    private Double devolucao;

    public String getEntidade() {
        return entidade;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Double getPagamentoAcumulado() {
        return pagamentoAcumulado;
    }

    public void setPagamentoAcumulado(Double pagamentoAcumulado) {
        this.pagamentoAcumulado = pagamentoAcumulado;
    }

    public Double getSaldoAPagar() {
        return saldoAPagar;
    }

    public void setSaldoAPagar(Double saldoAPagar) {
        this.saldoAPagar = saldoAPagar;
    }

    public Double getValorAcordo() {
        return valorAcordo;
    }

    public void setValorAcordo(Double valorAcordo) {
        this.valorAcordo = valorAcordo;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public Collection<Aditivo> getAditivos() {
        return aditivos;
    }

    public void setAditivos(Collection<Aditivo> aditivos) {
        this.aditivos = aditivos;
    }

    public Collection<Parcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(Collection<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

    public Collection<LancamentoInterface> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(Collection<LancamentoInterface> lancamentos) {
        this.lancamentos = lancamentos;
    }

    public Double getDevolucao() {
        return devolucao;
    }

    public void setDevolucao(Double devolucao) {
        this.devolucao = devolucao;
    }
    
}
