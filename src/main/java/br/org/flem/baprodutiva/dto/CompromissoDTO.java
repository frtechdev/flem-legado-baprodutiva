package br.org.flem.baprodutiva.dto;

import br.org.flem.fw.persistencia.dto.Compromisso;
import br.org.flem.baprodutiva.negocio.RateioCentroCusto;

/**
 *
 * @author MCCosta
 */
public class CompromissoDTO {
    
    Compromisso compromisso = new Compromisso();
    RateioCentroCusto rateioCentroCusto = new RateioCentroCusto();

    public Compromisso getCompromisso() {
        return compromisso;
    }

    public void setCompromisso(Compromisso compromisso) {
        this.compromisso = compromisso;
    }

    public RateioCentroCusto getRateioCentroCusto() {
        return rateioCentroCusto;
    }

    public void setRateioCentroCusto(RateioCentroCusto rateioCentroCusto) {
        this.rateioCentroCusto = rateioCentroCusto;
    }
}
