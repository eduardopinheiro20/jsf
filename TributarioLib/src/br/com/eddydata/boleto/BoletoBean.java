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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.text.DefaultFormatter;
import javax.swing.text.NumberFormatter;

/**
 * @author Fabio Souza
 * @version $Id$
 * Classe alterada por Flavio Brasil
 *
 */
public class BoletoBean {

    private String agencia;
    private String dvAgencia;
    private String contaCorrente;
    private String dvContaCorrente;
    private String moeda = "9";
    private String carteira;
    private String numConvenio;
    private String nossoNumero;
    private String dvNossoNumero;
    private String dataVencimento;
    private String dataDocumento;
    private String dataProcessamento;
    private String valorBoleto;
    private String caminho;
    private String tipoSaida;
    private String localPagamento;
    private String localPagamento2;
    private String cedente;
    private String qtdMoeda;
    private String valorMoeda;
    private String acrescimo;
    private String instrucao1;
    private String instrucao2;
    private String instrucao3;
    private String instrucao4;
    private String instrucao5;
    private String nomeSacado;
    private String cpfSacado;
    private String enderecoSacado;
    private String cepSacado;
    private String bairroSacado;
    private String cidadeSacado;
    private String ufSacado;
    private String especieDocumento;
    private String especie = "R$";
    private String aceite = "N";
    private String linhaDigitavel;
    private String codigoBarras;
    private String codCliente;
    private String ios;
    private String noDocumento;
    private String codigoOperacao;
    private String codigoFornecidoAgencia;
    private String dvCodigoFornecidoAgencia;
    private String imagemMarketing;
    private String enderecoCodBar;
    private String documento;
    private String parcela;
    private String inscricao;
    private String corpo_capa;
    private Integer exercicio;
    private String atividade;
    private String atividade_primaria;
    private String contribuinte;

    public BoletoBean() {
    }

    /**
     * @return the enderecoCodBar
     */
    public String getEnderecoCodBar() {

        return enderecoCodBar;
    }

    /**
     * @param enderecoCodBar the enderecoCodBar to set
     */
    public void setEnderecoCodBar(String enderecoCodBar) {

        this.enderecoCodBar = enderecoCodBar;
    }

    /**
     * Método para gerar o fator de vencimento do boleto
     * 
     * @return long
     */
    public long getFatorVencimento() {

        String[] data = getDataVencimento().split("/");
        String dia = data[0];
        String mes = data[1];
        String ano = data[2];

        Calendar dataBase = new GregorianCalendar(1997, Calendar.OCTOBER, 7);
        Calendar vencimento = new GregorianCalendar(Integer.parseInt(ano), Integer.parseInt(mes) - 1, Integer.parseInt(dia));
        long diferenca = vencimento.getTimeInMillis() - dataBase.getTimeInMillis();

        long diferencaDias = diferenca / (24 * 60 * 60 * 1000);

        return diferencaDias;
    }

    /**
     * Retorna o numero da agencia.
     * Completar com zeros a esquerda quando necessario
     * @return Retorna o numero da agencia.
     */
    public String getAgencia() {
        return agencia;
    }

    /**
     * Seta o numero da agencia (ex. 2971).
     * @param agencia
     * Seta o numero da agencia (ex. 2971).
     */
    public void setAgencia(String agencia) {
        this.agencia = agencia != null ? agencia.trim() : agencia;
    }

    /**
     * Retorna o numero da carteira.
     * Itau = 3 digitos
     * Bradesco = 2 digitos
     * Bancoob = 1 digito
     * @return Retorna o numero da carteira.
     */
    public String getCarteira() {
        return carteira;
    }

    /**
     * Seta a carteira para o titulo (ex. 175. Para outros tipos veja com seu banco).
     * @param carteira
     * Seta a carteira para o titulo (ex. 175. Para outros tipos veja com seu banco).
     */
    public void setCarteira(String carteira) {
        this.carteira = carteira != null ? carteira.trim() : null;
    }

    /**
     * Retorna o numero da conta corrente.
     * @return Retorna o numero da conta corrente.
     */
    public String getContaCorrente() {
        return contaCorrente;
    }

