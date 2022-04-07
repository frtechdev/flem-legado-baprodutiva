/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.dto;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author ILFernandes
 */
public class RelatorioEvolucaoGastosDTO {
    
    private Collection<EvolucaoGastosAnoDTO> evolucaoGastosBahia = new ArrayList<EvolucaoGastosAnoDTO>();
    private Collection<EvolucaoGastosAnoDTO> evolucaoGastosCeara = new ArrayList<EvolucaoGastosAnoDTO>();
    private BufferedImage graficoBahia;

    public Collection<EvolucaoGastosAnoDTO> getEvolucaoGastosBahia() {
        return evolucaoGastosBahia;
    }

    public void setEvolucaoGastosBahia(Collection<EvolucaoGastosAnoDTO> evolucaoGastosBahia) {
        this.evolucaoGastosBahia = evolucaoGastosBahia;
    }

    public Collection<EvolucaoGastosAnoDTO> getEvolucaoGastosCeara() {
        return evolucaoGastosCeara;
    }

    public void setEvolucaoGastosCeara(Collection<EvolucaoGastosAnoDTO> evolucaoGastosCeara) {
        this.evolucaoGastosCeara = evolucaoGastosCeara;
    }

    public BufferedImage getGraficoBahia() {
        return graficoBahia;
    }

    public void setGraficoBahia(BufferedImage graficoBahia) {
        this.graficoBahia = graficoBahia;
    }

}
