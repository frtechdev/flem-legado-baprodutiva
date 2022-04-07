package br.org.flem.baprodutiva.relatorio;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author dbbarreto
 */
public class FonteCategoriaCompletoDTO_old implements Serializable {

    private Collection<FonteCategoriaDTO> fonteCategorias = new HashSet<FonteCategoriaDTO>();
    private Double saldoValorGEF = 0D;
    private Double saldoValorCeara = 0D;
    private Double saldoValorBahia = 0D;
    private Double saldoValorComunidade = 0D;
    private Double saldoValorGEFAno = 0D;
    private Double saldoValorCearaAno = 0D;
    private Double saldoValorBahiaAno = 0D;
    private Double saldoValorComunidadeAno = 0D;
    private Double saldoValorGEFAcumulado = 0D;
    private Double saldoValorCearaAcumulado = 0D;
    private Double saldoValorBahiaAcumulado = 0D;
    private Double saldoValorComunidadeAcumulado = 0D;
    private Double saldoInicialValorGEF = 0D;
    private Double saldoInicialValorCeara = 0D;
    private Double saldoInicialValorBahia = 0D;
    private Double saldoInicialValorComunidade = 0D;
    private Double saldoInicialValorGEFAno = 0D;
    private Double saldoInicialValorCearaAno = 0D;
    private Double saldoInicialValorBahiaAno = 0D;
    private Double saldoInicialValorComunidadeAno = 0D;
    private Double saldoInicialValorGEFAcumulado = 0D;
    private Double saldoInicialValorCearaAcumulado = 0D;
    private Double saldoInicialValorBahiaAcumulado = 0D;
    private Double saldoInicialValorComunidadeAcumulado = 0D;

    public Collection<FonteCategoriaDTO> getFonteCategorias() {
        return fonteCategorias;
    }

    public void setFonteCategorias(Collection<FonteCategoriaDTO> fonteCategorias) {
        this.fonteCategorias = fonteCategorias;
    }

    public Double getSaldoInicialValorBahia() {
        return saldoInicialValorBahia;
    }

    public void setSaldoInicialValorBahia(Double saldoInicialValorBahia) {
        this.saldoInicialValorBahia = saldoInicialValorBahia;
    }

    public Double getSaldoInicialValorBahiaAcumulado() {
        return saldoInicialValorBahiaAcumulado;
    }

    public void setSaldoInicialValorBahiaAcumulado(Double saldoInicialValorBahiaAcumulado) {
        this.saldoInicialValorBahiaAcumulado = saldoInicialValorBahiaAcumulado;
    }

    public Double getSaldoInicialValorCeara() {
        return saldoInicialValorCeara;
    }

    public void setSaldoInicialValorCeara(Double saldoInicialValorCeara) {
        this.saldoInicialValorCeara = saldoInicialValorCeara;
    }

    public Double getSaldoInicialValorCearaAcumulado() {
        return saldoInicialValorCearaAcumulado;
    }

    public void setSaldoInicialValorCearaAcumulado(Double saldoInicialValorCearaAcumulado) {
        this.saldoInicialValorCearaAcumulado = saldoInicialValorCearaAcumulado;
    }

    public Double getSaldoInicialValorComunidade() {
        return saldoInicialValorComunidade;
    }

    public void setSaldoInicialValorComunidade(Double saldoInicialValorComunidade) {
        this.saldoInicialValorComunidade = saldoInicialValorComunidade;
    }

    public Double getSaldoInicialValorComunidadeAcumulado() {
        return saldoInicialValorComunidadeAcumulado;
    }

