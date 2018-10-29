package br.com.eddydata.boleto;

import net.sf.jasperreports.view.JasperViewer;

public class TesteCaixaEconomica {

    public static void main(String args[]) {
        Relatorio rel = new Relatorio(new CaixaEconomicaNovo());

        for (int i = 0; i < 3; i++) {
            BoletoBean boleto = new BoletoBean();

            boleto.setAgencia("3042"); // sem digito
            boleto.setDvAgencia("2");
            boleto.setContaCorrente("089172"); // modalidade(2) + conta(6)
            boleto.setDvContaCorrente("0"); // digito
//            boleto.setContaCorrente("87000000131"); // modalidade(2) + conta(6)
//            boleto.setDvContaCorrente("3"); // digito
            boleto.setMoeda("9"); // 9 = real
            boleto.setCedente("Eddydata.xxx");
            boleto.setCarteira("SR"); // exigencia do banco
            boleto.setLocalPagamento("Preferencialmente nas Casas Lotericas ou Ag�ncias"); // obrigatorio essa frase
            boleto.setLocalPagamento2("");
            boleto.setInstrucao1("Texto e emiss�o do boleto de Responsabilidade do Cedente"); // texto exigido pelo banco
            boleto.setInstrucao2("N�o Receber ap�s o vencimento");
            boleto.setInstrucao3("Multa de 10% ap�s vencimento");
            boleto.setEspecieDocumento("RC");

            boleto.setDataDocumento("14/08/2009");
            boleto.setDataProcessamento("14/08/2009");
            boleto.setDataVencimento("09/07/2010");
            boleto.setNossoNumero("000000004900617", 15);

            boleto.setNomeSacado("Cliente Compra Tudo");
            boleto.setEnderecoSacado("Av. Dompierri, 768");
            boleto.setBairroSacado("Centro");
            boleto.setCidadeSacado("Franca");
            boleto.setUfSacado("SP");
            boleto.setCepSacado("13400-902");
            boleto.setCpfSacado("123.456.789-01");
            boleto.setCodigoOperacao("001");

            boleto.setValorBoleto(103.46);
            boleto.setDocumento(boleto.getNossoNumero());

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