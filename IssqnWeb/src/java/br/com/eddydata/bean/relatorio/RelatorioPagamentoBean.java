/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Thiago Martos
 */
package br.com.eddydata.bean.relatorio;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.entidade.issqn.IssqnMovimento;
import br.com.eddydata.entidade.issqn.IssqnMovimentoParcela;
import br.com.eddydata.entidade.issqn.IssqnMovimentoParcelaItem;
import br.com.eddydata.servico.issqn.IssqnMovimentoParcelaItemServico;
import br.com.eddydata.suporte.BusinessViolation;
import br.com.eddydata.suporte.Util;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class RelatorioPagamentoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IssqnMovimentoParcelaItemServico servico;

    @Inject
    private GlobalBean global;

    private IssqnMovimentoParcelaItem item;
    private IssqnMovimento movimento;
    private IssqnMovimentoParcela parcela;
    private Boolean rdPagto = false;
    private Boolean rdBaixa = false;
    private Boolean rdLote = false;
    private String filtro = "";
    private String lote = null;
    private String ordem = " P.NOME ";
    private Date dataInicio = null;
    private Date dataFim = null;
    private Integer exercicio;

    @PostConstruct
    public void init() {

    }

    public void imprimir() {

        Date dt_inicial = dataInicio;
        Date dt_fim = dataFim;
        String txtPesquisa = "";

        StringBuilder pesquisa = new StringBuilder();
        String where = this.getFiltro();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (where == null) {
            Funcao.avisoErro("Por favor selecione uma opção!");
            return;
        }
        if (where.equals("MP.LOTE")) {
            rdLote = true;
            pesquisa.append("\nMP.LOTE = ").append(lote);
            txtPesquisa = "LOTE";
        } else if (where.equals("MP.DT_PAGAMENTO") && dataInicio != null && dataFim != null) {
            rdPagto = true;
            
            pesquisa.append("\nMP.DT_PAGAMENTO BETWEEN ")
                    .append(Util.quotarStr(sdf.format(dt_inicial)))
                    .append(" AND ")
                    .append(Util.quotarStr(sdf.format(dt_fim)));
            txtPesquisa = "PAGAMENTOS DE " + Util.convertToBrDate(dt_inicial) + " ATÉ " + Util.convertToBrDate(dt_fim);
        } else if (where.equals("MP.DT_BAIXA") && dataInicio != null && dataFim != null) {
            rdBaixa = true;
            pesquisa.append("\nMP.DT_BAIXA BETWEEN ")
                    .append(Util.quotarStr(sdf.format(dt_inicial)))
                    .append(" AND ")
                    .append(Util.quotarStr(sdf.format(dt_fim)));
            txtPesquisa = "BAIXAS DE " + Util.convertToBrDate(dt_inicial) + " ATÉ " + Util.convertToBrDate(dt_fim);
        } 

        if (!this.validaFiltroPagamento()) {
            return;
        }
        try {
            servico.imprimirRelacaoPagamentos(
                    global.getUsuarioLogado().getOrgao().getIdOrgao(), pesquisa.toString(), txtPesquisa);
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível fazer a impressão!");
            System.out.println(ex.getMessage());
        }
    }

    public void buscaPorPagamentoPorLote() {
        if (parcela.getLote() == null) {
            return;
        }
        try {
            item = servico.buscarPagamentoPorLote(parcela.getLote());
        } catch (BusinessViolation ex) {
            Funcao.avisoErro(ex.getMessage());
            System.out.println("Erro ao buscar lote\n" + ex.getMessage());
        }
    }

    public void selecionarPagamentoPorDataPagamento() throws BusinessViolation {
        if (parcela.getDtPagamento() == null) {
            Funcao.avisoAtencao("Data não informada");
            return;
        }
        try {
            item = (IssqnMovimentoParcelaItem) servico.buscarPagamentoPorDataPagamento(dataInicio, dataFim);
        } catch (Exception ex) {
            Funcao.avisoErro(ex.getMessage());
            System.out.println("Erro ao buscar pagamento por data de recebimento");
        }
    }

    public void selecionarPagamentoPorDataBaixa() throws BusinessViolation {
        if (parcela.getDtPagamento() == null) {
            Funcao.avisoAtencao("Data não informada");
            return;
        }
        try {
            item = (IssqnMovimentoParcelaItem) servico.buscarPagamentoPorDataPagamento(dataInicio, dataFim);
        } catch (Exception ex) {
            Funcao.avisoErro(ex.getMessage());
            System.out.println("Erro ao buscar pagamento por data de baixa\n" + ex.getMessage());
        }
    }

    public boolean validaFiltroPagamento() {
        boolean r = false;
        if (dataInicio == null && (rdPagto || rdBaixa)
                && dataFim == null) {
            Funcao.avisoErro("Informe a data inicial do periodo");
        } else if (rdLote && (lote == null || lote.trim().equals(""))) {
            Funcao.avisoErro("Informa o número do lote");
        } else {
            r = true;
        }
        return r;
    }

    //------------ getters e setters ---------------//
    public IssqnMovimentoParcelaItemServico getServico() {
        return servico;
    }

    public void setServico(IssqnMovimentoParcelaItemServico servico) {
        this.servico = servico;
    }

    public IssqnMovimento getMovimento() {
        return movimento;
    }

    public void setMovimento(IssqnMovimento movimento) {
        this.movimento = movimento;
    }

    public IssqnMovimentoParcela getParcela() {
        return parcela;
    }

    public void setParcela(IssqnMovimentoParcela parcela) {
        this.parcela = parcela;
    }

    public GlobalBean getGlobal() {
        return global;
    }

    public void setGlobal(GlobalBean global) {
        this.global = global;
    }

    public Boolean getRdPagto() {
        return rdPagto;
    }

    public void setRdPagto(Boolean rdDataPagto) {
        this.rdPagto = rdDataPagto;
    }

    public Boolean getRdBaixa() {
        return rdBaixa;
    }

    public void setRdBaixa(Boolean rdDataBaixa) {
        this.rdBaixa = rdDataBaixa;
    }

    public Boolean getRdLote() {
        return rdLote;
    }

    public void setRdLote(Boolean rdLote) {
        this.rdLote = rdLote;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public IssqnMovimentoParcelaItem getItem() {
        return item;
    }

    public void setItem(IssqnMovimentoParcelaItem item) {
        this.item = item;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Integer getExercicio() {
        return exercicio;
    }

    public void setExercicio(Integer exercicio) {
        this.exercicio = exercicio;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

}
