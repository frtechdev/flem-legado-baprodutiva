package br.org.flem.baprodutiva.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.baprodutiva.negocio.Receita;

/**
*
* Esta classe possui a lógica de acesso a dados, relativo a entidade Receita
*
* @author Gerador de Código da FLEM
*
*/
public class ReceitaDAO extends BaseDAOAb<Receita> {

        public ReceitaDAO() throws AcessoDadosException {}

        protected Class getClasseDto() {
                return Receita.class;
        }

}