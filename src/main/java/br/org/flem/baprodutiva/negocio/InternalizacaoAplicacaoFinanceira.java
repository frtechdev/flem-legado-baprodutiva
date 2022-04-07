package br.org.flem.baprodutiva.negocio;

import br.org.flem.baprodutiva.interfaces.ValorCambiavel;
import br.org.flem.baprodutiva.util.IFReceita;
import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dbbarreto
 */
@Entity
public class InternalizacaoAplicacaoFinanceira extends BaseDTOAb implements IFReceita, ValorCambiavel {

    @Id
    @Column(name = "id_internalizacaoaplicacaofinanceira")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Temporal(TemporalType.DATE)
    private Date entrada;
    private String descricao;
    private Double valor = 0d;

    @OneToOne
    private TipoFonte tipoFonte = new TipoFonte();

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
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

    @Override
    public Date getEntrada() {
        return entrada;
    }

    public void setEntrada(Date entrada) {
        this.entrada = entrada;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String getTipoReceita() {
        return "APL";
    }

    public TipoFonte getTipoFonte() {
        return tipoFonte;
    }

    public void setTipoFonte(TipoFonte tipoFonte) {
        this.tipoFonte = tipoFonte;
    }

    @Override
    public Date getData() {
        return entrada;
    }
}
