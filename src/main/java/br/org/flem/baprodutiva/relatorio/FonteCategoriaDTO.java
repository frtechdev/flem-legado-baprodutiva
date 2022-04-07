package br.org.flem.baprodutiva.relatorio;

import java.io.Serializable;

/**
 *
 * @author dbbarreto
 */
public class FonteCategoriaDTO implements Serializable {

    public static final String TIPO_FONTE = "I. Fontes";
    //public static final String TIPO_RENDIMENTO = "II. Rendimento";
    public static final String TIPO_USOS = "II. Usos";
    private String tipo;
    private String categoria;
    private String nome;
    private Double valorGEF = 0D;
    private Double valorGEFAno = 0D;
    private Double valorGEFAcumulado = 0D;
    private Double valorRendimento = 0d;
    private Double valorRendimentoAno = 0d;
    private Double valorRendimentoAcumulado = 0D;
    private Double valorPrevisto = 0D;
    private Double valorPrevistoAno = 0D;
    private Double valorPrevistoAcumulado = 0D;
    private Double aDesembolsar = 0D;

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValorGEFAno() {
        return valorGEFAno;
    }

    public void setValorGEFAno(Double valorGEFAno) {
        this.valorGEFAno = valorGEFAno;
    }

    public Double getValorGEF() {
        return valorGEF;
    }

    public void setValorGEF(Double valorGEF) {
        this.valorGEF = valorGEF;
    }

    public Double getValorGEFAcumulado() {
        return valorGEFAcumulado;
    }

    public void setValorGEFAcumulado(Double valorGEFAcumulado) {
        this.valorGEFAcumulado = valorGEFAcumulado;
    }

    public Double getTotal() {
        return (this.valorGEF);
    }

    public Double getTotalAcumulado() {
        return (this.valorGEFAcumulado);
    }

    public Double getTotalAno() {
        return (this.valorGEFAno);
    }

    public Double getValorRendimento() {
        return valorRendimento;
    }

    public void setValorRendimento(Double valorRendimento) {
        this.valorRendimento = valorRendimento;
    }

    public Double getValorRendimentoAno() {
        return valorRendimentoAno;
    }

    public void setValorRendimentoAno(Double valorRendimentoAno) {
        this.valorRendimentoAno = valorRendimentoAno;
    }

    public Double getValorRendimentoAcumulado() {
        return valorRendimentoAcumulado;
    }

    public void setValorRendimentoAcumulado(Double valorRendimentoAcumulado) {
        this.valorRendimentoAcumulado = valorRendimentoAcumulado;
    }

    public Double getValorPrevisto() {
        return valorPrevisto;
    }

    public void setValorPrevisto(Double valorPrevisto) {
        this.valorPrevisto = valorPrevisto;
    }

    public Double getValorPrevistoAno() {
        return valorPrevistoAno;
    }

    public void setValorPrevistoAno(Double valorPrevistoAno) {
        this.valorPrevistoAno = valorPrevistoAno;
    }

    public Double getValorPrevistoAcumulado() {
        return valorPrevistoAcumulado;
    }

    public void setValorPrevistoAcumulado(Double valorPrevistoAcumulado) {
        this.valorPrevistoAcumulado = valorPrevistoAcumulado;
    }

    public Double getaDesembolsar() {
        return aDesembolsar;
    }

    public void setaDesembolsar(Double aDesembolsar) {
        this.aDesembolsar = aDesembolsar;
    }

    @Override
    public String toString() {
        return this.getTipo() + " - " + this.getCategoria() + " - " + this.getTipo() + " - " + this.getNome() + " - " + this.valorGEF + " - " + this.valorGEFAcumulado + "\n";
    }

}