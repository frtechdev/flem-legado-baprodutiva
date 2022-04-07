
/*


 */
package br.org.flem.baprodutiva.bo;

import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.baprodutiva.negocio.SubCategoria;
import br.org.flem.fw.service.CentroResponsabilidade;
import br.org.flem.fw.service.GEM;
import br.org.flem.fw.service.impl.GEMImpl;
import br.org.flem.fwe.exception.AcessoDadosException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 *
 * @author fcsilva
 */
public class CentroCustoBO {

    GEM gem;

    public CentroCustoBO() {
        gem = new GEMImpl();
    }
    //Este método foi alterado para só retornar o centros de custo que não foram usados ainda.
    public List<CentroResponsabilidade> obterFilhos(String pai) throws AcessoDadosException {

//        List<CentroResponsabilidade> ccsNaoUsados = new ArrayList();

        return gem.obterCentrosCustoFilhos(pai);

   //     Set<String> ccsUsados = new CompositeFolhaBO().obterTodosCentrosCusto();

//        for (CentroResponsabilidade cc : ccs) {
//     //       if (!ccsUsados.contains(cc.getId())) {
//                ccsNaoUsados.add(cc);
//     //       }
//        }
//
//        return ccsNaoUsados;
    }
    public List<CentroResponsabilidade> obterCentroCustoPorCodigo(String[] ids) throws AcessoDadosException {

        List<CentroResponsabilidade> ccs = gem.obterCentrosCustoPorId(ids);

        return ccs;
    }
    //Este método foi alterado para só retornar o centros de custo que não foram usados ainda.
    public List<CentroResponsabilidade> obterFilhosAnaliticos(String pai) throws AcessoDadosException {

        List<CentroResponsabilidade> ccsNaoUsados = new ArrayList();

        List<CentroResponsabilidade> ccs = gem.obterCentrosCustoAnaliticosFilhos(pai);

        Set<String> ccsUsados = new CompositeFolhaBO().obterTodosCentrosCusto();

        for (CentroResponsabilidade cc : ccs) {
           if (!ccsUsados.contains(cc.getId())) {
                ccsNaoUsados.add(cc);
            }
        }

        return ccsNaoUsados;
    }
    public List<CentroResponsabilidade> obterFilhosAnaliticosPorPlanejamento(String pai, Planejamento poa) throws AcessoDadosException {

        List<CentroResponsabilidade> ccsNaoUsados = new ArrayList();

        List<CentroResponsabilidade> ccs = gem.obterCentrosCustoAnaliticosFilhos(pai);
        CentroResponsabilidade cr = new CentroResponsabilidade();
        Set<String> ccsUsados = new CompositeFolhaBO().obterTodosCentrosCustoPorPlanejamento(poa);

        for (CentroResponsabilidade cc : ccs) {
            if (!ccsUsados.contains(cc.getId())) {
                ccsNaoUsados.add(cc);
            }
        }
        return ccsNaoUsados;
    }

    public List<CentroResponsabilidade> obterFilhosAnaliticosPorPlanejamentoCategoria(String pai, Planejamento poa, Categoria categoria) throws AcessoDadosException {

        List<CentroResponsabilidade> ccsContem = new ArrayList();

        List<CentroResponsabilidade> ccs = gem.obterCentrosCustoAnaliticosFilhos(pai);
        CentroResponsabilidade cr = new CentroResponsabilidade();
        Set<String> ccsUsados = new CompositeFolhaBO().obterTodosCentrosCustoPorCategoriaPlanejamento(categoria, poa);


        for (CentroResponsabilidade cc : ccs) {
            if (ccsUsados.contains(cc.getId())) {
                ccsContem.add(cc);
            }
        }
        return ccsContem;
    }
    