    /**
     * Seta o numero da conta corrente - Coloque zeros a esquerda quando necessario
     * @param contaCorrente
     * Seta o numero da conta corrente - Coloque zeros a esquerda quando necessario
     */
    public void setContaCorrente(String contaCorrente) {
        this.contaCorrente = contaCorrente.trim();
    }

    /**
     * Retorna o nosso numero.
     * @return Retorna o nosso numero.
     */
    public String getNossoNumero() {
        return nossoNumero;
    }

    /**
     * Seta o nosso numero.
     * @param nossoNumero
     * @param qtdDigitos - Quantidade de digitos que contem o campo nosso numero referente ao seu banco
     * Seta o nosso numero.
     */
    public void setNossoNumero(String nossoNumero, int qtdDigitos) {
        nossoNumero = nossoNumero.trim();
        int dif = qtdDigitos - nossoNumero.length();
        if (dif < 0) {
            throw new IllegalArgumentException("Quantidade de d�gitos do nosso n�mero maior do que definido!");
        }
        StringBuffer sb = new StringBuffer(dif);
        for (int i = 0; i < dif; i++) {
            sb.append('0');
        }
        this.setNossoNumero(sb + nossoNumero);
    }

    /**
     * Retorna a data do vencimento do titulo.
     * @return Retorna a data do vencimento do titulo.
     */
    public String getDataVencimento() {
        return dataVencimento;
    }

    /**
     * Seta a data de vencimento do titulo (ex. 21/06/2005).
     * @param dataVencimento
     * Seta a data de vencimento do titulo (ex. 21/06/2005).
     */
    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    /**
     * Retorna o valor do titulo.
     * @return Retorna o valor do titulo.
     */
    public String getValorBoleto() {
        return valorBoleto;
    }

    /**
     * Seta o valor do titulo (ex. 23.45 ou 1234.45).
     * @param valorBoleto
     * Seta o valor do titulo (ex. 23.45 ou 1234.45).
     */
    public void setValorBoleto(double valorBoleto) {
        this.valorBoleto = new BigDecimal(valorBoleto).toString();
    }

    /**
     * Retorna o caminho onde o PDF foi salvo.
     * @return Retorna o caminho onde o PDF foi salvo.
     */
    public String getCaminho() {
        return caminho;
    }

    /**
     * Seta o caminho onde o arquivo devera ser salvo. (ex.: /home/fabio/boleto-05-10-2005.pdf
     * @param caminho
     * Seta o caminho onde o arquivo devera ser salvo. (ex.: /home/fabio/boleto-05-10-2005.pdf
     */
    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    /**
     * Retorna o tipo da moeda.
     * @return Retorna o tipo da moeda.
     */
    public String getMoeda() {
        return moeda;
    }

    /**
     * Retorna o acrescimo fornecido ao boleto.
     * @return Retorna o acrescimo fornecido ao boleto.
     */
    public String getAcrescimo() {
        return acrescimo;
    }

    /**
     * Seta o acrescimo ao boleto.
     * @param acrescimo
     * Seta o acrescimo ao boleto.
     */
    public void setAcrescimo(String acrescimo) {
        this.acrescimo = acrescimo;
    }

    /**
     * Retorna o bairro do sacado.
     * @return Retorna o bairro do sacado.
     */
    public String getBairroSacado() {
        return bairroSacado;
    }

    /**
     * Seta o bairro do sacado.
     * @param bairroSacado
     * Seta o bairro do sacado.
     */
    public void setBairroSacado(String bairroSacado) {
        this.bairroSacado = bairroSacado;
    }

    /**
     * Retorna o nome do cedente.
     * @return Retorna o nome do cedente.
     */
    public String getCedente() {
        return cedente;
    }

    /**
     * Seta o nome do cedente.
     * @param cedente
     * Seta o nome do cedente.
     */
    public void setCedente(String cedente) {
        this.cedente = cedente;
    }

    /**
     * Retorna o cep do sacado.
     * @return Retorna o cep do sacado.
     */
    public String getCepSacado() {
        return cepSacado;
    }

    /**
     * Seta o cep do sacado.
     * @param cepSacado
     * Seta o cep do sacado.
     */
    public void setCepSacado(String cepSacado) {
        this.cepSacado = cepSacado;
    }

    /**
     * Retorna a cidade do sacado.
     * @return Retorna a cidade do sacado.
     */
    public String getCidadeSacado() {
        return cidadeSacado;
    }

