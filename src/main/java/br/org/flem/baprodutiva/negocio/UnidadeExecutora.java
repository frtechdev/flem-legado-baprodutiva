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
import org.directwebremoting.annotations.RemoteProperty;
/**
 *
 * @author fcsilva
 */
@Entity
public class UnidadeExecutora extends BaseDTOAb implements java.io.Serializable {

    @RemoteProperty
    @Id
    @Column(name = "id_unidadeexecutora")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String descricao;

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
}
