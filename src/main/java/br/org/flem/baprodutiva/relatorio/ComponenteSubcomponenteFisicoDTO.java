package br.org.flem.baprodutiva.relatorio;

import java.io.Serializable;

/**
 *
 * @author dbbarreto
 */
public class ComponenteSubcomponenteFisicoDTO implements Serializable{
    
    private String componente;
    private String subcomponente;
    private String atividade;
    private String subAtividade;
    private String produto;
    
    private String unidadeMedida;
    private Double metaTotal = 0D;
    private Double custoTotal = 0D;
    
    private Double fisicoTrimestreProgramado = 0D;
    private Double fisicoTrimestreExecutado = 0D;
    private Double fisicoAcumuladoAnoProgramado = 0D;
    private Double fisicoAcumuladoAnoExecutado = 0D;

    private Double gefTrimestreProgramado = 0D;
    private Double gefTrimestreExecutado = 0D;
    private Double gefAcumuladoAnoProgramado = 0D;
    private Double gefAcumuladoAnoExecutado = 0D;

    private Double estadoTrimestreProgramado = 0D;
    private Double estadoTrimestreExecutado = 0D;
    private Double estadoAcumuladoAnoProgramado = 0D;
    private Double estadoAcumuladoAnoExecutado = 0D;
    
    private Double comunidadeTrimestreProgramado = 0D;
    private Double comunidadeTrimestreExecutado = 0D;
    private Double comunidadeAcumuladoAnoProgramado = 0D;
    private Double comunidadeAcumuladoAnoExecutado = 0D;

    private Double estadoTotalTrimestreExecutadoSubComponente = 0D;
    private Double estadoTotalAcumuladoExecutadoSubComponente = 0D;
    private Double comunidadeTotalTrimestreExecutadoSubcomponente = 0D;
    private Double comunidadeTotalAcumuladoExecutadoSubComponente = 0D;
    private Double estadoTotalTrimestreExecutadoAtividade = 0D;
    private Double estadoTotalAcumuladoExecutadoAtividade = 0D;
    private Double comunidadeTotalTrimestreExecutadoAtividade = 0D;
    private Double comunidadeTotalAcumuladoExecutadoAtividade = 0D;
    private Boolean mostrar = true;
    private Boolean mostrarTotalSubatividade = true;
    private Boolean mostrarTotalAtividade = true;

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public Double getComunidadeTrimestreExecutado() {
        return comunidadeTrimestreExecutado;
    }

    public void setComunidadeTrimestreExecutado(Double comunidadeTrimestreExecutado) {
        this.comunidadeTrimestreExecutado = comunidadeTrimestreExecutado;
    }

    public Double getComunidadeTrimestreProgramado() {
        return comunidadeTrimestreProgramado;
    }

    public void setComunidadeTrimestreProgramado(Double comunidadeTrimestreProgramado) {
        this.comunidadeTrimestreProgramado = comunidadeTrimestreProgramado;
    }

    public Double getCustoTotal() {
        return custoTotal;
    }

    public void setCustoTotal(Double custoTotal) {
        this.custoTotal = custoTotal;
    }

    public Double getEstadoTrimestreExecutado() {
        return estadoTrimestreExecutado;
    }

    public void setEstadoTrimestreExecutado(Double estadoTrimestreExecutado) {
        this.estadoTrimestreExecutado = estadoTrimestreExecutado;
    }

    public Double getEstadoTrimestreProgramado() {
        return estadoTrimestreProgramado;
    }

    public void setEstadoTrimestreProgramado(Double estadoTrimestreProgramado) {
        this.estadoTrimestreProgramado = estadoTrimestreProgramado;
    }

    public Double getFisicoTrimestreExecutado() {
        return fisicoTrimestreExecutado;
    }

    public void setFisicoTrimestreExecutado(Double fisicoTrimestreExecutado) {
        this.fisicoTrimestreExecutado = fisicoTrimestreExecutado;
    }

    public Double getFisicoTrimestreProgramado() {
        return fisicoTrimestreProgramado;
    }

