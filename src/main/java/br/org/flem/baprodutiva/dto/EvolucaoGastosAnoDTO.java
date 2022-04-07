/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.dto;


/**
 *
 * @author ILFernandes
 */
public class EvolucaoGastosAnoDTO {
    
    private Integer anoInicial;
    private Integer anoFinal;
    private Double desembolsado = 0d;
    private Double aDesembolsar = 0d;
    private Double totalGeralAno = 0d;

    public Integer getAnoFinal() {
        return anoFinal;
    }

    public void setAnoFinal(Integer anoFinal) {
        this.anoFinal = anoFinal;
    }

    public Integer getAnoInicial() {
        return anoInicial;
    }

    public void setAnoInicial(Integer anoInicial) {
        this.anoInicial = anoInicial;
    }
    
    public Double getaDesembolsar() {
        return aDesembolsar;
    }

    public void setaDesembolsar(Double aDesembolsar) {
        this.aDesembolsar = aDesembolsar;
    }

    public Double getDesembolsado() {
        return desembolsado;
    }

    public void setDesembolsado(Double desembolsado) {
        this.desembolsado = desembolsado;
    }

    public Double getTotal() {
        return desembolsado+aDesembolsar;
    }

    public Double getTotalGeralAno() {
        return totalGeralAno;
    }

    public void setTotalGeralAno(Double totalGeralAno) {
        this.totalGeralAno = totalGeralAno;
    }
   
}
