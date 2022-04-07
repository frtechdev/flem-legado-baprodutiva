/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.baprodutiva.bo.CentroCustoBO;
import br.org.flem.baprodutiva.bo.TransferenciaBancariaBO;
import br.org.flem.baprodutiva.negocio.TransferenciaBancaria;
import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fw.persistencia.dto.Compromisso;
import br.org.flem.fw.service.GEM;
import br.org.flem.fw.service.impl.GEMImpl;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.fwe.web.util.MensagemTagUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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

/**
 *
 * @author AJLima
 */
public class TransferenciaAction extends DispatchAction {
      
    @Override
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
        try {
            GEMImpl gem = new GEMImpl();           
            String banco =PropertiesUtil.getProperties().getProperty("banco1");
            String agencia =PropertiesUtil.getProperties().getProperty("agencia1");
            String conta =PropertiesUtil.getProperties().getProperty("conta1");
           
            //Collection<Compromisso> compromissos = new ArrayList<Compromisso>();
            Collection<Compromisso> compromissos = gem.obterTransferenciasPorBanco(banco, agencia, conta);
            Collection<TransferenciaBancaria> transferenciasReconhecidas =  new TransferenciaBancariaBO().obterTodosOrdemData();
            
            Collection transferenciasValidas = new ArrayList();
            for(Compromisso compromisso :compromissos){
                
                boolean insere = true;
                for(TransferenciaBancaria t : transferenciasReconhecidas ){
                    if(compromisso.getApdId().equals(t.getApdId()) && compromisso.getApdTp().equals(t.getApdTp())){    
                        insere = false;
                    }
                }
                //Situação 1 significa que o compromisso não está estornado
                if (insere && compromisso.getSituacao().equalsIgnoreCase("1")) {
                    transferenciasValidas.add(compromisso);
                }
            }
            request.setAttribute("irregulares", transferenciasValidas);
            request.setAttribute("regulares", transferenciasReconhecidas);
        } catch (Exception ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), ex.getMessage(), "erro", this.getClass().getName(), ex);
            Logger.getLogger(TransferenciaBancaria.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return mapping.findForward("lista");
    }
    
     public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        
        dyna.set("nomeFornecedor", "");
        dyna.set("codigoFornecedor", "");
        dyna.set("centroCusto", "");
        dyna.set("descricao", "");
        
        try {
            request.setAttribute("centros", new CentroCustoBO().obterFilhosAnaliticos(PropertiesUtil.getProperties().getProperty("projeto")));
        } catch (Exception ex) {
            Logger.getLogger(TransferenciaBancaria.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        
        return mapping.findForward("reconhecer");
    }
    
    public ActionForward selecionarEdicao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        
        String id = request.getParameter("id");
        
        try {
            if (GenericValidator.isInt(id)) {
            TransferenciaBancaria transferencia = new TransferenciaBancariaBO().obterPorPk(Integer.valueOf(id));
            BeanUtils.copyProperties(dyna, transferencia);
        }
        } catch (Exception e) {
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Erro ao Selecionar uma Transferência.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
            
        }
        return mapping.findForward("alterar");
    }
    
    public ActionForward associar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
        DynaActionForm dyna = (DynaActionForm) form;
        String msg = "";
        try {
            GEM gem = new GEMImpl();
            String banco =PropertiesUtil.getProperties().getProperty("banco1");
            String agencia =PropertiesUtil.getProperties().getProperty("agencia1");
            String conta =PropertiesUtil.getProperties().getProperty("conta1");

            /*Obtem da sessão atributos da devolução */
            
            String apdId = dyna.getString("apdId");
            String apdTp = dyna.getString("apdTp");
            String cc = dyna.getString("centroCusto");
            String desc = dyna.getString("descricao");
            String seqLinha = dyna.getString("seqLinha");
            String nomeFornecedor = dyna.getString("nomeFornecedor");
            String codigoFornecedor = dyna.getString("codigoFornecedor");
            
            Collection<Compromisso> transferencias =  gem.obterTransferenciasPorBanco(banco, agencia, conta);

            for(Compromisso c : transferencias){

                if(c.getApdId().equals(apdId)  &&  c.getApdTp().equals(apdTp) ){

                    TransferenciaBancaria t = new TransferenciaBancariaBO().obterPorApdIdEApdTp(apdId, apdTp);
                    if( t == null ){
                         t = new TransferenciaBancaria();
                         t.setApdId(apdId);
                         t.setApdTp(apdTp);
                         t.setCentroCusto(cc);
                         t.setCodigoFornecedor(codigoFornecedor);
                         t.setNomeFornecedor(nomeFornecedor);
                         t.setDescricao(desc);
                         t.setSeqLinha(seqLinha);
                         t.setValor(c.getValor().doubleValue());

                         t.setDataPagamento(c.getData());
                         new TransferenciaBancariaBO().inserir(t);
                         msg = "Transferência reconhecida com sucesso.";
                         break;
                    }else{
                        msg = "Transferência já foi reconhecida.";
                    }
                }
            }
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add(msg);
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } catch (Exception e) {
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Erro ao reconhecer uma Transferência.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("redirect");
    }
    
    public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            String id =  request.getParameter("id");

            TransferenciaBancaria t =  new TransferenciaBancariaBO().obterPorPk(Integer.valueOf(id));  
            new TransferenciaBancariaBO().excluir(t);
            MensagemTagUtil.adicionarMensagem(request.getSession(), "Excluído com Sucesso.");
        } catch (Exception ex) {
            MensagemTagUtil.adicionarMensagem(request.getSession(), ex.getMessage(), "erro", this.getClass().getName(), ex);
            Logger.getLogger(AditivoAction.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return mapping.findForward("redirect");
    }
    
    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
        DynaActionForm dyna = (DynaActionForm) form;
        try {        
            TransferenciaBancaria transferencia = new TransferenciaBancariaBO().obterPorPk(Integer.valueOf(dyna.getString("id")));
            transferencia.setCodigoFornecedor(dyna.getString("codigoFornecedor"));
            transferencia.setNomeFornecedor(dyna.getString("nomeFornecedor"));
            transferencia.setCentroCusto(dyna.getString("centroCusto"));
            transferencia.setDescricao(dyna.getString("descricao"));
            new TransferenciaBancariaBO().alterar(transferencia);
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Transferencia alterada com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            
            List<String> mensagens = new ArrayList<String>();
            mensagens.add("Ocorreu um erro ao tentar alterar a Transferencia");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return mapping.findForward("redirect");
    }
    
}
