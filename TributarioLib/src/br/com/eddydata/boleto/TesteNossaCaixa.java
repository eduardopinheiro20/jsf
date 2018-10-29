package br.com.eddydata.boleto;

import net.sf.jasperreports.view.JasperViewer;

public class TesteNossaCaixa {

    public static void main(String args[]) {
        Relatorio rel = new Relatorio(new NossaCaixa());

        for (int i = 0; i < 5; i++) {
            BoletoBean boleto = new BoletoBean();

            boleto.setAgencia("0016"); // sem digito
            boleto.setDvAgencia("7");
            boleto.setContaCorrente("04 002330"); // modalidade(2) + conta(6)
            boleto.setDvContaCorrente("1"); // digito
            boleto.setMoeda("9"); // 9 = real
            boleto.setCedente("Eddydata");
            boleto.setCarteira("CEDENT"); // exigencia do banco
            boleto.setLocalPagamento("Pague preferencialmente no Banco Nossa Caixa S.A. ou na rede banc�ria at� o vencimento."); // obrigat?rio essa frase
            boleto.setLocalPagamento2("");
            boleto.setInstrucao1("Texto e emiss�o do boleto de Responsabilidade do Cedente"); // texto exigido pelo banco
            boleto.setInstrucao2("N�o Receber ap�s o vencimento");
            boleto.setInstrucao3("Multa de 10% ap�s vencimento");
            boleto.setEspecieDocumento("DS");

            boleto.setDataDocumento("15/03/2007");
            boleto.setDataProcessamento("15/03/2007");
            boleto.setDataVencimento("30/0" + (i + 3) + "/2007");
            boleto.setNossoNumero(String.valueOf(i + 1), 7);

            boleto.setNomeSacado("Cliente Compra Tudo");
            boleto.setEnderecoSacado("Av. Dompierri, 768");
            boleto.setBairroSacado("Centro");
            boleto.setCidadeSacado("Franca");
            boleto.setUfSacado("SP");
            boleto.setCepSacado("13400-902");
            boleto.setCpfSacado("123.456.789-01");

            boleto.setValorBoleto(50 * (i + 1));

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