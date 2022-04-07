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
 *
 * @author essantos
 */
@Entity
public class LancamentoAvulso extends BaseDTOAb implements ValorCambiavel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_despesadataexibicao")
    private Integer id;

    private String codigoItem;
    private String nomeForn;
    private String descricao;
    private String numeroContrato;

    @Temporal(TemporalType.DATE)
    private Date dataPagamento;

    private Double valorPagamento;
    private String centroCusto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValorPagamento() {
        return valorPagamento;
    }

    public void setValorPagamento(Double valorPagamento) {
        this.valorPagamento = valorPagamento;
    }

    public String getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(String centroCusto) {
        this.centroCusto = centroCusto;
    }

    public String getCodigoItem() {
        return codigoItem;
    }

    public void setCodigoItem(String codigoItem) {
        this.codigoItem = codigoItem;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeForn() {
        return nomeForn;
    }

    public void setNomeForn(String nomeForn) {
        this.nomeForn = nomeForn;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    @Override
    public Serializable getPk() {
        return id;
    }

    @Override
    public Date getData() {
        return this.getDataPagamento();
    }

    @Override
    public Double getValor() {
        return this.getValorPagamento();
    }

}
