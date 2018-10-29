package br.com.eddydata.boleto;

import java.math.BigDecimal;
import net.sf.jasperreports.view.JasperViewer;

public class TesteBancoob {

    public static void main(String args[]) {
        Relatorio rel = new Relatorio(new Bancoob(), 1);

        for (int i = 0; i < 6; i++) {
            BoletoBean boleto = new BoletoBean();

            boleto.setAgencia("3210");
            boleto.setContaCorrente("02 00092339"); // modalidade(2) + codigo cliente(7)
            boleto.setMoeda("9"); // 9 = real
            boleto.setCedente("Eddydata");
            boleto.setCarteira("1"); // exigencia do banco
            boleto.setLocalPagamento("Teste de boleto - Bancoob.");
            boleto.setLocalPagamento2("");
            boleto.setInstrucao1("Texto e emiss�o do boleto de Responsabilidade do Cedente"); // texto exigido pelo banco
            boleto.setInstrucao2("N�o Receber ap�s o vencimento");
            boleto.setInstrucao3("Multa de 10% ap�s vencimento");
            boleto.setEspecieDocumento("DS");

            boleto.setDataDocumento("10/05/2008");
            boleto.setDataProcessamento("10/05/2008");
            boleto.setDataVencimento("10/0" + (i + 5) + "/2008");
            boleto.setNossoNumero("1089328", 7);

            boleto.setNomeSacado("Cliente Compra Tudo");
            boleto.setEnderecoSacado("Av. Dompierri, 768");
            boleto.setBairroSacado("Centro");
            boleto.setCidadeSacado("Franca");
            boleto.setUfSacado("SP");
            boleto.setCepSacado("13400-902");
            boleto.setCpfSacado("123.456.789-01");
            
            boleto.setDocumento("03.11.16.05.13.00");

            boleto.setValorBoleto(225.15);

            rel.addBoleto(boleto);
        }

        try {
            JasperViewer jv = new JasperViewer(rel.getJasperPrint());
            jv.setVisible(true);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}