package br.org.flem.baprodutiva.web.struts.action;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fw.persistencia.dto.Compromisso;
import br.org.flem.fw.service.CentroResponsabilidade;
import br.org.flem.fw.service.GEM;
import br.org.flem.fw.service.impl.GEMImpl;
import br.org.flem.baprodutiva.bo.CentroCustoBO;
import br.org.flem.baprodutiva.bo.ContratoBO;
import br.org.flem.baprodutiva.bo.InternalizacaoDevolucaoBO;
import br.org.flem.baprodutiva.negocio.CodigoItem;
import br.org.flem.baprodutiva.negocio.Contrato;
import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fwe.util.Data;
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
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author mgsilva
 */
public class ValidacoesFinanceiraAction extends DispatchAction {

    @Override
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        Collection<Compromisso> compromissoIrregular = null;
        Collection<Compromisso> tipoCompromissoIrregular = null;
        Collection<Compromisso> internalizacaoIrregular = null;
        GEM gem = new GEMImpl();
        try {
            Collection<Compromisso> lista = gem.obterCompromissosPorFiltroGrupoCC(PropertiesUtil.getProperties().getProperty("projeto") + "%");

            compromissoIrregular = validarCompromissosIrregular(lista);//lista;
            tipoCompromissoIrregular = validarTipoCompromissos(lista);

            internalizacaoIrregular = validarDevolucoes();
        } catch (Exception ex) {
            Logger.getLogger(ValidacoesFinanceiraAction.class.getName()).log(Level.SEVERE, "Ocorreu um erro: " + ex.getMessage(), ex);
        }

        try {
            Collection<CentroResponsabilidade> listaCC = new CentroCustoBO().obterFilhosAnaliticos(PropertiesUtil.getProperties().getProperty("projeto"));
            CentroResponsabilidade cr = new CentroResponsabilidade();
            CentroResponsabilidade cr2 = new CentroResponsabilidade();
            String ccProjeto = PropertiesUtil.getProperties().getProperty("projeto");
            
            for (CentroResponsabilidade centroResponsabilidade : listaCC) {
                if (centroResponsabilidade.getId().equals(ccProjeto+"0000")) {
                    cr = centroResponsabilidade;
                }
                if (centroResponsabilidade.getId().equals(ccProjeto+"133001")) {
                    cr2 = centroResponsabilidade;
                }
            }

            listaCC.remove(cr);
            listaCC.remove(cr2);
            request.setAttribute("listaCC", listaCC);

        } catch (Exception e) {
            Logger.getLogger(ValidacoesFinanceiraAction.class.getName()).log(Level.SEVERE, "Ocorreu um erro: " + e.getMessage(), e);
        }
        request.setAttribute("listaContratoIrregular", compromissoIrregular);
        request.setAttribute("listaTipoIrregular", tipoCompromissoIrregular);
        request.setAttribute("listaInternalizacaoIrregular", internalizacaoIrregular);

