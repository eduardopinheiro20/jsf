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


/**
 * Classe para gerar o boleto do santander
 * @author Mario Grigioni
 */
/**
 * Codigo do cliente eh o CPF
 * @author cassio
 */
public class SantanderBanespa extends Banco {

    public String D1 = "0";
    public String D2 = "0";

    /**
     * Metodo responsavel por resgatar o numero do banco, coloque no return o codigo do seu banco
     */
    @Override
    public String getNumero() {
        return "033";
    }


    /**
     * Metodo particular do santander
     */
    public String getDacCampoLivre(String campo) {

        D1 = boleto.getModulo10(campo);
        D2 = boleto.getModulo11(campo + D1, 7);

        return D1 + D2;
    }

    private String getCampoLivre() {
        String campo = Util.formatarDecimal("00000000", new Long(new Integer(boleto.getAgencia().trim()) + "" + new Integer(boleto.getContaCorrente().replaceAll(" ", "").trim()) + boleto.getDvContaCorrente().trim())) + boleto.getNossoNumero() + "00" + getNumero();
        campo += getDacCampoLivre(campo);

        return campo;
    }

    /**
     * Metodo que monta o primeiro campo do codigo de barras 
     * Este campo como os demais e feito a partir do da documentacao do banco
     * A documentacao do banco tem a estrutura de como monta cada campo, depois disso
     * Ã© sÃ³ concatenar os campos como no exemplo abaixo.
     */
    private String getCampo1() {
        String campo = getNumero() + String.valueOf(boleto.getMoeda()) + getCampoLivre().substring(0, 5);

        return campo + boleto.getModulo10(campo);
    }

    /**
     * ver documentacao do banco para saber qual a ordem deste campo 
     */
    private String getCampo2() {
        String campo = getCampoLivre().substring(5, 15);

        return campo + boleto.getModulo10(campo);
    }

    /**
     * ver documentacao do banco para saber qual a ordem deste campo
     */
    private String getCampo3() {
        String campo = getCampoLivre().substring(15, 25);

        return campo + boleto.getModulo10(campo);
    }

    /** 
     * Calculo do Digito de autoconferencia D3 para BOLETO BANCARIO
     * Obrigatorio no codigo de Barras - IPTU
     * 4� caracter do codigo de barras com 44 digitos
     * Segundo apostila no SantanderBanespa-Banespa
     * 
     * @param Barras - Codigo de Barras ja montado com 43 digitos
     *
     * @return int 
     **/
    private static int D3(String Barras) {
        String juncao = Barras;
        String campo1 = juncao.substring(0, 1);
        String campo2 = juncao.substring(1, 2);
        String campo3 = juncao.substring(2, 3);
        String campo4 = juncao.substring(3, 4);
        String campo5 = juncao.substring(4, 5);
        String campo6 = juncao.substring(5, 6);
        String campo7 = juncao.substring(6, 7);
        String campo8 = juncao.substring(7, 8);
        String campo9 = juncao.substring(8, 9);
        String campo10 = juncao.substring(9, 10);
        String campo11 = juncao.substring(10, 11);
        String campo12 = juncao.substring(11, 12);
        String campo13 = juncao.substring(12, 13);
        String campo14 = juncao.substring(13, 14);
        String campo15 = juncao.substring(14, 15);
        String campo16 = juncao.substring(15, 16);
        String campo17 = juncao.substring(16, 17);
        String campo18 = juncao.substring(17, 18);
        String campo19 = juncao.substring(18, 19);
        String campo20 = juncao.substring(19, 20);
        String campo21 = juncao.substring(20, 21);
        String campo22 = juncao.substring(21, 22);
        String campo23 = juncao.substring(22, 23);
        String campo24 = juncao.substring(23, 24);
        String campo25 = juncao.substring(24, 25);
        String campo26 = juncao.substring(25, 26);
        String campo27 = juncao.substring(26, 27);
        String campo28 = juncao.substring(27, 28);
        String campo29 = juncao.substring(28, 29);
        String campo30 = juncao.substring(29, 30);
        String campo31 = juncao.substring(30, 31);
        String campo32 = juncao.substring(31, 32);
        String campo33 = juncao.substring(32, 33);
        String campo34 = juncao.substring(33, 34);
        String campo35 = juncao.substring(34, 35);
        String campo36 = juncao.substring(35, 36);
        String campo37 = juncao.substring(36, 37);
        String campo38 = juncao.substring(37, 38);
        String campo39 = juncao.substring(38, 39);
        String campo40 = juncao.substring(39, 40);
        String campo41 = juncao.substring(40, 41);
        String campo42 = juncao.substring(41, 42);
        String campo43 = juncao.substring(42, 43);
        int /*Peso1*/ p1 = Util.extractInt(campo1) * 4;
        int p2 = Util.extractInt(campo2) * 3;
        int p3 = Util.extractInt(campo3) * 2;
        int p4 = Util.extractInt(campo4) * 9;
        int p5 = Util.extractInt(campo5) * 8;
        int p6 = Util.extractInt(campo6) * 7;
        int p7 = Util.extractInt(campo7) * 6;
        int p8 = Util.extractInt(campo8) * 5;
        int p9 = Util.extractInt(campo9) * 4;
        int p10 = Util.extractInt(campo10) * 3;
        int p11 = Util.extractInt(campo11) * 2;
        int p12 = Util.extractInt(campo12) * 9;
        int p13 = Util.extractInt(campo13) * 8;
        int p14 = Util.extractInt(campo14) * 7;
        int p15 = Util.extractInt(campo15) * 6;
        int p16 = Util.extractInt(campo16) * 5;
        int p17 = Util.extractInt(campo17) * 4;
        int p18 = Util.extractInt(campo18) * 3;
        int p19 = Util.extractInt(campo19) * 2;
        int p20 = Util.extractInt(campo20) * 9;
        int p21 = Util.extractInt(campo21) * 8;
        int p22 = Util.extractInt(campo22) * 7;
        int p23 = Util.extractInt(campo23) * 6;
        int p24 = Util.extractInt(campo24) * 5;
        int p25 = Util.extractInt(campo25) * 4;
        int p26 = Util.extractInt(campo26) * 3;
        int p27 = Util.extractInt(campo27) * 2;
        int p28 = Util.extractInt(campo28) * 9;
        int p29 = Util.extractInt(campo29) * 8;
        int p30 = Util.extractInt(campo30) * 7;
        int p31 = Util.extractInt(campo31) * 6;
        int p32 = Util.extractInt(campo32) * 5;
        int p33 = Util.extractInt(campo33) * 4;
        int p34 = Util.extractInt(campo34) * 3;
        int p35 = Util.extractInt(campo35) * 2;
        int p36 = Util.extractInt(campo36) * 9;
        int p37 = Util.extractInt(campo37) * 8;
        int p38 = Util.extractInt(campo38) * 7;
        int p39 = Util.extractInt(campo39) * 6;
        int p40 = Util.extractInt(campo40) * 5;
        int p41 = Util.extractInt(campo41) * 4;
        int p42 = Util.extractInt(campo42) * 3;
        int p43 = Util.extractInt(campo43) * 2;

        int k = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9 + p10 +
                p11 + p12 + p13 + p14 + p15 + p16 + p17 + p18 +
                p19 + p20 + p21 + p22 + p23 + p24 + p25 + p26 +
                p27 + p28 + p29 + p30 + p31 + p32 + p33 + p34 +
                p35 + p36 + p37 + p38 + p39 + p40 + p41 + p42 + p43;
        int resto = k % 11;
        if ((resto == 0) || (resto == 1) || (resto == 10)) {
            return 1;
        } else {
            return 11 - resto;
        }
    }

