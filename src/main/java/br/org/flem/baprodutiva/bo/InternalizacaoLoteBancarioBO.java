package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fw.persistencia.dto.Compromisso;
import java.util.Collection;
import br.org.flem.baprodutiva.dao.InternalizacaoLoteBancarioDAO;
import br.org.flem.baprodutiva.dto.DevolucaoDTO;
import br.org.flem.baprodutiva.negocio.InternalizacaoLoteBancario;
import java.math.BigDecimal;
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
public class InternalizacaoLoteBancarioBO {

    InternalizacaoLoteBancarioDAO dao;

    public InternalizacaoLoteBancarioBO() throws AcessoDadosException {

        dao = new InternalizacaoLoteBancarioDAO();
    }

    public void inserirOuAlterar(InternalizacaoLoteBancario objeto) throws AcessoDadosException {
        dao.inserirOuAlterar(objeto);
    }

    public void inserir(InternalizacaoLoteBancario objeto) throws AcessoDadosException {
        dao.inserir(objeto);
    }

    public void alterar(InternalizacaoLoteBancario objeto) throws AcessoDadosException {
        dao.alterar(objeto);
    }

    public void excluir(InternalizacaoLoteBancario objeto) throws AcessoDadosException {
        dao.excluir(objeto);
    }

    public void excluir(Collection<InternalizacaoLoteBancario> objetos) throws AcessoDadosException {
        dao.excluir(objetos);
    }

    public InternalizacaoLoteBancario obterPorPk(InternalizacaoLoteBancario objeto) throws AcessoDadosException {
        return dao.obterPorPk(objeto);
    }

    public InternalizacaoLoteBancario obterPorPk(Integer id) throws AcessoDadosException {
        return dao.obterPorPk(id);
    }

    public Collection<InternalizacaoLoteBancario> obterTodos() throws AcessoDadosException {
        return dao.obterTodos();
    }

    public InternalizacaoLoteBancario obterPorIdCompromisso(String idCompromisso) throws AcessoDadosException {
        return dao.obterPorIdCompromisso(idCompromisso);
    }
    
    public Collection<Compromisso> obterCompromissos() throws AcessoDadosException {
        Collection<InternalizacaoLoteBancario> internalizacoes = dao.obterTodos();
        
        Collection<Compromisso> compromissos = new ArrayList<Compromisso>();
        
        for (InternalizacaoLoteBancario internalizacao : internalizacoes) {
            Compromisso compromisso = new Compromisso();

            compromisso.setId(internalizacao.getIdCompromisso());
            compromisso.setTipo(internalizacao.getTipo());
            compromisso.setSeqLinha(internalizacao.getSeqLinha());
            compromisso.setDescricao(internalizacao.getDescricao());
            compromisso.setValor(new BigDecimal(-1*internalizacao.getValor()));
            compromisso.setData(internalizacao.getEntrada());
            compromisso.setApdId(internalizacao.getIdCompromisso());
            compromisso.setApdTp(internalizacao.getTipo());

            compromissos.add(compromisso);
        }
        return compromissos;
    }

    public Collection<DevolucaoDTO> obterLotesBancarios() throws AcessoDadosException {
        Collection<InternalizacaoLoteBancario> internalizacoes = dao.obterTodos();

        Collection<DevolucaoDTO> devolucoes = new ArrayList<DevolucaoDTO>();

        for (InternalizacaoLoteBancario internalizacao : internalizacoes) {
            DevolucaoDTO devolucao = new DevolucaoDTO();

            devolucao.setId(internalizacao.getId().toString());
            devolucao.setTipo(internalizacao.getTipo());
            devolucao.setSeqLinha(internalizacao.getSeqLinha());
            devolucao.setDescricao(internalizacao.getDescricao());
            devolucao.setValor(new BigDecimal(-1*internalizacao.getValor()));
            devolucao.setApdId(internalizacao.getIdCompromisso());
            devolucao.setApdTp(internalizacao.getTipo());
            devolucao.setClassificacao(internalizacao.getClassificacao());
            devolucao.setData(internalizacao.getEntrada());
            devolucao.setEntrada(internalizacao.getEntrada());
            devolucao.setNomeFornecedorComp(internalizacao.getNomeFornecedorComp());
            devolucao.setTipoReceita(internalizacao.getTipoReceita());
            devolucao.setCentroCusto(internalizacao.getCentroCusto());

            devolucoes.add(devolucao);
        }
        return devolucoes;
    }

    public Compromisso obterCompromissoPorInternalizacaoLoteBancario(String idCompromisso) throws AcessoDadosException {
        InternalizacaoLoteBancario internalizacao = obterPorIdCompromisso(idCompromisso);

        Compromisso compromisso = new Compromisso();

        compromisso.setId(internalizacao.getIdCompromisso());
        compromisso.setTipo(internalizacao.getTipo());
        compromisso.setSeqLinha(internalizacao.getSeqLinha());
        compromisso.setDescricao(internalizacao.getDescricao());
        compromisso.setValor(new BigDecimal(-1*internalizacao.getValor()));
        compromisso.setData(internalizacao.getEntrada());
        compromisso.setApdId(internalizacao.getIdCompromisso());
        compromisso.setApdTp(internalizacao.getTipo());

        return compromisso;
    }

    public Collection<InternalizacaoLoteBancario> obterTodosOrdenadoPorCampo(String campo)
            throws AcessoDadosException {
        return dao.obterTodosOrdenadoPorCampo(campo);
    }

    public Collection<InternalizacaoLoteBancario> obterPorFiltro(InternalizacaoLoteBancario objeto) throws AcessoDadosException {
        return dao.obterPorFiltro(objeto);
    }
    
    public List<InternalizacaoLoteBancario> obterPorPeriodo(Date inicio, Date fim) {
        return dao.obterPorPeriodo(inicio, fim);
    }

}
