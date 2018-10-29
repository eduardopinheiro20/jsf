package br.com.eddydata.boleto;

import net.sf.jasperreports.view.JasperViewer;

public class TesteBB {

    public static void main(String args[]) {
        Relatorio rel = new Relatorio(new BancoBrasil(), 1);

        for (int i = 0; i <= 1; i++) {
            BoletoBean boleto = new BoletoBean();

            boleto.setContaCorrente("130009"); // 7 digitos
            boleto.setDvContaCorrente("1");
            boleto.setAgencia("6965");
            boleto.setDvAgencia("5");
            boleto.setNumConvenio("2176431");
            boleto.setMoeda("9"); //9 = real
            boleto.setCedente("PREFEITURA MUNICIPAL DE RIN��O");
            boleto.setCarteira("17"); // exigencia do banco
            boleto.setLocalPagamento("Pagavel em qualquer Banco at� o vencimento."); // obrigatorio essa frase
            boleto.setLocalPagamento2("");
            boleto.setInstrucao1("Texto e emiss�o do boleto de Responsabilidade do Cedente"); // texto exigido pelo banco
            boleto.setInstrucao2("N�o Receber ap�s o vencimento");
            boleto.setInstrucao3("");
            boleto.setEspecieDocumento("R$");
            boleto.setDocumento("212");
            boleto.setParcela("1");

            boleto.setDataDocumento("19/01/2011");
            boleto.setDataProcessamento("19/01/2011");
            boleto.setDataVencimento("30/03/2011");
            boleto.setNossoNumero("1898913", 10);

            boleto.setNomeSacado("ADRIANO MIGANI TEIXEIRA");
            boleto.setEnderecoSacado("Av. Orlando Dompierri, 2094");
            boleto.setBairroSacado("Jardim Bar�o");
            boleto.setCidadeSacado("Franca");
            boleto.setUfSacado("SP");
            boleto.setCepSacado("14409-003");
            boleto.setCpfSacado("190.238.728-78");

            boleto.setValorBoleto(1.01);

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