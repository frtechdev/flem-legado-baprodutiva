package br.org.flem.baprodutiva.web.dwr;

import br.org.flem.baprodutiva.bo.CategoriaBO;
import br.org.flem.baprodutiva.bo.CentroCustoBO;
import br.org.flem.baprodutiva.bo.CompositeFolhaBO;
import br.org.flem.baprodutiva.bo.SubCategoriaBO;
import br.org.flem.baprodutiva.bo.TipoFonteBO;
import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.CompositeFolha;
import br.org.flem.baprodutiva.negocio.CompositeNoAtividade;
import br.org.flem.baprodutiva.negocio.GrupoTipoFonte;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.baprodutiva.negocio.SubCategoria;
import br.org.flem.baprodutiva.negocio.TipoFonte;
import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fwe.exception.AcessoDadosException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.validator.GenericValidator;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 *
 * Classe com as funçÃµes utilizadas pelo DWR, nos JSP.
 *
 * @author dbbarreto
 */
@RemoteProxy
public class Funcoes {

  

    @RemoteMethod
    public Collection obterSubAtividadePorAtividade(String atividadeId) {
        Collection lista = new ArrayList();
        if (GenericValidator.isLong(atividadeId)) {
            try {
                CompositeFolha filtro = new CompositeFolha();

                CompositeNoAtividade atividade = new CompositeNoAtividade();
                atividade.setId(Integer.valueOf(atividadeId));

          //      filtro.setPai(atividade);

                lista = new CompositeFolhaBO().obterPorFiltro(filtro);
            } catch (AcessoDadosException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    @RemoteMethod
    public Collection obterTipoFontePorGrupoTipoFonte(String grupoTipoFonteId) {
        Collection lista = new ArrayList();
        if (GenericValidator.isLong(grupoTipoFonteId)) {
            try {
                TipoFonte filtro = new TipoFonte();

                GrupoTipoFonte grupoTipoFonte = new GrupoTipoFonte();
                grupoTipoFonte.setId(Integer.valueOf(grupoTipoFonteId));

                filtro.setGrupoTipo(grupoTipoFonte);

                lista = new TipoFonteBO().obterPorFiltro(filtro);
            } catch (AcessoDadosException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    @RemoteMethod
    public Collection obterTipoFonteSemGefPorGrupoTipoFonte(String grupoTipoFonteId) {
        Collection lista = new ArrayList();
        if (GenericValidator.isLong(grupoTipoFonteId)) {
            try {
                TipoFonte filtro = new TipoFonte();

                GrupoTipoFonte grupoTipoFonte = new GrupoTipoFonte();
                grupoTipoFonte.setId(Integer.valueOf(grupoTipoFonteId));

                filtro.setGrupoTipo(grupoTipoFonte);
                lista = new TipoFonteBO().obterPorFiltro(filtro);
            } catch (AcessoDadosException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }
    
    @RemoteMethod
    public Collection obterCentroDeCustosPorCategoria(String categoriaId){
        Collection lista = new ArrayList();
        if(GenericValidator.isLong((categoriaId))){
            try{
            Categoria categoria = new Categoria();
            categoria.setId(Integer.valueOf(categoriaId));
            lista = new CentroCustoBO().obterFilhosAnaliticosPorCategoria(PropertiesUtil.getProperties().getProperty("projeto"), categoria);
            }catch(Exception e){
                Logger.getLogger(Funcoes.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        
        }
        return lista;
    }
    
    @RemoteMethod
    public Collection obterCentroDeCustosNaoUsados(String planejamentoId) {
        Collection lista = new ArrayList();
        if (GenericValidator.isLong(planejamentoId)) {
            try {
                Planejamento planejamento = new Planejamento();
                planejamento.setId(Integer.valueOf(planejamentoId));
                lista = new CentroCustoBO().obterFilhosAnaliticosPorPlanejamento(PropertiesUtil.getProperties().getProperty("projeto"), planejamento);
            } catch (Exception e) {
                Logger.getLogger(Funcoes.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return lista;
    }


    
    @RemoteMethod
    public Collection obterCentroDeCustosNaoUsadosExceto(String poaId, String[] ccs) {
        Collection lista = new ArrayList();
        if (GenericValidator.isLong(poaId)) {
            try {
                Planejamento poa = new Planejamento();
                poa.setId(Integer.valueOf(poaId));
                lista = new CentroCustoBO().
                        obterFilhosAnaliticosPorPOAExceto(PropertiesUtil.getProperties().getProperty("projeto"),
                        Arrays.asList(ccs), poa);

            } catch (Exception e) {
                Logger.getLogger(Funcoes.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return lista;
    }
    
    @RemoteMethod
    public Collection obterSubcategoriaPorCategoria(Integer categoriaId) throws Exception{
        
        Collection lista = new ArrayList();
        
        if(GenericValidator.isLong(Long.toString(categoriaId))){
            try{
                  Categoria  cat = new CategoriaBO().obterPorPk(categoriaId);
                  Collection<SubCategoria> subcategorias = new SubCategoriaBO().obterTodos();
                  for(SubCategoria subcategoria : subcategorias){
                      if(subcategoria.getCategoria().getId().equals(cat.getId())){
                         lista.add(subcategoria);
                      }
                  } 
            }catch(Exception e){    
               Logger.getLogger(Funcoes.class.getName()).log(Level.SEVERE, e.getMessage(), e);      
            }
        }
     return lista;
    }
    
    @RemoteMethod
    public Collection obterCCPorSubCategoria(Integer categoriaId) throws Exception{
        
        Collection lista = new ArrayList();
        
        try{
            SubCategoria  cat = new SubCategoriaBO().obterPorPk(categoriaId);
            //cat.setId(categoriaId);
            lista = new CentroCustoBO().obterCCNaoAssociadosPorSubCat(cat, PropertiesUtil.getProperties().getProperty("projeto"));
            
        }catch(Exception e){    
           Logger.getLogger(Funcoes.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        
        return lista;
    }

}
