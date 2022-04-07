package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.dao.PlanejamentoDAO;
import br.org.flem.baprodutiva.negocio.CompositeFolha;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.fwe.exception.AcessoDadosException;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author mgsilva
 */
public class PlanejamentoBO {

    PlanejamentoDAO dao;

    public PlanejamentoBO() throws AcessoDadosException {

        dao = new PlanejamentoDAO();
    }

    public void inserirOuAlterar(Planejamento objeto) throws AcessoDadosException {
        dao.inserirOuAlterar(objeto);
    }

    public void inserir(Planejamento objeto) throws AcessoDadosException {
        dao.inserir(objeto);
    }

    public void alterar(Planejamento objeto) throws AcessoDadosException {
        dao.alterar(objeto);
    }

    public void excluir(Planejamento objeto) throws AcessoDadosException {
        dao.excluir(objeto);
    }

    public void excluir(Collection<Planejamento> objetos) throws AcessoDadosException {
        dao.excluir(objetos);
    }

    public Planejamento obterPorPk(Planejamento objeto) throws AcessoDadosException {
        return dao.obterPorPk(objeto);
    }

    public Planejamento obterPorPk(Integer id) throws AcessoDadosException {
        return dao.obterPorPk(id);
    }
    
    public Planejamento obterPlanejamentoAtivoPorPk(Integer id) throws AcessoDadosException {
        return dao.obterPlanejamentoAtivoPorPk(id);
    }

    public Collection<Planejamento> obterTodosAtivos() throws AcessoDadosException {
        return dao.obterTodosAtivos();
    }
    public Collection<Planejamento> obterTodos() throws AcessoDadosException {
        return dao.obterTodos();
    }

    public Collection<Planejamento> obterTodosOrdenadoPorCampo(String campo)
            throws AcessoDadosException {
        return dao.obterTodosOrdenadoPorCampo(campo);
    }

    public Collection<Planejamento> obterPorFiltro(Planejamento objeto) throws AcessoDadosException {
        return dao.obterPorFiltro(objeto);
    }

    public Collection<Planejamento> obterPoaNaoPlanejadoPorSubAtividade(CompositeFolha subAtividade) {
        return ((PlanejamentoDAO) this.dao).obterPoaNaoPlanejadoPorSubAtividade(subAtividade);
    }

    public Collection<Planejamento> obterPoaSemFinanceiroPrevistoPorSubAtividade(CompositeFolha subAtividade) {
        return ((PlanejamentoDAO) this.dao).obterPoaSemFinanceiroPrevistoPorSubAtividade(subAtividade);
    }
    
    public Collection<Planejamento> obterPendentesDeTaxa() {
        return ((PlanejamentoDAO)dao).obterPendentesDeTaxa();
    }
    
    public Planejamento obterPlanejamentoEntreDatas(Date dataInicial, Date dataFinal){
       return dao.obterPlanejamentoEntreDatas(dataInicial, dataFinal);
    }
}
