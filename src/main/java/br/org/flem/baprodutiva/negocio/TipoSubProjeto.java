package br.org.flem.baprodutiva.negocio;

import org.directwebremoting.annotations.DataTransferObject;

/**
 *
 * @author fcsilva
 */
@DataTransferObject
public enum TipoSubProjeto {
    REABILITACAO_AREAS_DEGRADADAS(0),
    CONSERVACAO_USO_SUSTENTAVEL_BIODIVERSIDADE(1),
    GESTAO_RECURSOS_HIDRICOS_SOLO(2),
    DESENVOLVIMENTO_SISTEMAS_PRODUTIVOS(3),
    DESENVOLVIMENTO_CULTURAL_SOCIAL(4),
    FOMENTO_INCENTIVOS_AMBIENTAIS(5);

    private int codigo = -1;
    private TipoSubProjeto(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
    
    public String getConstante() {
        return this.toString();
    }
    
    public static TipoSubProjeto obterPorCodigo(int codigo){
        switch(codigo){
            case 0:
                return REABILITACAO_AREAS_DEGRADADAS;
                
            case 1:
                return CONSERVACAO_USO_SUSTENTAVEL_BIODIVERSIDADE;
                
            case 2 :
                return GESTAO_RECURSOS_HIDRICOS_SOLO;
                
            case 3:
                return DESENVOLVIMENTO_SISTEMAS_PRODUTIVOS;
                
            case 4:
                return DESENVOLVIMENTO_CULTURAL_SOCIAL;
                
            case 5: 
                return FOMENTO_INCENTIVOS_AMBIENTAIS;
        }
        return null;
    }
    
    public String getNome(){
        switch(this){
            case REABILITACAO_AREAS_DEGRADADAS:
                return "ReabilitaÁ„o de √Åreas Degradadas";
                
            case CONSERVACAO_USO_SUSTENTAVEL_BIODIVERSIDADE:
                return "ConservaÁ„o do Uso Sustent√°vel da Biodiversidade";
                
            case GESTAO_RECURSOS_HIDRICOS_SOLO:
                return "Gest„o de Recursos HÌdricos e do Solo";
                
            case DESENVOLVIMENTO_SISTEMAS_PRODUTIVOS:
                return "Desenvolvimento de Sistemas Produtivos Sustent√°veis e Economicamente Vi√°veis";
                
            case DESENVOLVIMENTO_CULTURAL_SOCIAL:
                return "Desenvolvimento Cultural e Social";
                
            case FOMENTO_INCENTIVOS_AMBIENTAIS:
                return "Fomento a Incentivos Ambientais";                
        }
        return null;
    }    
}
