package br.com.eddydata.febraban;

import br.com.eddydata.suporte.Util;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class Febraban {

    private ArrayList<Boleto> arrayBoleto = new ArrayList<Boleto>();
    private String idSegmento;
    private String nomeOrgao;
    private String idOrgao;
    private String idTributo;
    private java.awt.Image logo;
    private final FebrabanModelo febrabanModelo;

    public Febraban(java.awt.Image logo, String idSegmento, String nomeOrgao, String idOrgao, String idTributo) {
        this(logo, idSegmento, nomeOrgao, idOrgao, idTributo, FebrabanModelo.QUATRO_VIAS);
    }

    /**
     *
     * @param idSegmento 1 = Prefeituras, 2 = Saneamento, 3 = Energia El�trica e
     * G�s, 4 = Telecomunica��es, 5 = �rg�os Gorvenamentais, 6 = Carn�s e
     * Assemelhados ou demais Empresas/�rg�os que ser�o identificados atrav�s do
     * CNPJ, 7 = Multas de tr�nsito, 9. Uso exclusivo do banco
     *
     * @param nomeOrgao Nome do Org�o
     *
     * @param idOrgao Codigo de quatro posi��es atribu�do e controlado pela
     * Febraban ou as primeiras outo posi��es do cadastro geral de contribuintes
     * do Minist�rio da Fazenda
     *
     * @param idTributo
     */
    public Febraban(java.awt.Image logo, String idSegmento, String nomeOrgao, String idOrgao, String idTributo,
            FebrabanModelo febrabanModelo) {
        this.idSegmento = idSegmento;
        this.nomeOrgao = nomeOrgao;
        this.idOrgao = idOrgao;
        this.idTributo = idTributo;
        this.logo = logo;
        this.febrabanModelo = febrabanModelo;
    }

    public JasperPrint getJasperPrint() throws JRException {
        // Lista com beans
        List lista = getRelatorio();
        // O datasource
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        // Parametros do relatorios
        Map parametros = new HashMap();
        parametros.put("logo", logo);

        String jasper;
        switch (febrabanModelo) {
            default:
            case QUATRO_VIAS:
                jasper = "/br/com/eddydata/febraban/Febraban.jasper";
                break;
            case TRES_VIAS:
                jasper = "/br/com/eddydata/febraban/Febraban2.jasper";
                break;
        }

        InputStream is = getClass().getResourceAsStream(jasper);
        JasperPrint jp = JasperFillManager.fillReport(is, parametros, ds);
        return jp;
    }

    public byte[] getPdfBytes() throws JRException {
        // Lista com beans
        List lista = getRelatorio();
        // O datasource
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        // Parametros do relatorios
        Map parametros = new HashMap();
        parametros.put("logo", logo);

        InputStream is = getClass().getResourceAsStream("/br/com/eddydata/febraban/Febraban.jasper");
        return JasperRunManager.runReportToPdf(is, parametros, ds);
    }

    public void exibirRelatorio(boolean visualizar) throws JRException {

        // Lista com beans
        List lista = getRelatorio();
        // O datasource
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lista);
        // Parametros do relatorios
        Map parametros = new HashMap();
        parametros.put("logo", logo);
        JasperPrint impressao = null;

        /* implementacao da interface JRDataSource para DataSource ResultSet */
        InputStream input = getClass().getResourceAsStream("/br/com/eddydata/febraban/Febraban.jasper");
        impressao = JasperFillManager.fillReport(input, parametros, ds);
        if (visualizar) {
            JasperViewer viewer = new JasperViewer(impressao, false);
            viewer.setVisible(visualizar);
        } else {
            JasperPrintManager.printReport(impressao, false);
        }
    }

    public List getRelatorio() {

        List lista = new ArrayList();

        for (int i = 0; i < arrayBoleto.size(); i++) {
            Map tab = new HashMap();

            Boleto boleto = arrayBoleto.get(i);

            tab.put("orgao", boleto.getOrgao());
            tab.put("nomeTributo", boleto.getNomeTributo());
            tab.put("inscricaoCadastral", boleto.getInscricaoCadastral());
            tab.put("identificacao", boleto.getIdentificacao());
            tab.put("registro", boleto.getRegistro());
            tab.put("valorDocumento", boleto.getValorDocumento());
            tab.put("valorCobrado", boleto.getValorCobrado());
            tab.put("formaPagamento", boleto.getFormaPagamento());
            tab.put("parcela", boleto.getParcela());
            tab.put("vencimento", boleto.getVencimento());
            tab.put("locaisPgto", boleto.getLocaisPgto());
            tab.put("exercicio", boleto.getExercicio());
            tab.put("especie", boleto.getEspecie());
            tab.put("codigoBarras", boleto.getCodigoBarras());
            tab.put("bar1", boleto.getCodigoBarras().substring(0, 11) + "-" + calcModulo10(boleto.getCodigoBarras().substring(0, 11)));// calculamos o digito verificador de cada bloco
            tab.put("bar2", boleto.getCodigoBarras().substring(11, 22) + "-" + calcModulo10(boleto.getCodigoBarras().substring(11, 22)));
            tab.put("bar3", boleto.getCodigoBarras().substring(22, 33) + "-" + calcModulo10(boleto.getCodigoBarras().substring(22, 33)));
            tab.put("bar4", boleto.getCodigoBarras().substring(33, 44) + "-" + calcModulo10(boleto.getCodigoBarras().substring(33, 44)));
            tab.put("linhas", boleto.getLinhas());
            tab.put("multa", boleto.getMulta());
            tab.put("juros", boleto.getJuros());

            lista.add(tab);
        }

        return lista;
    }

    /**
     * Adiciona um novo objeto do tipo Boleto, no ArrayList
     *
     * @param nossoNumero
     * @param nomeTributo
     * @param instricaoCadastral
     * @param identificacaoo
     * @param registro
     * @param valorDocumento
     * @param valorCobrado
     * @param parcelaUnica - Parcela unica = true, Parcelado = false
     * @param parcela - atual/total
     * @param vencimento - AAAA/MM/DD
     * @param locaisPgto
     * @param exercicio
     * @param especie - REAL
     * @param linhas
     * @param bloquear
     */
    public void add(String nossoNumero, String nomeTributo,
            String instricaoCadastral, String identificacaoo, String registro, double valorDocumento,
            double valorCobrado, boolean parcelaUnica, String parcela, Date vencimento, String locaisPgto,
            int exercicio, String especie, String linhas, boolean bloquear) {

        String codigoDeBarras;
        int codParcela;

        //Formar o valor do documento
        DecimalFormat df = new DecimalFormat("#,###.00");
        String sValor = df.format(valorDocumento > valorCobrado ? valorDocumento : valorCobrado);
        sValor = sValor.replaceAll(",", "");

        Boleto boleto = new Boleto();

        boleto.setOrgao(this.nomeOrgao);
        boleto.setNomeTributo(nomeTributo);
        boleto.setInscricaoCadastral(instricaoCadastral);
        boleto.setIdentificacao(identificacaoo);
        boleto.setRegistro(registro);
        boleto.setValorDocumento(valorDocumento);
        if (parcelaUnica) {
            boleto.setFormaPagamento("Parcela única");
            boleto.setValorCobrado(valorCobrado);
        } else {
            boleto.setFormaPagamento("Parcelado");
        }
        if (bloquear) {
            codParcela = 6;
        } else {
            codParcela = 7;
        }
        boleto.setParcela(parcela);
        boleto.setVencimento(vencimento);
        boleto.setLocaisPgto(locaisPgto);
        boleto.setExercicio(exercicio);
        boleto.setEspecie(especie);

        sValor = sValor.replaceAll("\\.", "");//tirar o '.'
        sValor = Util.Texto.alinharDireita(sValor, 11).replaceAll(" ", "0");//completar com zeros

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dataVencimento = sdf.format(vencimento);
        if (idSegmento.equals("6")) {
            nossoNumero = Util.Texto.alinharDireita(nossoNumero, 10).replaceAll(" ", "0");//completar com zeros
        } else {
            nossoNumero = Util.Texto.alinharDireita(nossoNumero, 14).replaceAll(" ", "0");//completar com zeros
        }
        //parcela
        if (parcela.length() < 5) {
            parcela = parcela.substring(0, 1);
            parcela = Util.Texto.alinharDireita(parcela, 2).replaceAll(" ", "0");
        } else {
            parcela = parcela.substring(0, 2);
        }

        //Criar o primeiro bloco, sem o quarto digito
        codigoDeBarras = "8" + this.idSegmento + codParcela + sValor + this.idOrgao + dataVencimento + nossoNumero + this.idTributo;
        //contante 8 acima e para identificar a arrecadacaoo, e fixo conforme o manual

        //Agora podemos criar  primeiro bloco complento            \/
        codigoDeBarras = "8" + this.idSegmento + codParcela + calcModulo10(codigoDeBarras) + sValor + this.idOrgao + dataVencimento + nossoNumero + this.idTributo;
        // vamos adicionar o codigo no objeto boleto
        boleto.setCodigoBarras(codigoDeBarras);
        boleto.setLinhas(linhas);

        arrayBoleto.add(boleto);

    }

    /**
     * Sobrecarga desse metodo, para atender as exigencias da cidade de Motuca
     * Adiciona um novo objeto do tipo Boleto, no ArrayList
     *
     * @param nossoNumero
     * @param nomeTributo
     * @param instricaoCadastral
     * @param identificacaoo
     * @param registro
     * @param valorDocumento
     * @param valorCobrado
     * @param parcelaUnica - Parcela unica = true, Parcelado = false
     * @param parcela - atual/total
     * @param vencimento - AAAA/MM/DD
     * @param locaisPgto
     * @param exercicio
     * @param especie - REAL
     * @param linhas
     * @param multa
     * @param juros
     * @param bloquear
     */
    public void add(String nossoNumero, String nomeTributo,
            String instricaoCadastral, String identificacaoo, String registro, double valorDocumento,
            double valorCobrado, boolean parcelaUnica, String parcela, Date vencimento, String locaisPgto,
            int exercicio, String especie, String linhas, double multa, double juros, boolean bloquear) {

        String codigoDeBarras;
        int codParcela;

        //Formar o valor do documento
        DecimalFormat df = new DecimalFormat("#,###.00");
        String sValor = df.format(valorDocumento > valorCobrado ? valorDocumento : valorCobrado);
        sValor = sValor.replaceAll(",", "");

        Boleto boleto = new Boleto();

        boleto.setOrgao(this.nomeOrgao);
        boleto.setNomeTributo(nomeTributo);
        boleto.setInscricaoCadastral(instricaoCadastral);
        boleto.setIdentificacao(identificacaoo);
        boleto.setRegistro(registro);
        boleto.setValorDocumento(valorDocumento);
        boleto.setValorCobrado(valorCobrado);
        if (parcelaUnica) {
            boleto.setFormaPagamento("Parcela única");
            codParcela = 6;
        } else {
            boleto.setFormaPagamento("Parcelado");
            codParcela = 7;
        }
        if (bloquear) {
            codParcela = 6;
        } else {
            codParcela = 7;
        }
        boleto.setParcela(parcela);
        boleto.setVencimento(vencimento);
        boleto.setLocaisPgto(locaisPgto);
        boleto.setExercicio(exercicio);
        boleto.setEspecie(especie);
        boleto.setMulta(multa);
        boleto.setJuros(juros);

        sValor = sValor.replaceAll("\\.", "");//tirar o '.'
        sValor = Util.Texto.alinharDireita(sValor, 11).replaceAll(" ", "0");//completar com zeros

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dataVencimento = sdf.format(vencimento);
        if (idSegmento.equals("6")) {
            nossoNumero = Util.Texto.alinharDireita(nossoNumero, 10).replaceAll(" ", "0");//completar com zeros
        } else {
            nossoNumero = Util.Texto.alinharDireita(nossoNumero, 14).replaceAll(" ", "0");//completar com zeros
        }

        //parcela
        if (parcela.length() < 5) {
            parcela = parcela.substring(0, 1);
            parcela = Util.Texto.alinharDireita(parcela, 2).replaceAll(" ", "0");
        } else {
            parcela = parcela.substring(0, 2);
        }

        //Criar o primeiro bloco, sem o quarto digito
        codigoDeBarras = "8" + this.idSegmento + codParcela + sValor + this.idOrgao + dataVencimento + nossoNumero + this.idTributo;
        //contante 8 acima e para identificar a arrecadacaoo, e fixo conforme o manual

        //Agora podemos criar  primeiro bloco complento            \/
        codigoDeBarras = "8" + this.idSegmento + codParcela + calcModulo10(codigoDeBarras) + sValor + this.idOrgao + dataVencimento + nossoNumero + this.idTributo;
        // vamos adicionar o codigo no objeto boleto
        boleto.setCodigoBarras(codigoDeBarras);
        boleto.setLinhas(linhas);

        arrayBoleto.add(boleto);

    }

    public class Boleto {

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
        private int exercicio;
        private String especie;
        private String codigoBarras;
        private String linhas;
        private double multa;
        private double juros;

        public double getJuros() {
            return juros;
        }

        public void setJuros(double juros) {
            this.juros = juros;
        }

        public double getMulta() {
            return multa;
        }

        public void setMulta(double multa) {
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

        public String getInscricaoCadastral() {
            return inscricaoCadastral;
        }

        public void setInscricaoCadastral(String inscricaoCadastral) {
            this.inscricaoCadastral = inscricaoCadastral;
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
    }

    /**
     * Calculo do DAC (D�gito de Auto-Confer�ncia) de cada bloco
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
        result = 10 - (result % 10);// pego o resto da divisão do valor calculado acima por 10 e subtrai de 10
        if (result == 10) {
            result = 0;
        }

        return result;
    }
}
