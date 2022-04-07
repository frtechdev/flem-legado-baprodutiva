package br.org.flem.baprodutiva.util;

/**
 *
 * @author mgsilva
 */
public class InternalizacaoSaldoDTO {

    IFReceita internalizacao;
    Double debito;
    Double saldoTotal;

    public IFReceita getInternalizacao() {
        return internalizacao;
    }

    public void setInternalizacao(IFReceita inter) {
        this.internalizacao = inter;
    }

    public Double getDebito() {
        return debito;
    }

    public void setDebito(Double debito) {
        this.debito = debito;
    }

    public Double getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(Double saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

}
