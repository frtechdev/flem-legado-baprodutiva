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
/**
 *
 * @author fcsilva
 */
@Entity
public class UnidadeMedida extends BaseDTOAb implements java.io.Serializable{
    @Id
    @Column(name = "id_unidademedida")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;        
    
    private String descricao;

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
    
    @Override
    public Serializable getPk() {
        return id;
    }

    @Override
    public String toString() {
        return descricao;
    }
    
    
    
}
