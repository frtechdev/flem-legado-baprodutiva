package br.org.flem.baprodutiva.dto;

import br.org.flem.baprodutiva.negocio.CompositeFolha;
import br.org.flem.baprodutiva.negocio.Planejamento;

/**
 *
 * @author essantos
 */
public class ConsistenciaPOA {

    private Planejamento POA;
    private CompositeFolha subatividade;
    private boolean fisico;
    private boolean financeiro;

    public ConsistenciaPOA ConsistenciaPOA(){
        this.financeiro = false;
        this.fisico = false;
        return this;
    }

    public Planejamento getPOA() {
        return POA;
    }

    public void setPOA(Planejamento POA) {
        this.POA = POA;
    }

    public boolean isFinanceiro() {
        return financeiro;
    }

    public void setFinanceiro(boolean financeiro) {
        this.financeiro = financeiro;
    }

    public boolean isFisico() {
        return fisico;
    }

    public void setFisico(boolean fisico) {
        this.fisico = fisico;
    }

    public CompositeFolha getSubatividade() {
        return subatividade;
    }

    public void setSubatividade(CompositeFolha subatividade) {
        this.subatividade = subatividade;
    }

}
