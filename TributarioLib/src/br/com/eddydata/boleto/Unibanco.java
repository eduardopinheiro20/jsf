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

/**
 * Classe para gerar o boleto do unibanco
 * @author Fabio Souza
 */
public class Unibanco extends Banco {
    /**
     * Metdodo responsavel por resgatar o numero do banco, coloque no return o codigo do seu banco
     */
    public String getNumero() {
        return "409";
    }
    
    /**
     * Método paticular do unibanco
     */
    public String getCvt() {
        return "5";
    }
    
    /**
     * Método paticular do unibanco
     */
    public String getZero() {
        return "00";
    }
    
    /**
     * Metodo para gerar o digito verificador do nosso numero.
     * o parametro deve conter somente numeros
     */
    public String getDvNossoNumero() {
        return boleto.getModulo11(String.valueOf(Integer.parseInt(boleto.getNossoNumero())), 9);
    }
    
    public String getNossoNumeroComDv() {
        return boleto.getNossoNumero() + getDvNossoNumero();
    }
    
    /**
     * Metodo que monta o primeiro campo do codigo de barras
     * Este campo como os demais e feito a partir do da documentacao do banco
     * A documentacao do banco tem a estrutura de como monta cada campo, depois disso
     * é só concatenar os campos como no exemplo abaixo.
     */
    private String getCampo1() {
        String campo = getNumero() + String.valueOf(boleto.getMoeda()) + getCvt() + boleto.getCodCliente().substring(0,4);
        
        return boleto.getDigitoCampo(campo,2);
    }
    
    /**
     * ver documentacao do banco para saber qual a ordem deste campo
     */
    private String getCampo2() {
        String campo = boleto.getCodCliente().substring(4) + getZero() + getNossoNumeroComDv().substring(0,5);
        
        return boleto.getDigitoCampo(campo,1);
    }
    
    /**
     * ver documentacao do banco para saber qual a ordem deste campo
     */
    private String getCampo3() {
        String campo = getNossoNumeroComDv().substring(5);
        
        return boleto.getDigitoCampo(campo,1);
    }
    
    /**
     * ver documentacao do banco para saber qual a ordem deste campo
     */
    private String getCampo4() {
        String campo = 	getNumero() + String.valueOf(boleto.getMoeda()) +
                boleto.getFatorVencimento() + boleto.getValorTitulo() + getCvt() + boleto.getCodCliente() +
                getZero() + String.valueOf(boleto.getNossoNumero()) + getDvNossoNumero();
        
        return boleto.getDigitoCodigoBarras(campo);
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
    public String getCodigoBarras() {
        return getNumero() + String.valueOf(boleto.getMoeda()) + String.valueOf(getCampo4()) + String.valueOf(getCampo5()) + getCvt() + boleto.getCodCliente() + getZero() + boleto.getNossoNumero() + getDvNossoNumero();
    }
    
    /**
     * Metodo que concatena os campo para formar a linha digitavel
     * E necessario tambem olhar a documentacao do banco para saber a ordem correta a linha digitavel
     */
    
    public String getLinhaDigitavel() {
        return 	getCampo1().substring(0,5) + "." + getCampo1().substring(5) + "  " +
                getCampo2().substring(0,5) + "." + getCampo2().substring(5) + "  " +
                getCampo3().substring(0,5) + "." + getCampo3().substring(5) + "  " +
                getCampo4() + "  " + getCampo5();
    }
    
    /**
     * Recupera a carteira no padrao especificado pelo banco
     * @author Gladyston Batista/Eac Software
     */
    public String getCarteiraFormatted() {
        return boleto.getCarteira();
    }
    
    /**
     * Recupera a agencia / codigo cedente no padrao especificado pelo banco
     * @author Gladyston Batista/Eac Software
     */
    public String getAgenciaCodCedenteFormatted() {
        return boleto.getAgencia() + " / " + boleto.getContaCorrente() + "-" + boleto.getDvContaCorrente();
    }
    
    /**
     * Recupera o nossoNumero no padrao especificado pelo banco
     * @author Gladyston Batista/Eac Software
     */
    public String getNossoNumeroFormatted() {
        return String.valueOf(Integer.parseInt(boleto.getNossoNumero()));
    }
    
}