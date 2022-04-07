package br.org.flem.baprodutiva.relatorio;

/**
 *
 * @author essantos
 */
public class PlanejamentoDTO {

    private String categoria;
    private String subcategoria;
    private String atividade;
    private String entidadesExecutoras;

    private Double valor = 0D;
    
    private boolean mostrar = true;

    public boolean getMostrar() {
        return mostrar;
    }

    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getEntidadesExecutoras() {
        return entidadesExecutoras;
    }

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

    public void setEntidadesExecutoras(String entidadesExecutoras) {
        this.entidadesExecutoras = entidadesExecutoras;
    }

}
