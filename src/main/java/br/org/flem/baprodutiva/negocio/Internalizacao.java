/*


 */

package br.org.flem.baprodutiva.negocio;

import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import br.org.flem.fwe.util.Data;
import br.org.flem.baprodutiva.util.IFReceita;
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
public class Internalizacao extends BaseDTOAb implements IFReceita {
    
    @Id
    @Column(name = "id_internalizacao")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Temporal(TemporalType.DATE)
    private Date entrada;
    
    private String idCompromisso;
    
    private String parcela;
    
    private String descricao;
    
    private Double valor =0d;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public Serializable getPk() {
        return this.id;
    }

    public Date getEntrada() {
        return entrada;
    }

    public void setEntrada(Date entrada) {
        this.entrada = entrada;
    }

    public String getIdCompromisso() {
        return idCompromisso;
    }

    public void setIdCompromisso(String idCompromisso) {
        this.idCompromisso = idCompromisso;
    }

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return Data.formataData(entrada) + " - " + valor + " - " + "Internalização" + "\n";
    }

    public String getTipoReceita() {
        return "INT";
    }


   
}
