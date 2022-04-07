package br.org.flem.baprodutiva.quartz;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;
import org.quartz.impl.StdSchedulerFactory;
/**
 *
 * @author mgsilva
 */
public class AgendadorTarefasServlet extends GenericServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {

            Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
            sched.start();
            JobDetail jobDetail = newJob(VerificaCCNaoAssociadosJob.class).withIdentity("verificaCCNaoAssociadosJob", Scheduler.DEFAULT_GROUP).build();
            Trigger trigger = newTrigger().withIdentity("verificaCCNaoAssociados").withSchedule(cronSchedule("0 00 20 ? * MON-FRI")).build();


            Scheduler sched1 = StdSchedulerFactory.getDefaultScheduler();
            sched1.start();
            JobDetail jobDetail1 = newJob(ReconhecerDespesas.class).withIdentity("ReconhecerDespesas", Scheduler.DEFAULT_GROUP).build();
            Trigger trigger1 = newTrigger().withIdentity("Reconhece Despesas").withSchedule(cronSchedule("0 0/05 * * * ?")).build();
                
            sched.scheduleJob(jobDetail, trigger);
            sched1.scheduleJob(jobDetail1, trigger1);
            
        } catch (Exception ex) {
            Logger.getLogger(AgendadorTarefasServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                HibernateUtil.closeSession();
            } catch (AcessoDadosException ex) {
                Logger.getLogger(AgendadorTarefasServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
