/*
 * Esta biblioteca e um software livre, que pode ser redistribuido e/ou
 * modificado sob os termos da Licenca Publica Geral Reduzida GNU,
 * conforme publicada pela Free Software Foundation, versao 2.1 da licenca.
 *
 * Esta biblioteca e distribuida na experanca de ser util aos seus usuarios,
 * porem NAO TEM NENHUMA GARANTIA, EXPLICITAS OU IMPLICITAS, COMERCIAIS OU
 * DE ATENDIMENTO A UMA DETERMINADA FINALIDADE.
 * Veja a Licenca Publica Geral Reduzida GNU para maiores detalhes.
 * A licenca se encontra no arquivo lgpl-br.txt
 */
package br.com.eddydata.boleto;

/**
 * Classe modelo para a criacao de outros
 * Copie este arquivo para o nome do banco q vc pretende criar, seguindo a mesma nomeclatura de nomes das outras classes
 * Caso o banco tenha algum metodo diferente de calcular os seus campos, vc pode criar os seu metodos particulares dentro
 * desta classe, pois os metodos que tem nesta classe sao padroes
 * @author Fabio Souza
 */
public class CaixaEconomicaNovo extends Banco {

    /**
     * Metdodo responsavel por resgatar o numero do banco, coloque no return o codigo do seu banco
     */
    public static final int qtdeDigitoNossoNumero = 15;
//    private final String inicioNossoNumero = "875";
    private String indetSistema = "";
    private String dv = null, dv1 = null;
    private String codigoBarras = null;
    private String campoLivre = null;

    @Override
    public String getNumero() {
        return "104";
    }

    public String getNumeroSemDigito() {
        return "104";
    }

    @Override
    public String getDvNumero() {
        return "-0";
    }

    @Override
    public void setBoletoBean(BoletoBean boleto) {
        super.setBoletoBean(boleto);
        
        // adicionando o inicio para o nosso numero
        System.out.println(boleto.getNossoNumero());
        boleto.setNossoNumero(boleto.getNossoNumero(), qtdeDigitoNossoNumero);
        // **********************

        String calcdv = boleto.getContaCorrente();
        this.dv1 = modulo_11(calcdv);

        String c1 = "2";
        String c2 = "4";
        String nn = boleto.getNossoNumero();
        this.campoLivre = boleto.getContaCorrente() + this.dv1 + nn.substring(0, 3)
                + c1 + nn.substring(3, 6) + c2 + nn.substring(6);
        this.campoLivre += getModulo11(this.campoLivre, 9);

        this.codigoBarras = getNumeroSemDigito() + boleto.getMoeda() + boleto.getFatorVencimento()
                + boleto.getValorTitulo() + this.campoLivre;
        this.dv = boleto.getDigitoCodigoBarras(this.codigoBarras);
        this.codigoBarras = getNumeroSemDigito() + boleto.getMoeda() + this.dv + boleto.getFatorVencimento()
                + boleto.getValorTitulo() + this.campoLivre;
        // **********************

        // Montando o codigo de barras
        // **********************
        // Adicionando o DV para o nosso numero
        //System.out.println("passou caixa economica novo" + ("24" + boleto.getNossoNumero()));
        String dvNossoNumero = getDvNossoNumero_("24" + boleto.getNossoNumero());
//        String tmp = "00" + boleto.getNossoNumero();
//        String tmp1 = tmp;
//        tmp = tmp.substring(0, 3) + "2";
//        tmp1 = "0004" + tmp1.substring(8, 17);
//        tmp = tmp + tmp1;
//        System.out.println("nosso numero:" + tmp);
//        String dvNossoNumero = getDvNossoNumero_(tmp);
        boleto.setNossoNumero(boleto.getNossoNumero() + "-" + dvNossoNumero);
        // **********************

        //definindo o campolivre
        // **********************
    }
    
      public String getModulo11(String campo, int type) {
        //Modulo 11 - 234567   (type = 7)
        //Modulo 11 - 23456789 (type = 9)

        int multiplicador = 2;
        int multiplicacao = 0;
        int soma_campo = 0;

        for (int i = campo.length(); i > 0; i--) {
            multiplicacao = Integer.parseInt(campo.substring(i - 1, i)) * multiplicador;

            soma_campo = soma_campo + multiplicacao;

            multiplicador++;
            if (multiplicador > 7 && type == 7) {
                multiplicador = 2;
            } else if (multiplicador > 9 && type == 9) {
                multiplicador = 2;
            }
        }

        int dac = 11 - (soma_campo % 11);

        if (dac > 9 && type == 7) {
            dac = 0;
        } else if ((dac > 9) && type == 9) {
            dac = 0;
        }
        
        return ((Integer) dac).toString();
    }
      
