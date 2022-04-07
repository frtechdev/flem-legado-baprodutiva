package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.baprodutiva.bo.InternalizacaoDevolucaoBO;
import br.org.flem.baprodutiva.negocio.InternalizacaoDevolucao;
import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fw.persistencia.dto.Compromisso;
import br.org.flem.fw.service.GEM;
import br.org.flem.fw.service.impl.GEMImpl;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fwe.util.Data;
import br.org.flem.fwe.web.tag.MensagemTag;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

/**
 * @author dbbarreto
 */
public class InternalizacaoDevolucaoAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            Collection internalizacoes = new InternalizacaoDevolucaoBO().obterTodos();
            
            if (internalizacoes != null && !internalizacoes.isEmpty()) {
            
                request.setAttribute("lista", internalizacoes);
            }
            else {
                request.setAttribute("lista", new ArrayList());
            }
            
        }
        catch(AcessoDadosException e) {
            e.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("lista");
    }
    
    public ActionForward listarDevolucoesGEM(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            GEM gem = new GEMImpl();
            Collection<Compromisso> compromissos = new ArrayList<Compromisso>();
            String banco1,agencia1,conta1,banco2,agencia2,conta2,dataInicio;
            try {
                banco1 = PropertiesUtil.getProperties().getProperty("banco1");
                agencia1 = PropertiesUtil.getProperties().getProperty("agencia1");
                conta1 = PropertiesUtil.getProperties().getProperty("conta1");
                
                banco2 = PropertiesUtil.getProperties().getProperty("banco2");
                agencia2 = PropertiesUtil.getProperties().getProperty("agencia2");
                conta2 = PropertiesUtil.getProperties().getProperty("conta2");
                dataInicio = PropertiesUtil.getProperties().getProperty("projeto.dataInicio");
            } catch (IOException ex) {
                throw new AcessoDadosException(ex);
            }
            compromissos = gem.obterDevolucoesViagemPorContaDataInicio(Integer.valueOf(Data.retornaDataInversa(dataInicio)), banco1, agencia1, conta1);
            compromissos.addAll(gem.obterDevolucoesViagemPorContaDataInicio(Integer.valueOf(Data.retornaDataInversa(dataInicio)), banco2, agencia2, conta2));
            
            
            Collection<Compromisso> internalizacoesJaReconhecidas = new InternalizacaoDevolucaoBO().obterCompromissos();

            Collection compromissosValidos = new ArrayList();
            
            for (Compromisso compromisso : compromissos) {
                boolean insere = true;
                for (Compromisso internalizacao : internalizacoesJaReconhecidas) {
                    if (compromisso.getId().contentEquals(internalizacao.getId()) && compromisso.getTipo().equals(internalizacao.getTipo()) && compromisso.getSeqLinha().equals(internalizacao.getSeqLinha())) {
                        insere = false;
                    }
                }
                if (insere) {
                    compromissosValidos.add(compromisso);
                }
            }

            request.setAttribute("lista", compromissosValidos);
            
        }
        catch(Exception e) {
            e.printStackTrace();
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar acessar o banco de dados.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("listaDevolucoesGEM");
    }

    private Compromisso obterDevolucoesViagemPorIdTipoSeqLinha(GEM gem,String id, String tipo, String seqLinha){
        Compromisso devolucao = null;
        try {
            String banco1   = PropertiesUtil.getProperties().getProperty("banco1");
            String banco2   = PropertiesUtil.getProperties().getProperty("banco2");
            String agencia1 = PropertiesUtil.getProperties().getProperty("agencia1");
            String agencia2 = PropertiesUtil.getProperties().getProperty("agencia2");
            String conta1 = PropertiesUtil.getProperties().getProperty("conta1");
            String conta2 = PropertiesUtil.getProperties().getProperty("conta2");
            devolucao = gem.obterDevolucoesViagemPorIdTipoSeqLinha(id, tipo, seqLinha, banco1, agencia1, conta1);
            if(devolucao == null){
                devolucao = gem.obterDevolucoesViagemPorIdTipoSeqLinha(id, tipo, seqLinha, banco2, agencia2, conta2);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return devolucao;
    }

    public ActionForward associarCompromisso(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {

            DynaActionForm dyna = (DynaActionForm) form;
            GEM gem = new GEMImpl();

            String id = request.getParameter("id");
            String tipo = request.getParameter("tipo");
            String seqLinha = request.getParameter("seqLinha");
            

            Compromisso devolucao = obterDevolucoesViagemPorIdTipoSeqLinha(gem,id, tipo, seqLinha);
            List<Compromisso> compromissos = gem.obterCompromissosPorFiltroGrupoCC(PropertiesUtil.getProperties().getProperty("projeto") + "%");


            /*dyna.set("id", id);
              dyna.set("tipo", tipo);
              dyna.set("seqLinha", seqLinha);*/


            request.getSession().setAttribute("idDev", id);
            request.getSession().setAttribute("tipoDev",tipo);
            request.getSession().setAttribute("seqLinhaDev", seqLinha);

            request.setAttribute("dataDev", new SimpleDateFormat("dd/MM/yyyy").format(devolucao.getData()));
            request.setAttribute("valorDev", devolucao.getValor().doubleValue());
            request.setAttribute("devolucao",devolucao);
            request.setAttribute("lista", compromissos);

        } catch (IOException ex) {
            Logger.getLogger(InternalizacaoDevolucaoAction.class.getName()).log(Level.SEVERE, null, ex);
        }

         return mapping.findForward("associarCompromisso");
    }

    public ActionForward reconhecer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;

        String idComp = (String) request.getParameter("idComp");
        String tipoComp = (String) request.getParameter("tipoComp");
        String seqLinhaComp = (String) request.getParameter("seqLinhaComp");
        /*String nomeFornecedorComp = (String) request.getParameter("nomeFornecedorComp");*/

        request.setAttribute("compromisso", log);

        dyna.set("id", idComp);
        dyna.set("tipo", tipoComp);
        dyna.set("seqLinha", seqLinhaComp);
       /*dyna.set("nomeFornecedor", URLDecoder.decode(request.getParameter("nomeFornecedorComp"), "UTF-8"));*/

        return mapping.findForward("reconhecimento");
    }

    public ActionForward salvarReconhecimento(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {        
            //String id = request.getParameter("id");
            //String parcela = request.getParameter("parcela");
            //System.out.println(dyna.getString("id")+dyna.getString("tipo"));
            GEM gem = new GEMImpl();

            /*Obtem da sessão atributos da devolução */
            String idDev = (String)request.getSession().getAttribute("idDev");
            String tipoDev = (String)request.getSession().getAttribute("tipoDev");
            String seqLinhaDev = (String)request.getSession().getAttribute("seqLinhaDev");

            Compromisso devolucao = obterDevolucoesViagemPorIdTipoSeqLinha(gem, idDev, tipoDev, seqLinhaDev);
            
            InternalizacaoDevolucao internalizacao = new InternalizacaoDevolucao();
            
            internalizacao.setIdCompromisso(devolucao.getId());
            internalizacao.setTipo(devolucao.getTipo());
            internalizacao.setSeqLinha(devolucao.getSeqLinha());
            internalizacao.setDescricao(devolucao.getDescricao());
            internalizacao.setEntrada(devolucao.getData());
            internalizacao.setValor(devolucao.getValor().doubleValue());
            internalizacao.setClassificacao(dyna.getString("classificacao"));
            internalizacao.setCentroCusto(devolucao.getCentroCusto());

            /* Dados do compromisso associado a devolução */
            Compromisso compromisso = gem.obterCompromissosPorTipoId(dyna.getString("tipo"), dyna.getString("id"));
            internalizacao.setIdComp(dyna.getString("id"));
            internalizacao.setTipoComp(dyna.getString("tipo"));
            internalizacao.setSeqLinhaComp(dyna.getString("seqLinha"));
            internalizacao.setNomeFornecedorComp(compromisso.getNomeFornecedor());
            new InternalizacaoDevolucaoBO().inserir(internalizacao);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Internalização reconhecida com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar reconhecer a Internalização.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }        
        request.setAttribute("acao", "InternalizacaoDevolucao.do");
        return mapping.findForward("redirect");
    }

    public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ArrayList erros = new ArrayList();
        try {
            String[] id = new String[0];
            if (request.getParameterValues("ids_exclusao") != null) {
                id = request.getParameterValues("ids_exclusao");
            }
            HibernateUtil.beginTransaction();
            for (int i = 0; i < id.length; i++) {
                InternalizacaoDevolucaoBO internalizacaoBO = new InternalizacaoDevolucaoBO();
                InternalizacaoDevolucao internalizacao = internalizacaoBO.obterPorPk(Integer.valueOf(id[i]));
                try {
                    internalizacaoBO.excluir(internalizacao);
                } 
                catch (Exception ex) {
                    HibernateUtil.rollbackTransaction();
                    erros.add("A internalização \"" + internalizacao.getDescricao() + "\" está associada. Não pode ser excluído!");
                    request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
                    break;
                }
            }
            HibernateUtil.commitTransaction();

        } 
        catch (AcessoDadosException ex) {
            Logger.getLogger(InternalizacaoDevolucaoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (erros.size() <= 0) {
            erros.add("Exclusão realizada com sucesso!");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        }

        request.setAttribute("acao", "InternalizacaoDevolucao.do");
        return mapping.findForward("redirect");
    }


    public ActionForward filtrarCompromisso(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            DynaActionForm dyna = (DynaActionForm) form;
            GEM gem = new GEMImpl();
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

            String descricao = dyna.getString("descricao");
            String centroCusto = dyna.getString("centroCusto");
            Date dataInicial = !dyna.getString("dataInicial").trim().isEmpty() ? fmt.parse(dyna.getString("dataInicial")) : null;
            Date dataFinal = !dyna.getString("dataFinal").trim().isEmpty() ? fmt.parse(dyna.getString("dataFinal")) : null;
            //String valor = dyna.getString("valor");

            /*Obtem da sessão atributos da devolução */
            String idDev = (String)request.getSession().getAttribute("idDev");
            String tipoDev = (String)request.getSession().getAttribute("tipoDev");
            String seqLinhaDev = (String)request.getSession().getAttribute("seqLinhaDev");

            Compromisso devolucao = obterDevolucoesViagemPorIdTipoSeqLinha(gem, idDev, tipoDev, seqLinhaDev);

            request.setAttribute("dataDev", new SimpleDateFormat("dd/MM/yyyy").format(devolucao.getData()));
            request.setAttribute("valorDev", devolucao.getValor().doubleValue());
            request.setAttribute("devolucao",devolucao);
           /*dyna.set("id", devolucao.getApdId());
             dyna.set("tipo", devolucao.getApdTp());
             dyna.set("seqLinha", devolucao.getSeqLinha());*/
            request.setAttribute("lista", gem.obterCompromissosPorFiltros(descricao, centroCusto, dataInicial, dataFinal));

        } catch (Exception ex) {
            Logger.getLogger(InternalizacaoDevolucaoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapping.findForward("associarCompromisso");
    }
    
    public ActionForward associarTodas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
    
      
         try {
                    GEM gem = new GEMImpl();
                    Collection<Compromisso> compromissos = new ArrayList<Compromisso>();
                    String banco1,agencia1,conta1,banco2,agencia2,conta2,dataInicio,cc;
                    try {
                            banco1 = PropertiesUtil.getProperties().getProperty("banco1");
                            agencia1 = PropertiesUtil.getProperties().getProperty("agencia1");
                            conta1 = PropertiesUtil.getProperties().getProperty("conta1");
                            dataInicio = PropertiesUtil.getProperties().getProperty("projeto.dataInicio");
                            cc=PropertiesUtil.getProperties().getProperty("projeto") + "%";
                        } catch (IOException ex) {
                            throw new AcessoDadosException(ex);
                        }
                    Collection <Compromisso> todos = gem.obterCompromissosDeViagensPorCC(cc);
            try {
                //Todas as devoluções de viagens...
                compromissos = gem.obterDevolucoesViagemPorContaDataInicio(Integer.valueOf(Data.retornaDataInversa(dataInicio)), banco1, agencia1, conta1);
            } catch (Exception ex) {
                throw new AcessoDadosException(ex);
            }

                    //Internalizações já reconhecidas...
                    Collection<Compromisso> internalizacoesJaReconhecidas = new InternalizacaoDevolucaoBO().obterCompromissos();
                    Collection<Compromisso> compromissosValidos = new ArrayList();

                    //pega somente as devoluções que não estao na tabela do baprodutiva
                    for (Compromisso compromisso : compromissos) {
                            boolean insere = true;
                            for (Compromisso internalizacao : internalizacoesJaReconhecidas) {
                                if (compromisso.getId().contentEquals(internalizacao.getId()) && compromisso.getTipo().equals(internalizacao.getTipo()) && compromisso.getSeqLinha().equals(internalizacao.getSeqLinha())) {
                                    insere = false;
                                }
                            }
                            if (insere) {
                                 compromissosValidos.add(compromisso);
                            }
                    }
                    List<InternalizacaoDevolucao> lista = new  ArrayList<InternalizacaoDevolucao>();
            
                    for (Compromisso comp:compromissosValidos){
                        for(Compromisso c : todos){
                            if(comp.getRecibo().equals(c.getRecibo())){
                                    InternalizacaoDevolucao internalizacao = new InternalizacaoDevolucao();
                                    
                                    internalizacao.setIdCompromisso(comp.getId());
                                    internalizacao.setTipo(comp.getTipo());
                                    internalizacao.setSeqLinha(comp.getSeqLinha());
                                    internalizacao.setDescricao(comp.getDescricao());
                                    internalizacao.setEntrada(comp.getData());
                                    internalizacao.setValor(comp.getValor().doubleValue());
                                    internalizacao.setClassificacao("CO");
                                    internalizacao.setCentroCusto(comp.getCentroCusto());
                                    //* Dados do compromisso associado a devolução      
                                    internalizacao.setIdComp(c.getApdId());
                                    internalizacao.setTipoComp(c.getApdTp());
                                    internalizacao.setSeqLinhaComp(c.getSeqLinha());
                                    internalizacao.setNomeFornecedorComp(c.getNomeFornecedor());
                                    new InternalizacaoDevolucaoBO().inserir(internalizacao);
                            }      
                        }
                  }

        } catch (AcessoDadosException ex) {
            
        }
    
  
        return mapping.findForward("lista");
    }
  
}