package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fw.persistencia.dto.Compromisso;
import java.util.Collection;
import br.org.flem.baprodutiva.dao.InternalizacaoDAO;
import br.org.flem.baprodutiva.negocio.Internalizacao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * Esta classe possui regras de negócios, referentes a entidade Categoria
 *
 * @author Gerador de Código da FLEM
 *
 */
public class InternalizacaoBO {

    InternalizacaoDAO dao;

    public InternalizacaoBO() throws AcessoDadosException {

        dao = new InternalizacaoDAO();
    }

    public void inserirOuAlterar(Internalizacao objeto) throws AcessoDadosException {
        dao.inserirOuAlterar(objeto);
    }

    public void inserir(Internalizacao objeto) throws AcessoDadosException {
        dao.inserir(objeto);
    }

    public void alterar(Internalizacao objeto) throws AcessoDadosException {
        dao.alterar(objeto);
    }

    public void excluir(Internalizacao objeto) throws AcessoDadosException {
        dao.excluir(objeto);
    }

    public void excluir(Collection<Internalizacao> objetos) throws AcessoDadosException {
        dao.excluir(objetos);
    }

    public Internalizacao obterPorPk(Internalizacao objeto) throws AcessoDadosException {
        return dao.obterPorPk(objeto);
    }

    public Internalizacao obterPorPk(Integer id) throws AcessoDadosException {
        return dao.obterPorPk(id);
    }

    public Collection<Internalizacao> obterTodos() throws AcessoDadosException {
        return dao.obterTodos();
    }
    
    public Collection<Compromisso> obterCompromissos() throws AcessoDadosException {
        Collection<Internalizacao> internalizacoes = dao.obterTodos();
        
        Collection<Compromisso> compromissos = new ArrayList<Compromisso>();
        
        for (Internalizacao internalizacao : internalizacoes) {
            Compromisso compromisso = new Compromisso();
            
            compromisso.setId(internalizacao.getIdCompromisso());
            compromisso.setParcela(internalizacao.getParcela());
            
            compromissos.add(compromisso);
        }
        return compromissos;
    }

    public Collection<Internalizacao> obterTodosOrdenadoPorCampo(String campo)
            throws AcessoDadosException {
        return dao.obterTodosOrdenadoPorCampo(campo);
    }

    public Collection<Internalizacao> obterPorFiltro(Internalizacao objeto) throws AcessoDadosException {
        return dao.obterPorFiltro(objeto);
    }
    
    public List<Internalizacao> obterPorPeriodo(Date inicio, Date fim) {
        return dao.obterPorPeriodo(inicio, fim);
    }
    
}
