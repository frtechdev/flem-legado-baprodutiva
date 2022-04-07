/*
 * Documento.java
 * 
 * Created on 19/09/2007, 16:48:02
 * 


 */

package br.org.flem.baprodutiva.negocio;

import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author dbbarreto
 * 
 */
@Entity
public class RetornoViagem extends BaseDTOAb {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_retornoviagem")
    private Integer id;
    
    private String motivo;
    
    private String idCompromisso1;
    
    private String idCompromisso2;
    
    private String numeroSequencia;
    
    
    public Serializable getPk() {
        return this.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdCompromisso1() {
        return idCompromisso1;
    }

    public void setIdCompromisso1(String idCompromisso1) {
        this.idCompromisso1 = idCompromisso1;
    }

    public String getIdCompromisso2() {
        return idCompromisso2;
    }

    public void setIdCompromisso2(String idCompromisso2) {
        this.idCompromisso2 = idCompromisso2;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getNumeroSequencia() {
        return numeroSequencia;
    }

    public void setNumeroSequencia(String numeroSequencia) {
        this.numeroSequencia = numeroSequencia;
    }
    
    

}