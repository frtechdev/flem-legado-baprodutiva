package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.baprodutiva.bo.FonteBO;
import br.org.flem.baprodutiva.bo.GrupoTipoFonteBO;
import br.org.flem.baprodutiva.bo.PlanejamentoBO;
import br.org.flem.baprodutiva.bo.TipoFonteBO;
import br.org.flem.baprodutiva.negocio.Fonte;
import br.org.flem.baprodutiva.negocio.FonteArquivo;
import br.org.flem.baprodutiva.negocio.GrupoTipoFonte;
import br.org.flem.baprodutiva.negocio.Origem;
import br.org.flem.baprodutiva.negocio.Planejamento;
import br.org.flem.baprodutiva.negocio.TipoFonte;
import br.org.flem.fw.persistencia.dto.Documento;
import br.org.flem.fw.service.IUsuario;
import br.org.flem.fw.service.impl.GEDImpl;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.util.Constante;
import br.org.flem.fwe.web.tag.MensagemTag;
import java.util.ArrayList;
import java.util.List;
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
import org.apache.struts.upload.FormFile;

/**
 * @author dbbarreto
 */
public class FonteAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("lista", new FonteBO().obterTodos());
            
        }
        catch(AcessoDadosException e) {
            e.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("lista");
    }
    
    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("tipoFontes", new TipoFonteBO().obterTodos());
            request.setAttribute("grupoTipoFontes", new GrupoTipoFonteBO().obterTodos());
            request.setAttribute("planejamentos", new PlanejamentoBO().obterTodos() );
            
            return mapping.findForward("novo");
        } 
        
        catch (AplicacaoException ex) {
            Logger.getLogger(FonteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return unspecified(mapping, form, request, response);
    }
    
    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {        
            Fonte fonte = new Fonte();
            Planejamento planejamento = new PlanejamentoBO().obterPorPk(Integer.valueOf( dyna.getString("planejamentoId") ));
            fonte.setPlanejamento( planejamento);
            fonte.setValor(Double.valueOf(dyna.getString("valor")));

            TipoFonte categoria = new TipoFonteBO().obterPorPk(Integer.valueOf(dyna.getString("tipoFonteId")));    
            fonte.setTipo(categoria);
            fonte.setOrigem(Origem.valueOf(dyna.getString("origemId")));
            new FonteBO().inserir(fonte);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Contrapartida inserida com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar inserir a Contrapartida.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }        
        //request.setAttribute("acao", "Fonte.do");
        return mapping.findForward("redirect");
    }
    
    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {        
            Fonte fonte = new FonteBO().obterPorPk(Integer.valueOf(dyna.getString("id")));
            Planejamento planejamento = new PlanejamentoBO().obterPorPk(Integer.valueOf( dyna.getString("planejamentoId") ));
            fonte.setPlanejamento(planejamento);
            fonte.setValor(Double.valueOf(dyna.getString("valor")));

            TipoFonte tipoFonte = new TipoFonteBO().obterPorPk(Integer.valueOf(dyna.getString("tipoFonteId")));    
            fonte.setTipo(tipoFonte);
            fonte.setOrigem(Origem.valueOf(dyna.getString("origemId")));
            new FonteBO().alterar(fonte);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Receita alterada com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar alterar a Receita.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }        
        //request.setAttribute("acao", "Fonte.do");
        return mapping.findForward("redirect");
    }
    
    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        String id = request.getParameter("id");
        request.setAttribute("grupoTipoFontes", new ArrayList());
        request.setAttribute("tipoFontes", new ArrayList());
        try {
            if (GenericValidator.isInt(id)) {

                Fonte fonte = new FonteBO().obterPorPk(Integer.valueOf(id));
                BeanUtils.copyProperties(dyna, fonte);
                
                dyna.set("planejamentoId", fonte.getPlanejamento().getId() + "" );
                dyna.set("grupoTipoFonteId",fonte.getTipo().getGrupoTipo().getId().toString());
                dyna.set("tipoFonteId",fonte.getTipo().getId().toString());
                dyna.set("origemId",fonte.getOrigem().name());

                TipoFonte tipoFiltro = new TipoFonte();
                tipoFiltro.setGrupoTipo(fonte.getTipo().getGrupoTipo());
                
                request.setAttribute("tipoFontes", new TipoFonteBO().obterPorFiltro(tipoFiltro));
                
                GrupoTipoFonte grupoFiltro = new GrupoTipoFonte();
                
                request.setAttribute("grupoTipoFontes", new GrupoTipoFonteBO().obterPorFiltro(grupoFiltro));
                request.setAttribute("planejamentos", new PlanejamentoBO().obterTodos());
            }
            return mapping.findForward("alterar");
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar selecionar a Receita.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return unspecified(mapping, form, request, response);
    }

    public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ArrayList erros = new ArrayList();
        try {
            DynaActionForm dyna = (DynaActionForm) form;
            String[] id = new String[0];
            if (request.getParameterValues("ids_exclusao") != null) {
                id = request.getParameterValues("ids_exclusao");
            }
            HibernateUtil.beginTransaction();
            for (int i = 0; i < id.length; i++) {
                FonteBO fonteBO = new FonteBO();
                Fonte fonte = fonteBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    fonteBO.excluir(fonte);
                } 
                catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    //ex.printStackTrace();
                    erros.add("Uma ou mais receitas estão associadas e não podem ser excluídas!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } 
        catch (AcessoDadosException ex) {
            Logger.getLogger(FonteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        return mapping.findForward("redirect");
    }
    
    public ActionForward listarArquivos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("fonte", new FonteBO().obterPorPk(Integer.parseInt(request.getParameter("fonte"))));
        }
        catch(AcessoDadosException e) {
            e.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("listaArquivos");
    }
       
    public ActionForward novoArquivo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            
            request.setAttribute("fonte", new FonteBO().obterPorPk(Integer.parseInt(request.getParameter("fonte"))));
            
            return mapping.findForward("novoArquivo");
        } 
        
        catch (AcessoDadosException ex) {
            ex.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        
        return mapping.findForward("redirect");
    }
    
    public ActionForward excluirArquivo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ArrayList erros = new ArrayList();
        try {
            DynaActionForm dyna = (DynaActionForm) form;
            String[] id = new String[0];
            if (request.getParameterValues("ids_exclusao") != null) {
                id = request.getParameterValues("ids_exclusao");
            }
            HibernateUtil.beginTransaction();
            for (int i = 0; i < id.length; i++) {
                FonteBO fonteBO = new FonteBO();
                Fonte fonte = fonteBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    fonteBO.excluir(fonte);
                } 
                catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    //ex.printStackTrace();
                    erros.add("Uma ou mais contrapartidas estão associadas e não podem ser excluídas!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } 
        catch (AcessoDadosException ex) {
            Logger.getLogger(FonteAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        return mapping.findForward("redirect");
    }
    
    public ActionForward adicionarArquivo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {        
            
            Fonte fonte = new FonteBO().obterPorPk(Integer.parseInt(request.getParameter("fonte")));
            
            FormFile arquivoContrato = (FormFile) dyna.get("arquivo");
            Documento arquivo = new Documento();
            arquivo.setArquivo(arquivoContrato.getFileData());
            arquivo.setDescricao(dyna.getString("descricao"));
            IUsuario usuario = (IUsuario) request.getSession().getAttribute(Constante.USUARIO_LOGADO);
            arquivo.setCriador(usuario.getMatriculaHR());
            String nomearquivo = arquivoContrato.getFileName().trim();
            int index = nomearquivo.lastIndexOf(".");
            arquivo.setExtensaoArquivo(nomearquivo.substring(index + 1));
            arquivo.setTitulo(nomearquivo.substring(1, index - 1));
            
            FonteArquivo fonteArquivo = new FonteArquivo();     
            fonteArquivo.setDescricao(dyna.getString("descricao"));
            fonteArquivo.setArquivo(new GEDImpl().inserirDocumento(arquivo));
            fonteArquivo.setFonte(fonte);
            
            fonte.getFonteArquivos().add(fonteArquivo);
            
            new FonteBO().alterar(fonte);
            
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar inserir a Contrapartida.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }        
        //request.setAttribute("acao", "Fonte.do?metodo=listarArquivos&fonte="+request.getParameter("fonte"));
        return mapping.findForward("redirect");
    }       
    

}