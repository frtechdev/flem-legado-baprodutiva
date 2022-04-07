/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.negocio;

import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author AJLima
 */

@Entity
@DataTransferObject
public class SubCategoria extends BaseDTOAb{
    
    
    @RemoteProperty
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_subcategoria")
    private Integer id;
    
    @RemoteProperty
    private String descricao;
    
    @ManyToOne
    @JoinColumn(name="id_categoria", nullable=false)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Categoria categoria;

    
    
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public Serializable getPk() {
        return this.getId();
    }
    
    
    
}