    /**
     * Seta a cidade do sacado.
     * @param cidadeSacado
     * Seta a cidade do sacado.
     */
    public void setCidadeSacado(String cidadeSacado) {
        this.cidadeSacado = cidadeSacado;
    }

    /**
     * Retorna o cpf ou cnpj do sacado.
     * @return Retorna o cpf ou cnpj do sacado.
     */
    public String getCpfSacado() {
        return cpfSacado;
    }

    /**
     * Seta o cpf ou cnpj do sacado.
     * @param cpfSacado
     * Seta o cpf ou cnpj do sacado.
     */
    public void setCpfSacado(String cpfSacado) {
        this.cpfSacado = cpfSacado;
    }

    /**
     * Retorna o endereco do sacado.
     * @return Retorna o endereco do sacado.
     */
    public String getEnderecoSacado() {
        return enderecoSacado;
    }

    /**
     * Seta o endereco do sacado.
     * @param enderecoSacado
     * Seta o endereco do sacado.
     */
    public void setEnderecoSacado(String enderecoSacado) {
        this.enderecoSacado = enderecoSacado;
    }

    /**
     * Retorna o campo para a 1 linha da instrucao.
     * @return Retorna o campo para a 1 linha da instrucao.
     */
    public String getInstrucao1() {
        return instrucao1;
    }

    /**
     * Seta o campo para a 1 linha da instrucao.
     * @param instrucao1
     * Seta o campo para a 1 linha da instrucao.
     */
    public void setInstrucao1(String instrucao1) {
        this.instrucao1 = instrucao1;
    }

    /**
     * Retorna o campo para a 2 linha da instrucao.
     * @return Retorna o campo para a 2 linha da instrucao.
     */
    public String getInstrucao2() {
        return instrucao2;
    }

    /**
     * Seta o campo para a 2 linha da instrucao.
     * @param instrucao2
     * Seta o campo para a 2 linha da instrucao.
     */
    public void setInstrucao2(String instrucao2) {
        this.instrucao2 = instrucao2;
    }

    /**
     * Retorna o campo para a 3 linha da instrucao.
     * @return Retorna o campo para a 3 linha da instrucao.
     */
    public String getInstrucao3() {
        return instrucao3;
    }

    /**
     * Seta o campo para a 3 linha da instrucao.
     * @param instrucao3
     * Seta o campo para a 3 linha da instrucao.
     */
    public void setInstrucao3(String instrucao3) {
        this.instrucao3 = instrucao3;
    }

    /**
     * Retorna o campo para a 4 linha da instrucao.
     * @return Retorna o campo para a 4 linha da instrucao.
     */
    public String getInstrucao4() {
        return instrucao4;
    }

    /**
     * seta o campo para a 4 linha da instrucao.
     * @param instrucao4
     * seta o campo para a 4 linha da instrucao.
     */
    public void setInstrucao4(String instrucao4) {
        this.instrucao4 = instrucao4;
    }

    /**
     * Retorna o campo para a 5 linha da instrucao.
     * @return Retorna o campo para a 5 linha da instrucao.
     */
    public String getInstrucao5() {
        return instrucao5;
    }

    /**
     * Seta o campo para a 5 linha da instrucao.
     * @param instrucao5
     * Seta o campo para a 5 linha da instrucao.
     */
    public void setInstrucao5(String instrucao5) {
        this.instrucao5 = instrucao5;
    }

    /**
     * Retorna o local de pagamento.
     * @return Retorna o local de pagamento.
     */
    public String getLocalPagamento() {
        return localPagamento;
    }

    /**
     * Seta o local de pagamento.
     * @param localPagamento
     * Seta o local de pagamento.
     */
    public void setLocalPagamento(String localPagamento) {
        this.localPagamento = localPagamento;
    }

    /**
     * Retorna o nome do sacado.
     * @return Retorna o nome do sacado.
     */
    public String getNomeSacado() {
        return nomeSacado;
    }

    /**
     * Seta o nome do sacado.
     * @param nomeSacado
     * Seta o nome do sacado.
     */
    public void setNomeSacado(String nomeSacado) {
        this.nomeSacado = nomeSacado;
    }

    /**
     * Retorna a quantidade da moeda.
     * @return Retorna a quantidade da moeda.
     */
    public String getQtdMoeda() {
        return qtdMoeda;
    }

