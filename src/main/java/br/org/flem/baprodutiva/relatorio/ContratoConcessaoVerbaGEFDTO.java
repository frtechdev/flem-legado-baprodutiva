/*


 */
package br.org.flem.baprodutiva.relatorio;

import java.io.Serializable;

/**
 *
 * @author fcsilva
 */
public class ContratoConcessaoVerbaGEFDTO implements Serializable {

    private Double saldo;
    private Double desembolsoContaEspecial;
    private Double reembolsoContaEspecial;
    private Double aplicadosCategoriasInversao;
    private Double contaEspecialEmDolar;
    private Double contaEmReais;

    public Double getAplicadosCategoriasInversao() {
        return aplicadosCategoriasInversao;
    }

    public void setAplicadosCategoriasInversao(Double aplicadosCategoriasInversao) {
        this.aplicadosCategoriasInversao = aplicadosCategoriasInversao;
    }

    public Double getContaEmReais() {
        return contaEmReais;
    }

    public void setContaEmReais(Double contaEmReais) {
        this.contaEmReais = contaEmReais;
    }

    public Double getContaEspecialEmDolar() {
        return contaEspecialEmDolar;
    }

    public void setContaEspecialEmDolar(Double contaEspecialEmDolar) {
        this.contaEspecialEmDolar = contaEspecialEmDolar;
    }

    public Double getDesembolsoContaEspecial() {
        return desembolsoContaEspecial;
    }

    public void setDesembolsoContaEspecial(Double desembolsoContaEspecial) {
        this.desembolsoContaEspecial = desembolsoContaEspecial;
    }

    public Double getReembolsoContaEspecial() {
        return reembolsoContaEspecial;
    }

    public void setReembolsoContaEspecial(Double reembolsoContaEspecial) {
        this.reembolsoContaEspecial = reembolsoContaEspecial;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
    
    private Double getTotalDisponivel() {
        return null;
    }    
    
}
