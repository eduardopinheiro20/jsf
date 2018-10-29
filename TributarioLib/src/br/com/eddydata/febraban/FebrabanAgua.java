package br.com.eddydata.febraban;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class FebrabanAgua {

    private ArrayList<Boleto> arrayBoleto = new ArrayList<Boleto>();
    private String idSegmento;
    private String nomeOrgao;
    private String idOrgao;
    private String idTributo;
    private java.awt.Image logo;
    private Map parametros;

    public FebrabanAgua(String idSegmento, String nomeOrgao, String idOrgao, String idTributo, Map parametros) {
        this.idSegmento = idSegmento;
        this.nomeOrgao = nomeOrgao;
        this.idOrgao = idOrgao;
        this.idTributo = idTributo;
        this.parametros = parametros;
    }

    public JRBeanCollectionDataSource getJasperPrint() throws JRException {
        // Lista com beans
        List lista = getRelatorio();
        // O datasource
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        // Parametros do relatorios
        if (parametros == null) {
            parametros = new HashMap();
            parametros.put("logo", logo);
        }

        return ds;
    }

    public List getRelatorio() {
        List lista = new ArrayList();
        for (int i = 0; i < arrayBoleto.size(); i++) {
            Map tab = new HashMap();
            Boleto boleto = arrayBoleto.get(i);
            tab.put("orgao", boleto.getOrgao());
            tab.put("nomeTributo", boleto.getNomeTributo());
            tab.put("inscricao", boleto.getInscricao());
            tab.put("valorCobrado", boleto.getValorCobrado());
            tab.put("exercicio", boleto.getExercicio());
            tab.put("vencimento", boleto.getVencimento());
            tab.put("linhas", boleto.getLinhas());
            tab.put("codigoBarras", boleto.getCodigoBarras());
            tab.put("bar1", boleto.getCodigoBarras().substring(0, 11) + "-" + calcModulo10(boleto.getCodigoBarras().substring(0, 11)));// calculamos o digito verificador de cada bloco
            tab.put("bar2", boleto.getCodigoBarras().substring(11, 22) + "-" + calcModulo10(boleto.getCodigoBarras().substring(11, 22)));
            tab.put("bar3", boleto.getCodigoBarras().substring(22, 33) + "-" + calcModulo10(boleto.getCodigoBarras().substring(22, 33)));
            tab.put("bar4", boleto.getCodigoBarras().substring(33, 44) + "-" + calcModulo10(boleto.getCodigoBarras().substring(33, 44)));
            tab.put("hidrometro", boleto.getHidrometro());
            tab.put("dtEmissao", boleto.getDtEmissao());
            tab.put("referencia", boleto.getReferencia());
            lista.add(tab);
        }

        return lista;
    }

    public void add(String nossoNumero, String inscricao, double valorCobrado,
            int exercicio, String hidrometro, Date dtEmissao, Date dtVencimento, String referencia, String codigoDeBarras) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        String sValor = df.format(valorCobrado);
        sValor = sValor.replaceAll(",", "");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Boleto boleto = new Boleto();
        boleto.setOrgao(this.nomeOrgao);
        boleto.setNomeTributo("Água");
        boleto.setInscricao(inscricao);
        boleto.setValorDocumento(valorCobrado);
        boleto.setValorCobrado(valorCobrado);
        boleto.setExercicio(exercicio);
        boleto.setHidrometro(hidrometro);
        boleto.setDtEmissao(sdf.format(dtEmissao));
        boleto.setVencimento(dtVencimento);
        boleto.setReferencia(referencia);
        boleto.setCodigoBarras(codigoDeBarras);
        arrayBoleto.add(boleto);
    }

    public class Boleto {

        //CAMPOS COMUNS A IPTU E AGUA
        private String orgao;
        private String nomeTributo;
        private String inscricao;
        private double valorDocumento;
        private double valorCobrado;
        private int exercicio;
        private Date vencimento;
        private String codigoBarras;
        private String linhas;

        //CAMPOS RELATIVOS À IPTU
        private String identificacao;
        private String registro;
        private String formaPagamento;
        private String parcela;
        private String locaisPgto;
        private String especie;
        private String multa;
        private String juros;

        //CAMPOS RELATIVOS À AGUA
        private String hidrometro;
        private String dtEmissao;
        private String referencia;

        public String getJuros() {
            return juros;
        }

        public void setJuros(String juros) {
            this.juros = juros;
        }

        public String getMulta() {
            return multa;
        }

        public void setMulta(String multa) {
            this.multa = multa;
        }

        public String getEspecie() {
            return especie;
        }

        public void setEspecie(String especie) {
            this.especie = especie;
        }

        public int getExercicio() {
            return exercicio;
        }

        public void setExercicio(int exercicio) {
            this.exercicio = exercicio;
        }

        public String getFormaPagamento() {
            return formaPagamento;
        }

        public void setFormaPagamento(String formaPagamento) {
            this.formaPagamento = formaPagamento;
        }

        public String getIdentificacao() {
            return identificacao;
        }

        public void setIdentificacao(String identificacao) {
            this.identificacao = identificacao;
        }

        public String getInscricao() {
            return inscricao;
        }

        public void setInscricao(String inscricao) {
            this.inscricao = inscricao;
        }

        public String getLocaisPgto() {
            return locaisPgto;
        }

        public void setLocaisPgto(String locaisPgto) {
            this.locaisPgto = locaisPgto;
        }

        public String getNomeTributo() {
            return nomeTributo;
        }

        public void setNomeTributo(String nomeTributo) {
            this.nomeTributo = nomeTributo;
        }

        public String getOrgao() {
            return orgao;
        }

        public void setOrgao(String orgao) {
            this.orgao = orgao;
        }

        public String getParcela() {
            return parcela;
        }

        public void setParcela(String parcela) {
            this.parcela = parcela;
        }

        public String getRegistro() {
            return registro;
        }

        public void setRegistro(String registro) {
            this.registro = registro;
        }

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

        public String getCodigoBarras() {
            return codigoBarras;
        }

        public void setCodigoBarras(String codigoBarras) {
            this.codigoBarras = codigoBarras;
        }

        public String getLinhas() {
            return linhas;
        }

        public void setLinhas(String linhas) {
            this.linhas = linhas;
        }

        /**
         * @return the hidrometro
         */
        public String getHidrometro() {
            return hidrometro;
        }

        /**
         * @param hidrometro the hidrometro to set
         */
        public void setHidrometro(String hidrometro) {
            this.hidrometro = hidrometro;
        }

        /**
         * @return the dtEmissao
         */
        public String getDtEmissao() {
            return dtEmissao;
        }

        /**
         * @param dtEmissao the dtEmissao to set
         */
        public void setDtEmissao(String dtEmissao) {
            this.dtEmissao = dtEmissao;
        }

        /**
         * @return the referencia
         */
        public String getReferencia() {
            return referencia;
        }

        /**
         * @param referencia the referencia to set
         */
        public void setReferencia(String referencia) {
            this.referencia = referencia;
        }
    }

    /**
     * Cï¿½lculo do DAC (Dï¿½gito de Auto-Conferï¿½ncia) de cada bloco
     *
     * @param seq1 - Sequencia de digitos
     * @return Retorna o DAC
     */
    private int calcModulo10(String seq0) {
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
        result = 10 - (result % 10);// pego o resto da divisï¿½o do valor calculado acima por 10 e subtrai de 10
        if (result == 10) {
            result = 0;
        }

        return result;
    }
}
