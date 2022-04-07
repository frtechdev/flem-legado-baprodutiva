/*
 * Documento.java
 * 
 * Created on 19/09/2007, 16:48:02
 * 


 */

package br.org.flem.baprodutiva.negocio;

import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * Essa classe agrupa os Tipos, que por sua vez agrupam as Fontes. Exemplo:
 * 
 * - Doação
 * - Contrapartida
 * 
 * @author dbbarreto
 */
@DataTransferObject
@Entity
public class GrupoTipoFonte extends BaseDTOAb {
    
    @RemoteProperty
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_grupotipo")
    private Integer id;
    
    @RemoteProperty
    private String descricao;
    
    @OneToMany(mappedBy="grupoTipo")
    @Cascade(CascadeType.SAVE_UPDATE)
    private Set<TipoFonte> tipos = new HashSet<TipoFonte>();
    
    public Serializable getPk() {
        return this.getId();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<TipoFonte> getTipos() {
        return tipos;
    }

    public void setTipos(Set<TipoFonte> tipos) {
        this.tipos = tipos;
    }
    
}