/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2015, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.suporte;

import br.com.eddydata.entidade.admin.EmailParametro;
import com.sun.mail.util.MailSSLSocketFactory;
import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.activation.DataHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class Util {

    private static final int[] weightCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] weightCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private final static String format_date_br = "dd/MM/yyyy"; // formato dia/mes/ano
    private final static String format_time_br = "hh:mm:ss"; // formato dia/mes/ano
    private static String empresa;
    private static final Pattern PATTERN_EMAIL = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public static String getEmpresa() {
        return empresa;
    }

    public static void setEmpresa(String empresa) {
        empresa = "eddydata Informática ltda.";
        Util.empresa = empresa;
    }

    // formato DDMMAAA
    public synchronized static Calendar convertToDate(String date) {
        try {
            if (date == null || date.length() < 8) {
                return null;
            }

            int day = Integer.parseInt(date.substring(0, 2));
            int month = Integer.parseInt(date.substring(2, 4)) - 1;
            int year = Integer.parseInt(date.substring(4, 8));

            return new GregorianCalendar(year, month, day);
        } catch (Exception e) {
            return null;
        }
    }

    static synchronized public boolean validateCNPJ(String str_cnpj) {
        try {
            int soma = 0, dig;
            String cnpj_calc = str_cnpj.substring(0, 12);

            if (str_cnpj.length() != 14) {
                return false;
            }

            char[] chr_cnpj = str_cnpj.toCharArray();

            /*
             * Primeira parte
             */
            for (int i = 0; i < 4; i++) {
                if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                    soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
                }
            }

            for (int i = 0; i < 8; i++) {
                if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                    soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
                }
            }

            dig = 11 - (soma % 11);

            cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

            /*
             * Segunda parte
             */
            soma = 0;
            for (int i = 0; i < 5; i++) {
                if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                    soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
                }
            }

            for (int i = 0; i < 8; i++) {
                if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                    soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
                }
            }

            dig = 11 - (soma % 11);
            cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

            return str_cnpj.equals(cnpj_calc);
        } catch (Exception e) {
            return false;
        }
    }

    public static String hoje() {
        java.util.Date hoje = new java.util.Date();
        return parseSqlToBrDate(hoje);
    }

    public static String desmascarar(
            String mascara, String valor) {
        if (mascara == null) {
            return valor;
        } else {
            HashMap hm = new HashMap();
            for (int j = 0; j
                    < mascara.length(); j++) {
                if ((mascara.charAt(j) == '0' || mascara.charAt(j) == '#') == false) {
                    hm.put(mascara.charAt(j), null);
                }

            }

            StringBuffer sb = new StringBuffer(valor);
            for (int j = 0; j
                    < sb.length(); j++) {
                if (hm.containsKey(sb.charAt(j))) {
                    sb.delete(j, j + 1);
                    j--;

                }
            }
            return new String(sb);
        }
    }

    public static String getNomeMes(byte mes) {
        switch (mes) {
            case 1:
                return "JANEIRO";
            case 2:
                return "FEVEREIRO";
            case 3:
                return "MARÇO";
            case 4:
                return "ABRIL";
            case 5:
                return "MAIO";
            case 6:
                return "JUNHO";
            case 7:
                return "JULHO";
            case 8:
                return "AGOSTO";
            case 9:
                return "SETEMBRO";
            case 10:
                return "OUTUBRO";
            case 11:
                return "NOVEMBRO";
            case 12:
                return "DEZEMBRO";
            default:
                return "";
        }
    }

    public static String geraCertificado(int num_certidao) throws SQLException {

        String validacao = "";

        try {
            if (num_certidao <= 0) {
                num_certidao = 1;
            }
            Double d = Math.random() * 100000;
            Integer i = 100000 + d.intValue();

            validacao = i + "-" + new java.text.SimpleDateFormat("mmMMdd-").format(new Date()) + i.toString().substring(1, 2) + new java.text.SimpleDateFormat("ddssyy").format(new Date()) + "-" + i.toString().substring(1, 3) + num_certidao;

        } catch (Exception e) {
            return "";
        }

        return validacao;
    }

    public static boolean validateCPF(String s_aux) {
        if (s_aux.length() == 11) {
            try {
                if (Long.parseLong(s_aux) <= 0) {
                    throw new Exception("Numero de CPF invalido");
                }

                double primeiraSoma = 0.0, segundaSoma = 0.0, primeiroDigito, segundoDigito, valorTemp;

                for (int i = 0; i < 10; i++) {
                    valorTemp = Double.parseDouble("" + s_aux.charAt(i));
                    primeiraSoma += (i < 9 ? valorTemp * (10 - i) : 0);
                    segundaSoma += valorTemp * (11 - i);
                }

                primeiroDigito = (primeiraSoma % 11 < 2 ? 0 : 11 - (primeiraSoma % 11));
                segundoDigito = (segundaSoma % 11 < 2 ? 0 : 11 - (segundaSoma % 11));

                return Integer.parseInt("" + s_aux.charAt(9)) == primeiroDigito
                        && Integer.parseInt("" + s_aux.charAt(10)) == segundoDigito;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public synchronized static int calculateCpfCnpjDigit(String str, int[] peso) {
        try {
            int soma = 0;
            for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
                digito = Integer.parseInt(str.substring(indice, indice + 1));
                soma += digito * peso[peso.length - str.length() + indice];
            }

            soma = 11 - soma % 11;
            return soma > 9 ? 0 : soma;
        } catch (Exception e) {
            return -1;
        }
    }

    public synchronized static boolean isValidCNPJ(String cnpj) {
        return isValidCNPJ(convertToCNPJNumber(cnpj));
    }

    public synchronized static boolean isValidCNPJ(long cnpjNumber) {
        try {
            //Log.trace("Validando CNPJ: " + cnpjNumber);

            if (cnpjNumber <= 0) {
                return false;
            }

            String cnpj = String.format("%014d", cnpjNumber);

            Integer digito1 = calculateCpfCnpjDigit(cnpj.substring(0, 12), weightCNPJ);
            Integer digito2 = calculateCpfCnpjDigit(cnpj.substring(0, 12) + digito1, weightCNPJ);

            return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
        } catch (Exception e) {
            return false;
        }

    }

    public synchronized static boolean isValidCPF(String cnpj) {
        return isValidCPF(convertToCNPJNumber(cnpj));
    }

    public synchronized static boolean isValidCPF(long cpfNumber) {
        //Log.trace("Validando CPF: " + cpfNumber);

        String cpf = String.format("%011d", cpfNumber);

        if (cpf == null || cpf.length() != 11) {
            return false;
        }

        Integer digito1 = calculateCpfCnpjDigit(cpf.substring(0, 9), weightCPF);
        Integer digito2 = calculateCpfCnpjDigit(cpf.substring(0, 9) + digito1, weightCPF);

        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    public synchronized static String convertToFormatedCNPJ(long cnpj) {
        String value = String.format("%016d", cnpj);

        value = value.substring(value.length() - 15, value.length() - 12) + "."
                + value.substring(value.length() - 12, value.length() - 9) + "." + value.substring(value.length() - 9, value.length() - 6) + "/"
                + value.substring(value.length() - 6, value.length() - 2) + "-" + value.substring(value.length() - 2, value.length());

        return value;
    }

    public synchronized static long convertToCNPJNumber(String cnpj) {
        try {
            if (cnpj == null || cnpj.length() <= 0) {
                throw new Exception("String CNPJ invalida");
            }

            return Long.parseLong(cnpj.replaceAll("[\\D]", ""));
        } catch (Exception e) {
            return 0L;
        }
    }

    //-------------------------------------------------------------------------
    public static String mask(String mask, String value) {
        if (value == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer(value.length() + 4);
        int j = 0;
        for (int i = 0; i < mask.length(); i++) {
            if (j >= value.length()) {
                break;
            } else if (mask.charAt(i) == '0' || mask.charAt(i) == '#') {
                sb.append(value.charAt(j));
                j++;
            } else if (mask.charAt(i) == '*') {
                sb.append(value.substring(j, value.length()));
                return new String(sb);
            } else {
                sb.append(mask.charAt(i));
            }
        }
        return new String(sb);
    }

    public static String unMask(
            String mascara, String valor) {
        if (valor == null) {
            return null;
        }
        HashMap hm = new HashMap();
        for (int j = 0; j
                < mascara.length(); j++) {
            if ((mascara.charAt(j) == '0' || mascara.charAt(j) == '#') == false) {
                hm.put(mascara.charAt(j), null);
            }

        }

        StringBuffer sb = new StringBuffer(valor);
        for (int j = 0; j
                < sb.length(); j++) {
            if (hm.containsKey(sb.charAt(j))) {
                sb.delete(j, j + 1);
                j--;

            }
        }
        return new String(sb);
    }

    public static boolean isFloat(Object valor) {
        if (valor == null || valor.toString().length() == 0) {
            return true;
        }
        try {
            Double.parseDouble(valor.toString());
            return true;
        } catch (NumberFormatException er1) {
            return false;
        }
    }

    public static boolean isDate(Object data) {
        try {
            if (data.toString().replace(" ", "").equals("//")) {
                return false;
            }
            convertBrToDate(data.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int mesesEntre(Calendar inicial, Calendar fim) {
        int qtdMesesIni = (inicial.get(Calendar.YEAR) * 12) + inicial.get(Calendar.MONTH);
        int qtdMesesFim = (fim.get(Calendar.YEAR) * 12) + fim.get(Calendar.MONTH);
        return qtdMesesFim - qtdMesesIni;
    }

    public static String convertToJavaFloat(String valor) {
        // formata valores fracionarios digitados no padrao brasileiro para o padrao do java
        if (valor == null) {
            return "0";
        }
        StringBuffer sb = new StringBuffer(valor.length());
        sb.append(valor);
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            switch (c) {
                case ',':
                    sb.setCharAt(i, '.');
                    break;
                case '.':
                    sb.deleteCharAt(i--);
                    break;
            }
        }
        if (sb.length() == 0) {
            return "0";
        } else {
            return new String(sb);
        }
    }

    public static String convertToBrFloat(Object val) {
        if (val == null || val.toString().length() == 0) {
            val = "0";
        }
        if (isFloat(val) == false) {
            erro("Valor fracion\u00e1rio inv\u00e1lido!", (Exception) null);
            return null;
        } else {
            double val_;
            val_ = Double.parseDouble(val.toString());
            DecimalFormat format = new DecimalFormat();
            format.applyPattern("#,##0.00##");
            String out = format.format(val_);
            return out;
        }
    }

    public static java.util.Date convertBrToDate(String val) {
        SimpleDateFormat format = new SimpleDateFormat(format_date_br);
        try {
            return format.parse(val);
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static java.sql.Date convertBrToSQLDate(String val) {
        SimpleDateFormat format = new SimpleDateFormat(format_date_br);
        try {
            return new java.sql.Date(format.parse(val).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String convertToBrDate(Object data) {
        SimpleDateFormat f = new SimpleDateFormat(format_date_br);
        if (data != null) {
            if (data.getClass() == String.class) {
                Date d = java.sql.Date.valueOf(data.toString());
                return f.format(d);
            }
            return f.format(data);
        } else {
            return null;
        }
    }

    public static String convertToBrTime(Object data) {
        SimpleDateFormat f = new SimpleDateFormat(format_time_br);
        if (data != null) {
            if (data.getClass() == String.class) {
                Date d = java.sql.Date.valueOf(data.toString());
                return f.format(d);
            }
            return f.format(data);
        } else {
            return null;
        }
    }

    public static String convertBrToDoubleStr(String valor) { // formata valores fracionarios digitados no padrao brasileiro para o padrao do java
        if (valor == null) {
            return "0";
        }
        StringBuffer sb = new StringBuffer(valor.length());
        sb.append(valor);
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            switch (c) {
                case ',':
                    sb.setCharAt(i, '.');
                    break;
                case '.':
                    sb.deleteCharAt(i--);
                    break;
            }
        }
        if (sb.length() == 0) {
            return "0";
        } else {
            return new String(sb);
        }
    }

    public static double convertBrToDouble(String val) {
        return Double.parseDouble(convertBrToDoubleStr(val));
    }

    public static String extractStr(Object val) {
        if (val == null) {
            return "";
        } else {
            return String.valueOf(val);
        }
    }

    public static int extractInt(Object val) {
        String out = extractStr(val);
        if (out.length() == 0) {
            return 0;
        } else {
            return Integer.parseInt(val.toString());
        }
    }

    public static double extractDouble(Object val) {
        if (val == null) {
            return 0.00;
        } else if (val.getClass() == BigDecimal.class) {
            return ((BigDecimal) val).doubleValue();
        } else if (val.getClass() == double.class) {
            return ((Double) val);
        } else {
            String out = extractStr(val);
            if (out.length() == 0) {
                return 0.0;
            } else {
                return Double.parseDouble(val.toString());
            }
        }
    }

    public static java.util.Date extractDate(Object val) {
        if (val == null) {
            java.util.Date date = new java.util.Date();
            date.setTime(0);
            return date;
        }
        if (val.getClass() == java.sql.Date.class) {
            java.sql.Date d = (java.sql.Date) val;
            java.util.Date out = new java.util.Date();
            out.setTime(d.getTime());
            return out;
        } else {
            return convertBrToDate(val.toString());
        }
    }

    public static class Texto {

        public static String alinharDireita(Object obj, int tamanho) {
            String out = "";
            String obj_ = Util.extractStr(obj);
            int fim = tamanho - 1;
            int j = obj_.length();
            for (int i = fim; i >= 0; i--) {
                j--;
                if (j < 0) {
                    out = ' ' + out;
                } else {
                    out = obj_.charAt(j) + out;
                }
            }
            return out;
        }

        public static String removeAcentos(String str) {
            if (str != null) {
                str = Normalizer.normalize(str, Normalizer.Form.NFD);
                str = str.replaceAll("[^\\p{ASCII}]", "");
                return str;
            } else {
                return null;
            }
        }

        public static String alinharEsquerda(Object obj, int tamanho) {
            String out = "";
            String obj_ = Util.extractStr(obj);

            for (int i = 0; i < tamanho; i++) {
                if (i < obj_.length()) {
                    out += obj_.charAt(i);
                } else {
                    out += ' ';
                }
            }
            return out;
        }

        public static String strZero(Object obj, int tamanho) {
            StringBuffer sb = new StringBuffer(tamanho);
            String strValue = Util.extractStr(obj);
            strValue = Util.unMask(".", strValue);
            int diff = tamanho - strValue.length();
            if (diff < 0) {
                return new String(sb);
            }
            while (diff > 0) {
                sb.append('0');
                diff--;
            }
            sb.append(strValue);

            return new String(sb);
        }
    }

    public static String quotarStr(Object str) {
        String s = str == null ? "" : str.toString();
        StringBuffer sb = new StringBuffer(s.length() + 8);
        sb.append(s);
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '\'') {
                sb.insert(i++, '\'');
            }
        }
        sb.insert(0, '\'');
        sb.append('\'');
        return new String(sb);
    }

    /**
     * Busca por itens com texto semelhante dentro do combo. O primeiro item
     * semelhante sera selecionado.
     *
     * @param inicio Indice de origem da busca do combo.
     * @param valor String com o texto a procurar dentro do combo.
     * @param combo Combo alvo.
     */
    public static void findItemCombo(int inicio, String valor, JComboBox combo) {
        String item;
        for (int i = 0; i < combo.getItemCount(); i++) {
            item = combo.getItemAt(i).toString();
            if (valor != null && item.regionMatches(true, inicio, valor, 0, valor.length())) {
                combo.setSelectedIndex(i);
                return;

            }
        }
        combo.setSelectedIndex(-1);
    }

    /**
     * Busca por itens com texto semelhante dentro do combo. O primeiro item
     * semelhante sera selecionado.
     *
     * @param valor String com o texto a procurar dentro do combo.
     * @param combo Combo alvo.
     */
    public static void findItemCombo(String valor, JComboBox combo) {
        findItemCombo(0, valor, combo);
    }

    public static String getNameMonth(int mes) {
        switch (mes) {
            case 0:
                return "ABERTURA EXERCICIO";
            case 1:
                return "JANEIRO";
            case 2:
                return "FEVEREIRO";
            case 3:
                return "MAR\u00c7O";
            case 4:
                return "ABRIL";
            case 5:
                return "MAIO";
            case 6:
                return "JUNHO";
            case 7:
                return "JULHO";
            case 8:
                return "AGOSTO";
            case 9:
                return "SETEMBRO";
            case 10:
                return "OUTUBRO";
            case 11:
                return "NOVEMBRO";
            case 12:
                return "DEZEMBRO";
            default:
                return "?";
        }
    }

    /**
     * Retorna o numero do mes da data.
     *
     * @param data data a extrair mes
     * @return numero do mes
     */
    public static int getMonth(java.util.Date data) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(data);
        return c.get(GregorianCalendar.MONTH) + 1;
    }

    /**
     * Retorna o numero do mes da data.
     *
     * @param data data a extrair mes no formato brasileiro dd/MM/yyyy
     * @return numero do mes
     */
    public static int getMonth(String data) {
        return getMonth(Util.convertBrToDate(data));
    }

    public static int getDay(java.util.Date data) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(data);
        return c.get(GregorianCalendar.DAY_OF_MONTH);
    }

    public static int getYear(java.util.Date data) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(data);
        return c.get(GregorianCalendar.YEAR);
    }

    /**
     * Exibe uma caixa de confirmacao com a mensagem passada de parametro.<p>
     *
     * msg -> mensagem a exibir.<p>
     *
     * Se o usuario clicar em sim sera retornado <tt>true</tt>, caso contrario
     * sera retornado <tt>false</tt>.
     *
     * @param msg
     * @return
     */
    public static boolean confirmed(String msg) {
        String[] opcoes = new String[]{"Sim", "N\u00e3o"};
        return (JOptionPane.showOptionDialog(null, msg, "Confirma\u00e7\u00e3o",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoes, opcoes[0]) == JOptionPane.YES_OPTION);
    }

    /**
     * Exibe uma caixa de dialogo com a mensagem de informacao. A execucao da
     * thread de origem e interrompida ate que a caixa de dialogo seja
     * fechada.<p>
     *
     * @param msg Mensagem a exibir.
     */
    public static void messageInformation(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Informa\u00e7\u00e3o",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Exibe uma caixa de dialogo com a mensagem de alerta. A execucao da thread
     * de origem e interrompida ate que a caixa de dialogo seja fechada.<p>
     *
     * @param msg Mensagem a exibir.
     */
    public static void messageAlert(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Aten\u00e7\u00e3o",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Gera uma excessao em tempo de execucao e notifica o usuario atraves de
     * uma janela informativa.
     *
     * @param msg Mensagem para o usuario.
     * @param e Excessao originaria.
     */
    public static void erro(String msg, Exception e) {
        String erro = msg;
        if (e != null) {
            erro += "\n\nCausa:\n'" + e.getMessage() + "'";
        }
        messageErro(erro);
        Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, e);
    }

    /**
     * Exibe uma caixa de dialogo com a mensagem de erro. A execucao da thread
     * de origem e interrompida ate que a caixa de dialogo seja fechada.<p>
     *
     * @param msg Mensagem a exibir.
     */
    public static void messageErro(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Erro",
                JOptionPane.ERROR_MESSAGE);
    }

    public static boolean isNumeric(String val) {
        if (val == null || val.equals("")) {
            return false;
        }
        for (int i = 0; i < val.length(); i++) {
            if (val.charAt(i) < '0' || val.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    public static boolean isNota(String val) {
        if (val == null || val.equals("")) {
            return false;
        }
        for (int i = 0; i < val.length(); i++) {
            if ((val.charAt(i) < '0' || val.charAt(i) > '9') && val.charAt(i) != '.') {
                return false;
            }
        }
        return true;
    }

    public static boolean isFloat(String val) {
        for (int i = 0; i < val.length(); i++) {
            if (val.charAt(i) < '0' || val.charAt(i) > '9'
                    || val.charAt(i) != '.' || val.charAt(i) != ',') {
                return false;
            }
        }
        return true;
    }

    public static Date dateToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date addMounth(Date hoje, int quant) {

        Calendar agora = Calendar.getInstance();
        agora.setTime(hoje);

        agora.add(Calendar.MONTH, quant);

        return agora.getTime();
    }

    public static Date addDay(Date hoje, int quant) {

        Calendar agora = Calendar.getInstance();
        agora.setTime(hoje);

        agora.add(Calendar.DAY_OF_MONTH, quant);

        return agora.getTime();
    }

    public static Date minusDay(Date hoje, int quant) {

        Calendar agora = Calendar.getInstance();
        agora.setTime(hoje);

        agora.add(Calendar.DAY_OF_MONTH, -quant);

        return agora.getTime();
    }

    public static String dateExtended(Date data, boolean mostrar_diasemana) {
        String diaf = null;
        String mesf = null;
        String retorno = null;

        if (data == null) {
            return retorno;
        }

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        int semana = calendar.get(Calendar.DAY_OF_WEEK);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int ano = calendar.get(Calendar.YEAR);

        // semana
        switch (semana) {
            case 1:
                diaf = "Domingo";
                break;
            case 2:
                diaf = "Segunda";
                break;
            case 3:
                diaf = "Terça";
                break;
            case 4:
                diaf = "Quarta";
                break;
            case 5:
                diaf = "Quinta";
                break;
            case 6:
                diaf = "Sexta";
                break;
            case 7:
                diaf = "Sábado";
                break;
        }
        // mês
        switch (mes) {
            case 1:
                mesf = "Janeiro";
                break;
            case 2:
                mesf = "Fevereiro";
                break;
            case 3:
                mesf = "Março";
                break;
            case 4:
                mesf = "Abril";
                break;
            case 5:
                mesf = "Maio";
                break;
            case 6:
                mesf = "Junho";
                break;
            case 7:
                mesf = "Julho";
                break;
            case 8:
                mesf = "Agosto";
                break;
            case 9:
                mesf = "Setembro";
                break;
            case 10:
                mesf = "Outubro";
                break;
            case 11:
                mesf = "Novembro";
                break;
            case 12:
                mesf = "Dezembro";
                break;
        }
        if (mostrar_diasemana) {
            retorno = diaf + ", " + dia + " de " + mesf + " de " + ano;
        } else {
            retorno = dia + " de " + mesf + " de " + ano;
        }
        return retorno;
    }

    public static String formatDecimal(String formato, Object valor) {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern(formato);
        return format.format(valor);
    }

    public static java.util.Date parseBrStringToDate(String val) {
        if (val == null || val.equals("")) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return format.parse(val);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Date parseBrDateToSQLDate(String val) {
        if (val == null || !Util.isDate(val)) {
            return null;
        }
        String data = Util.getYear(Util.parseBrStringToDate(val)) + "/"
                + Util.Texto.strZero(Util.getMonth(Util.parseBrStringToDate(val)), 2) + "/"
                + Util.Texto.strZero(Util.getDay(Util.parseBrStringToDate(val)), 2);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/dd/MM");
        try {
            return format.parse(data);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    //efetua a leitura do arquivo
    public static byte[] readFile(File file) {
        int len = (int) file.length();
        byte[] sendBuf = new byte[len];
        FileInputStream inFile;
        try {
            inFile = new FileInputStream(file);
            inFile.read(sendBuf, 0, len);

        } catch (FileNotFoundException e) {
            System.out.print("Arquivo não encontrado");
        } catch (IOException e) {
            System.out.print("Erro na leitura do arquivo");
        }
        return sendBuf;
    }

    public static String getHora() {

        // cria um StringBuilder
        StringBuilder sb = new StringBuilder();

        // cria um GregorianCalendar que vai conter a hora atual
        GregorianCalendar d = new GregorianCalendar();

        // anexa do StringBuilder os dados da hora
        sb.append(Util.Texto.strZero(d.get(GregorianCalendar.HOUR_OF_DAY), 2));
        sb.append(":");
        sb.append(Util.Texto.strZero(d.get(GregorianCalendar.MINUTE), 2));
        sb.append(":");
        sb.append(Util.Texto.strZero(d.get(GregorianCalendar.SECOND), 2));

        // retorna a String do StringBuilder
        return sb.toString();
    }

    /**
     * Calcula a diferença de duas datas em dias <br> <b>Importante:</b> Quando
     * realiza a diferença em dias entre duas datas, este método considera as
     * horas restantes e as converte em fração de dias.
     *
     * @param dataInicial
     * @param dataFinal
     * @return quantidade de dias existentes entre a dataInicial e dataFinal.
     */
    public static double diferencaEmDias(Date dataInicial, Date dataFinal) {
        double result;
        long diferenca = dataFinal.getTime() - dataInicial.getTime();
        double diferencaEmDias = (diferenca / 1000) / 60 / 60 / 24; //resultado é diferença entre as datas em dias
        long horasRestantes = (diferenca / 1000) / 60 / 60 % 24; //calcula as horas restantes
        result = diferencaEmDias + (horasRestantes / 24d); //transforma as horas restantes em fração de dias

        return Util.truncarValor(result, 0);
    }

    /**
     * Calcula a diferença de duas datas em horas <br> <b>Importante:</b> Quando
     * realiza a diferença em horas entre duas datas, este método considera os
     * minutos restantes e os converte em fração de horas.
     *
     * @param dataInicial
     * @param dataFinal
     * @return quantidade de horas existentes entre a dataInicial e dataFinal.
     */
    public static double diferencaEmHoras(Date dataInicial, Date dataFinal) {
        double result;
        long diferenca = dataFinal.getTime() - dataInicial.getTime();
        long diferencaEmHoras = (diferenca / 1000) / 60 / 60;
        long minutosRestantes = (diferenca / 1000) / 60 % 60;
        double horasRestantes = minutosRestantes / 60d;
        result = diferencaEmHoras + (horasRestantes);

        return result;
    }

    /**
     * Calcula a diferença de duas datas em minutos <br> <b>Importante:</b>
     * Quando realiza a diferença em minutos entre duas datas, este método
     * considera os segundos restantes e os converte em fração de minutos.
     *
     * @param dataInicial
     * @param dataFinal
     * @return quantidade de minutos existentes entre a dataInicial e dataFinal.
     */
    public static double diferencaEmMinutos(Date dataInicial, Date dataFinal) {
        double result;
        long diferenca = dataFinal.getTime() - dataInicial.getTime();
        double diferencaEmMinutos = (diferenca / 1000) / 60; //resultado é diferença entre as datas em minutos
        long segundosRestantes = (diferenca / 1000) % 60; //calcula os segundos restantes
        result = diferencaEmMinutos + (segundosRestantes / 60d); //transforma os segundos restantes em minutos

        return result;
    }

    /**
     * Elimina sem arredondamentos a quantidade desejada de casas decimais.
     *
     * @param val valor a truncar.
     * @param tamanhoFracionario numero de casas fracionarias do valor a
     * eliminar casas.
     *
     * @return double com as casas decimais eliminadas.
     */
    public static double truncarValor(double val, int tamanhoFracionario) {
        double fator = Math.pow(10, tamanhoFracionario);
        long valInt = (long) (val * fator);

        return valInt / fator;
    }

    public static int obterUltimoDiaMes(int mes, int ano) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date(ano, mes - 1, 1));

        int dia = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        return dia;
    }

    public static int obterIdade(Date data_nascimento) {
        int anoAtual = Util.getYear(new Date());
        int anoNascimento = Util.getYear(data_nascimento);
        int idade = anoAtual - anoNascimento;
        return idade;
    }

    public static String iniMaiuscula(String value) {
        String result = "";
        String[] nomes = value.split(" ");

        for (String palavra : nomes) {
            result = palavra.substring(0, 1).toUpperCase() + palavra.substring(1).toLowerCase();
        }
        return result.trim();
    }

    public static String formatarDecimal(String formato, Object valor) {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern(formato);
        return format.format(valor);
    }

    public static void criarArquivoBranco(String caminho) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(caminho));
        pw.close();
    }

    public static void criarArquivoTexto(String caminho, String texto) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(caminho));
        String[] linhas = texto.split("\n");
        for (String s : linhas) {
            pw.println(s);
        }
        pw.close();
    }

    public static Boolean isSqlDate(Object data) {
        try {
            String regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
            Pattern pat = Pattern.compile(regexp);
            Matcher m = pat.matcher(data.toString());

            return (m.find());
        } catch (Exception e) {
            System.out.println("Erro ao verificar data\n" + e.getCause().getMessage());
            return false;
        }
    }

    public static String parseSqlDate(Object data) {
        if (data == null) {
            return null;
        }
        if (isSqlDate(data)) { // se ja veio em formato do SQL
            return data.toString();
        }
        if (data.getClass() == String.class) { // se for uma data em string, deve ser convertida
            if (isDate(data) == false) {
                erro("Data inv\u00e1lida: '" + data + "'", (Exception) null);
                return null;
            } else { // se a data nao for string, deve estar em formato nativo
                SimpleDateFormat f = new SimpleDateFormat(format_date_br);
                try {
                    return quotarStr(new java.sql.Date(f.parse((String) data).getTime()));
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else {
            String formato = "yyyy-MM-dd";
            SimpleDateFormat f = new SimpleDateFormat(formato);
            return quotarStr(f.format(data));
        }
    }

    public static String brToJavaFloat(String valor) { // formata valores fracionarios digitados no padrao brasileiro para o padrao do java
        if (valor == null) {
            return "0";
        }
        StringBuffer sb = new StringBuffer(valor.length());
        sb.append(valor);
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            switch (c) {
                case ',':
                    sb.setCharAt(i, '.');
                    break;
                case '.':
                    sb.deleteCharAt(i--);
                    break;
            }
        }
        if (sb.length() == 0) {
            return "0";
        } else {
            return new String(sb);
        }
    }

    public static Email conectaEmail(String hostname, String porta, String email, String senha, boolean starttls, String addressemail) throws EmailException {
        Email email2 = new SimpleEmail();
        email2.setHostName(hostname);
        email2.setSmtpPort(Integer.parseInt(porta));
        email2.setAuthenticator(new DefaultAuthenticator(email, senha));
        email2.setTLS(starttls);
        email2.setFrom(addressemail);
        email2.setCharset("UTF-8");
        return email2;
    }

    public static String parseSqlToBrDate(Object data) {
        SimpleDateFormat f = new SimpleDateFormat(format_date_br);
        if (data != null) {
            if (isBrDate(data)) { //se ja veio com formato br apenas retorna
                return data.toString();
            } else {
                if (data.getClass() == String.class) {
                    try {
                        String formatDateOrTime = "yyyy-MM-dd";
                        if (data.toString().length() > 10) {
                            formatDateOrTime = "HH:mm:ss";
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat(formatDateOrTime);
                        return f.format(sdf.parse((String) data));
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                return f.format(data);
            }
        } else {
            return null;
        }
    }

    public static Boolean isBrDate(Object data) {
        try {
            String regexp = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
            Pattern pat = Pattern.compile(regexp);
            Matcher m = pat.matcher(data.toString());

            return (m.find());
        } catch (Exception e) {
            System.out.println("Erro ao verificar data\n" + e.getCause().getMessage());
            return false;
        }
    }

    /**
     * Função para retornar valor em double validando caso seja um bigdecimal
     *
     * @param val Object
     * @return double
     */
    public static Double convertObjectToDecimal(Object val) {
        if (val instanceof Double) {
            return Double.parseDouble(val.toString());
        } else if (val instanceof BigDecimal) {
            return Double.parseDouble(((BigDecimal) val).toString());
        } else {
            return 0.0;
        }
    }

    public static java.util.Date parseBrStrToDate(String val) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return format.parse(val);
        } catch (ParseException ex) {
            throw new Exception("Erro ao converter data");
        }
    }

    public static String removerMascara(String str) {
        return str.replaceAll("\\D", "");
    }

    public static boolean isValidEmail(String email) {
        if (email != null && !email.equals("")) {
            Matcher matcher = PATTERN_EMAIL.matcher(email);
            return (matcher.matches());
        } else {
            return false;
        }
    }

    public static String downloadFile(String nomeDoArquivoGeradoParaDownload, String caminhoRelativoComNomeeExtensao) {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) context.getContext();
        //Obtem o caminho para o arquivo e efetua a leitura
        byte[] arquivo = readFile(new File(servletContext.getRealPath("") + caminhoRelativoComNomeeExtensao));
        HttpServletResponse response = (HttpServletResponse) context.getResponse();
        //configura o arquivo que vai voltar para o usuario.
        response.setHeader("Content-Disposition", "attachment;filename=\"" + nomeDoArquivoGeradoParaDownload + "\"");
        response.setContentLength(arquivo.length);
        //abre a janela do download
        response.setContentType("application/download");
        //envia o arquivo de volta
        try {
            OutputStream out = response.getOutputStream();
            out.write(arquivo);
            out.flush();
            out.close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("Erro no envio do arquivo" + e.getMessage());
        }
        return "";
    }

    public static void dispararEmail(EmailParametro p, String assunto, String mensagem, String destino)
            throws BusinessViolation, Exception {
        dispararEmail(p, assunto, mensagem, destino, null, null, null, false);
    }

    public static void dispararEmail(EmailParametro p, String assunto, String mensagem, String destino, boolean debug)
            throws BusinessViolation, Exception {
        dispararEmail(p, assunto, mensagem, destino, null, null, null, debug);
    }

    public static void dispararEmail(EmailParametro p, String assunto, String mensagem, String destino, byte[] anexo, String nomeArquivo, String tipoArquivo)
            throws BusinessViolation, Exception {
        dispararEmail(p, assunto, mensagem, destino, anexo, nomeArquivo, tipoArquivo, false);
    }

    public static void dispararEmail(final EmailParametro p, String assunto, String mensagem, String destino, byte[] anexo, String nomeArquivo, String tipoArquivo, boolean debug)
            throws BusinessViolation, Exception {
        if (p == null
                || p.getEmailEmail() == null || "".equals(p.getEmailEmail())
                || p.getEmailPassword() == null || "".equals(p.getEmailPassword())
                || p.getEmailHostname() == null || "".equals(p.getEmailHostname())
                || p.getEmailSolicitacao() == null || "".equals(p.getEmailSolicitacao())
                || p.getEmailPort() == null) {
            throw new BusinessViolation("Parâmetro de e-mail não configurado corretamente");
        }
        if (!isValidEmail(p.getEmailSolicitacao())) {
            throw new BusinessViolation("E-mail de origem inválido");
        }
        if (!isValidEmail(destino)) {
            throw new BusinessViolation("E-mail de destino inválido");
        }

        String saida_debug = "";
        Properties properties = new Properties();
        properties.put("mail.smtp.host", p.getEmailHostname());
        properties.put("mail.smtp.port", p.getEmailPort());
        properties.put("mail.smtp.auth", (p.getEmailAuthenticated() ? "true" : "false"));
        properties.put("mail.smtp.starttls.enable", (p.getEmailStarttls() ? "true" : "false"));

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.socketFactory.port", p.getEmailPort());
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        if (debug) {
            saida_debug += "\nPropriedades: " + properties.toString();
        }

        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(p.getEmailEmail(), p.getEmailPassword());
            }
        };
        if (debug) {
            saida_debug += "\nAutenticação: usuario(" + p.getEmailEmail() + ") senha(" + p.getEmailPassword() + ")";
        }
        Session session = Session.getInstance(properties, auth);

        MimeMessage msg = new MimeMessage(session);
        Multipart mp = new MimeMultipart();

        if (anexo != null) {
            MimeBodyPart mbp = new MimeBodyPart();
            ByteArrayDataSource data;
            data = new ByteArrayDataSource(new ByteArrayInputStream(anexo), tipoArquivo + "; charset=utf-8");
            mbp.setDataHandler(new DataHandler(data));
            mbp.setFileName(nomeArquivo);

            mp.addBodyPart(mbp);

            if (debug) {
                saida_debug += "\nAnexo: " + nomeArquivo + " - " + data.toString();
            }
        }

        MimeBodyPart mbp2 = new MimeBodyPart();
        mbp2.setContent(new String(mensagem.getBytes("utf-8"), "utf-8"), "text/html; charset=utf-8");
        mp.addBodyPart(mbp2);

        msg.setFrom(new InternetAddress(p.getEmailSolicitacao()));
        if (debug) {
            saida_debug += "\nORIGEM: " + p.getEmailSolicitacao();
        }

        InternetAddress[] addressTo = new InternetAddress[1];
        addressTo[0] = new InternetAddress(destino);
        if (debug) {
            saida_debug += "\nDESTINO: " + destino;
        }

        msg.setRecipients(Message.RecipientType.TO, addressTo);
        msg.setSentDate(new Date());
        msg.setSubject(new String(assunto.getBytes("utf-8"), "utf-8"), "utf-8");
        msg.setContent(mp);

        if (debug) {
            saida_debug += "\nAssunto: " + new String(assunto.getBytes("utf-8"), "utf-8");
        }

        if (debug) {
            System.out.println(saida_debug);
        }

        try {
            Transport.send(msg);
            if (debug) {
                System.out.println("E-mail disparado com sucesso para o destino " + destino);
            }
        } catch (Exception ex) {
            throw new Exception("Não foi possível disparar email: \n" + ex.getMessage());
        }
    }
}
