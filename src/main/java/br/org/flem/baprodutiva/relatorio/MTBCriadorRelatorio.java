/*
 * CriadorRelatorio.java
 *
 * Created on 13 de Mar�o de 2007, 08:32
 *
 * To change this template, choose Tools | Template Manager

 */

package br.org.flem.baprodutiva.relatorio;

import br.org.flem.fwe.exception.RelatorioSemDadosException;
import br.org.flem.fwe.web.relatorio.CriadorRelatorio;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Classe respons�vel por montar e exportar os relat�rios.
 * @author dbbarreto
 */
public class MTBCriadorRelatorio extends CriadorRelatorio{
    
   
    /**
     * M�todo que monta o relat�rio com os par�metros e a cole��o de objetos. Este m�todo � privado para que o usu�rio da classe escolha um dos m�todos para a exporta��o desse relat�rio.
     * @param request O request utilizado no momento, pela action.
     * @param localArquivo Local do arquivo, relativo ao contexto da aplica��o.
     * @param parametros Lista de par�metros definida para o relat�rio.
     * @param resultado Cole��o de objetos que ser� utilizada como DataSource do relat�rio.
     * @throws net.sf.jasperreports.engine.JRException Exce��o lan�ada, caso o relat�rio apresente algum problema.
     * @return Relat�rio pronto, que ser� utilizado por algum m�todo de exporta��o.
     */
    protected void montaRelatorio(HttpServletRequest request, Map parametros) {
        //parametros.put("logo", request.getSession().getServletContext().getRealPath("/img/logohd.gif"));
    }
    
    public void exportaRelatorioXLS(HttpServletRequest request, HttpServletResponse response, String localArquivo, Map parametros, Collection resultado) throws JRException, RelatorioSemDadosException, IOException {
       
        //if (resultado == null || resultado.size() <= 0) {
        //    throw new RelatorioSemDadosException("O relat�rio selecionado n�o possui dados a serem exibidos.");
        //}
        ServletOutputStream servletOutputStream = null;
        try {
            
            response.setContentType("application/vnd.ms-excel");

            response.setHeader("Content-disposition", "attachment; filename=" + "FlemWeb_Relatorio_CER.xls" );

            servletOutputStream = response.getOutputStream();

            //InputStream input = request.getSession().getServletContext().getResourceAsStream(localArquivo);

            JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource( resultado );

            this.montaRelatorio(request, parametros);
            
            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(request.getSession().getServletContext().getRealPath(localArquivo));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, beanDataSource);
            //JExcelApiExporterParameter
            JExcelApiExporter exporter = new JExcelApiExporter();

            exporter.setParameter(JExcelApiExporterParameter.SHEET_NAMES, new String[]{"Relatorio"});
            exporter.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, servletOutputStream);
            exporter.setParameter(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporter.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, true);
            exporter.setParameter(JExcelApiExporterParameter.CREATE_CUSTOM_PALETTE,true);
            exporter.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,true);
            exporter.exportReport();
        }
        
        catch(Exception e) {
            e.printStackTrace();
            throw new JRException(e);
        }
        
        finally {
            if (servletOutputStream != null) {
                servletOutputStream.flush();
                servletOutputStream.close();
            }
        }
        
    }
}
