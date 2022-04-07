package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.baprodutiva.bo.CategoriaBO;
import br.org.flem.baprodutiva.bo.CentroCustoBO;
import br.org.flem.baprodutiva.bo.CompositeFolhaBO;
import br.org.flem.baprodutiva.bo.EntidadeExecutoraBO;
import br.org.flem.baprodutiva.bo.OrgaoResponsavelBO;
import br.org.flem.baprodutiva.bo.PlanejamentoBO;
import br.org.flem.baprodutiva.bo.SubCategoriaBO;
import br.org.flem.baprodutiva.negocio.Categoria;
import br.org.flem.baprodutiva.negocio.CompositeFolha;
import br.org.flem.baprodutiva.negocio.EntidadeExecutora;
import br.org.flem.baprodutiva.negocio.OrgaoResponsavel;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.baprodutiva.negocio.SubCategoria;
import br.org.flem.baprodutiva.web.dwr.Funcoes;
import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fw.service.CentroResponsabilidade;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.util.Lista;
import br.org.flem.fwe.web.tag.MensagemTag;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

/**
 * @author dbbarreto
 */
public class SubAtividadeAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws AplicacaoException {
        /*
        try {
            request.setAttribute("lista", new CompositeFolhaBO().obterTodos());    
            request.setAttribute("categorias", new CategoriaBO().obterTodos());
            request.setAttribute("planejamentos",new PlanejamentoBO().obterTodos());
            request.setAttribute("subcategorias", new SubCategoriaBO().obterTodos());

        }
        catch(AcessoDadosException e) {
            e.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("lista");
        */
        
