package br.org.flem.baprodutiva.bo;

import br.org.flem.fw.persistencia.dto.Compromisso;
import br.org.flem.fw.persistencia.dto.LancamentoInterface;
import br.org.flem.fwe.exception.AplicacaoException;
import br.org.flem.fwe.util.Data;
import br.org.flem.fwe.util.Valores;
import br.org.flem.baprodutiva.bo.util.OrganizadorLancamentosBO;
import br.org.flem.baprodutiva.negocio.Pedido;
import br.org.flem.baprodutiva.relatorio.SoeDTO;
import br.org.flem.baprodutiva.web.struts.action.InternalizacaoSaldoAction;
import br.org.flem.commons.util.PropertiesUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mgsilva
 */
public class InternalizacaoSaldoBO {

    public Collection<SoeDTO> geraDados(Pedido pedido) {
        BuilderSOE builder = new InternalizacaoSaldo();
        //BuilderSOE builder = new SOEListao();
        try {

            builder.setPedido(pedido);

            //inclui Despesas Problematicas cadastradas no pedido referente
            Collection<LancamentoInterface> compromissos = OrganizadorLancamentosBO.getInstancia().obterCompromissos();

        //    builder.adicionaListaCentrosCusto(new CompositeFolhaBO().obterTodosCentrosCusto());
            builder.adicionaListaCompromissos(compromissos);
            builder.adicionaListaAvulsos(OrganizadorLancamentosBO.getInstancia().obterAvulsos());
            builder.adicionaListaInternalizacoes(new InternalizacoesBO().obterTodosTiposInternalizacaoSemAgrupar());
            builder.adicionaListaDevolucoes(OrganizadorLancamentosBO.getInstancia().obterDevolucoes());

        } catch (Exception ex) {
            Logger.getLogger(RelatorioSOEBO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return builder.resultado();
    }
    
    public static void main(String[] args) throws IOException, ParseException, AplicacaoException {
         Collection<SoeDTO> soes = new ArrayList<SoeDTO>();
    
         Pedido pedido = new Pedido();
            Date dataInicial = new SimpleDateFormat("dd/MM/yyyy").parse(PropertiesUtil.getProperties().getProperty("projeto.dataInicio"));
            pedido.setInicio(dataInicial);
            pedido.setFim(Calendar.getInstance().getTime());

            soes.addAll(new InternalizacaoSaldoBO().geraDados(pedido));
            
            Double valor = 0d;
            for (SoeDTO soe: soes) {
                valor += soe.getParcela();
            }
            //System.out.println(Valores.formataMoeda(valor));
            
            valor = 0d;
            
            for (LancamentoInterface la : OrganizadorLancamentosBO.getInstancia().obterDevolucoes()) {
                valor += la.getValor().doubleValue();
            }
            //System.out.println("Devolucoes: "+Valores.formataMoeda(valor));
    }
}