    public void setSaldoInicialValorComunidadeAcumulado(Double saldoInicialValorComunidadeAcumulado) {
        this.saldoInicialValorComunidadeAcumulado = saldoInicialValorComunidadeAcumulado;
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

    public Double getSaldoValorBahia() {
        return saldoValorBahia;
    }

    public void setSaldoValorBahia(Double saldoValorBahia) {
        this.saldoValorBahia = saldoValorBahia;
    }

    public Double getSaldoValorBahiaAcumulado() {
        return saldoValorBahiaAcumulado;
    }

    public void setSaldoValorBahiaAcumulado(Double saldoValorBahiaAcumulado) {
        this.saldoValorBahiaAcumulado = saldoValorBahiaAcumulado;
    }

    public Double getSaldoValorCeara() {
        return saldoValorCeara;
    }

    public void setSaldoValorCeara(Double saldoValorCeara) {
        this.saldoValorCeara = saldoValorCeara;
    }

    public Double getSaldoValorCearaAcumulado() {
        return saldoValorCearaAcumulado;
    }

    public void setSaldoValorCearaAcumulado(Double saldoValorCearaAcumulado) {
        this.saldoValorCearaAcumulado = saldoValorCearaAcumulado;
    }

    public Double getSaldoValorComunidade() {
        return saldoValorComunidade;
    }

    public void setSaldoValorComunidade(Double saldoValorComunidade) {
        this.saldoValorComunidade = saldoValorComunidade;
    }

    public Double getSaldoValorComunidadeAcumulado() {
        return saldoValorComunidadeAcumulado;
    }

    public void setSaldoValorComunidadeAcumulado(Double saldoValorComunidadeAcumulado) {
        this.saldoValorComunidadeAcumulado = saldoValorComunidadeAcumulado;
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

    public Double getSaldoInicialValorBahiaAno() {
        return saldoInicialValorBahiaAno;
    }

    public void setSaldoInicialValorBahiaAno(Double saldoInicialValorBahiaAno) {
        this.saldoInicialValorBahiaAno = saldoInicialValorBahiaAno;
    }

    public Double getSaldoInicialValorCearaAno() {
        return saldoInicialValorCearaAno;
    }

    public void setSaldoInicialValorCearaAno(Double saldoInicialValorCearaAno) {
        this.saldoInicialValorCearaAno = saldoInicialValorCearaAno;
    }

    public Double getSaldoInicialValorComunidadeAno() {
        return saldoInicialValorComunidadeAno;
    }

    public void setSaldoInicialValorComunidadeAno(Double saldoInicialValorComunidadeAno) {
        this.saldoInicialValorComunidadeAno = saldoInicialValorComunidadeAno;
    }

    public Double getSaldoInicialValorGEFAno() {
        return saldoInicialValorGEFAno;
    }

    public void setSaldoInicialValorGEFAno(Double saldoInicialValorGEFAno) {
        this.saldoInicialValorGEFAno = saldoInicialValorGEFAno;
    }

    public Double getSaldoValorBahiaAno() {
        return saldoValorBahiaAno;
    }

    public void setSaldoValorBahiaAno(Double saldoValorBahiaAno) {
        this.saldoValorBahiaAno = saldoValorBahiaAno;
    }

    public Double getSaldoValorCearaAno() {
        return saldoValorCearaAno;
    }

    public void setSaldoValorCearaAno(Double saldoValorCearaAno) {
        this.saldoValorCearaAno = saldoValorCearaAno;
    }

    public Double getSaldoValorComunidadeAno() {
        return saldoValorComunidadeAno;
    }

    public void setSaldoValorComunidadeAno(Double saldoValorComunidadeAno) {
        this.saldoValorComunidadeAno = saldoValorComunidadeAno;
    }

    public Double getSaldoValorGEFAno() {
        return saldoValorGEFAno;
    }

    public void setSaldoValorGEFAno(Double saldoValorGEFAno) {
        this.saldoValorGEFAno = saldoValorGEFAno;
    }

    public Double getSaldoFinalValorBahia() {
        return saldoValorBahia+saldoInicialValorBahia;
    }

    public Double getSaldoFinalValorBahiaAcumulado() {
        return saldoValorBahiaAcumulado+saldoInicialValorBahiaAcumulado;
    }

    public Double getSaldoFinalValorBahiaAno() {
        return saldoValorBahiaAno+saldoInicialValorBahiaAno;
    }

    public Double getSaldoFinalValorCeara() {
        return saldoValorCeara+saldoInicialValorCeara;
    }

    public Double getSaldoFinalValorCearaAcumulado() {
        return saldoValorCearaAcumulado+saldoInicialValorCearaAcumulado;
    }

    public Double getSaldoFinalValorCearaAno() {
        return saldoValorCearaAno+saldoInicialValorCearaAno;
    }

    public Double getSaldoFinalValorComunidade() {
        return saldoValorComunidade+saldoInicialValorComunidade;
    }

    public Double getSaldoFinalValorComunidadeAcumulado() {
        return saldoValorComunidadeAcumulado+saldoInicialValorComunidadeAcumulado;
    }

    public Double getSaldoFinalValorComunidadeAno() {
        return saldoValorComunidadeAno+saldoInicialValorComunidadeAno;
    }

    public Double getSaldoFinalValorGEF() {
        return saldoValorGEF+saldoInicialValorGEF;
    }

    public Double getSaldoFinalValorGEFAcumulado() {
        return saldoValorGEFAcumulado+saldoInicialValorGEFAcumulado;
    }

    public Double getSaldoFinalValorGEFAno() {
        return saldoValorGEFAno+saldoInicialValorGEFAno;
    }
    
    
}
