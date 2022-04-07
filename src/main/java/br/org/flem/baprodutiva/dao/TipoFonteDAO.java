package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.Fonte;
import br.org.flem.baprodutiva.negocio.TipoFonte;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;

/**
*
* Esta classe possui a lógica de acesso a dados, relativo a entidade TipoFonte
*
* @author Gerador de Código da FLEM
*
*/
public class TipoFonteDAO extends BaseDAOAb<TipoFonte> {

        public TipoFonteDAO() throws AcessoDadosException {}


    protected Class getClasseDto() {
        return TipoFonte.class;
    }

    @Override
    protected void adicionarAgregacoesCriteria(Criteria c, TipoFonte objeto) {
        if (objeto.getGrupoTipo() != null && objeto.getGrupoTipo().getId() != null) {
            c.add(Expression.eq("grupoTipo.id", objeto.getGrupoTipo().getId()));
        }
    }
}