    /**
     * Seta a quantidade da moeda.
     * @param qtdMoeda
     * Seta a quantidade da moeda.
     */
    public void setQtdMoeda(String qtdMoeda) {
        this.qtdMoeda = qtdMoeda;
    }

    /**
     * Retorna o uf do sacado.
     * @return Retorna o uf do sacado.
     */
    public String getUfSacado() {
        return ufSacado;
    }

    /**
     * Seta o Uf do sacado.
     * @param ufSacado
     * Seta o Uf do sacado.
     */
    public void setUfSacado(String ufSacado) {
        this.ufSacado = ufSacado;
    }

    /**
     * Retorna o valor da moeda
     * @return Retorna o valor da moeda
     */
    public String getValorMoeda() {
        return valorMoeda;
    }

    /**
     * Seta o valor da moeda.
     * @param valorMoeda
     * Seta o valor da moeda.
     */
    public void setValorMoeda(String valorMoeda) {
        this.valorMoeda = valorMoeda;
    }

    /**
     * Retorna a segunda linha do local de pagamento
     * @return Retorna a segunda linha do local de pagamento
     */
    public String getLocalPagamento2() {
        return localPagamento2;
    }

    /**
     * Seta a segunda linha do local de pagamento.
     * @param localPagamento2
     * Seta a segunda linha do local de pagamento.
     */
    public void setLocalPagamento2(String localPagamento2) {
        this.localPagamento2 = localPagamento2;
    }

    /**
     * Retorna o campo aceite que por padrao vem com N.
     * @return Retorna o campo aceite que por padrao vem com N.
     */
    public String getAceite() {
        return aceite;
    }

    /**
     * Seta o campo aceite que por padrao vem com N.
     * @param aceite
     * Seta o campo aceite que por padrao vem com N.
     */
    public void setAceite(String aceite) {
        this.aceite = aceite != null ? aceite.trim() : null;
    }

    /**
     * Retorna o campo especie do documento que por padrao vem com DV
     * @return Retorna o campo especie do documento que por padrao vem com DV
     */
    public String getEspecieDocumento() {
        return especieDocumento;
    }

    /**
     * Seta o campo especie do documento que por padrao vem com DV.
     * @param especieDocumento
     * Seta o campo especie do documento que por padrao vem com DV.
     */
    public void setEspecieDocumento(String especieDocumento) {
        this.especieDocumento = especieDocumento;
    }

    /**
     * Retorna a data do documento.
     * @return Retorna a data do documento.
     */
    public String getDataDocumento() {
        return dataDocumento;
    }

    /**
     * Seta a data do documento.
     * @param dataDocumento
     * Seta a data do documento.
     */
    public void setDataDocumento(String dataDocumento) {
        this.dataDocumento = dataDocumento;
    }

    /**
     * Retorna a data do processamento
     * @return Retorna a data do processamento.
     */
    public String getDataProcessamento() {
        return dataProcessamento;
    }

