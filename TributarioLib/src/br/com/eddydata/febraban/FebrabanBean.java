package br.com.eddydata.febraban;

import br.com.eddydata.suporte.Util;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author cassio
 */
public class FebrabanBean implements Cloneable {

    private String orgao;
    private String nomeTributo;
    private String inscricaoCadastral;
    private String identificacao;
    private String registro;
    private double valorDocumento;
    private double valorCobrado;
    private String formaPagamento;
    private String parcela;
    private Date vencimento;
    private String locaisPgto;
    private Integer exercicio;
    private String especie;
    private String codigoBarras;
    private String bar1;
    private String bar2;
    private String bar3;
    private String bar4;
    private String linhas;
    private String multa;
    private String juros;

    public double getValorCobrado() {
        return valorCobrado;
    }

    public void setValorCobrado(double valorCobrado) {
        this.valorCobrado = valorCobrado;
    }

    public double getValorDocumento() {
        return valorDocumento;
    }

    public void setValorDocumento(double valorDocumento) {
        this.valorDocumento = valorDocumento;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    /**
     * @return the orgao
     */
    public String getOrgao() {
        return orgao;
    }

    /**
     * @param orgao the orgao to set
     */
    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }

    /**
     * @return the nomeTributo
     */
    public String getNomeTributo() {
        return nomeTributo;
    }

    /**
     * @param nomeTributo the nomeTributo to set
     */
    public void setNomeTributo(String nomeTributo) {
        this.nomeTributo = nomeTributo;
    }

    /**
     * @return the inscricaoCadastral
     */
    public String getInscricaoCadastral() {
        return inscricaoCadastral;
    }

    /**
     * @param inscricaoCadastral the inscricaoCadastral to set
     */
    public void setInscricaoCadastral(String inscricaoCadastral) {
        this.inscricaoCadastral = inscricaoCadastral;
    }

    /**
     * @return the identificacao
     */
    public String getIdentificacao() {
        return identificacao;
    }

    /**
     * @param identificacao the identificacao to set
     */
    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    /**
     * @return the registro
     */
    public String getRegistro() {
        return registro;
    }

    /**
     * @param registro the registro to set
     */
    public void setRegistro(String registro) {
        this.registro = registro;
    }

    /**
     * @return the formaPagamento
     */
    public String getFormaPagamento() {
        return formaPagamento;
    }

    /**
     * @param formaPagamento the formaPagamento to set
     */
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
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
     * @return the locaisPgto
     */
    public String getLocaisPgto() {
        return locaisPgto;
    }

    /**
     * @param locaisPgto the locaisPgto to set
     */
    public void setLocaisPgto(String locaisPgto) {
        this.locaisPgto = locaisPgto;
    }

    /**
     * @return the exercicio
     */
    public Integer getExercicio() {
        return exercicio;
    }