    public List<CentroResponsabilidade> obterFilhosAnaliticosPorCategoria(String pai,Categoria categoria) throws AcessoDadosException{
    
        List<CentroResponsabilidade> ccporcategoriausados = new ArrayList();

        List<CentroResponsabilidade> ccs = gem.obterCentrosCustoAnaliticosFilhos(pai);
        CentroResponsabilidade cr = new CentroResponsabilidade();
        Set<String> todosccporcategoria = new CompositeFolhaBO().obterTodosCentrosCustoPorCategoria(categoria);
    
        for (CentroResponsabilidade cc : ccs) {
            if (!ccporcategoriausados.contains(cc.getId())) {
                ccporcategoriausados.add(cc);
            }
        }
        return ccporcategoriausados;
    
    }
    //Este método foi alterado para só retornar o centros de custo que não foram usados ainda.
    public List<CentroResponsabilidade> obterFilhosAnaliticosExceto(String pai, Set<String> exceto) throws AcessoDadosException {
        List<CentroResponsabilidade> ccsNaoUsados = new ArrayList();

        List<CentroResponsabilidade> ccs = gem.obterCentrosCustoAnaliticosFilhos(pai);

//        Set<String> ccsUsados = new CompositeFolhaBO().obterTodosCentrosCusto();

        //System.out.println(ccsUsados.toString()+" ccs usados");

        //remove os ccs usados na subatividade selecionada
        for (String remover : exceto) {
     //       ccsUsados.remove(remover);
        }

        //
        for (CentroResponsabilidade cc : ccs) {
  //          if (!ccsUsados.contains(cc.getId())) {
                ccsNaoUsados.add(cc);
    //        }
            //System.out.println(cc.getDescricao()+cc.getId()+" Todos ccs");
        }


        return ccsNaoUsados;
    }
    public List<CentroResponsabilidade> obterFilhosAnaliticosPorPOAExceto(String pai, Collection<String> exceto, Planejamento poa) throws AcessoDadosException {
        List<CentroResponsabilidade> ccsNaoUsados = new ArrayList();

        List<CentroResponsabilidade> ccs = gem.obterCentrosCustoAnaliticosFilhos(pai);

        Set<String> ccsUsados = new CompositeFolhaBO().obterTodosCentrosCustoPorPlanejamento(poa);

        //remove os ccs usados na subatividade selecionada
        for (String remover : exceto) {
            ccsUsados.remove(remover);
        }

        
        for (CentroResponsabilidade cc : ccs) {
            if (!ccsUsados.contains(cc.getId())) {
                ccsNaoUsados.add(cc);
            }
        }


        return ccsNaoUsados;
    }
    
    public List<CentroResponsabilidade> obterCCNaoAssociadosPorSubCat(SubCategoria subCat, String pai) throws AcessoDadosException {
        String descricao = "";
        if( subCat != null ){
            descricao = subCat.getDescricao();
        }
        String [] cats = descricao.split(" ");
        String cateAndSub = cats[0];
        
        List<CentroResponsabilidade> ccsNaoUsados = new ArrayList();

        List<CentroResponsabilidade> ccs = gem.obterCentrosCustoAnaliticosFilhos(pai);
        
        Set<String> ccsUsados = new CompositeFolhaBO().obterTodosCentrosCusto();

        for (CentroResponsabilidade cc : ccs) {
            
            if (!ccsUsados.contains(cc.getId())) {
                
                String cat = cc.getId().substring(5, 6);
                String subCate = cc.getId().substring(7, 8);
                System.out.println(cateAndSub+" : "+cat+"."+subCate);
                if(cateAndSub.equals(cat+"."+subCate)){
                    ccsNaoUsados.add(cc);
                }
                
            }
            
        }
        return ccsNaoUsados;
    }
    
    public List<CentroResponsabilidade> obterCCEdicao(String pai, List<String> selecionados, Set<CentroResponsabilidade> centrosCustos) throws AcessoDadosException {
        
        List<CentroResponsabilidade> ccs = new ArrayList();

        List<CentroResponsabilidade> ccsGem = gem.obterCentrosCustoAnaliticosFilhos(pai);
        
        Set<String> ccsUsados = new CompositeFolhaBO().obterTodosCentrosCusto();

        for (CentroResponsabilidade cc : ccsGem) {
            boolean existe = false;
            for(String val : selecionados){
                
                if (val.equals(cc.getId())) {
                    ccs.add(cc);
                    existe = true;
                    break;
                }
            
            }
            for(CentroResponsabilidade res : centrosCustos){
                if(res.getId().equals(cc.getId())){
                    existe = true;
                    break;
                }
            }
            if(!existe){
                if (!ccs.contains(cc.getId())) {
                    String val = selecionados.get(0);
                    String catGem = cc.getId().substring(5, 6) + "." +cc.getId().substring(7, 8);

                    String catSel = val.substring(5, 6) + "." +val.substring(7, 8);

                    if(catSel.equals(catGem)){
                        ccs.add(cc);
                    }
                    
                }
            }
            
        }
        return ccs;
    }
}
