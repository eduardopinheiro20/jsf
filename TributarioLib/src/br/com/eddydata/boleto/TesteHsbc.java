package br.com.eddydata.boleto;

import net.sf.jasperreports.view.JasperViewer;

public class TesteHsbc {

    public static void main(String args[]) {
        Relatorio rel = new Relatorio(new Hsbc());

        for (int i = 0; i <= 10; i++) {
            BoletoBean boleto = new BoletoBean();

            boleto.setContaCorrente("4041798"); // 7 digitos
            boleto.setMoeda("9"); // 9 = real
            boleto.setCedente("Eddydata");
            boleto.setCarteira("CNR"); // exigencia do banco
            boleto.setLocalPagamento("Pague preferencialmente no Banco HSBC ou na rede banc�ria at� o vencimento."); // obrigat?rio essa frase
            boleto.setLocalPagamento2("");
            boleto.setInstrucao1("Texto e emiss�o do boleto de Responsabilidade do Cedente"); // texto exigido pelo banco
            boleto.setInstrucao2("N�o Receber ap�s o vencimento");
            boleto.setInstrucao3("");
            boleto.setEspecieDocumento("");

            boleto.setDataDocumento("17/03/2010");
            boleto.setDataProcessamento("17/03/2010");
            boleto.setDataVencimento("15/06/2010");
            boleto.setNossoNumero(/*(526458 + i) + ""*/"0100123182960", 13);

            boleto.setNomeSacado("Eddydata");
            boleto.setEnderecoSacado("Av. Dompierri, 768");
            boleto.setBairroSacado("Centro");
            boleto.setCidadeSacado("Franca");
            boleto.setUfSacado("SP");
            boleto.setCepSacado("13400-902");
            boleto.setCpfSacado("999.999.999-99");

            boleto.setValorBoleto(1 * i);

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