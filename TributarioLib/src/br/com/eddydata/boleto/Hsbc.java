/*
 * Esta biblioteca e um software livre, que pode ser redistribuido e/ou
 * modificado sob os termos da LicenÃ§a Publica Geral Reduzida GNU,
 * conforme publicada pela Free Software Foundation, versao 2.1 da licenca.
 *
 * Esta biblioteca e distribuida na experanca de ser util aos seus usuarios,
 * porem NAO TEM NENHUMA GARANTIA, EXPLICITAS OU IMPLICITAS, COMERCIAIS OU
 * DE ATENDIMENTO A UMA DETERMINADA FINALIDADE.
 * Veja a Licenca Publica Geral Reduzida GNU para maiores detalhes.
 * A licenca se encontra no arquivo lgpl-br.txt
 */
package br.com.eddydata.boleto;

import br.com.eddydata.suporte.Util;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe para gerar o boleto do unibanco
 * @author Fabio Souza
 */
public class Hsbc extends Banco {

    /**
     * Metdodo responsavel por resgatar o numero do banco, coloque no return o codigo do seu banco
     */
    @Override
    public String getNumero() {
        return "399";
    }

    public String getCnr() {
        return "2";
    }

    public String getApp() {
        return "2";
    }

    public int getDataJuliano() {
        try {
            Calendar cal = GregorianCalendar.getInstance();
            
            Date data = Util.parseBrStrToDate(boleto.getDataVencimento());
            String ano = boleto.getDataVencimento().split("/")[2].substring(3);
            
            cal.setTime(data);
            int diaDoAno = cal.get(Calendar.DAY_OF_YEAR);
            
            String retorno = String.valueOf(diaDoAno) + String.valueOf(ano);
            
            return Integer.parseInt(retorno);
        } catch (Exception ex) {
            return -1;
        }
    }

    /**
     * MÃ©todo particular do hsbc
     */
    public String getCampoLivre() {
        return getNumero() + boleto.getMoeda() + getCampo4() + boleto.getFatorVencimento() + boleto.getValorTitulo() + boleto.getContaCorrente() + boleto.getNossoNumero() + getDataJuliano() + getApp();
    }

    /**
     * Metodo que monta o primeiro campo do codigo de barras
     * Este campo como os demais e feito a partir do da documentacao do banco
     * A documentacao do banco tem a estrutura de como monta cada campo, depois disso
     * Ã© sÃ³ concatenar os campos como no exemplo abaixo.
     */
    private String getCampo1() {
        String campo = getNumero() + boleto.getMoeda() + boleto.getContaCorrente().substring(0, 5);

        return campo + boleto.getModulo10(campo);
    }

    /**
     * ver documentacao do banco para saber qual a ordem deste campo
     */
    private String getCampo2() {
        String campo = boleto.getContaCorrente().substring(5, 7) + boleto.getNossoNumero().substring(0, 8);

        return campo + boleto.getModulo10(campo);
    }

    /**
     * ver documentacao do banco para saber qual a ordem deste campo
     */
    private String getCampo3() {
        String campo = boleto.getNossoNumero().substring(8, 13) + Util.formatarDecimal("0000", getDataJuliano()) + getApp();

        return campo + boleto.getModulo10(campo);
    }

    /**
     * ver documentacao do banco para saber qual a ordem deste campo
     */
    private String getCampo4() {
        String campo = getNumero() + String.valueOf(boleto.getMoeda()) + Util.formatarDecimal("0000", boleto.getFatorVencimento()) + boleto.getValorTitulo() + boleto.getContaCorrente() + boleto.getNossoNumero() + Util.formatarDecimal("0000", getDataJuliano()) + getApp();

        return boleto.getDigitoCodigoBarras(campo);
    }

    /**
     * ver documentacao do banco para saber qual a ordem deste campo
     */
    private String getCampo5() {
        String campo = Util.formatarDecimal("0000", boleto.getFatorVencimento()) + boleto.getValorTitulo();
        return campo;
    }

    /**
     * Metodo para monta o desenho do codigo de barras
     * A ordem destes campos tambem variam de banco para banco, entao e so olhar na documentacao do seu banco
     * qual a ordem correta
     */
    @Override
    public String getCodigoBarras() {
        return getNumero() + String.valueOf(boleto.getMoeda()) + getCampo4() + Util.formatarDecimal("0000", boleto.getFatorVencimento()) + boleto.getValorTitulo() + boleto.getContaCorrente() + boleto.getNossoNumero() + Util.formatarDecimal("0000", getDataJuliano()) + getApp();
    }

    /**
     * Metodo que concatena os campo para formar a linha digitavel
     * E necessario tambem olhar a documentacao do banco para saber a ordem correta a linha digitavel
     */
    @Override
    public String getLinhaDigitavel() {
        String str = getCampo1().substring(0, 5) + "." + getCampo1().substring(5) + "  " + getCampo2().substring(0, 5) + "." + getCampo2().substring(5) + "  " + getCampo3().substring(0, 5) + "." + getCampo3().substring(5) + "  " + getCampo4() + "  " + getCampo5();
        return str;
    }

    /**
     * Recupera a carteira no padrao especificado pelo banco
     * @author Gladyston Batista/Eac Software
     */
    @Override
    public String getCarteiraFormatted() {
        return boleto.getCarteira();
    }

    /**
     * Recupera a agencia / codigo cedente no padrao especificado pelo banco
     * @author Gladyston Batista/Eac Software
     */
    @Override
    public String getAgenciaCodCedenteFormatted() {
        return boleto.getContaCorrente();
    }

    private String getDigitoVerificadorNossoNumero() {
        try {
            StringBuffer dig = new StringBuffer(3);
            String nossoNumero = boleto.getNossoNumero();
            dig.append(getModulo11(nossoNumero));
            dig.append("4"); // Codigo do Sacado, Codigo do Cedente e Data do Vencimento
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date venc = sdf.parse(boleto.getDataVencimento());
            sdf = new SimpleDateFormat("ddMMyy");
            String vencStr = sdf.format(venc);
            long sum = new Long(nossoNumero + dig) + new Long(boleto.getContaCorrente())
                    + new Long(vencStr);
            String calc = BigDecimal.valueOf(sum).toString();
            System.out.println(calc);
            dig.append(getModulo11(calc));
            return dig.toString();
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getModulo11(String campo) {
        //Modulo 11 - 234567   (type = 7)
        //Modulo 11 - 23456789 (type = 9)

        int multiplicador = 9;
        int multiplicacao = 0;
        int soma_campo = 0;

        for (int i = campo.length(); i > 0; i--) {
            multiplicacao = Integer.parseInt(campo.substring(i - 1, i)) * multiplicador;

            soma_campo = soma_campo + multiplicacao;

            multiplicador--;
            if (multiplicador < 2) {
                multiplicador = 9;
            }
        }

        int dac = soma_campo % 11;

        if (dac == 0 || dac == 10)
            return "0";
        else
            return ((Integer) dac).toString();
    }

    /**
     * Recupera o nossoNumero no padrao especificado pelo banco
     * @author Gladyston Batista/Eac Software
     */
    @Override
    public String getNossoNumeroFormatted() {
        return boleto.getNossoNumero() + getDigitoVerificadorNossoNumero();
    }
}
