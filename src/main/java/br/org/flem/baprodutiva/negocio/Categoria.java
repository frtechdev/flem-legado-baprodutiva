package br.org.flem.baprodutiva.negocio;

import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * @author dbbarreto
 * 
 */
@DataTransferObject
@Entity
public class Categoria extends BaseDTOAb {
    
    @RemoteProperty
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_categoria")
    private Integer id;
    
    private String descricao;
    
    @OneToMany(mappedBy="categoria")
    @Cascade(CascadeType.SAVE_UPDATE)
    private Set<CompositeNoAtividade> atividades = new HashSet<CompositeNoAtividade>();

    @Enumerated(EnumType.STRING)
    private Origem origem;
    
    private Double planejado = 0d;

    @Override
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

    public Set<CompositeNoAtividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(Set<CompositeNoAtividade> atividades) {
        this.atividades = atividades;
    }

    public Origem getOrigem() {
        return origem;
    }

    public void setOrigem(Origem origem) {
        this.origem = origem;
    }

    public Double getPlanejado() {
        return planejado;
    }

    public void setPlanejado(Double planejado) {
        this.planejado = planejado;
    }

}