    /**     
     * ver documentacao do banco para saber qual a ordem deste campo     
     */
    private String getCampo4() {
        String campo = getNumero() + String.valueOf(boleto.getMoeda()) +
                boleto.getFatorVencimento() + boleto.getValorTitulo() + getCampoLivre();

        return String.valueOf(D3(campo));
    }

    /**
     * ver documentacao do banco para saber qual a ordem deste campo
     */
    private String getCampo5() {
        String campo = boleto.getFatorVencimento() + boleto.getValorTitulo();
        return campo;
    }

    /**
     * Metodo para monta o desenho do codigo de barras
     * A ordem destes campos tambem variam de banco para banco, entao e so olhar na documentacao do seu banco
     * qual a ordem correta
     */
    @Override
    public String getCodigoBarras() {

        return getNumero() + String.valueOf(boleto.getMoeda()) + getCampo4() + getCampo5() + getCampoLivre();

    }

    /**
     * Metodo que concatena os campo para formar a linha digitavel
     * E necessario tambem olhar a documentacao do banco para saber a ordem correta a linha digitavel
     */
    @Override
    public String getLinhaDigitavel() {
        return getCampo1().substring(0, 5) + "." + getCampo1().substring(5) + "  " +
                getCampo2().substring(0, 5) + "." + getCampo2().substring(5) + "  " +
                getCampo3().substring(0, 5) + "." + getCampo3().substring(5) + "  " +
                getCampo4() + "  " + getCampo5();
    }

    @Override
    public String getCarteiraFormatted() {
        return boleto.getCarteira();
    }

    @Override
    public String getAgenciaCodCedenteFormatted() {
        return new Integer(boleto.getAgencia().trim()) + " / " + new Long(boleto.getContaCorrente().trim()) + " " + boleto.getDvContaCorrente();
    }
    /*
    function DigitoNN(Valor: String) : string;
    var
    Contador, Digito, Soma, Multiplo10 : Integer;
    Auxiliar : String;
    Peso : Array[1..10] of Integer;
    begin
    Auxiliar := '';
    Peso[1]  := 7;
    Peso[2]  := 3;
    Peso[3]  := 1;
    Peso[4]  := 9;
    Peso[5]  := 7;
    Peso[6]  := 3;
    Peso[7]  := 1;
    Peso[8]  := 9;
    Peso[9]  := 7;
    Peso[10] := 3;

    Soma := 0;

    for Contador := Length(Valor) downto 1 do
    begin
    Auxiliar := IntToStr((StrToInt(Valor[Contador]) * Peso[Contador]));
    Soma := Soma + StrToInt(Copy(Auxiliar, Length(Auxiliar), 1));
    end;

    Multiplo10 := Soma mod 10;

    if Multiplo10 = 0 then
    Digito := 0
    else
    Digito := 10 - StrToInt((Copy(IntToStr(Soma), Length(Auxiliar), 1)));

    Result := IntToStr(Digito);
    end;*/

    private String getDvNossoNumero() {
        String s = new Integer(boleto.getAgencia()) + boleto.getNossoNumero();
        String c = "7319731973";

        int val = 0;
        String aux = null;
        for (int i = s.length() - 1; i >= 0; i--) {
            aux = String.valueOf(new Integer(s.substring(i, i + 1)) * new Integer(c.substring(i, i + 1)));

            val += new Integer(aux.substring(aux.length() - 1));
        }

        int resto = val % 10;
        if (resto == 0) {
            return "0";
        } else {
            return String.valueOf(10 - Integer.parseInt(String.valueOf(val).substring(aux.length() - 1, aux.length())));
        }
    }

    @Override
    public String getNossoNumeroFormatted() {
        return new Integer(boleto.getAgencia()) + " " + boleto.getNossoNumero() + " " + getDvNossoNumero();
    }
}