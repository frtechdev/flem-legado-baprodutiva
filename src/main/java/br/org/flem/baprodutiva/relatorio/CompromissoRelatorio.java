package br.org.flem.baprodutiva.relatorio;

import br.org.flem.fw.persistencia.dto.Compromisso;

/**
 *
 * @author dbbarreto
 */
public class CompromissoRelatorio extends Compromisso{
    
    private Double valorAcumuladoContrato;

    public Double getValorAcumuladoContrato() {
        return valorAcumuladoContrato;
    }

    public void setValorAcumuladoContrato(Double valorAcumuladoContrato) {
        this.valorAcumuladoContrato = valorAcumuladoContrato;
    }
    
    
}
