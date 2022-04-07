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
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author dbbarreto
 */
@Entity
public class FonteArquivo extends BaseDTOAb {
    
    @Id
    @Column(name = "id_fontearquivo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_fonte", nullable = false)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Fonte fonte;
    
    /**
     * Id do arquivo no GED.
     */
    private Integer arquivo;
    
    private String descricao;

    @Override
    public Serializable getPk() {
        return this.getId();
    }

    public Fonte getFonte() {
        return fonte;
    }

    public void setFonte(Fonte fonte) {
        this.fonte = fonte;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArquivo() {
        return arquivo;
    }

    public void setArquivo(Integer arquivo) {
        this.arquivo = arquivo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