    /**
     * @param exercicio the exercicio to set
     */
    public void setExercicio(Integer exercicio) {
        this.exercicio = exercicio;
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

    /**
     * @return the codigoBarras
     */
    public String getCodigoBarras() {
        return codigoBarras;
    }

    /**
     * @param codigoBarras the codigoBarras to set
     */
    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    /**
     * @return the bar1
     */
    public String getBar1() {
        return bar1;
    }

    /**
     * @param bar1 the bar1 to set
     */
    public void setBar1(String bar1) {
        this.bar1 = bar1;
    }

    /**
     * @return the bar2
     */
    public String getBar2() {
        return bar2;
    }

    /**
     * @param bar2 the bar2 to set
     */
    public void setBar2(String bar2) {
        this.bar2 = bar2;
    }

    /**
     * @return the bar3
     */
    public String getBar3() {
        return bar3;
    }

    /**
     * @param bar3 the bar3 to set
     */
    public void setBar3(String bar3) {
        this.bar3 = bar3;
    }

    /**
     * @return the bar4
     */
    public String getBar4() {
        return bar4;
    }

    /**
     * @param bar4 the bar4 to set
     */
    public void setBar4(String bar4) {
        this.bar4 = bar4;
    }

    /**
     * @return the linhas
     */
    public String getLinhas() {
        return linhas;
    }

    /**
     * @param linhas the linhas to set
     */
    public void setLinhas(String linhas) {
        this.linhas = linhas;
    }
    
    /**
     * C�lculo do DAC (D�gito de Auto-Confer�ncia) de cada bloco
     * @param seq1 -  Sequencia de digitos
     * @return Retorna o DAC
     */
    public static int calcModulo10(String seq0) {
        // converter String em vetor de interios
        char[] charCodigoDeBarras = seq0.toCharArray();
        int[] seq1 = new int[charCodigoDeBarras.length];
        for (int i = 0; i < charCodigoDeBarras.length; i++) {
            seq1[i] = Integer.parseInt(String.valueOf(charCodigoDeBarras[i]));
        }


        int[] seq2 = new int[seq1.length];
        int result = 0;
        boolean flag = false;

        for (int i = seq1.length - 1; i >= 0; i--) {// for para preencher o valor da sequencia 2 conforme o tamanho passado
            if (flag) {
                seq2[i] = 1;
            } else {
                seq2[i] = 2;
            }
            flag = !flag;
        }
        for (int i = 0; i < seq1.length; i++) {// multiplicado um vetor pelo outro
            int temp = seq1[i] * seq2[i];
            if (temp >= 10) {// se for maior que 10 tem que quebrar
                result += temp / 10 + temp % 10;
            } else {
                result += temp;
            }
        }
        result = 10 - (result % 10);// pego o resto da divis�o do valor calculado acima por 10 e subtrai de 10
        if (result == 10) {
            result = 0;
        }

        return result;
    }
    
    /**
     *
     * @param nossoNumero
     * @param nomeTributo
     * @param inscricaoCadastral
     * @param identificacao
     * @param registro
     * @param valorDocumento
     * @param valorCobrado
     * @param parcelaUnica - Parcela �nica = true, Parcelado = false
     * @param parcela - atual/total
     * @param vencimento - AAAA/MM/DD
     * @param locaisPgto
     * @param exercicio
     * @param especie - REAL
     */
    public static FebrabanBean getFebraban(String nossoNumero, String nomeTributo,
            String inscricaoCadastral, String identificacao, String registro, double valorDocumento,
            double valorCobrado, boolean parcelaUnica, String parcela, Date vencimento, String locaisPgto,
            int exercicio, String especie, String linhas, java.awt.Image logo, 
            String idSegmento, String nomeOrgao, String idOrgao, String idTributo) {

        String codigoDeBarras;
        int codParcela;

        //Formar o valor do documento
        DecimalFormat df = new DecimalFormat("#,###.00");
        String sValor = df.format(valorDocumento);
        sValor = sValor.replaceAll(",", "");
        
        String formaPagamento;
        if (parcelaUnica) {
            formaPagamento = "Parcela �nica";
            codParcela = 6;
        } else {
            formaPagamento = "Parcelado";
            codParcela = 7;
        }
        sValor = sValor.replaceAll("\\.", "");//tirar o '.'
        sValor = Util.Texto.alinharDireita(sValor, 11).replaceAll(" ", "0");//completar com zeros

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dataVencimento = sdf.format(vencimento);
        nossoNumero = Util.Texto.alinharDireita(nossoNumero, 14).replaceAll(" ", "0");//completar com zeros

        //Criar o primeiro bloco, sem o quarto digito
        codigoDeBarras = "8" + idSegmento + codParcela + sValor + idOrgao + dataVencimento + nossoNumero + idTributo;
        //contante 8 acima � para identificar a arrecadacaoo, � fixo conforme o manual

        //Agora podemos criar  primeiro bloco complento            \/
        codigoDeBarras = "8" + idSegmento + codParcela + 
                FebrabanBean.calcModulo10(codigoDeBarras) + sValor + idOrgao + dataVencimento + nossoNumero + idTributo;
        // vamos adicionar o c�digo no objeto boleto
        String codigoBarras = codigoDeBarras;
        
        FebrabanBean cb = new FebrabanBean();
        cb.setOrgao(nomeOrgao);
        cb.setNomeTributo(nomeTributo);
        cb.setInscricaoCadastral(inscricaoCadastral);
        cb.setIdentificacao(identificacao);
        cb.setRegistro(registro);
        cb.setValorDocumento(valorDocumento);
        cb.setValorCobrado(valorCobrado);
        cb.setFormaPagamento(formaPagamento);
        cb.setParcela(parcela);
        cb.setVencimento(vencimento);
        cb.setLocaisPgto(locaisPgto);
        cb.setExercicio(exercicio);
        cb.setEspecie(especie);
        cb.setCodigoBarras(codigoBarras);
        cb.setBar1(codigoBarras.substring(0, 11) + "-" + FebrabanBean.calcModulo10(codigoBarras.substring(0, 11)));// calculamos o digito verificador de cada bloco
        cb.setBar2(codigoBarras.substring(11, 22) + "-" + FebrabanBean.calcModulo10(codigoBarras.substring(11, 22)));
        cb.setBar3(codigoBarras.substring(22, 33) + "-" + FebrabanBean.calcModulo10(codigoBarras.substring(22, 33)));
        cb.setBar4(codigoBarras.substring(33, 44) + "-" + FebrabanBean.calcModulo10(codigoBarras.substring(33, 44)));
        cb.setLinhas(linhas);

        return cb;
    }

    /**
     * @return the multa
     */
    public String getMulta() {
        return multa;
    }

    /**
     * @param multa the multa to set
     */
    public void setMulta(String multa) {
        this.multa = multa;
    }

    /**
     * @return the juros
     */
    public String getJuros() {
        return juros;
    }

    /**
     * @param juros the juros to set
     */
    public void setJuros(String juros) {
        this.juros = juros;
    }
}
