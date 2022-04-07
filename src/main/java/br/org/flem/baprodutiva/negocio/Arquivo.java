/*


 */

package br.org.flem.baprodutiva.negocio;

import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author dbbarreto
 */
public class Arquivo extends BaseDTOAb{
    
    @Id
    @Column(name = "id_fonte")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String descricao;
    
    @OneToOne
    @JoinColumn(name = "id_fontearquivo", nullable = false)
    @Cascade(CascadeType.SAVE_UPDATE)
    private FonteArquivo fonteArquivo;

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
    
    
}
