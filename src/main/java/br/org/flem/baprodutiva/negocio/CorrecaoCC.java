package br.org.flem.baprodutiva.negocio;

import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dbbarreto
 */
@Entity
public class CorrecaoCC extends BaseDTOAb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_correcaocc")
    private Integer id;

    private String apdId;
    private String apdTp;
    private String seqLinha;

    private String centroCusto;

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

    public String getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(String centroCusto) {
        this.centroCusto = centroCusto;
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
