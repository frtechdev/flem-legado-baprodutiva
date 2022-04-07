package br.org.flem.baprodutiva.negocio;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.CollectionOfElements;

/**
 *
 * @author mjpereira
 */
@Entity
@DataTransferObject
public class CompositeFolha extends CompositeABS {
    

    
    @ManyToOne
    @JoinColumn(name = "id_subcategoria", nullable = false)
    private SubCategoria subcategoria;
    
    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;
    
    @CollectionOfElements(fetch=FetchType.EAGER)
    @Column(name="centroCusto")
    private Set<String> centrosCusto = new HashSet<String>();

    private String observacao;
    
    @Column(nullable=false)
    private Boolean ccCompartilhado;
    
    @ManyToMany(mappedBy="compositeFolhas", cascade=javax.persistence.CascadeType.ALL)
    //@Cascade(CascadeType.SAVE_UPDATE)
    private Set<OrgaoResponsavel> orgaosResponsaveis = new HashSet<OrgaoResponsavel>();
    
    @ManyToMany(mappedBy="compositeFolhas", cascade=javax.persistence.CascadeType.ALL)
    //@Cascade(CascadeType.SAVE_UPDATE)
    private Set<EntidadeExecutora> entidadesExecutoras = new HashSet<EntidadeExecutora>();

    /*
    @ManyToOne
    @JoinColumn(name="id_planejamento")
    private Planejamento planejamento = new Planejamento();
     */
    
    private Integer anoOcorrencia;
    
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Integer getAnoOcorrencia() {
        return anoOcorrencia;
    }

    public void setAnoOcorrencia(Integer anoOcorrencia) {
        this.anoOcorrencia = anoOcorrencia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    /*
    public Planejamento getPlanejamento() {
        return planejamento;
    }

    public void setPlanejamento(Planejamento planejamento) {
        this.planejamento = planejamento;
    }*/

    public SubCategoria getSubCategoria() {
        return subcategoria;
    }

    public void setSubCategoria(SubCategoria subcategoria) {
        this.subcategoria = subcategoria;
    }

    public SubCategoria getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(SubCategoria subcategoria) {
        this.subcategoria = subcategoria;
    }

    public Set<String> getCentrosCusto() {
        return centrosCusto;
    }

    public void setCentrosCusto(Set<String> centrosCusto) {
        this.centrosCusto = centrosCusto;
    }

    public Boolean getCcCompartilhado() {
        return ccCompartilhado;
    }

    public void setCcCompartilhado(Boolean ccCompartilhado) {
        this.ccCompartilhado = ccCompartilhado;
    }

    public Set<OrgaoResponsavel> getOrgaosResponsaveis() {
        return orgaosResponsaveis;
    }

    public void setOrgaosResponsaveis(Set<OrgaoResponsavel> orgaosResponsaveis) {
        this.orgaosResponsaveis = orgaosResponsaveis;
    }

    public Set<EntidadeExecutora> getEntidadesExecutoras() {
        return entidadesExecutoras;
    }

    public void setEntidadesExecutoras(Set<EntidadeExecutora> entidadesExecutoras) {
        this.entidadesExecutoras = entidadesExecutoras;
    }


    
    
}
