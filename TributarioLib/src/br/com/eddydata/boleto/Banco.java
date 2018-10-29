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

/**
 * @author Flavio Brasil - Modificado por Cassio Freitas
 */
public abstract class Banco {
    protected BoletoBean boleto;
    
    public void setBoletoBean(BoletoBean boleto) {
        this.boleto = boleto;
    }
    
    public BoletoBean getBoletoBean() {
        return this.boleto;
    }
    
    public String getDvNumero() {
        return null;
    }
    
    /**
     * Recupera o codigo do banco
     */
    public abstract String getNumero();

    /**
     * Recupera o numero necessario para a geracao do codigo de barras
     */
    public abstract String getCodigoBarras();

    /**
     * Recupera a numeracao para a geracao da linha digitavel do boleto
     */
    public abstract String getLinhaDigitavel();

    /**
     * @Return Recupera a carteira no padrao especificado pelo banco
     * @author Gladyston Batista/Eac Software
     */
    public abstract String getCarteiraFormatted();

    /**
     * @Return Recupera a agencia/codigo cedente no padrao especificado pelo banco
     * @author Gladyston Batista/Eac Software
     */
    public abstract String getAgenciaCodCedenteFormatted();

    /**
     * @Return Recupera o nosso numero no padrao especificado pelo banco
     * @author Gladyston Batista/Eac Software
     */
    public abstract String getNossoNumeroFormatted();
}