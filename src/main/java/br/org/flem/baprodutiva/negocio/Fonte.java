/*
 * Arquivo.java
 *
 * Created on 18/09/2007, 17:41:31
 *


 */

package br.org.flem.baprodutiva.negocio;

import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * Essa classe represeta um valor recebido e que deve ser utilizado na execução 
 * do projeto. Os valores podem vir da Doação.
 * 
 * @author dbbarreto
 */
@Entity
public class Fonte extends BaseDTOAb implements java.io.Serializable {

    @Id
    @Column(name = "id_fonte")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_tipo", nullable = false)
    @Cascade(CascadeType.SAVE_UPDATE)
    private TipoFonte tipo;
    
    @OneToMany(mappedBy="fonte")
    @Cascade(CascadeType.SAVE_UPDATE)
    private Set<FonteArquivo> fonteArquivos = new HashSet<FonteArquivo>();
    
    private Double valor;

    @ManyToOne
    @JoinColumn(name="id_planejamento", nullable=false)
    private Planejamento planejamento;
    
    @Enumerated
    private Origem origem;
    
    public Serializable getPk() {
        return this.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoFonte getTipo() {
        return tipo;
    }

    public void setTipo(TipoFonte tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    
    public Origem getOrigem() {
        return origem;
    }

    public void setOrigem(Origem origem) {
        this.origem = origem;
    }

    public Set<FonteArquivo> getFonteArquivos() {
        return fonteArquivos;
    }

    public void setFonteArquivos(Set<FonteArquivo> fonteArquivos) {
        this.fonteArquivos = fonteArquivos;
    }

    public Planejamento getPlanejamento() {
        return planejamento;
    }

    public void setPlanejamento(Planejamento planejamento) {
        this.planejamento = planejamento;
    }

}
