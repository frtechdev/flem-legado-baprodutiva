/*
 * ConvenioMedicoEnum.java
 * 
 * Created on 14/08/2007, 17:12:39
 * 


 */
 
package br.org.flem.baprodutiva.negocio;

import java.io.Serializable;

/**
 *
 * Classe que representa a Origem das Fontes e Custos (receitas e despezas) do 
 * projeto.
 * 
 * @author dbbarreto
 */
public enum Origem implements Serializable {
    FLEM(0),
    APLICACAO_FINANCEIRA(1);

    private final int origem;
    
    public String getNome(){        
        switch(this){
            case FLEM: return "FLEM";
            case APLICACAO_FINANCEIRA: return "Aplicação financeira";
        }
        return null;
    }

    public String getConstante(){       
        return this.toString();
    }

    public int getOrigem() {
        return origem;
    }

    Origem(int origem) {
        this.origem = origem;
    }

}