    public void setFisicoTrimestreProgramado(Double fisicoTrimestreProgramado) {
        this.fisicoTrimestreProgramado = fisicoTrimestreProgramado;
    }

    public Double getGefTrimestreExecutado() {
        return gefTrimestreExecutado;
    }

    public void setGefTrimestreExecutado(Double gefTrimestreExecutado) {
        this.gefTrimestreExecutado = gefTrimestreExecutado;
    }

    public Double getGefTrimestreProgramado() {
        return gefTrimestreProgramado;
    }

    public void setGefTrimestreProgramado(Double gefTrimestreProgramado) {
        this.gefTrimestreProgramado = gefTrimestreProgramado;
    }

    public Double getMetaTotal() {
        return metaTotal;
    }

    public void setMetaTotal(Double metaTotal) {
        this.metaTotal = metaTotal;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getSubAtividade() {
        return subAtividade;
    }

    public void setSubAtividade(String subAtividade) {
        this.subAtividade = subAtividade;
    }

    public String getSubcomponente() {
        return subcomponente;
    }

    public void setSubcomponente(String subcomponente) {
        this.subcomponente = subcomponente;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Double getComunidadeTotalAcumuladoExecutadoAtividade() {
        return comunidadeTotalAcumuladoExecutadoAtividade;
    }

    public void setComunidadeTotalAcumuladoExecutadoAtividade(Double comunidadeTotalAcumuladoExecutadoAtividade) {
        this.comunidadeTotalAcumuladoExecutadoAtividade = comunidadeTotalAcumuladoExecutadoAtividade;
    }

    public Double getComunidadeTotalAcumuladoExecutadoSubComponente() {
        return comunidadeTotalAcumuladoExecutadoSubComponente;
    }

    public void setComunidadeTotalAcumuladoExecutadoSubComponente(Double comunidadeTotalAcumuladoExecutadoSubComponente) {
        this.comunidadeTotalAcumuladoExecutadoSubComponente = comunidadeTotalAcumuladoExecutadoSubComponente;
    }

    public Double getComunidadeTotalTrimestreExecutadoAtividade() {
        return comunidadeTotalTrimestreExecutadoAtividade;
    }

    public void setComunidadeTotalTrimestreExecutadoAtividade(Double comunidadeTotalTrimestreExecutadoAtividade) {
        this.comunidadeTotalTrimestreExecutadoAtividade = comunidadeTotalTrimestreExecutadoAtividade;
    }

    public Double getComunidadeTotalTrimestreExecutadoSubcomponente() {
        return comunidadeTotalTrimestreExecutadoSubcomponente;
    }

    public void setComunidadeTotalTrimestreExecutadoSubcomponente(Double comunidadeTotalTrimestreExecutadoSubcomponente) {
        this.comunidadeTotalTrimestreExecutadoSubcomponente = comunidadeTotalTrimestreExecutadoSubcomponente;
    }

    public Double getEstadoTotalAcumuladoExecutadoAtividade() {
        return estadoTotalAcumuladoExecutadoAtividade;
    }

    public void setEstadoTotalAcumuladoExecutadoAtividade(Double estadoTotalAcumuladoExecutadoAtividade) {
        this.estadoTotalAcumuladoExecutadoAtividade = estadoTotalAcumuladoExecutadoAtividade;
    }

    public Double getEstadoTotalAcumuladoExecutadoSubComponente() {
        return estadoTotalAcumuladoExecutadoSubComponente;
    }

    public void setEstadoTotalAcumuladoExecutadoSubComponente(Double estadoTotalAcumuladoExecutadoSubComponente) {
        this.estadoTotalAcumuladoExecutadoSubComponente = estadoTotalAcumuladoExecutadoSubComponente;
    }

    public Double getEstadoTotalTrimestreExecutadoAtividade() {
        return estadoTotalTrimestreExecutadoAtividade;
    }

    public void setEstadoTotalTrimestreExecutadoAtividade(Double estadoTotalTrimestreExecutadoAtividade) {
        this.estadoTotalTrimestreExecutadoAtividade = estadoTotalTrimestreExecutadoAtividade;
    }

    public Double getEstadoTotalTrimestreExecutadoSubComponente() {
        return estadoTotalTrimestreExecutadoSubComponente;
    }

    public void setEstadoTotalTrimestreExecutadoSubComponente(Double estadoTotalTrimestreExecutadoSubComponente) {
        this.estadoTotalTrimestreExecutadoSubComponente = estadoTotalTrimestreExecutadoSubComponente;
    }

//    public Double getComunidadeTotalAcumuladoExecutadoBahia() {
//        return comunidadeTotalAcumuladoExecutadoBahia;
//    }
//
//    public void setComunidadeTotalAcumuladoExecutadoBahia(Double comunidadeTotalAcumuladoExecutadoBahia) {
//        this.comunidadeTotalAcumuladoExecutadoBahia = comunidadeTotalAcumuladoExecutadoBahia;
//    }
//
//    public Double getComunidadeTotalTrimestreExecutadoBahia() {
//        return comunidadeTotalTrimestreExecutadoBahia;
//    }
//
//    public void setComunidadeTotalTrimestreExecutadoBahia(Double comunidadeTotalTrimestreExecutadoBahia) {
//        this.comunidadeTotalTrimestreExecutadoBahia = comunidadeTotalTrimestreExecutadoBahia;
//    }
//
//    public Double getEstadoTotalAcumuladoExecutadoBahia() {
//        return estadoTotalAcumuladoExecutadoBahia;
//    }
//
//    public void setEstadoTotalAcumuladoExecutadoBahia(Double estadoTotalAcumuladoExecutadoBahia) {
//        this.estadoTotalAcumuladoExecutadoBahia = estadoTotalAcumuladoExecutadoBahia;
//    }
//
//    public Double getEstadoTotalTrimestreExecutadoBahia() {
//        return estadoTotalTrimestreExecutadoBahia;
//    }
//
//    public void setEstadoTotalTrimestreExecutadoBahia(Double estadoTotalTrimestreExecutadoBahia) {
//        this.estadoTotalTrimestreExecutadoBahia = estadoTotalTrimestreExecutadoBahia;
//    }

    public Double getComunidadeAcumuladoAnoExecutado() {
        return comunidadeAcumuladoAnoExecutado;
    }

    public void setComunidadeAcumuladoAnoExecutado(Double comunidadeAcumuladoAnoExecutado) {
        this.comunidadeAcumuladoAnoExecutado = comunidadeAcumuladoAnoExecutado;
    }

    public Double getComunidadeAcumuladoAnoProgramado() {
        return comunidadeAcumuladoAnoProgramado;
    }

    public void setComunidadeAcumuladoAnoProgramado(Double comunidadeAcumuladoAnoProgramado) {
        this.comunidadeAcumuladoAnoProgramado = comunidadeAcumuladoAnoProgramado;
    }

    public Double getEstadoAcumuladoAnoExecutado() {
        return estadoAcumuladoAnoExecutado;
    }

    public void setEstadoAcumuladoAnoExecutado(Double estadoAcumuladoAnoExecutado) {
        this.estadoAcumuladoAnoExecutado = estadoAcumuladoAnoExecutado;
    }

    public Double getEstadoAcumuladoAnoProgramado() {
        return estadoAcumuladoAnoProgramado;
    }

    public void setEstadoAcumuladoAnoProgramado(Double estadoAcumuladoAnoProgramado) {
        this.estadoAcumuladoAnoProgramado = estadoAcumuladoAnoProgramado;
    }

    public Double getFisicoAcumuladoAnoExecutado() {
        return fisicoAcumuladoAnoExecutado;
    }

    public void setFisicoAcumuladoAnoExecutado(Double fisicoAcumuladoAnoExecutado) {
        this.fisicoAcumuladoAnoExecutado = fisicoAcumuladoAnoExecutado;
    }

    public Double getFisicoAcumuladoAnoProgramado() {
        return fisicoAcumuladoAnoProgramado;
    }

    public void setFisicoAcumuladoAnoProgramado(Double fisicoAcumuladoAnoProgramado) {
        this.fisicoAcumuladoAnoProgramado = fisicoAcumuladoAnoProgramado;
    }

    public Double getGefAcumuladoAnoExecutado() {
        return gefAcumuladoAnoExecutado;
    }

    public void setGefAcumuladoAnoExecutado(Double gefAcumuladoAnoExecutado) {
        this.gefAcumuladoAnoExecutado = gefAcumuladoAnoExecutado;
    }

    public Double getGefAcumuladoAnoProgramado() {
        return gefAcumuladoAnoProgramado;
    }

    public void setGefAcumuladoAnoProgramado(Double gefAcumuladoAnoProgramado) {
        this.gefAcumuladoAnoProgramado = gefAcumuladoAnoProgramado;
    }

    public Double getFisicoTrimestreVariacao() {
        return this.fisicoTrimestreProgramado == 0 ? 0 : (this.fisicoTrimestreExecutado * 100 / this.fisicoTrimestreProgramado);
    }

    public Double getFisicoAcumuladoAnoVariacao() {
        return this.fisicoAcumuladoAnoProgramado == 0 ? 0 : (this.fisicoAcumuladoAnoExecutado * 100 / this.fisicoAcumuladoAnoProgramado);
    }
    
    public Double getGefTrimestreVariacao() {
        return this.gefTrimestreProgramado == 0 ? 0 : (this.gefTrimestreExecutado * 100 / this.gefTrimestreProgramado);
    }

    public Double getGefAcumuladoAnoVariacao() {
        return this.gefAcumuladoAnoProgramado == 0 ? 0 : (this.gefAcumuladoAnoExecutado * 100 / this.gefAcumuladoAnoProgramado);
    }
    
    public Double getEstadoTrimestreVariacao() {
        return this.estadoTrimestreProgramado == 0 ? 0 : (this.estadoTrimestreExecutado * 100 / this.estadoTrimestreProgramado);
    }
    
    public Double getEstadoAcumuladoAnoVariacao() {
        return this.estadoAcumuladoAnoProgramado == 0 ? 0 : (this.estadoAcumuladoAnoExecutado * 100 / this.estadoAcumuladoAnoProgramado);
    }
    
    public Double getComunidadeTrimestreVariacao() {
        return this.comunidadeTrimestreProgramado == 0 ? 0 : (this.comunidadeTrimestreExecutado * 100 / this.comunidadeTrimestreProgramado);
    }
    
    public Double getComunidadeAcumuladoAnoVariacao() {
        return this.comunidadeAcumuladoAnoProgramado == 0 ? 0 : (this.comunidadeAcumuladoAnoExecutado * 100 / this.comunidadeAcumuladoAnoProgramado);
    }
    
    public Double getTotalProgramado() {
        return gefAcumuladoAnoProgramado + estadoAcumuladoAnoProgramado + comunidadeAcumuladoAnoProgramado;
    }
    
    public Double getTotalExecutado() {
        return gefAcumuladoAnoExecutado + estadoAcumuladoAnoExecutado + comunidadeAcumuladoAnoExecutado;
    }
    
    public Double getTotalVariacao() {
        return this.getTotalProgramado() == 0 ? 0 : (this.getTotalExecutado() * 100 / this.getTotalProgramado());
    }

    public Boolean getMostrar() {
        return mostrar;
    }

    public void setMostrar(Boolean mostrar) {
        this.mostrar = mostrar;
    }

    public Boolean getMostrarTotalAtividade() {
        return mostrarTotalAtividade;
    }

    public void setMostrarTotalAtividade(Boolean mostrarTotalAtividade) {
        this.mostrarTotalAtividade = mostrarTotalAtividade;
    }

    public Boolean getMostrarTotalSubatividade() {
        return mostrarTotalSubatividade;
    }

    public void setMostrarTotalSubatividade(Boolean mostrarTotalSubatividade) {
        this.mostrarTotalSubatividade = mostrarTotalSubatividade;
    }
}
