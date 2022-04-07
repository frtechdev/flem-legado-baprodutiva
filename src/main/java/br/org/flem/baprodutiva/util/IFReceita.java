/*


 */

package br.org.flem.baprodutiva.util;

import java.util.Date;

/**
 *
 * @author dbbarreto
 */
public interface IFReceita {
    public Integer getId();
    public Double getValor();
    public String getDescricao();
    public Date getEntrada();
    public String getTipoReceita(); //DEV para Devolu��o, INT para Internaliza��o e APL para Aplica��o Financeira
}
