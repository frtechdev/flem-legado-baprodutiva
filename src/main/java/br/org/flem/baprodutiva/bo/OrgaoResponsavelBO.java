package br.org.flem.baprodutiva.bo;

import br.org.flem.fwe.bo.BaseBOAb;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.baprodutiva.dao.OrgaoResponsavelDAO;

import br.org.flem.baprodutiva.negocio.OrgaoResponsavel;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*
* Esta classe possui regras de negócios, referentes aos Orgãos ResponsÃ¡veis
*
* @author Gerador de Código da FLEM
*
*/
public class OrgaoResponsavelBO extends BaseBOAb<OrgaoResponsavel> {

    public OrgaoResponsavelBO() throws AplicacaoException {
        super(new OrgaoResponsavelDAO());
    }
    
    public Set<OrgaoResponsavel> obterPorPk(Collection<Serializable> ids) {
        Set<OrgaoResponsavel> objetos = new HashSet<OrgaoResponsavel>();
        try {
            OrgaoResponsavelDAO dao = new OrgaoResponsavelDAO();
            for (Serializable s : ids) {
                objetos.add(dao.obterPorPk(s));
            }
        } catch (AcessoDadosException ex) {
            Logger.getLogger(EntidadeExecutoraBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objetos;
    }
}