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

/**
 *
 * @author AJLima
 */

@Entity
public class CorrecaoNome extends BaseDTOAb {
    
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_correcaonome")
        
    private Integer id;
    private String apdId;
    private String apdTp;
    private String seqLinha;
    private String descricao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApdId() {
        return apdId;
    }

    public void setApdId(String apdId) {
        this.apdId = apdId;
    }

    public String getApdTp() {
        return apdTp;
    }

    public void setApdTp(String apdTp) {
        this.apdTp = apdTp;
    }

    public String getSeqLinha() {
        return seqLinha;
    }

    public void setSeqLinha(String seqLinha) {
        this.seqLinha = seqLinha;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public Serializable getPk() {
        return id;
    }
    
    
}
