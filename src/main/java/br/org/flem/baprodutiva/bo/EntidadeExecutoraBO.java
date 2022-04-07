package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.baprodutiva.dao.EntidadeExecutoraDAO;

import br.org.flem.baprodutiva.negocio.EntidadeExecutora;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Esta classe possui regras de negócios, referentes a entidade EntidadeExecutora
 *
 * @author Gerador de Código da FLEM
 *
 */
public class EntidadeExecutoraBO extends BaseBOAb<EntidadeExecutora> {

    public EntidadeExecutoraBO() throws AplicacaoException {
        super(new EntidadeExecutoraDAO());
    }
    
    public Set<EntidadeExecutora> obterPorPk(Collection<Serializable> ids) {
        Set<EntidadeExecutora> objetos = new HashSet<EntidadeExecutora>();
        try {
            EntidadeExecutoraDAO dao = new EntidadeExecutoraDAO();
            for (Serializable s : ids) {
                objetos.add(dao.obterPorPk(s));
            }
        } catch (AcessoDadosException ex) {
            Logger.getLogger(EntidadeExecutoraBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objetos;
    }
}