    /**
     * Seta a data do processamento.
     * @param dataProcessamento
     * Seta a data do processamento.
     */
    public void setDataProcessamento(String dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    /**
     * Seta o tipo da moeda
     * @param moeda
     * Seta o tipo da moeda.
     */
    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    /**
     * Retorna tipo de saida do arquivo
     * @return Retorna tipo de saida do arquivo
     */
    public String getTipoSaida() {
        return tipoSaida;
    }

    /**
     * Seta o tipo de saida do arquivo (html ou pdf)
     * @param tipoSaida
     * Seta o tipo de saida do arquivo (html ou pdf)
     */
    public void setTipoSaida(String tipoSaida) {
        this.tipoSaida = tipoSaida;
    }

    /**
     * Retorna o número digitável do código de barras
     * @return Retorna o número digitável do código de barras
     */
    public String getLinhaDigitavel() {
        return linhaDigitavel;
    }

    /**
     * Seta o número digitável do código de barras
     * @param codigoBarrasDividido
     * Seta o número digitável do código de barras
     */
    public void setLinhaDigitavel(String linhaDigitavel) {
        this.linhaDigitavel = linhaDigitavel;
    }

    /**
     * Retorna o número do código de barras
     * @return Retorna o número do código de barras
     */
    public String getCodigoBarras() {
        return codigoBarras;
    }

    /**
     * Seta o número do código de barras
     * @param codigoBarrasDividido
     * Seta o número do código de barras
     */
    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    /**
     * Retorna o quinto campo da linha digitavel do codigo
     * @return Retorna o quinto campo da linha digitavel do codigo
     */
    public String getValorTitulo() {

        String zeros = "0000000000";
        DefaultFormatter formatter = new NumberFormatter(new DecimalFormat("#,##0.00"));

        String valor = "";

        try {

            valor = formatter.valueToString(new Double(getValorBoleto()));
        } catch (Exception ex) {
        }

        valor = valor.replace(",", "").replace(".", "");
        String valorTitulo = zeros.substring(0, zeros.length() - valor.length()) + valor;

        return valorTitulo;
    }

    /**
     * Modulo 10 (212121)\n
     * Retorna o digito verificador de cada campo da linha digitavel. Voce deve passar como parametro a string do campo conforme o seu banco.
     * @return Retorna o digito verificador de cada campo da linha digitavel. Voce deve passar como parametro a string do campo conforme o seu banco.
     */
    public String getDigitoCampo(String campo, int mult) {
        //Esta rotina faz o calcula 212121

        int multiplicador = mult;
        int multiplicacao = 0;
        int soma_campo = 0;

        for (int i = 0; i < campo.length(); i++) {
            multiplicacao = Integer.parseInt(campo.substring(i, 1 + i)) * multiplicador;

            if (multiplicacao >= 10) {
                multiplicacao = Integer.parseInt(String.valueOf(multiplicacao).substring(0, 1)) + Integer.parseInt(String.valueOf(multiplicacao).substring(1));
            }

            soma_campo = soma_campo + multiplicacao;

            // ALTERADO POR VITOR MOTTA PARA SUBSTITUIR O COMENTARIO ABAIXO
            // valores assumidos: 212121...
            multiplicador = (multiplicador % 2) + 1;
        /*
        if (multiplicador == 2)
        multiplicador = 1;
        else
        multiplicador = 2;
         */

        }
        int dac = 10 - (soma_campo % 10);

        if (dac == 10) {
            dac = 0;
        }
        campo = campo + String.valueOf(dac);

        return campo;
    }

    /**
     * ALTERADO POR VITOR MOTTA - METODO ALTERNATIVO AO METODO getDigitoCampo(String, int)
     * Modulo 10 (212121)\n
     * Retorna o digito verificador de cada campo da linha digitavel. Voce deve passar como parametro a string do campo conforme o seu banco.\n
     * Nao tem a necessidade do mult e possui o mesmo efeito que o m�todo getDigitoCampo(String, int)
     * @return Retorna o digito verificador de cada campo da linha digitavel. Voce deve passar como parametro a string do campo conforme o seu banco.
     */
    public String getDigitoCampo(String campo) {
        //Esta rotina faz o calcula 212121

        int multiplicador = 2;
        int multiplicacao = 0;
        int soma_campo = 0;

        for (int i = campo.length(); i > 0; i--) {
            multiplicacao = Integer.parseInt(campo.substring(i - 1, i)) * multiplicador;

            if (multiplicacao >= 10) {
                multiplicacao = Integer.parseInt(String.valueOf(multiplicacao).substring(0, 1)) + Integer.parseInt(String.valueOf(multiplicacao).substring(1));
            }
            soma_campo += multiplicacao;

            // valores assumidos: 212121...
            multiplicador = (multiplicador % 2) + 1;
        }
        int dac = 10 - (soma_campo % 10);

        if (dac == 10) {
            dac = 0;
        }
        campo = campo + String.valueOf(dac);

        return campo;
    }

    /**
     * Modulo 11
     * Retorna o digito verificador do codigo de barras (4o campo da linha digitavel. Voce deve passar como parametro a string do campo conforme o seu banco.
     * @return Modulo 11 - Retorna o digito verificador do codigo de barras (4o campo da linha digitavel. Voce deve passar como parametro a string do campo conforme o seu banco.
     */
    public String getDigitoCodigoBarras(String campo) {
        //Esta rotina faz o calcula no modulo 11 - 23456789

        int multiplicador = 4;
        int multiplicacao = 0;
        int soma_campo = 0;

        for (int i = 0; i < campo.length(); i++) {
            multiplicacao = Integer.parseInt(campo.substring(i, 1 + i)) * multiplicador;

            soma_campo = soma_campo + multiplicacao;

            // ALTERADO POR VITOR MOTTA PARA SUBSTITUIR O COMENTARIO ABAIXO
            // valores assumidos: 43298765432987...
            multiplicador = ((multiplicador + 5) % 8) + 2;
        /*
        if (multiplicador == 4)
        multiplicador = 3;
        
        else if (multiplicador == 3)
        multiplicador = 2;
        
        else if (multiplicador == 2)
        multiplicador = 9;
        
        else if (multiplicador == 9)
        multiplicador = 8;
        
        else if (multiplicador == 8)
        multiplicador = 7;
        
        else if (multiplicador == 7)
        multiplicador = 6;
        
        else if (multiplicador == 6)
        multiplicador = 5;
        
        else if (multiplicador == 5)
        multiplicador = 4;
         */
        }

        int dac = 11 - (soma_campo % 11);

        if (dac == 0 || dac == 1 || dac > 9) {
            dac = 1;
        }
        campo = String.valueOf(dac);

        return campo;
    }

    /**
     * Retorna o codigo do cliente. Caseo seja codigo do cliente da caixa este campo deve conter 6 posicoes.
     * @return Retorna o codigo do cliente.
     */
    public String getCodCliente() {
        return codCliente;
    }

    /**
     * Seta um codigo de cliente. Alguns bancos pedem este campo.
     * @param codCliente
     * Seta um codigo de cliente. Alguns bancos pedem este campo
     */
    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente != null ? codCliente.trim() : null;
    }

