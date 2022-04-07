package br.org.flem.baprodutiva.negocio;

import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dbbarreto
 */
@Entity
public class Contrato extends BaseDTOAb {
    
    @Id
    @Column(name = "id_contrato")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Temporal(TemporalType.DATE)
    private Date inicioVigencia;
    
    @Temporal(TemporalType.DATE)
    private Date fimVigencia;
    
    @Column(unique=true)
    private String numero;
    
    private String idFornecedor;
    @Column(name="documentoFornecedor")
    private String codigoAntigo;
    
    private String nomeFornecedor;
    
    private Double valor;
    
    private Double valorUSD;
    
    @Column(length=350)
    private String objeto;
    
    private String moeda;
    
    private String tipo;

    private String clientConnection;
    
    @Column(length=500)
    private String observacao;
    
    @OneToMany(mappedBy="contrato")
    private List<Parcela> parcelas = new ArrayList<Parcela>();
    @OneToMany(mappedBy="contrato")
    private List<Aditivo> aditivos = new ArrayList<Aditivo>();

    public String getClientConnection() {
        return clientConnection;
    }

    public void setClientConnection(String clientConnection) {
        this.clientConnection = clientConnection;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Serializable getPk() {
        return this.id;
    }

    public Date getFimVigencia() {
        return fimVigencia;
    }

    public void setFimVigencia(Date fimVigencia) {
        this.fimVigencia = fimVigencia;
    }

    public String getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(String idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public Date getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(Date inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValorUSD() {
        return valorUSD;
    }

    public void setValorUSD(Double valorUSD) {
        this.valorUSD = valorUSD;
    }

    public List<Aditivo> getAditivos() {
        return aditivos;
    }

    public void setAditivos(List<Aditivo> aditivos) {
        this.aditivos = aditivos;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getCodigoAntigo() {
        return codigoAntigo;
    }

    public void setCodigoAntigo(String documentoFornecedor) {
        this.codigoAntigo = documentoFornecedor;
    }
}
