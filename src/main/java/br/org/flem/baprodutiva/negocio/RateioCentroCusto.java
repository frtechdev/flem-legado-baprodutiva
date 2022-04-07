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
 * @author ILFernandes
 */
@Entity
public class RateioCentroCusto extends BaseDTOAb {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_RateioCentroCusto")
    private Integer id;
    
    
    private String apdId;
    private String apdTp;
    private String seqLinha;
    private String centroCusto1;
    private String centroCusto2;
    
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

    public String getCentroCusto1() {
        return centroCusto1;
    }

    public void setCentroCusto1(String centroCusto1) {
        this.centroCusto1 = centroCusto1;
    }

    public String getCentroCusto2() {
        return centroCusto2;
    }

    public void setCentroCusto2(String centroCusto2) {
        this.centroCusto2 = centroCusto2;
    }

    public String getSeqLinha() {
        return seqLinha;
    }

    public void setSeqLinha(String seqLinha) {
        this.seqLinha = seqLinha;
    }
    
    
    
    @Override
    public Serializable getPk() {
        return this.getId();
    }
}