        return mapping.findForward("lista");
    }

    public Collection<Compromisso> validarCompromissosIrregular(Collection<Compromisso> lista) throws AcessoDadosException, IOException {

        Collection<Compromisso> compromissoIrregular = new ArrayList<Compromisso>();

        List<String> numContratos = new ContratoBO().obterNumerosContratos();

        for (Compromisso compromisso : lista) {
            if ((compromisso.getNumeroContrato() != null) && (!compromisso.getNumeroContrato().trim().isEmpty())) {
                // Ignora os compromissos dos fornecedores de código (G000001, G000002 e G000003) - Governo
                if (compromisso.getCodigoFornecedor().trim().matches("G00000[1-3]")
                        || (compromisso.getCodigoFornecedor().trim().substring(0, 5).matches("0000[1-3]")
                        && compromisso.getCodigoFornecedor().trim().length() == 9)
                        || compromisso.getCodigoFornecedor().equals("13927801000149")) {
                    continue;
                }
                // Pega os compromissos com contrato que ainda não está cadastrado no MTB
                if (!numContratos.contains(compromisso.getNumeroContrato())) {
                    compromissoIrregular.add(compromisso);
                } else { //checa se o compromisso contém um contrato com valores divergente de fornecedor
                    for (Contrato contrato : new ContratoBO().obterTodos()) {
                        if (contrato.getNumero().contentEquals(compromisso.getNumeroContrato())
                                && !(contrato.getIdFornecedor().trim().equals(compromisso.getCodigoFornecedor().trim())
                                || (contrato.getCodigoAntigo() != null && contrato.getCodigoAntigo().trim().equals(compromisso.getCodigoFornecedor().trim())))) {
                            compromissoIrregular.add(compromisso);
                        }
                    }
                }
            }
        }
        return compromissoIrregular;
    }

    public Collection<Compromisso> validarTipoCompromissos(Collection<Compromisso> lista) throws AcessoDadosException, IOException {
        Collection<Compromisso> tipoCompromissoIrregular = new ArrayList<Compromisso>();

        for (Compromisso compromisso : lista) {
            if (!CodigoItem.getListaNomes().contains(compromisso.getId())) {
                tipoCompromissoIrregular.add(compromisso);
            }
        }
        return tipoCompromissoIrregular;
    }

    public ActionForward selecionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;

        String id = request.getParameter("id").trim();
        String tipo = request.getParameter("tipo").trim();
        GEM gem = new GEMImpl();
        try {
            Compromisso compromisso = gem.obterCompromissosPorTipoId(tipo, id);

            BeanUtils.copyProperties(dyna, compromisso);

            request.setAttribute("numeroCont", new ContratoBO().obterNumerosContratos());
        } catch (Exception ex) {
            Logger.getLogger(ValidacoesFinanceiraAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mapping.findForward("alterar");
    }

    public ActionForward alterar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        GEM gem = new GEMImpl();
        Compromisso compromisso = gem.obterCompromissosPorTipoId(dyna.getString("apdTp").trim(), dyna.getString("apdId").trim());

        compromisso.setId(dyna.getString("id"));
        compromisso.setNumeroContrato(dyna.getString("numeroContrato"));
        compromisso.setNumeroClienteConnect(dyna.getString("numeroClienteConnect"));

        gem.atualizaDadosUsuario(compromisso);

        return unspecified(mapping, form, request, response);
    }

    public Collection<Compromisso> validarDevolucoes() throws AcessoDadosException {
        GEM gem = new GEMImpl();
        Collection<Compromisso> internalizacaoIrregular = new ArrayList<Compromisso>();
        Collection<Compromisso> compromissos = new ArrayList<Compromisso>();
        String dataInicio;
        try {
            String banco = PropertiesUtil.getProperties().getProperty("banco1");
            String agencia = PropertiesUtil.getProperties().getProperty("agencia1");
            String conta = PropertiesUtil.getProperties().getProperty("conta1");
            dataInicio = PropertiesUtil.getProperties().getProperty("projeto.dataInicio");
            
            compromissos = gem.obterDevolucoesViagemPorContaDataInicio(Integer.valueOf(Data.retornaDataInversa(dataInicio)), banco, agencia, conta);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AcessoDadosException(ex);
        }

        Collection<Compromisso> internalizacoesJaReconhecidas = new InternalizacaoDevolucaoBO().obterCompromissos();

        for (Compromisso compromisso : compromissos) {
            boolean insere = true;
            for (Compromisso internalizacao : internalizacoesJaReconhecidas) {
                if (compromisso.getId().equals(internalizacao.getId()) && compromisso.getTipo().equals(internalizacao.getTipo()) && compromisso.getSeqLinha().equals(internalizacao.getSeqLinha())) {
                    insere = false;
                }
            }
            if (insere) {
                internalizacaoIrregular.add(compromisso);
            }
        }
        return internalizacaoIrregular;
    }
}