        return filtrar(mapping, form, request, response);
    }
    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws AplicacaoException {
        try {
            request.setAttribute("categorias", new CategoriaBO().obterTodos());
            //request.setAttribute("planejamentos", new PlanejamentoBO().obterTodos());
            request.setAttribute("subcategorias", new SubCategoriaBO().obterTodos());
            request.setAttribute("centroCustos", new ArrayList());
            request.setAttribute("entidadesExecutoras", new EntidadeExecutoraBO().obterTodos());
            request.setAttribute("orgaosResponsaveis", new OrgaoResponsavelBO().obterTodos());
            return mapping.findForward("novo");
        } catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return unspecified(mapping, form, request, response);
    }
    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            CompositeFolha atividade = new CompositeFolha();
            Integer idSubcategoria = Integer.valueOf(request.getParameter ("subcategoriaId"));
            Integer idCategoria    = Integer.valueOf(request.getParameter("categoriaId"));
            //Integer idPlanejamento  = Integer.valueOf(dyna.getString("planejamentoId"));
            

            //Planejamento planejamento = new PlanejamentoBO().obterPorPk(idPlanejamento);
            Categoria categoria = new CategoriaBO().obterPorPk(idCategoria);
            SubCategoria subcategoria = new SubCategoriaBO().obterPorPk(idSubcategoria);

            atividade.setDescricao(dyna.getString("descricao"));
            atividade.setObservacao(dyna.getString("observacao"));

           // atividade.setPlanejamento(planejamento);
            atividade.setCategoria(categoria);
            atividade.setSubCategoria(subcategoria);

            
            atividade.setCcCompartilhado(dyna.getString("ccCompartilhado").equals("on") ? true : false);

            if (!atividade.getCcCompartilhado()) {
                String[] ccs = dyna.getStrings("centroCusto");
                atividade.getCentrosCusto().addAll(Arrays.asList(ccs));
            }

            Collection<Serializable> idsEntidades = Lista.convertCollectionInteger(dyna.getStrings("entidades"), new ArrayList<Integer>());
            Set<EntidadeExecutora> entidades = new EntidadeExecutoraBO().obterPorPk(idsEntidades);

            for (EntidadeExecutora e : entidades) {
                atividade.getEntidadesExecutoras().add(e);
                e.getCompositeFolhas().add(atividade);
            }

            Collection<Serializable> idsOrgaos = Lista.convertCollectionInteger(dyna.getStrings("orgaos"), new ArrayList<Integer>());
            Set<OrgaoResponsavel> orgaos = new OrgaoResponsavelBO().obterPorPk(idsOrgaos);

            for (OrgaoResponsavel o : orgaos) {
                atividade.getOrgaosResponsaveis().add(o);
                o.getCompositeFolhas().add(atividade);
            }

            new CompositeFolhaBO().inserir(atividade);


            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Atividade inserida com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } catch (Exception ex) {
            ex.printStackTrace();

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar inserir a Subatividade.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        request.setAttribute("acao", "SubAtividade.do");
        return mapping.findForward("redirect");
    }
    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            CompositeFolha atividade = new CompositeFolhaBO().obterPorPk(Integer.valueOf(dyna.getString("id")));
            atividade.setDescricao(dyna.getString("descricao"));
            atividade.setObservacao(dyna.getString("observacao"));

            //Planejamento planejamento = new PlanejamentoBO().obterPorPk(Integer.valueOf(dyna.getString("planejamentoId")));
            Categoria categoria = new CategoriaBO().obterPorPk(Integer.valueOf(dyna.getString("categoriaId")));
            SubCategoria subcategoria = new SubCategoriaBO().obterPorPk(Integer.valueOf(dyna.getString("subcategoriaId")));

            //atividade.setPlanejamento(planejamento);
            atividade.setCategoria(categoria);
            atividade.setSubCategoria(subcategoria);
            

            String[] ccs = dyna.getStrings("centroCusto");
            if (!atividade.getCcCompartilhado()) {
                atividade.getCentrosCusto().clear();
                atividade.getCentrosCusto().addAll(Arrays.asList(ccs));
            }
            
            Collection<Serializable> idsEntidades = Lista.convertCollectionInteger(dyna.getStrings("entidades"), new ArrayList<Integer>());
            Set<EntidadeExecutora> entidades = new EntidadeExecutoraBO().obterPorPk(idsEntidades);

            CompositeFolhaBO folhaBO = new CompositeFolhaBO();

            folhaBO.excluirEntidadeExecutoraAssociada(atividade);
            for (EntidadeExecutora e : entidades) {
                e.getCompositeFolhas().add(atividade);
            }

            Collection<Serializable> idsOrgaos = Lista.convertCollectionInteger(dyna.getStrings("orgaos"), new ArrayList<Integer>());
            Collection<OrgaoResponsavel> orgaos = new OrgaoResponsavelBO().obterPorPk(idsOrgaos);

            folhaBO.excluirOrgaoResponsavelAssociado(atividade);
            for (OrgaoResponsavel o : orgaos) {
                o.getCompositeFolhas().add(atividade);
            }

            new CompositeFolhaBO().inserirOuAlterar(atividade);

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Atividade alterada com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } catch (Exception ex) {
            ex.printStackTrace();

            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar alterar a Subatividade.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        request.setAttribute("acao", "SubAtividade.do");
        return mapping.findForward("redirect");
    }
    @SuppressWarnings("empty-statement")
    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws AplicacaoException {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");

        try {
            request.setAttribute("entidadesExecutoras", new EntidadeExecutoraBO().obterTodos());
            request.setAttribute("orgaosResponsaveis", new OrgaoResponsavelBO().obterTodos());
            request.setAttribute("planejamentos", new PlanejamentoBO().obterTodos());
            request.setAttribute("categorias", new CategoriaBO().obterTodos());
            request.setAttribute("subcategorias", new SubCategoriaBO().obterTodos());
//            request.setAttribute("centroCustos", new CentroCustoBO().obter(id));
            if (GenericValidator.isInt(id)) {
                CompositeFolha atividade = new CompositeFolhaBO().obterPorPk(Integer.valueOf(id));
                Set<CentroResponsabilidade> centrosCustos = new HashSet(new Funcoes().obterCCPorSubCategoria(atividade.getSubCategoria().getId()));
                if(!atividade.getCentrosCusto().isEmpty()){
                    centrosCustos.addAll(new CentroCustoBO().obterCCEdicao(PropertiesUtil.getProperties().getProperty("projeto"), new ArrayList(atividade.getCentrosCusto()), centrosCustos));
                }
                BeanUtils.copyProperties(dyna, atividade);

                dyna.set("categoriaId", atividade.getCategoria().getId().toString());
                dyna.set("ccCompartilhado", atividade.getCcCompartilhado() ? "on" : "");
                //dyna.set("planejamentoId",atividade.getPlanejamento().getId().toString());
                dyna.set("categoriaId", atividade.getCategoria().getId().toString());
                dyna.set("subcategoriaId", atividade.getSubCategoria().getId().toString());

                dyna.set("descricao", atividade.getDescricao());
                dyna.set("observacao", atividade.getObservacao());
                request.setAttribute("centroCustos", new ArrayList(centrosCustos));
                //request.setAttribute("centroCustos", new CentroCustoBO().obterFilhosAnaliticosPorPOAExceto(PropertiesUtil.getProperties().getProperty("projeto"), atividade.getCentrosCusto(), atividade.getPlanejamento()));
                
//                dyna.set("centroCusto",atividade.getCentrosCusto().toArray(new String[0]));
                
                Set<String> cc = atividade.getCentrosCusto();
                StringBuffer sb = new StringBuffer();
                
                sb.delete(0, sb.length());
                for (String obj : cc) {
                    sb.append(obj);
                    sb.append(",");
                }
    
                String lista = (sb.length() > 0) ? sb.substring(0, sb.length() - 1) : "";
                request.setAttribute("centroCustoMarcados", lista);                      
                dyna.set("centroCusto",atividade.getCentrosCusto().toArray(new String[0]));
             
                Set<EntidadeExecutora> e = atividade.getEntidadesExecutoras();
                sb.delete(0, sb.length());
                for (EntidadeExecutora obj : e) {
                    sb.append(obj.getId());
                    sb.append(",");
                }
                lista = (sb.length() > 0) ? sb.substring(0, sb.length() - 1) : "";
                request.setAttribute("entidadesMarcadas", lista);

                Set<OrgaoResponsavel> o = atividade.getOrgaosResponsaveis();
                sb.delete(0, sb.length());
                for (OrgaoResponsavel obj : o) {
                    sb.append(obj.getId());
                    sb.append(",");
                }
                lista = (sb.length() > 0) ? sb.substring(0, sb.length() - 1) : "";
                request.setAttribute("orgaosMarcados", lista);

            }
            return mapping.findForward("alterar");
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar selecionar a Atividade.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return unspecified(mapping, form, request, response);
    }
    public ActionForward filtrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws AplicacaoException {
        DynaActionForm dyna = (DynaActionForm) form;

        try {
            request.setAttribute("categorias", new CategoriaBO().obterTodos());
            request.setAttribute("planejamentos", new PlanejamentoBO().obterTodos());
            request.setAttribute("subcategorias", new SubCategoriaBO().obterTodos());

            String categoriaIdFiltro = (String) dyna.get("categoriaId");
            String subcategoriaIdFiltro = (String) dyna.get("subcategoriaId");
            String planejamentoIdFiltro = (String) dyna.get("planejamentoId");
            String descricaoFiltro = (String) dyna.get("descricao");

            Categoria categoria = null;
            SubCategoria subcategoria = null;
            Planejamento planejamento = null;
            
            if (categoriaIdFiltro != null && !categoriaIdFiltro.isEmpty()) {
                if (GenericValidator.isInt(categoriaIdFiltro)) {
                    categoria = new CategoriaBO().obterPorPk(new Integer(categoriaIdFiltro));
                }
            }
			
            if(subcategoriaIdFiltro != null && !subcategoriaIdFiltro.isEmpty()){
                if(GenericValidator.isInt(subcategoriaIdFiltro)){
                    subcategoria = new SubCategoriaBO().obterPorPk(new Integer(subcategoriaIdFiltro));
                }
            }

            if (GenericValidator.isInt(planejamentoIdFiltro)) {
                if(GenericValidator.isInt(planejamentoIdFiltro)){
                   planejamento =  new PlanejamentoBO().obterPorPk(new Integer (planejamentoIdFiltro));
                }
            }

            List<CompositeFolha> lista = new CompositeFolhaBO().obterPorCategoriaPlanejamentoSubcategoria(planejamento, categoria, subcategoria);
            Collections.sort(lista, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    CompositeFolha comp1 = (CompositeFolha) o1;
                    CompositeFolha comp2 = (CompositeFolha) o2;
                    int comparacao = comp1.getCategoria().getDescricao().compareTo(comp2.getCategoria().getDescricao());
                    if (comparacao == 0) {
                        comparacao = comp1.getSubCategoria().getDescricao().compareTo(comp2.getSubCategoria().getDescricao());
                        if (comparacao == 0) {
                            comparacao = comp1.getDescricao().compareTo(comp2.getDescricao());
                            if (comparacao == 0) {
                                comparacao = comp1.getDescricao().compareTo(comp2.getDescricao());
                            }
                        }
                    }
                    return comparacao;
                }
            });

            request.setAttribute("lista", lista);
        } catch (AcessoDadosException e) {
            e.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }

        return mapping.findForward("lista");
    }
    public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws AplicacaoException {
        ArrayList erros = new ArrayList();
        try {
            DynaActionForm dyna = (DynaActionForm) form;
            String[] id = new String[0];
            if (request.getParameterValues("ids_exclusao") != null) {
                id = request.getParameterValues("ids_exclusao");
            }

            HibernateUtil.beginTransaction();
            for (int i = 0; i < id.length; i++) {
                CompositeFolhaBO subAtividadeBO = new CompositeFolhaBO();
                CompositeFolha subAtividade = subAtividadeBO.obterPorPk(Integer.valueOf(id[i]));

                try {
                    subAtividadeBO.excluir(subAtividade);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    HibernateUtil.rollbackTransaction();
                    erros.add("A Subatividade \"" + subAtividade.getDescricao() + "\" está associada. Não pode ser excluída!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } catch (AcessoDadosException ex) {
            Logger.getLogger(SubAtividadeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        return unspecified(mapping, form, request, response);
    }
}
