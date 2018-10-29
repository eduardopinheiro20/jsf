/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.boleto;

import java.awt.Image;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author cassio
 */
public class Relatorio {

    private Banco banco;
    private List<BoletoBeanJasper> boletos;
    private DecimalFormat formatter;
    private Image logotipo_banco;
    private Image fundo;
    private String modelo;

    public Relatorio(Banco banco) {
        this(banco, 1);
    }

    public Relatorio(Banco banco, int modelo) {
        //TODO: rever o suporte dos demais bancos
        if (!(banco instanceof SantanderBanespa) && !(banco instanceof NossaCaixa)&& !(banco instanceof Bancoob)
                && !(banco instanceof CaixaEconomica) && !(banco instanceof CaixaEconomicaNovo)
                && !(banco instanceof Hsbc) && !(banco instanceof BancoBrasil) && !(banco instanceof Bradesco)) {
            throw new UnsupportedOperationException("O banco utilizado ainda não é suportado.");
        }

        this.banco = banco;
        switch (modelo) {
            default:
            case 0:
                this.modelo = "";
                break;
            case 1:
                this.modelo = "2";
                break;
        }
        formatter = new DecimalFormat("#,##0.00");
        boletos = new ArrayList();
        String brasao = "/br/com/eddydata/boleto/resource/" + new Integer(banco.getNumero()) + ".gif";
        logotipo_banco = new ImageIcon(getClass().getResource(brasao)).getImage();
        fundo = new ImageIcon(getClass().getResource("/br/com/eddydata/boleto/resource/fundo" + this.modelo + ".gif")).getImage();
    }

    public void addBoleto(BoletoBean boleto) {
        BoletoBeanJasper jasper = new BoletoBeanJasper();
        banco.setBoletoBean(boleto);
        String numero = banco.getDvNumero() == null ? banco.getNumero() + "-" + boleto.getDigitoCodigoBarras(banco.getNumero()) : banco.getNumero() + banco.getDvNumero();
        jasper.setBanco(numero);
        jasper.setCarteira(banco.getCarteiraFormatted());
        jasper.setCedente(boleto.getCedente());
        jasper.setCliente_nome(boleto.getNomeSacado());
        jasper.setConta_corrente(banco.getAgenciaCodCedenteFormatted());
        jasper.setDt_doc(boleto.getDataDocumento());
        jasper.setDt_processamento(boleto.getDataProcessamento());
        jasper.setDt_vencimento(boleto.getDataVencimento());
        jasper.setEndereco1(boleto.getNomeSacado() + "     " + boleto.getCpfSacado());
        jasper.setEndereco2(boleto.getEnderecoSacado());
        jasper.setEndereco3(boleto.getCepSacado() + " " + boleto.getBairroSacado() + " - " + boleto.getCidadeSacado() + " " + boleto.getUfSacado());
        jasper.setEspecie(boleto.getEspecie());
        jasper.setInstrucao1(boleto.getInstrucao1());
        jasper.setInstrucao2(boleto.getInstrucao2());
        jasper.setInstrucao3(boleto.getInstrucao3());
        jasper.setInstrucao4(boleto.getInstrucao4());
        jasper.setInstrucao5(boleto.getInstrucao5());
        jasper.setLocal_pagamento(boleto.getLocalPagamento());
        jasper.setLocal_pagamento2(boleto.getLocalPagamento2());
        jasper.setMoeda(/*boleto.getMoeda()*/"R$");
        jasper.setNosso_numero(banco.getNossoNumeroFormatted());
        jasper.setValor(formatter.format(new Double(boleto.getValorBoleto())));
        jasper.setDigito_codbarra(banco.getLinhaDigitavel());
        jasper.setDocumento(boleto.getDocumento());
        jasper.setSacado_cpf_cnpj(boleto.getCpfSacado());
        jasper.setSacado_nome(boleto.getNomeSacado());
        jasper.setEspecie_doc(boleto.getEspecieDocumento());
        jasper.setParcela(boleto.getParcela());
        jasper.setAceite(boleto.getAceite());
        jasper.setInscricao(boleto.getInscricao());
        jasper.setCorpo_capa(boleto.getCorpo_capa());
        jasper.setExercicio(boleto.getExercicio());
        jasper.setAtividade(boleto.getAtividade());
        jasper.setAtividade_primaria(boleto.getAtividade_primaria());
        jasper.setContribuinte(boleto.getContribuinte());

//        BarcodeInter25 code = new BarcodeInter25();
//        code.setCode(banco.getCodigoBarras());
//        System.out.println("C�d. barras: " + banco.getCodigoBarras());
/*        code.setExtended(true);
        code.setTextAlignment(Element.ALIGN_LEFT);
        code.setBarHeight(43.00f);
        code.setFont(null);
        code.setX(0.73f);
        code.setN(3);
        Image codbarra = code.createAwtImage(Color.BLACK, Color.WHITE);*/
        jasper.setCodbarra(banco.getCodigoBarras());

        getBoletos().add(jasper);
    }

    public JasperPrint getJasperPrint() throws JRException {
        InputStream is = getClass().getResourceAsStream("/br/com/eddydata/boleto/resource/boleto" + modelo + ".jasper");
        Map parametros = new HashMap();
        parametros.put("logotipo_banco", logotipo_banco);
        parametros.put("fundo", fundo);
        JasperPrint jp = JasperFillManager.fillReport(is, parametros, new JRBeanCollectionDataSource(getBoletos()));
        return jp;
    }

    public byte[] getPdfBytes() throws JRException {
        InputStream is = getClass().getResourceAsStream("/br/com/eddydata/boleto/resource/boleto" + modelo + ".jasper");
        Map parametros = new HashMap();
        parametros.put("logotipo_banco", logotipo_banco);
        parametros.put("fundo", fundo);
        return JasperRunManager.runReportToPdf(is, parametros, new JRBeanCollectionDataSource(getBoletos()));
    }

    public List<BoletoBeanJasper> getBoletos() {
        return boletos;
    }

    public void setBoletos(List<BoletoBeanJasper> boletos) {
        this.boletos = boletos;
    }

    public Image getLogotipo_banco() {
        return logotipo_banco;
    }

    public Image getFundo() {
        return fundo;
    }
    
    
}
