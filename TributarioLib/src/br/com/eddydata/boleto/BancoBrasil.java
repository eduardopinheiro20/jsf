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
 * Classe responsavel em criar os campos do Banco do Brasil
 * @author Fabio Souza
 */
public class BancoBrasil extends Banco {
    @Override
    public String getNumero() {
        return "001";
    }

    private String getCampoLivre() {
        if (boleto.getNumConvenio() == null || boleto.getNumConvenio().trim().isEmpty()) {
            throw new IllegalArgumentException("Boleto sem n�mero de conv�nio.");
        }
        
        if (boleto.getCarteira() == null || boleto.getCarteira().trim().isEmpty()) {
            throw new IllegalArgumentException("Boleto sem n�mero da carteira.");
        }
        
        String campo = "000000" + boleto.getNumConvenio() + boleto.getNossoNumero() + boleto.getCarteira();

        return campo;
    }

    private String getCampo1() {

        String campo = getNumero() + boleto.getMoeda() + getCampoLivre().substring(0, 5);
        return boleto.getDigitoCampo(campo, 2);
        
    }

    private String getCampo2() {
        String campo = getCampoLivre().substring(5, 15);// + boleto.getAgencia();

        return boleto.getDigitoCampo(campo, 1);
    }

    private String getCampo3() {
        String campo = getCampoLivre().substring(15);

        return boleto.getDigitoCampo(campo, 1);
    }

    private String getCampo4() {
        String campo = getNumero() + String.valueOf(boleto.getMoeda()) +
                boleto.getFatorVencimento() + boleto.getValorTitulo() + getCampoLivre();

        return boleto.getDigitoCodigoBarras(campo);
    }

    private String getCampo5() {
        String campo = boleto.getFatorVencimento() + boleto.getValorTitulo();
        return campo;
    }

    @Override
    public String getCodigoBarras() {

        String campo = getNumero() + String.valueOf(boleto.getMoeda()) + getCampo4() +
                boleto.getFatorVencimento() + boleto.getValorTitulo() + getCampoLivre();
//        System.out.println(campo);
        return campo;
    }

    @Override
    public String getLinhaDigitavel() {
        
        return getCampo1().substring(0, 5) + "." + getCampo1().substring(5) + "  " +
                getCampo2().substring(0, 5) + "." + getCampo2().substring(5) + "  " +
                getCampo3().substring(0, 5) + "." + getCampo3().substring(5) + "  " +
                getCampo4() + "  " + getCampo5();
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
        return boleto.getAgencia()+ "-"+ boleto.getDvAgencia() + " / " + boleto.getContaCorrente() + "-" + boleto.getDvContaCorrente();
    }

    /**
     * Recupera o nossoNumero no padrao especificado pelo banco
     * @author Gladyston Batista/Eac Software
     */
    @Override
    public String getNossoNumeroFormatted() {
        return boleto.getNumConvenio()+boleto.getNossoNumero();
    }
}