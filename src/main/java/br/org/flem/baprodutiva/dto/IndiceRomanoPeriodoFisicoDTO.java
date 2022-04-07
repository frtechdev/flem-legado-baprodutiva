package br.org.flem.baprodutiva.dto;

import br.org.flem.baprodutiva.negocio.Planejamento;

/**
 *
 * @author ilfernandes
 */
public class IndiceRomanoPeriodoFisicoDTO {

    private Integer idFisico;
    private Planejamento planejamento;
    private Double porcentagem;

    public Integer getIdFisico() {
        return idFisico;
    }

    public void setIdFisico(Integer idFisico) {
        this.idFisico = idFisico;
    }

    public IndiceRomanoPeriodoFisicoDTO() {
    }

    public Planejamento getPlanejamento() {
        return planejamento;
    }

    public void setPlanejamento(Planejamento planejamento) {
        this.planejamento = planejamento;
    }

    public Double getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(Double porcentagem) {
        this.porcentagem = porcentagem;
    }
}
