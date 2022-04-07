package br.org.flem.baprodutiva.quartz;

import br.org.flem.fw.email.EmailBuilder;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fw.service.CentroResponsabilidade;
import br.org.flem.baprodutiva.bo.CentroCustoBO;
import br.org.flem.commons.util.PropertiesUtil;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author mgsilva
 */
public class VerificaCCNaoAssociadosJob implements Job {

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        try {
            this.enviarEmail(lerEmails());
        } catch (IOException ex) {
            Logger.getLogger(VerificaCCNaoAssociadosJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String[] lerEmails() throws IOException {
        return PropertiesUtil.getProperties().getProperty("email").trim().split(";");
    }

    public void enviarEmail(String[] destinatarios) {
        try {
            Collection<CentroResponsabilidade> lista = verificaCCNaoAssociados();
            StringBuilder texto = new StringBuilder();

            if (lista != null && lista.size() > 0) {
                HtmlEmail email = new EmailBuilder().getHelpDeskEmail();
                for (String destinatario : destinatarios) {
                    email.addTo(destinatario);
                }
                email.setSubject("Projeto Ba Produtiva - Centro de Custo(s) não associado(s)");
                texto.append(new SimpleDateFormat("HH:mm dd/MM/yyyy").format(new Date())).append("\n");
                texto.append("Os seguinte(s) centro de custo(s) encontram-se desassociados no sistema BA Produtiva :\n\n");
                for (CentroResponsabilidade cr : lista) {
                    texto.append(cr.getDescricaoCompleta()).append("\n");
                }
                email.setMsg(texto.toString());
                email.send();
            }
        } catch (EmailException ex) {
            Logger.getLogger(VerificaCCNaoAssociadosJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Collection<CentroResponsabilidade> verificaCCNaoAssociados() {
        Collection<CentroResponsabilidade> lista = null;
        try {
            String ccProjeto = PropertiesUtil.getProperties().getProperty("projeto");
            lista = new CentroCustoBO().obterFilhosAnaliticos(ccProjeto);
            Collection<CentroResponsabilidade> listaRemover = new ArrayList<CentroResponsabilidade>();
            for (CentroResponsabilidade centroResponsabilidade : lista) {
                if (centroResponsabilidade.getId().equals(ccProjeto + "0000")) {
                    listaRemover.add(centroResponsabilidade);
                } else if (centroResponsabilidade.getId().equals(PropertiesUtil.getProperties().getProperty("ccOperacional"))) {
                    listaRemover.add(centroResponsabilidade);
                } else if( centroResponsabilidade.getDescricaoCompleta().contains("203200100")){
                    //Funcionário(Mara) Garante que não existe esse centro de custo no GEM
                     listaRemover.add(centroResponsabilidade);
                }
            }
            for (CentroResponsabilidade cr : listaRemover) {
                lista.remove(cr);
            }
        } catch (IOException ex) {
            Logger.getLogger(VerificaCCNaoAssociadosJob.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AcessoDadosException ex) {
            Logger.getLogger(VerificaCCNaoAssociadosJob.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}