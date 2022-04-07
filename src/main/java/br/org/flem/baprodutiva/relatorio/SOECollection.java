package br.org.flem.baprodutiva.relatorio;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author dbbarreto
 */
public class SOECollection {
    private String categoria;
    
    private Collection<SoeDTO> soes = new ArrayList();
    
    private Collection<SoeDTO> folhasResumo = new ArrayList();

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Collection<SoeDTO> getSoes() {
        return soes;
    }

    public void setSoes(Collection<SoeDTO> soes) {
        this.soes = soes;
    }

    public Collection<SoeDTO> getFolhasResumo() {
        return folhasResumo;
    }

    public void setFolhasResumo(Collection<SoeDTO> folhasResumo) {
        this.folhasResumo = folhasResumo;
    }
    
    public double getTotalSOE() {
        double retorno = 0;
        
        for(SoeDTO soe : soes) {
            retorno += soe.getValorFinanciado();
        }
        
        return retorno;
    }
    
    public double getTotalSOEReal() {
        double retorno = 0;
        
        for(SoeDTO soe : soes) {
            retorno += soe.getParcela();
        }
        
        return retorno;
    }
    
    public double getTotalSS() {
        double retorno = 0;
        
        for(SoeDTO ss : folhasResumo) {
            retorno += ss.getValorFinanciado();
        }
        
        return retorno;
    }
    
    public double getTotalSSReal() {
        double retorno = 0;
        
        for(SoeDTO ss : folhasResumo) {
            retorno += ss.getParcela();
        }
        
        return retorno;
    }
    
    public double getTotal() {
        return this.getTotalSOE() + this.getTotalSS();
    }
    
    public double getTotalReal() {
        return this.getTotalSOEReal() + this.getTotalSSReal();
    }
}
