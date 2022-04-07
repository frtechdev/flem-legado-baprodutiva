/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.negocio;

import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author ILFernandes
 */
@Entity
public class PlanejadoComponente extends BaseDTOAb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_planejadocomponente")
    private Integer id;

    @OneToOne
    @JoinColumn(name="id_poa")
    private Planejamento poa = new Planejamento();
    private Double gef = 0d;
    private Double gefReal = 0d;
    private Double contraPartida = 0d;


    public Double getContraPartida() {
        return contraPartida;
    }

    public void setContraPartida(Double contraPartida) {
        this.contraPartida = contraPartida;
    }

    public Double getGef() {
        return gef;
    }

    public void setGef(Double gef) {
        this.gef = gef;
    }

    public Double getGefReal() {
        return gefReal;
    }

    public void setGefReal(Double gefReal) {
        this.gefReal = gefReal;
    }

    public Planejamento getPoa() {
        return poa;
    }

    public void setPoa(Planejamento poa) {
        this.poa = poa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Serializable getPk() {
        return this.getId();
    }
}
