package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.dao.InternalizacaoDevolucaoDAO;
import br.org.flem.baprodutiva.dto.DevolucaoDTO;
import br.org.flem.baprodutiva.negocio.InternalizacaoDevolucao;
import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fw.persistencia.dto.Compromisso;
import br.org.flem.fwe.exception.AcessoDadosException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * Esta classe possui regras de negócios, referentes a entidade Categoria
 *
 * @author Gerador de Código da FLEM
 *
 */
public class InternalizacaoDevolucaoBO {

    InternalizacaoDevolucaoDAO dao;

    public InternalizacaoDevolucaoBO() throws AcessoDadosException {

        dao = new InternalizacaoDevolucaoDAO();
    }

    public void inserirOuAlterar(InternalizacaoDevolucao objeto) throws AcessoDadosException {
        dao.inserirOuAlterar(objeto);
    }

    public void inserir(InternalizacaoDevolucao objeto) throws AcessoDadosException {
         dao.inserir(objeto);
    }

    public void alterar(InternalizacaoDevolucao objeto) throws AcessoDadosException {
        dao.alterar(objeto);
    }

    public void excluir(InternalizacaoDevolucao objeto) throws AcessoDadosException {
        dao.excluir(objeto);
    }

    public void excluir(Collection<InternalizacaoDevolucao> objetos) throws AcessoDadosException {
        dao.excluir(objetos);
    }

    public InternalizacaoDevolucao obterPorPk(InternalizacaoDevolucao objeto) throws AcessoDadosException {
        return dao.obterPorPk(objeto);
    }

    public InternalizacaoDevolucao obterPorPk(Integer id) throws AcessoDadosException {
        return dao.obterPorPk(id);
    }

    public Collection<InternalizacaoDevolucao> obterTodos() throws AcessoDadosException {
        return dao.obterTodos();
    }

    public InternalizacaoDevolucao obterPorIdCompromisso(String idCompromisso) throws AcessoDadosException {
        return dao.obterPorIdCompromisso(idCompromisso);
    }

    public Collection<Compromisso> obterCompromissos() throws AcessoDadosException {
        Collection<InternalizacaoDevolucao> internalizacoes = dao.obterTodos();

        Collection<Compromisso> compromissos = new ArrayList<Compromisso>();

        for (InternalizacaoDevolucao internalizacao : internalizacoes) {
            Compromisso compromisso = new Compromisso();

            compromisso.setId(internalizacao.getIdCompromisso());
            compromisso.setTipo(internalizacao.getTipo());
            compromisso.setSeqLinha(internalizacao.getSeqLinha());
            compromisso.setDescricao(internalizacao.getDescricao());
            compromisso.setValor(new BigDecimal(-1 * internalizacao.getValor()));
            compromisso.setData(internalizacao.getEntrada());
            compromisso.setApdId(internalizacao.getIdCompromisso());
            compromisso.setApdTp(internalizacao.getTipo());

            compromissos.add(compromisso);
        }
        return compromissos;
    }

    public Collection<DevolucaoDTO> obterDevolucoes() throws AcessoDadosException {
        Collection<InternalizacaoDevolucao> internalizacoes = dao.obterTodos();

        Collection<DevolucaoDTO> devolucoes = new ArrayList<DevolucaoDTO>();

        for (InternalizacaoDevolucao internalizacao : internalizacoes) {
            DevolucaoDTO devolucao = new DevolucaoDTO();

            devolucao.setId(internalizacao.getId().toString());
            devolucao.setTipo(internalizacao.getTipo());
            devolucao.setSeqLinha(internalizacao.getSeqLinha());
            devolucao.setDescricao(internalizacao.getDescricao());
            devolucao.setValor(new BigDecimal(-1 * internalizacao.getValor()));
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

    public Compromisso obterCompromissoPorInternalizacaoDevolucao(String idCompromisso) throws AcessoDadosException {
        InternalizacaoDevolucao internalizacao = obterPorIdCompromisso(idCompromisso);

        Compromisso compromisso = new Compromisso();

        compromisso.setId(internalizacao.getIdCompromisso());
        compromisso.setTipo(internalizacao.getTipo());
        compromisso.setSeqLinha(internalizacao.getSeqLinha());
        compromisso.setDescricao(internalizacao.getDescricao());
        compromisso.setValor(new BigDecimal(-1 * internalizacao.getValor()));
        compromisso.setData(internalizacao.getEntrada());
        compromisso.setApdId(internalizacao.getIdCompromisso());
        compromisso.setApdTp(internalizacao.getTipo());

        return compromisso;
    }

    public Collection<InternalizacaoDevolucao> obterTodosOrdenadoPorCampo(String campo)
            throws AcessoDadosException {
        return dao.obterTodosOrdenadoPorCampo(campo);
    }

    public Collection<InternalizacaoDevolucao> obterPorFiltro(InternalizacaoDevolucao objeto) throws AcessoDadosException {
        return dao.obterPorFiltro(objeto);
    }

    public List<InternalizacaoDevolucao> obterPorPeriodo(Date inicio, Date fim) {
        return dao.obterPorPeriodo(inicio, fim);
    }

    public Collection<InternalizacaoDevolucao> obterDevolucoesExcetoCCOperacional() throws AcessoDadosException, IOException {
        Collection<InternalizacaoDevolucao> retorno = new ArrayList<InternalizacaoDevolucao>();
        for (InternalizacaoDevolucao devolucao : this.obterTodos()) {
            if (devolucao.getCentroCusto().equals(PropertiesUtil.getProperties().getProperty("ccOperacional"))) {
                continue;
            }
            retorno.add(devolucao);
        }
        return retorno;
    }
    
    public List<InternalizacaoDevolucao> obterDevolucoesCCOperacionalPorPeriodo(Date inicio, Date fim) throws IOException {
        return ((InternalizacaoDevolucaoDAO)this.dao).obterDevolucoesCCOperacionalPorPeriodo(inicio, fim);
    }
    
     public Collection<InternalizacaoDevolucao> obterDevolucoesSubDoacao() {
         return dao.obterDevolucoesSubDoacao();
     }
    public InternalizacaoDevolucao obterDevolucao(String idCompromisso, String idComp, String seqLinha){
        return dao.obterDevolucao(idCompromisso, idComp, seqLinha);
    }
     
    public static void main(String[] args) throws AcessoDadosException {
        System.out.println(new InternalizacaoDevolucaoBO().obterTodos());
    }
}
