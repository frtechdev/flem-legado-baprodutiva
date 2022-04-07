package br.org.flem.baprodutiva.relatorio;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author dbbarreto
 */
public class FonteCategoriaCompletoDTO implements Serializable {

    private Collection<FonteCategoriaDTO> fonteCategorias = new HashSet<FonteCategoriaDTO>();
    private Double saldoValorGEF = 0D;
    private Double saldoValorGEFAno = 0D;
    private Double saldoValorGEFAcumulado = 0D;
    private Double saldoInicialValorGEF = 0D;
    private Double saldoInicialValorGEFAno = 0D;
    private Double saldoInicialValorGEFAcumulado = 0D;
    private Double saldoInicialAplicacao = 0D;
    private Double saldoInicialAplicacaoAno = 0D;
    private Double saldoInicialAplicacaoAcumulado = 0D;
    private Double saldoAplicacao = 0D;
    private Double saldoAplicacaoAno = 0D;
    private Double saldoAplicacaoAcumulado = 0D;
    private Double saldoWB = 0D;
    private Double saldoWBAno = 0D;
    private Double saldoWBAcumulado = 0D;
    private Double saldoInicialWB = 0D;
    private Double saldoInicialWBAno = 0D;
    private Double saldoInicialWBAcumulado = 0D;

    public Collection<FonteCategoriaDTO> getFonteCategorias() {
        return fonteCategorias;
    }

    public void setFonteCategorias(Collection<FonteCategoriaDTO> fonteCategorias) {
        this.fonteCategorias = fonteCategorias;
    }

    public Double getSaldoInicialValorGEF() {
        return saldoInicialValorGEF;
    }

    public void setSaldoInicialValorGEF(Double saldoInicialValorGEF) {
        this.saldoInicialValorGEF = saldoInicialValorGEF;
    }

    public Double getSaldoInicialValorGEFAcumulado() {
        return saldoInicialValorGEFAcumulado;
    }

    public void setSaldoInicialValorGEFAcumulado(Double saldoInicialValorGEFAcumulado) {
        this.saldoInicialValorGEFAcumulado = saldoInicialValorGEFAcumulado;
    }

    public Double getSaldoValorGEF() {
        return saldoValorGEF;
    }

    public void setSaldoValorGEF(Double saldoValorGEF) {
        this.saldoValorGEF = saldoValorGEF;
    }

    public Double getSaldoValorGEFAcumulado() {
        return saldoValorGEFAcumulado;
    }

    public void setSaldoValorGEFAcumulado(Double saldoValorGEFAcumulado) {
        this.saldoValorGEFAcumulado = saldoValorGEFAcumulado;
    }

    public Double getSaldoInicialValorGEFAno() {
        return saldoInicialValorGEFAno;
    }

    public void setSaldoInicialValorGEFAno(Double saldoInicialValorGEFAno) {
        this.saldoInicialValorGEFAno = saldoInicialValorGEFAno;
    }

    public Double getSaldoValorGEFAno() {
        return saldoValorGEFAno;
    }

    public void setSaldoValorGEFAno(Double saldoValorGEFAno) {
        this.saldoValorGEFAno = saldoValorGEFAno;
    }

    public Double getSaldoFinalValorGEF() {
        return saldoValorGEF + saldoInicialValorGEF;
    }

    public Double getSaldoFinalValorGEFAcumulado() {
        return saldoValorGEFAcumulado + saldoInicialValorGEFAcumulado;
    }

    public Double getSaldoFinalValorGEFAno() {
        return saldoValorGEFAno + saldoInicialValorGEFAno;
    }

    public Double getSaldoAplicacao() {
        return saldoAplicacao;
    }

    public void setSaldoAplicacao(Double saldoAplicacao) {
        this.saldoAplicacao = saldoAplicacao;
    }

    public Double getSaldoWB() {
        return saldoWB;
    }

    public void setSaldoWB(Double saldoWB) {
        this.saldoWB = saldoWB;
    }

    public Double getSaldoAplicacaoAno() {
        return saldoAplicacaoAno;
    }

    public void setSaldoAplicacaoAno(Double saldoAplicacaoAno) {
        this.saldoAplicacaoAno = saldoAplicacaoAno;
    }

    public Double getSaldoAplicacaoAcumulado() {
        return saldoAplicacaoAcumulado;
    }

    public void setSaldoAplicacaoAcumulado(Double saldoAplicacaoAcumulado) {
        this.saldoAplicacaoAcumulado = saldoAplicacaoAcumulado;
    }

    public Double getSaldoWBAno() {
        return saldoWBAno;
    }

    public void setSaldoWBAno(Double saldoWBAno) {
        this.saldoWBAno = saldoWBAno;
    }

    public Double getSaldoWBAcumulado() {
        return saldoWBAcumulado;
    }

    public void setSaldoWBAcumulado(Double saldoWBAcumulado) {
        this.saldoWBAcumulado = saldoWBAcumulado;
    }

    public Double getSaldoInicialAplicacao() {
        return saldoInicialAplicacao;
    }

    public void setSaldoInicialAplicacao(Double saldoInicialAplicacao) {
        this.saldoInicialAplicacao = saldoInicialAplicacao;
    }

    public Double getSaldoInicialAplicacaoAno() {
        return saldoInicialAplicacaoAno;
    }

    public void setSaldoInicialAplicacaoAno(Double saldoInicialAplicacaoAno) {
        this.saldoInicialAplicacaoAno = saldoInicialAplicacaoAno;
    }

    public Double getSaldoInicialAplicacaoAcumulado() {
        return saldoInicialAplicacaoAcumulado;
    }

    public void setSaldoInicialAplicacaoAcumulado(Double saldoInicialAplicacaoAcumulado) {
        this.saldoInicialAplicacaoAcumulado = saldoInicialAplicacaoAcumulado;
    }

    public Double getSaldoInicialWB() {
        return saldoInicialWB;
    }

    public void setSaldoInicialWB(Double saldoInicialWB) {
        this.saldoInicialWB = saldoInicialWB;
    }

    public Double getSaldoInicialWBAno() {
        return saldoInicialWBAno;
    }

    public void setSaldoInicialWBAno(Double saldoInicialWBAno) {
        this.saldoInicialWBAno = saldoInicialWBAno;
    }

    public Double getSaldoInicialWBAcumulado() {
        return saldoInicialWBAcumulado;
    }

    public void setSaldoInicialWBAcumulado(Double saldoInicialWBAcumulado) {
        this.saldoInicialWBAcumulado = saldoInicialWBAcumulado;
    }
    
}
