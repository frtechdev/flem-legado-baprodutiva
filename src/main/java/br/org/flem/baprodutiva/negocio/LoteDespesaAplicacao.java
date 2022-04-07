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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author ilfernandes
 */
@Entity
public class LoteDespesaAplicacao extends BaseDTOAb implements ValorCambiavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lotedespesaaplicacaofinanceira")
    private Integer id;
    private String idDespesa;
    private String tipo;
    private String seqLinha;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataPagamento;
    private Double valor;
    private String descricao;

    @OneToOne
    @JoinColumn(name="id_categoria")
    private Categoria categoria = new Categoria();
    
    private String centroCusto;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataAtualizacao = new Date();

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdDespesa() {
        return idDespesa;
    }

    public void setIdDespesa(String idDespesa) {
        this.idDespesa = idDespesa;
    }

    public String getSeqLinha() {
        return seqLinha;
    }

    public void setSeqLinha(String seqLinha) {
        this.seqLinha = seqLinha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        return this.getId();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public Date getData() {
        return dataPagamento;
    }

    public String getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(String centroCusto) {
        this.centroCusto = centroCusto;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @Override
    public String toString() {
        return "LoteDespesaAplicacao{" + "id=" + id + ", idDespesa=" + idDespesa + ", tipo=" + tipo + ", seqLinha=" + seqLinha + ", dataPagamento=" + dataPagamento + ", valor=" + valor + ", descricao=" + descricao + ", categoria=" + categoria + '}';
    }
    
    

}
