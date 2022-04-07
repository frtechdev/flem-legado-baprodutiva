package br.org.flem.baprodutiva.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.directwebremoting.annotations.DataTransferObject;

/**
 *
 * Esta classe represeta a origem da contrapartida.
 * 
 * @author dbbarreto
 */
@DataTransferObject
public enum OrigemCusto implements Serializable {
    ESTADO(0),
    COMUNIDADES(1),
    GOVERNO_FEDERAL(3),
    ONG(4);

    private final int id;

    public int getId() {
        return id;
    }
    

    OrigemCusto(int id) {
        this.id = id;
    }
    
    public String getConstante() {
        return this.toString();
    }
    
    public String getNome() {
       if(this == ESTADO){
           return "Estado";
       }
       else if(this == COMUNIDADES){
           return "Comunidades";
       }
       else if(this == GOVERNO_FEDERAL){
           return "Governo Federal";
       }
       else if(this == ONG){
           return "ONG's";
       }
       return null;
    }
    
    public static List<OrigemFonte> getLista(){
        List<OrigemFonte> lista = new ArrayList(Arrays.asList(OrigemCusto.values()));      
        return lista;
    }
}