    /**
     * Retorna o IOS do Banco.
     * @return Retorna o IOS do Banco.
     */
    public String getIOS() {
        return getIos();
    }

    /**
     * Seta um codigo de IOS do Banco.
     * @param codCliente
     * Seta um codigo de IOS do Banco.
     */
    public void setIOS(String ios) {
        this.setIos(ios);
    }

    /**
     * Retorna o numero do convenio
     * @return Retorna o numero do convenio
     */
    public String getNumConvenio() {
        return numConvenio;
    }

    /**
     * Seta o numero do convenio
     * @param numConvenio
     * Seta o numero do convenio
     */
    public void setNumConvenio(String numConvenio) {
        this.numConvenio = numConvenio;
    }

    public String getDvAgencia() {
        return dvAgencia;
    }

    public void setDvAgencia(String dvAgencia) {
        this.dvAgencia = dvAgencia != null ? dvAgencia.trim() : null;
    }

    public String getDvContaCorrente() {
        return dvContaCorrente;
    }

    public void setDvContaCorrente(String dvContaCorrente) {
        this.dvContaCorrente = dvContaCorrente != null ? dvContaCorrente.trim() : null;
    }

    public void setNossoNumero(String nossoNumero) {
        this.nossoNumero = nossoNumero != null ? nossoNumero.trim() : null;
    }

    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
    }

    /**
     * recupera o codigo de operacao
     * @return Retorna o codigo de operacao
     */
    public String getCodigoOperacao() {
        return codigoOperacao;
    }

    /**
     * Seta o codigo de operacao - necessario somente para caixa
     * @param codigoOperacao
     * Seta o codigo da operacao
     */
    public void setCodigoOperacao(String codigoOperacao) {
        this.codigoOperacao = codigoOperacao;
    }

    /**
     * Recupera o codigo fornecido pela agencia
     * @return Recupera o codigo fornecido pela agencia
     */
    public String getCodigoFornecidoAgencia() {
        return codigoFornecidoAgencia;
    }

    /**
     * Seta o codigo fornecido pela agencia - necessario somente para caixa.
     * @param codigoFornecidoAgencia
     * Seta o codigo fornecido pela agencia
     */
    public void setCodigoFornecidoAgencia(String codigoFornecidoAgencia) {
        this.codigoFornecidoAgencia = codigoFornecidoAgencia;
    }

    /**
     * Recupara o numero do documento
     * @return Recupera o numero do documento
     */
    public String getNoDocumento() {
        return noDocumento;
    }

    /**
     * Seta o numero do documento
     * @param noDocumento
     * Seta o numero do documento
     */
    public void setNoDocumento(String noDocumento) {
        this.noDocumento = noDocumento;
    }

    /**
     * Completa uma String com zeros a esquerda
     * @param str String a ser completada
     * @param qtdZeros Quantidade de zeros que deseja a esquerda
     * Completa uma String com zeros a esquerda
     */
    public String completaZerosEsquerda(String str, int qtdZeros) {
        String zeros = "000000000000000000000000000000000000000";
        int dif = qtdZeros - str.length();

        return zeros.substring(0, dif) + str;
    }

    /**
     * Pega o caminho da imagem de marketing para ser colocada na parte superior do boleto
     */
    public String getImagemMarketing() {
        return imagemMarketing;
    }

    /**
     * Seta a imagem de marketing que ira na parte superior do boleto
     * O tamanho ideal para a imagem é de: 1000x668 px
     * @param imagemMarketing Caminho da imagem
     */
    public void setImagemMarketing(String imagemMarketing) {
        this.imagemMarketing = imagemMarketing;
    }

    /**
     * Recupar o digito verificador do nosso numero
     * @return Recupera o digito verificador do nosso numero
     * @author Gladyston Batista
     */
    public String getDvNossoNumero() {
        return (dvNossoNumero != null) ? dvNossoNumero : "";
    }

    /**
     * Seta o digito verificar do nosso numero
     * @author Gladyston Batista
     */
    public void setDvNossoNumero(String dvNossoNumero) {
        this.dvNossoNumero = dvNossoNumero;
    }

    /**
     * Recupar o digito verificador fornecido pela agencia
     * @return Recupar o digito verificador fornecido pela agencia
     * @author Gladyston Batista
     */
    public String getDvCodigoFornecidoAgencia() {
        return dvCodigoFornecidoAgencia;
    }

    /**
     * Seta o digito verificar do codigo fornecido pela Agencia
     * @author Gladyston Batista
     */
    public void setDvCodigoFornecidoAgencia(String dvCodigoFornecidoAgencia) {
        this.dvCodigoFornecidoAgencia = dvCodigoFornecidoAgencia;
    }

    /**
     * 	getModulo10
     *  @param String value
     * 	@return String dac
     *  @author Mario Grigioni
     */
    public String getModulo10(String campo) {

        int multiplicador = 2;
        int multiplicacao = 0;
        int soma_campo = 0;

        for (int i = campo.length(); i > 0; i--) {
            multiplicacao = Integer.parseInt(campo.substring(i - 1, i)) * multiplicador;

            if (multiplicacao >= 10) {
                multiplicacao = Integer.parseInt(String.valueOf(multiplicacao).substring(0, 1)) + Integer.parseInt(String.valueOf(multiplicacao).substring(1, 2));
            }
            soma_campo = soma_campo + multiplicacao;

            if (multiplicador == 1) {
                multiplicador = 2;
            } else {
                multiplicador = 1;
            }
        }
        int dac = 10 - (soma_campo % 10);

        if (dac > 9) {
            dac = 0;
        }
        return ((Integer) dac).toString();
    }

    /**
     * 	getModulo11
     *  @param String value
     *  @param int type
     * 	@return int dac
     *  @author Mario Grigioni
     */
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
        } else if ((dac == 0 || dac == 1 || dac > 9) && type == 9) {
            dac = 1;
        }
        return ((Integer) dac).toString();
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * @return the parcela
     */
    public String getParcela() {
        return parcela;
    }

    /**
     * @param parcela the parcela to set
     */
    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    /**
     * @return the especie
     */
    public String getEspecie() {
        return especie;
    }

    /**
     * @param especie the especie to set
     */
    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    public String getCorpo_capa() {
        return corpo_capa;
    }

    public void setCorpo_capa(String corpo_capa) {
        this.corpo_capa = corpo_capa;
    }

    public Integer getExercicio() {
        return exercicio;
    }

    public void setExercicio(Integer exercicio) {
        this.exercicio = exercicio;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public String getContribuinte() {
        return contribuinte;
    }

    public void setContribuinte(String contribuinte) {
        this.contribuinte = contribuinte;
    }

    public String getAtividade_primaria() {
        return atividade_primaria;
    }

    public void setAtividade_primaria(String atividade_primaria) {
        this.atividade_primaria = atividade_primaria;
    }

}
