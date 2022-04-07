/*
 * Documento.java
 * 
 * Created on 19/09/2007, 16:48:02
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
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * A classe TipoFonte representa o agrupamento de fontes, como exemplo:
 * 
 *  - Desembolso na conta designada
 *  - Pagamentos Diretos
 *  - Reembolsos para Conta
 * 
 * Esse agrupamento é utilizado para exibir os valores no Relatório de Fontes e Usos.
 * 
 * @author dbbarreto
 */
@DataTransferObject
@Entity
public class TipoFonte extends BaseDTOAb {
    
    @RemoteProperty
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_tipo")
    private Integer id;
    
    @RemoteProperty
    private String descricao;
    
    @ManyToOne
    @JoinColumn(name = "id_grupotipo", nullable = false)
    @Cascade(CascadeType.SAVE_UPDATE)
    private GrupoTipoFonte grupoTipo;
    
    @OneToMany(mappedBy="tipo")
    @Cascade(CascadeType.SAVE_UPDATE)
    private Set<Fonte> fontes = new HashSet<Fonte>();
    
    @Enumerated
    private OrigemFonte origem;
    
    public Serializable getPk() {
        return this.getId();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Fonte> getFontes() {
        return fontes;
    }

    public void setFontes(Set<Fonte> fontes) {
        this.fontes = fontes;
    }

    public GrupoTipoFonte getGrupoTipo() {
        return grupoTipo;
    }

    public void setGrupoTipo(GrupoTipoFonte grupoTipo) {
        this.grupoTipo = grupoTipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrigemFonte getOrigem() {
        return origem;
    }

    public void setOrigem(OrigemFonte origem) {
        this.origem = origem;
    }
    
}