package br.org.flem.baprodutiva.relatorio;

import java.io.Serializable;

/**
 *
 * @author emsilva
 */
public class GerencialFinanceiroDTO implements Serializable {

    private String categoria;
    private String subcategoria;
    private String atividade;

    private Double planejadoPeriodo = 0D;
    private Double aplicadoPeriodo = 0D;
    private Double planejadoAno = 0D;
    private Double aplicadoAno = 0D;
    private Double planejadoAcumulado = 0D;
    private Double aplicadoAcumulado = 0D;
    private Double planejadoAcumuladoCategoria = 0D;

    private String dataPeriodo = "";

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public Double getPlanejadoPeriodo() {
        return planejadoPeriodo;
    }

    public void setPlanejadoPeriodo(Double planejadoPeriodo) {
        this.planejadoPeriodo = planejadoPeriodo;
    }

    public Double getAplicadoPeriodo() {
        return aplicadoPeriodo;
    }

    public void setAplicadoPeriodo(Double aplicadoPeriodo) {
        this.aplicadoPeriodo = aplicadoPeriodo;
    }

    public Double getPlanejadoAno() {
        return planejadoAno;
    }

    public void setPlanejadoAno(Double planejadoAno) {
        this.planejadoAno = planejadoAno;
    }

    public Double getAplicadoAno() {
        return aplicadoAno;
    }

    public void setAplicadoAno(Double aplicadoAno) {
        this.aplicadoAno = aplicadoAno;
    }

    public Double getPlanejadoAcumulado() {
        return planejadoAcumulado;
    }

    public void setPlanejadoAcumulado(Double planejadoAcumulado) {
        this.planejadoAcumulado = planejadoAcumulado;
    }

    public Double getAplicadoAcumulado() {
        return aplicadoAcumulado;
    }

    public void setAplicadoAcumulado(Double aplicadoAcumulado) {
        this.aplicadoAcumulado = aplicadoAcumulado;
    }

    public Double getPlanejadoAcumuladoCategoria() {
        return planejadoAcumuladoCategoria;
    }

    public void setPlanejadoAcumuladoCategoria(Double planejadoAcumuladoCategoria) {
        this.planejadoAcumuladoCategoria = planejadoAcumuladoCategoria;
    }

    public String getDataPeriodo() {
        return dataPeriodo;
    }

    public void setDataPeriodo(String dataPeriodo) {
        this.dataPeriodo = dataPeriodo;
    }
    
    public Double getVariacaoPeriodo() {
        return this.planejadoPeriodo == 0 ? 0 : (this.aplicadoPeriodo * 100 / this.planejadoPeriodo);
    }

    public Double getVariacaoAcumulado() {
        return this.planejadoAcumulado == 0 ? 0 : (this.aplicadoAcumulado * 100 / this.planejadoAcumulado);
    }
    
    public Double getVariacaoAno() {
        return this.planejadoAno == 0 ? 0 : (this.aplicadoAno * 100 / this.planejadoAno);
    }
    
    public Double getTotal() {
        return Math.abs(this.planejadoAcumulado - this.aplicadoAcumulado);
    }


}