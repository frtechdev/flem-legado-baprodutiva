/*
 * Documento.java
 * 
 * Created on 19/09/2007, 16:48:02
 * 


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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dbbarreto
 * 
 */
@Entity
public class Receita extends BaseDTOAb {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_despesa")
    private Integer id;
    
    @Temporal(TemporalType.DATE)
    private Date data;
    
    private Double valor;
    
    private Double conversao;
    
    public Serializable getPk() {
        return this.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getConversao() {
        return conversao;
    }

    public void setConversao(Double conversao) {
        this.conversao = conversao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}