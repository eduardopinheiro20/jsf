/*
 * Esta biblioteca e um software livre, que pode ser redistribuido e/ou
 * modificado sob os termos da Licença Publica Geral Reduzida GNU,
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
 * Bancoob
 * 
 * Codigo da Cooperativa = Agencia
 * Codigo do cliente = Conta corrente (composta por dois digitos da modalidade mais o codigo do cliente)
 * Numero do titulo = Nosso numero
 * 
 * @author C�ssio Freitas
 */
public class Bancoob extends Banco {

    @Override
    public String getNumero() {
        return "756";
    }
    
    @Override
    public String getDvNumero() {
        return "-0";
    }

    private String getModalidadeCodCliente() {
        String s = Util.unMask(" ", boleto.getContaCorrente());
        if (s.length() == 10) {
            s = s.substring(0, 2) + s.substring(3);
        }
        return s;
    }

    private String getCampoCodigoBarras() {
        String dvCampo2 = boleto.getDigitoCampo(getCampo2());
        dvCampo2 = dvCampo2.substring(dvCampo2.length() - 1);
        return getNumero() + boleto.getMoeda() + boleto.getFatorVencimento() +
                boleto.getValorTitulo() + boleto.getCarteira() + boleto.getAgencia() +
                getModalidadeCodCliente() + boleto.getNossoNumero() + getDvNossoNumero() + 
                "001";
    }

    @Override
    public String getCodigoBarras() {
        String campo = getCampoCodigoBarras();
        String dvCodigoBarra = boleto.getDigitoCodigoBarras(campo);
        campo = campo.substring(0, 4) + dvCodigoBarra +
                campo.substring(4, campo.length());
        return campo;
    }

    private String getCampo2() {
        return getModalidadeCodCliente() + boleto.getNossoNumero().substring(0, 1);
    }

    private String getDvNossoNumero() {
        String c = "319731973197319731973";

        String s = boleto.getAgencia() + "000" + getModalidadeCodCliente().substring(2) + boleto.getNossoNumero();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            val += new Integer(s.substring(i, i + 1)) * new Integer(c.substring(i, i + 1));
        }

        int resto = val % 11;
        if (resto <= 1) {
           return "0";  
        }
        else{
           int resultado = 11 - resto; 
           return String.valueOf(resultado);
        }
            
//        int resultado = 11 - resto;
//        if (resultado <= 1) {
//            return "0";
//        } else {
//            return String.valueOf(resultado);
//        }
    }

    @Override
    public String getLinhaDigitavel() {
        String campo1 = getNumero() + boleto.getMoeda() + boleto.getCarteira() +
                boleto.getAgencia();
        campo1 = boleto.getDigitoCampo(campo1);

        String campo2 = getCampo2();
        campo2 = boleto.getDigitoCampo(campo2);

        String campo3 = boleto.getNossoNumero().substring(1) + getDvNossoNumero() + "001";
        campo3 = boleto.getDigitoCampo(campo3);

        String dvCodigoBarra = boleto.getDigitoCodigoBarras(getCampoCodigoBarras());

        String campo4 = boleto.getFatorVencimento() + boleto.getValorTitulo();

        return campo1 + campo2 + campo3 + dvCodigoBarra + campo4;
    }

    @Override
    public String getCarteiraFormatted() {
        return boleto.getCarteira();
    }

    @Override
    public String getAgenciaCodCedenteFormatted() {
        String codCliente = Util.mask("#######-#", "0" + getModalidadeCodCliente().substring(2));
        return boleto.getAgencia() + " / " + codCliente;
    }

    @Override
    public String getNossoNumeroFormatted() {
        return boleto.getNossoNumero() + " " + getDvNossoNumero();
    }
}