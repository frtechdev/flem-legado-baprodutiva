package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.GrupoTipoFonte;
import org.hibernate.Criteria;

/**
*
* Esta classe possui a lógica de acesso a dados, relativo a entidadeGrupoTipo
*
* @author Gerador de Código da FLEM
*
*/
public class GrupoTipoFonteDAO extends BaseDAOAb<GrupoTipoFonte> {

        public GrupoTipoFonteDAO() throws AcessoDadosException {}

        protected Class getClasseDto() {
                return GrupoTipoFonte.class;
        }
        
        @Override
        protected void adicionarAgregacoesCriteria(Criteria c, GrupoTipoFonte objeto) {

        }
}