    private String getCampo1() {
        String campo = getNumeroSemDigito() + boleto.getMoeda() + boleto.getContaCorrente().substring(0, 5);
        return boleto.getDigitoCampo(campo);
    }

    private String getCampo2() {
        String campo = this.campoLivre.substring(5, 15);
        System.out.println("campo2 " + campo);
        return boleto.getDigitoCampo(campo);
    }

    /**
     * ver documentacao do banco para saber qual a ordem deste campo
     */
    private String getCampo3() {
        String campo = this.campoLivre.substring(15);
        System.out.println("campo3 " + campo);
        return boleto.getDigitoCampo(campo);
    }

    private String getCampo4() {
        return this.dv;
    }

    private String getCampo5() {
        String campo = boleto.getFatorVencimento() + boleto.getValorTitulo();
        System.out.println(campo);
        return campo;
    }

    @Override
    public String getCodigoBarras() {
        return this.codigoBarras;
    }

    @Override
    public String getLinhaDigitavel() {
        return getCampo1().substring(0, 5) + "." + getCampo1().substring(5) + "  "
                + getCampo2().substring(0, 5) + "." + getCampo2().substring(5) + "  "
                + getCampo3().substring(0, 5) + "." + getCampo3().substring(5) + "  "
                + getCampo4() + "  " + getCampo5();
    }

    public String modulo_11(String numero) {

        int total = 0, temp;
        int fator = 2;
        int numeros;

        for (int i = numero.length(); i > 0; i--) {

            numeros = Integer.parseInt(numero.substring(i - 1, i));

            temp = numeros * fator;

            total += temp;

            if (fator < 9) {
                fator = fator + 1;
            } else {
                fator = 2;
            }

        }

        int resto = total % 11;
        resto = 11 - resto;
        if (resto > 9) {
            resto = 0;
        }
        return String.valueOf(resto);
    }

    public String getDvNossoNumero_(String numext) {

        String pesos = "29876543298765432";
        int total = 0, numeros, peso;

        for (int i = numext.length(); i > 0; i--) {

            numeros = Integer.parseInt(numext.substring(i - 1, i));
            peso = Integer.parseInt(pesos.substring(i - 1, i));

            total += numeros * peso;
        }

        int resto = total % 11;
        resto = 11 - resto;
        if (resto > 9) {
            resto = 0;
        }
        return String.valueOf(resto);
    }
    
    public String getDvNossoNumero(String numext) {
        int peso = 2;
        int total = 0;
        int[] numeros = new int[18];

        for (int i = numext.length(); i > 0; i--) {

            numeros[i] = Integer.parseInt(numext.substring(i - 1, i)) * peso;

            peso++;
            if (peso == 10) {
                peso = 2;
            }
        }

        for (int i = numext.length(); i > 0; i--) {
            total = total + numeros[i];
        }

        int resto = total % 11;
        resto = 11 - resto;
        if (resto > 9) {
            resto = 0;
        }
        return String.valueOf(resto);
    }

    private String getModalidade(String modalidade) {

        if (modalidade.equals("01")) {
            modalidade = "1";
        } else if (modalidade.equals("04")) {
            modalidade = "4";
        } else if (modalidade.equals("09")) {
            modalidade = "9";
        } else if (modalidade.equals("13")) {
            modalidade = "3";
        } else if (modalidade.equals("16")) {
            modalidade = "6";
        } else if (modalidade.equals("17")) {
            modalidade = "7";
        } else if (modalidade.equals("18")) {
            modalidade = "8";
        }
        return modalidade;
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
        return boleto.getAgencia() + "/" + boleto.getContaCorrente() + " " + boleto.getDvContaCorrente();
    }

    /**
     * Recupera a nossoNumero no padrao especificado pelo banco
     * @author Gladyston Batista/Eac Software
     */
    @Override
    public String getNossoNumeroFormatted() {
        return "24" + boleto.getNossoNumero();
    }
}
