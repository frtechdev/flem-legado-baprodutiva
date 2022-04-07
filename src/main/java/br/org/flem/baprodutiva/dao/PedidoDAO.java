package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.Contrato;
import br.org.flem.baprodutiva.negocio.Pedido;
import org.hibernate.Criteria;

/**
 *
 * Esta classe possui a lógica de acesso a dados, relativo a entidade Categoria
 *
 * @author Gerador de Código da FLEM
 *
 */
public class PedidoDAO extends BaseDAOAb<Pedido> {

    public PedidoDAO() throws AcessoDadosException {
    }

    @Override
    protected Class getClasseDto() {
        return Pedido.class;
    }
    
    /*public Contrato obterPorNumero(String numero) {        
        return (Contrato) session.createQuery("from Contrato c where c.numero = :numero").setString("numero", numero ).uniqueResult();        
    }*/

    @Override
    protected void adicionarAgregacoesCriteria(Criteria c, Pedido objeto) {
    }
}