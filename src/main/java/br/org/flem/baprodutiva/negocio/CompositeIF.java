package br.org.flem.baprodutiva.negocio;

import br.org.flem.baprodutiva.exception.FolhaException;
import java.io.Serializable;
import org.directwebremoting.annotations.DataTransferObject;

/**
 *
 * @author mjpereira
 */
@DataTransferObject
public interface CompositeIF extends Serializable {




    String getDescricao();

    void setDescricao(String descricao);

    Integer getId();

    void setId(Integer id);

}
