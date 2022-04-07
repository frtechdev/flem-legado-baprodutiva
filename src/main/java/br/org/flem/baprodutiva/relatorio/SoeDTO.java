package br.org.flem.baprodutiva.relatorio;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author mjpereira
 */
public class SoeDTO implements Serializable {

    private String codigoItem = "";
    private String nomeEntidadeFornecedor = "";
    private String codigoEntidadeFornecedor = "";
    private String numeroContrato = "";
    private String clienteConnection = "";
    private String descricao = "";
    private String moeda = "";
    private Double valorOriginalContrato = 0d;
    private Double valorAcumuladoContrato = 0d;
    private Date dataPagamento;
    private Date dataExibicao;
    private String numeroNotaFiscal = "";
    private Double parcela = 0d;
    private Double valorFinanciado = 0d;
    private String centroCusto = "";
    private String apdId = "";
    private String apdTp = "";
    private String seqLinha = "";
    private Integer idInternalizacao = 0;
    private String tipoInternalizacao = "";
    private String ordem = "";

    public Date getDataExibicao() {
        return dataExibicao;
    }

    public void setDataExibicao(Date dataExibicao) {
        this.dataExibicao = dataExibicao;
    }

    public String getCodigoItem() {
        return codigoItem;
    }

    public void setCodigoItem(String codigoItem) {
        this.codigoItem = codigoItem;
    }

    public String getNomeEntidadeFornecedor() {
        return nomeEntidadeFornecedor;
    }

    public void setNomeEntidadeFornecedor(String nomeEntidadeFornecedor) {
        this.nomeEntidadeFornecedor = nomeEntidadeFornecedor;
    }

    public String getClienteConnection() {
        return clienteConnection;
    }

    public void setClienteConnection(String clienteConnection) {
        this.clienteConnection = clienteConnection;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public Double getValorOriginalContrato() {
        return valorOriginalContrato;
    }

    public void setValorOriginalContrato(Double valorOriginalContrato) {
        this.valorOriginalContrato = valorOriginalContrato;
    }

    public Double getValorAcumuladoContrato() {
        return valorAcumuladoContrato;
    }

    public void setValorAcumuladoContrato(Double valorAcumuladoContrato) {
        this.valorAcumuladoContrato = valorAcumuladoContrato;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    public void setNumeroNotaFiscal(String numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
    }

    public Double getParcela() {
        return parcela;
    }

    public void setParcela(Double parcela) {
        this.parcela = parcela;
    }

    public String getTipoInternalizacao() {
        return tipoInternalizacao;
    }

    public void setTipoInternalizacao(String tipoInternalizacao) {
        this.tipoInternalizacao = tipoInternalizacao;
    }

    public Integer getIdInternalizacao() {
        return idInternalizacao;
    }

    public void setIdInternalizacao(Integer idInternalizacao) {
        this.idInternalizacao = idInternalizacao;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    @Override
    public String toString() {
        return this.getDataPagamento() + " - " + this.getDescricao() + "\n";
    }

    public String getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(String centroCusto) {
        this.centroCusto = centroCusto;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public String getCodigoEntidadeFornecedor() {
        return codigoEntidadeFornecedor;
    }

    public void setCodigoEntidadeFornecedor(String codigoEntidadeFornecedor) {
        this.codigoEntidadeFornecedor = codigoEntidadeFornecedor;
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

    public String getSeqLinha() {
        return seqLinha;
    }

    public void setSeqLinha(String seqLinha) {
        this.seqLinha = seqLinha;
    }

    public Double getValorFinanciado() {
        return valorFinanciado;
    }

    public void setValorFinanciado(Double valorFinanciado) {
        this.valorFinanciado = valorFinanciado;
    }

}
