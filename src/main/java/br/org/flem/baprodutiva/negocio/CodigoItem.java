package br.org.flem.baprodutiva.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 
 * @author dbbarreto
 */
public enum CodigoItem implements Serializable {
    CW(0),
    GO(1),
    CS(2),
    TR(3),
    CO(4),
    NCS(5);

    private final int origem;
    
    public String getDescricao(){        
        switch(this){
            case CW: return "Obras";
            case GO: return "Bens";
            case CS: return "Serviços de Consultorias";
            case TR: return "Treinamento";            
            case CO: return "Custos Operacionais";
            case NCS: return "Serviços de Não Consultorias";
        }
        return null;
    }
    
    public String getSigla(){        
        switch(this){
            case CW: return "CW";
            case GO: return "GO";
            case CS: return "CS";
            case TR: return "TR";            
            case CO: return "CO";
            case NCS: return "NCS";
        }
        return null;
    }
    
    public String getConstante(){       
        return this.toString();
    }

    public int getOrigem() {
        return origem;
    }
    
    CodigoItem(int origem) {
        this.origem = origem;
    }

    public static List<String> getListaNomes(){
        List<String> lista = new ArrayList<String>();
        for (CodigoItem codigoItem : CodigoItem.values()) {
            lista.add(codigoItem.getSigla());
        }
        return lista;
    }

}
