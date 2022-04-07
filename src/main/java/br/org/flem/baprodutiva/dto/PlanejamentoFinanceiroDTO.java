package br.org.flem.baprodutiva.dto;

/**
 *
 * @author essantos
 */
public class PlanejamentoFinanceiroDTO {

    private String id;

    private String planejamento;

    private Double valor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Double getValorGef() {
        return valor;
    }

    public void setValorGef(Double valorGef) {
        this.valor = valorGef;
    }

    public String getPlanejamento() {
        return planejamento;
    }

    public void setPlanejamento(String planejamento) {
        this.planejamento = planejamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

}
