/*


 */

package br.org.flem.baprodutiva.util;

import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author dbbarreto
 */
public class CompartilhamentoCC {
    private String centroCusto;
    
    private Collection<String> subatividades = new HashSet<String>();

    public String getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(String centroCusto) {
        this.centroCusto = centroCusto;
    }

    public Collection<String> getSubatividades() {
        return subatividades;
    }

    public void setSubatividades(Collection<String> subatividades) {
        this.subatividades = subatividades;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CompartilhamentoCC) {
            return this.getCentroCusto().equals(((CompartilhamentoCC)obj).getCentroCusto());
        }
        return super.equals(obj);
    }
    
    
}
