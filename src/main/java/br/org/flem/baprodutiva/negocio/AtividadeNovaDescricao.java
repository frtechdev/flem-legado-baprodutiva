/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.negocio;

import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author ILFernandes
 */
@Entity
public class AtividadeNovaDescricao extends BaseDTOAb {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_antividade_nova_descricao")
    private Integer id;
    @OneToOne
    private CompositeNoAtividade atividade = new CompositeNoAtividade();
    @OneToOne
    private Planejamento poa = new Planejamento();
    
    private String novaDescricao;

    public CompositeNoAtividade getAtividade() {
        return atividade;
    }

    public void setAtividade(CompositeNoAtividade atividade) {
        this.atividade = atividade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Planejamento getPoa() {
        return poa;
    }

    public void setPoa(Planejamento poa) {
        this.poa = poa;
    }

    public String getNovaDescricao() {
        return novaDescricao;
    }

    public void setNovaDescricao(String novaDescricao) {
        this.novaDescricao = novaDescricao;
    }
    
    @Override
    public Serializable getPk() {
        return this.getId();
    }
}
