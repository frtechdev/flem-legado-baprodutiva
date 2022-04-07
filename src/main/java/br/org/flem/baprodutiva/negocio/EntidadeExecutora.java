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
import javax.persistence.ManyToMany;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
/**
 *
 * @author fcsilva
 */
@Entity
@DataTransferObject
public class EntidadeExecutora extends BaseDTOAb implements java.io.Serializable {

    @RemoteProperty
    @Id
    @Column(name = "id_entidadeexecutora")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique=true )
    private String descricao;
    
    @ManyToMany
    private Set<CompositeFolha> compositeFolhas = new HashSet<CompositeFolha>();

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

    public Serializable getPk() {
        return id;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public Set getCompositeFolhas() {
        return compositeFolhas;
    }

    public void setCompositeFolhas(Set compositeFolhas) {
        this.compositeFolhas = compositeFolhas;
    }
    
}
