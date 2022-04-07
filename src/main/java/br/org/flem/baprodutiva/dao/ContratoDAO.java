package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.Contrato;
import java.util.Collection;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

/**
 *
 * Esta classe possui a lógica de acesso a dados, relativo a entidade Categoria
 *
 * @author Gerador de Código da FLEM
 *
 */
public class ContratoDAO extends BaseDAOAb<Contrato> {

    public ContratoDAO() throws AcessoDadosException {
    }

    @Override
    protected Class getClasseDto() {
        return Contrato.class;
    }
    
    public Contrato obterPorNumero(String numero) {        
        return (Contrato) session.createQuery("from Contrato c where c.numero = :numero").setString("numero", numero ).uniqueResult();        
    }

    public List<String> obterNumerosContratos() {
        return session.createQuery("select c.numero from Contrato c").list();
    }

    public Collection<Contrato> obterPorContratoFornecedor(String contrato, String nomeFornecedor){
        Criteria contratoCriteria = session.createCriteria(Contrato.class);
        if (contrato != null && !contrato.trim().equals("")){
            contratoCriteria.add(Expression.eq("numero", contrato.trim()));
        }

        if (nomeFornecedor != null && !nomeFornecedor.trim().equals("")){
            contratoCriteria.add(Expression.like("nomeFornecedor", "%"+nomeFornecedor.trim()+"%"));
        }
        return contratoCriteria.list();
    }
    
    @Override
    protected void adicionarAgregacoesCriteria(Criteria c, Contrato objeto) {
    }
}