/*


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
import org.directwebremoting.annotations.RemoteProperty;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author fcsilva
 */
//quantidade e unidade de medida
@Entity
public class MedidaPrevista extends BaseDTOAb implements java.io.Serializable{
    
    @RemoteProperty
    @Id
    @Column(name = "id_medidaprevista")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;    
    
    private Integer quantidade;
    
    @ManyToOne
    @JoinColumn(name = "id_unidademedida", nullable = false)
    @Cascade(CascadeType.SAVE_UPDATE)    
    private UnidadeMedida unidadeMedida;

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    @Override
    public Serializable getPk() {
        return id;
    }
}
