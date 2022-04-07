package br.org.flem.baprodutiva.negocio;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author dbbarreto
 */
@Entity
public class CompositeNoAtividade extends CompositeNo{
    
    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Categoria categoria;
    
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
/*
    public Collection<CompositeFolha> getSubAtividades() {
        Collection<CompositeFolha> retorno = new ArrayList<CompositeFolha>();
        for (CompositeIF subAtividade : getFilhos()) {
            retorno.add((CompositeFolha)subAtividade);
        }
        return retorno;
    }*/

}
