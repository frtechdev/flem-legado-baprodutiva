/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.negocio;

import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author ilfernandes
 */

@Entity
public class Aditivo extends BaseDTOAb {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_aditivo")
    private Integer id;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date novaData;
    
    @ManyToOne
    @JoinColumn(name="id_contrato", nullable=false)
    private Contrato contrato = new Contrato();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getNovaData() {
        return novaData;
    }

    public void setNovaData(Date novaData) {
        this.novaData = novaData;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    @Override
    public Serializable getPk() {
        return this.getId();
    }
    
    
}
