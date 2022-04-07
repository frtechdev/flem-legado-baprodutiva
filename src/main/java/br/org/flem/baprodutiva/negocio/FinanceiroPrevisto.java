package br.org.flem.baprodutiva.negocio;
import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
/**
 *
 * @author fcsilva
 */
@Entity
@DataTransferObject
public class FinanceiroPrevisto extends BaseDTOAb implements java.io.Serializable{
    
    @RemoteProperty
    @Id
    @Column(name = "id_financeirprevisto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;   
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date data; 
 
    private Double valor;

    @ManyToOne(targetEntity=CompositeABS.class)
    @JoinColumn(name = "id_composite", nullable = false)
    @Cascade(CascadeType.SAVE_UPDATE)
    private CompositeIF composite;

    //Periodo de referencia da execucao.
    @ManyToOne
    @JoinColumn(name="id_planejamento", nullable=false)
    private Planejamento planejamento;

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    
    public Planejamento getPlanejamento() {
        return planejamento;
    }

    public void setPlanejamento(Planejamento planejamento) {
        this.planejamento = planejamento;
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
    
    public CompositeIF getComposite() {
        return composite;
    }

    public void setComposite(CompositeIF composite) {
        this.composite = composite;
    }
    
    public CompositeFolha getSubatividade() {
        return (CompositeFolha) composite;
    }
    
        public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

}