/*
 * ConvenioMedicoEnum.java
 * 
 * Created on 14/08/2007, 17:12:39
 * 


 */
package br.org.flem.baprodutiva.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.directwebremoting.annotations.DataTransferObject;

/**
 * Esta classe representa a origem da receita do projeto. Exemplo:
 *
 * - FLEM são as internalizaçÃµes 
 *
 * @author dbbarreto
 */
@DataTransferObject
public enum OrigemFonte implements Serializable {

    FLEM(0),
    APLICACAO_FINANCEIRA(1);
    private final int id;

    private OrigemFonte(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        if (this == FLEM) {
            return "FLEM";
        } else if (this == APLICACAO_FINANCEIRA) {
            return "Aplicação Financeira";
        }
        return null;
    }

    public String getConstante() {
        return this.toString();
    }

    public static List<OrigemFonte> getLista() {
        List<OrigemFonte> lista = new ArrayList(Arrays.asList(OrigemFonte.values()));
        return lista;
    }
}
