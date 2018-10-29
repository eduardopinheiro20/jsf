package br.com.eddydata.febraban;

import java.awt.Image;
import java.text.SimpleDateFormat;
import net.sf.jasperreports.view.JasperViewer;

public class Teste {

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Image img = null;//new ImageIcon("C:\\Users\\cassio\\Desktop\\papel_parede_eddydata.jpg").getImage();

            Febraban fb = new Febraban(img, "1", "Prefeitura de Franca", "4484", "010", FebrabanModelo.TRES_VIAS);
            fb.add("3872005", "IPTU", "755", "0101064032601", "0105444", 138.15, 138.15, true, "01/01", sdf.parse("2010/04/12"), "Banco Do Brasil", 2010, "Real", "TERRITORIAL        86,85\nPREDIAL        19,34\nEXPEDIENTE        18,60", true);
            fb.add("3872006", "IPTU", "755", "0101064032601", "0105444", 24.80, 24.80, false, "03/06", sdf.parse("2010/06/14"), "Banco Do Brasil", 2010, "Real", "TERRITORIAL        86,85\nPREDIAL        19,34\nEXPEDIENTE        18,60", true);

            fb.add("3872006", "IPTU", "755", "0101064032601", "0105444", 24.80, 24.80, false, "04/06", sdf.parse("2010/07/12"), "Banco Do Brasil", 2010, "Real", "TERRITORIAL        86,85\nPREDIAL        19,34\nEXPEDIENTE        18,60", true);
            fb.add("3872006", "IPTU", "755", "0101064032601", "0105444", 24.80, 24.80, false, "05/06", sdf.parse("2010/08/12"), "Banco Do Brasil", 2010, "Real", "TERRITORIAL        86,85\nPREDIAL        19,34\nEXPEDIENTE        18,60", true);
            fb.add("3872006", "IPTU", "755", "0101064032601", "0105444", 24.80, 24.80, false, "06/06", sdf.parse("2010/09/13"), "Banco Do Brasil", 2010, "Real", "TERRITORIAL        86,85\nPREDIAL        19,34\nEXPEDIENTE        18,60", true);

            fb.add("3872006", "IPTU", "756", "0101064032601", "0105444", 24.80, 24.80, false, "06/06", sdf.parse("2010/09/13"), "Banco Do Brasil", 2010, "Real", "TERRITORIAL        86,85\nPREDIAL        19,34\nEXPEDIENTE        18,60", true);

            JasperViewer jv = new JasperViewer(fb.getJasperPrint());
            jv.setVisible(true);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
