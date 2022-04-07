package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.dao.FinanceiroPrevistoDAO;
import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.CompositeFolha;
import br.org.flem.baprodutiva.negocio.CompositeIF;
import br.org.flem.baprodutiva.negocio.FinanceiroPrevisto;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.fwe.exception.AcessoDadosException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * Esta classe possui regras de negócios, referentes aos Orgãos ResponsÃ¡veis
 * 
* @author Gerador de Código da FLEM
 * 
*/
public class FinanceiroPrevistoBO {

    FinanceiroPrevistoDAO dao;

    public FinanceiroPrevistoBO() throws AcessoDadosException {

        dao = new FinanceiroPrevistoDAO();
    }

    public void inserirOuAlterar(FinanceiroPrevisto objeto) throws AcessoDadosException {
        dao.inserirOuAlterar(objeto);
    }

    public void inserir(FinanceiroPrevisto objeto) throws AcessoDadosException {
        dao.inserir(objeto);
    }

    public void alterar(FinanceiroPrevisto objeto) throws AcessoDadosException {
        dao.alterar(objeto);
    }

    public void excluir(FinanceiroPrevisto objeto) throws AcessoDadosException {
        dao.excluir(objeto);
    }

    public void excluir(Collection<FinanceiroPrevisto> objetos) throws AcessoDadosException {
        dao.excluir(objetos);
    }

    public FinanceiroPrevisto obterPorPk(Serializable objeto) throws AcessoDadosException {
        return dao.obterPorPk(objeto);
    }

    public FinanceiroPrevisto obterPorPk(Integer id) throws AcessoDadosException {
        return dao.obterPorPk(id);
    }

    public Collection<FinanceiroPrevisto> obterTodos() throws AcessoDadosException {
        return dao.obterTodos();
    }

    public Collection<FinanceiroPrevisto> obterTodosOrdenadoPorCampo(String campo) throws AcessoDadosException {
        return dao.obterTodosOrdenadoPorCampo(campo);
    }

    public Collection<FinanceiroPrevisto> obterPorFiltro(FinanceiroPrevisto objeto) throws AcessoDadosException {
        return dao.obterPorFiltro(objeto);
    }

    public Collection<FinanceiroPrevisto> obterFinanceiroNoPeriodo(CompositeIF composite, Date inicio, Date fim) throws AcessoDadosException {
        return dao.obterFinanceiroNoPeriodo(composite, inicio, fim);
    }

    public Collection<FinanceiroPrevisto> obterFinanceiroNoPeriodo(Planejamento planejamento,Date inicio, Date fim) {
        return dao.obterFinanceiroNoPeriodo(planejamento,inicio, fim);
    }
    public Collection<FinanceiroPrevisto> obterFinanceiroNoPeriodoPlanejamentoAtivo(Collection<Planejamento> planejamentos,Date inicio, Date fim) {
        return dao.obterFinanceiroNoPeriodoPlanejamentoAtivo(planejamentos,inicio, fim);
    }
    
    
    public Collection<FinanceiroPrevisto> obterFinanceiroNoPeriodo(Categoria categoria, Planejamento planejamento, Date inicio, Date fim) {
        return dao.obterFinanceiroNoPeriodo(categoria, planejamento, inicio, fim);
    }

    public List<FinanceiroPrevisto> obterPorPlanejamento(Planejamento planejamento) {
        return dao.obterPorPlanejamento(planejamento);
    }
    
    
    public List<FinanceiroPrevisto> obterTodosPorSubatividadeOrdenadoPorData(CompositeFolha subAtividade) {
        return dao.obterTodosPorSubatividadeOrdenadoPorData(subAtividade);
    }

    public FinanceiroPrevisto obterPorSubAtividade(CompositeFolha subAtividade) {
        return dao.obterPorSubAtividade(subAtividade);
    }
}