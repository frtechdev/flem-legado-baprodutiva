package br.org.flem.baprodutiva.dto;

import br.org.flem.fw.persistencia.dto.LancamentoInterface;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author essantos
 */
public class DevolucaoDTO implements LancamentoInterface{

    private String id; //codigoItem tipoItem
    private String tipo;
    private BigDecimal valor = BigDecimal.ZERO;
    private Date data;
    private String descricao;
        private String descricaoAnterior;


    private String nomeFornecedor;
    private String numeroContrato;
    private String numeroClienteConnect;
    private String numero;
    private String parcela;
    private String centroCusto;
    private String centroCustoAnterior;
    private String codigoFornecedor;
    private String imposto;
    private String apdId;
    private String apdTp;
    private String seqLinha;
    private String recibo;
    private String ordem;
    private Date dataExibicao;
    private BigDecimal valorAnterior;

    private String classificacao;
    private Date entrada;
    private String nomeFornecedorComp;
    private String tipoReceita;

    @Override
    public String getApdId() {
        return apdId;
    }

    public void setApdId(String apdId) {
        this.apdId = apdId;
    }

    @Override
    public String getApdTp() {
        return apdTp;
    }

    public void setApdTp(String apdTp) {
        this.apdTp = apdTp;
    }

    @Override
    public String getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(String centroCusto) {
        this.centroCusto = centroCusto;
    }

    @Override
    public String getCodigoFornecedor() {
        return codigoFornecedor;
    }

    public void setCodigoFornecedor(String codigoFornecedor) {
        this.codigoFornecedor = codigoFornecedor;
    }

    @Override
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public Date getDataExibicao() {
        return dataExibicao;
    }

    public void setDataExibicao(Date dataExibicao) {
        this.dataExibicao = dataExibicao;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getImposto() {
        return imposto;
    }

    public void setImposto(String imposto) {
        this.imposto = imposto;
    }

    @Override
    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    @Override
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String getNumeroClienteConnect() {
        return numeroClienteConnect;
    }

    public void setNumeroClienteConnect(String numeroClienteConnect) {
        this.numeroClienteConnect = numeroClienteConnect;
    }

    @Override
    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    @Override
    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    @Override
    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    @Override
    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }

    @Override
    public String getSeqLinha() {
        return seqLinha;
    }

    public void setSeqLinha(String seqLinha) {
        this.seqLinha = seqLinha;
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public Date getEntrada() {
        return entrada;
    }

    public void setEntrada(Date entrada) {
        this.entrada = entrada;
    }

    public String getNomeFornecedorComp() {
        return nomeFornecedorComp;
    }

    public void setNomeFornecedorComp(String nomeFornecedorComp) {
        this.nomeFornecedorComp = nomeFornecedorComp;
    }

    public String getTipoReceita() {
        return tipoReceita;
    }

    public void setTipoReceita(String tipoReceita) {
        this.tipoReceita = tipoReceita;
    }

    @Override
    public BigDecimal getValorAnterior() {
        return valorAnterior;
    }

    public void setValorAnterior(BigDecimal valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    public String getCentroCustoAnterior() {
        return centroCustoAnterior;
    }

    public void setCentroCustoAnterior(String centroCustoAnterior) {
        this.centroCustoAnterior = centroCustoAnterior;
    }

    @Override
    public String getProcesso() {
        return "";
    }
    
        public String getDescricaoAnterior() {
        return descricaoAnterior;
    }

    public void setDescricaoAnterior(String descricaoAnterior) {
        this.descricaoAnterior = descricaoAnterior;
    }

}
