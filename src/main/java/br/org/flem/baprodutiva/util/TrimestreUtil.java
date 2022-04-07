/*


 */

package br.org.flem.baprodutiva.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author dbbarreto
 */
public class TrimestreUtil {
    
    public static Date obterDataInicial(String trimestre, String ano) {
        try {
            String diaMes = "";
            switch (Integer.valueOf(trimestre)) {
                case 1:
                    diaMes = "01/01";
                    break;
                case 2:
                    diaMes = "01/04";
                    break;
                case 3:
                    diaMes = "01/07";
                    break;
                case 4:
                    diaMes = "01/10";
                    break;
            }
            return new SimpleDateFormat("dd/MM/yyyy").parse(diaMes + "/" + ano);
        } catch (ParseException ex) {
            return null;
        }
    }
    
    public static Date obterDataFinal(String trimestre, String ano) {
        try {
            String diaMes = "";
            switch (Integer.valueOf(trimestre)) {
                case 1:
                    diaMes = "31/03";
                    break;
                case 2:
                    diaMes = "30/04";
                    break;
                case 3:
                    diaMes = "30/09";
                    break;
                case 4:
                    diaMes = "31/12";
                    break;
            }
            return new SimpleDateFormat("dd/MM/yyyy").parse(diaMes + "/" + ano);
        } catch (ParseException ex) {
            return null;
        }
    }
    
}
