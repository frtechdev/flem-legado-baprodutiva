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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Formula;

/**
 *
 * @author mjpereira
 */
@DataTransferObject
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class CompositeABS extends BaseDTOAb implements CompositeIF {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_composite")
    @RemoteProperty
    private Integer id;
    
    
    @RemoteProperty
    @Column(length=150)
    private String descricao;
    
    @OneToMany(mappedBy="composite")
    @Cascade({CascadeType.SAVE_UPDATE,CascadeType.DELETE_ORPHAN})
    private Set<FinanceiroPrevisto> financeirosPrevistos = new HashSet<FinanceiroPrevisto>();
    
    @Formula("(select top 1 f.valor from FinanceiroPrevisto f where f.id_composite = id_composite order by f.data desc)")
    private Double valorFinanceiroPrevisto;

    @Override
    public String getDescricao(){
        return this.descricao;
    }


    @Override
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }



    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Override
    public Serializable getPk() {
        return this.getId();
    }

    public Set<FinanceiroPrevisto> getFinanceirosPrevistos() {
        return financeirosPrevistos;
    }

    public void setFinanceirosPrevistos(Set<FinanceiroPrevisto> financeirosPrevistos) {
        this.financeirosPrevistos = financeirosPrevistos;
    }

    public Double getValorFinanceiroPrevisto() {
        return valorFinanceiroPrevisto;
    }

    public void setValorFinanceiroPrevisto(Double valorFinanceiroPrevisto) {
        this.valorFinanceiroPrevisto = valorFinanceiroPrevisto;
    }

}
