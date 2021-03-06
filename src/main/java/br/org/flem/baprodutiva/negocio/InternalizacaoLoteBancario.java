package br.org.flem.baprodutiva.negocio;

import br.org.flem.baprodutiva.util.IFReceita;
import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import br.org.flem.fwe.util.Data;
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
 * @author ilfernandes
 */
@Entity
public class InternalizacaoLoteBancario extends BaseDTOAb implements IFReceita {

    @Id
    @Column(name = "id_internalizacaodevolucao")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date entrada;

    private String idCompromisso;

    private String tipo;

    private String seqLinha;

    private String descricao;

    private Double valor =0d;

    private String classificacao;

    private String centroCusto;


    // Dados do Compromisso relacionado a devolu??o
    private String nomeFornecedorComp;

    private String idComp;

    private String tipoComp;

    private String seqLinhaComp;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataExibicao;
 

    public String getNomeFornecedorComp() {
        return nomeFornecedorComp;
    }

    public void setNomeFornecedorComp(String nomeFornecedorComp) {
        this.nomeFornecedorComp = nomeFornecedorComp;
    }


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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
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

    public String getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(String centroCusto) {
        this.centroCusto = centroCusto;
    }

    @Override

    public String toString() {
        return Data.formataData(entrada) + " - " + valor + " - " + "Lote Banc?rio" + "\n";
    }

    public String getTipoReceita() {
        return "LOT";
    }

    public String getIdComp() {
        return idComp;
    }

    public void setIdComp(String idComp) {
        this.idComp = idComp;
    }

    public String getSeqLinhaComp() {
        return seqLinhaComp;
    }

    public void setSeqLinhaComp(String seqLinhaComp) {
        this.seqLinhaComp = seqLinhaComp;
    }

    public String getTipoComp() {
        return tipoComp;
    }

    public void setTipoComp(String tipoComp) {
        this.tipoComp = tipoComp;
    }
    
    /**
     * Colocado devido ao chamado http://flemagil.flem.org.br/issues/2386
     * @return dataDeExibicao
     */

    public Date getDataExibicao() {
        return dataExibicao;
    }

    public void setDataExibicao(Date dataExibicao) {
        this.dataExibicao = dataExibicao;
    }



}
