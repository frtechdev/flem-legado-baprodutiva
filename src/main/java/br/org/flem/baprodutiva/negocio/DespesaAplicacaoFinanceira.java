package br.org.flem.baprodutiva.negocio;

import br.org.flem.baprodutiva.interfaces.ValorCambiavel;
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
 * Esta classe representa uma despesa do projeto que deve ser paga com a aplicação financeira.
 * @author dbbarreto
 * 
 */
@Entity
public class DespesaAplicacaoFinanceira extends BaseDTOAb implements ValorCambiavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_despesaaplicacaofinanceira")
    private Integer id;
    private String descricao;
    //  private String motivo;
    private String apdId;
    private String apdTp;
    //private String seqLinha;
    @Temporal(TemporalType.DATE)
    private Date entrada;
    private Double valor;

    @Override
    public Serializable getPk() {
        return this.getId();
    }

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getEntrada() {
        return entrada;
    }

    public void setEntrada(Date entrada) {
        this.entrada = entrada;
    }

    @Override
    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public Date getData() {
        return entrada;
    }

}