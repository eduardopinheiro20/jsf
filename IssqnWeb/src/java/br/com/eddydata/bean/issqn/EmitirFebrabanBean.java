/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2017, Eddydata ltda.
 * Diretors Reservados.
 * @author Juliano Alves
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnTipoCobranca;
import br.com.eddydata.servico.issqn.IssqnServico;
import br.com.eddydata.servico.issqn.TipoCobrancaServico;
import br.com.eddydata.suporte.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class EmitirFebrabanBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IssqnServico servico;
    @EJB
    private TipoCobrancaServico cobrancaServico;

    @Inject
    private GlobalBean global;

    private Issqn contribuinte;
    private boolean gerarTodos;
    private int ordem = 2;
    private String inscricaoInicial;
    private String inscricaoFinal;
    private String parcelaInicial;
    private String parcelaFinal;
    private Date vencimento;
    private List<IssqnTipoCobranca> listTipoCobranca = new ArrayList<>();
    private Integer tipoCobrancaBoleto = 0;

    public void imprimir() {
        StringBuilder pesquisa = new StringBuilder();

        if ((inscricaoInicial == null || inscricaoInicial.isEmpty()) && (inscricaoFinal == null || inscricaoFinal.isEmpty()) && (!gerarTodos) && contribuinte == null) {
            Funcao.avisoAtencao("Selecione uma opção de busca");
            return;
        }

        if (!gerarTodos) {
            if ((inscricaoInicial != null && !inscricaoInicial.isEmpty()) && ((inscricaoFinal == null || inscricaoFinal.isEmpty()))) {
                pesquisa.append(" AND II.INSCRICAO = ").append(Util.quotarStr(inscricaoInicial));
            }

            if ((inscricaoInicial != null && !inscricaoInicial.isEmpty()) && (inscricaoFinal != null && !inscricaoFinal.isEmpty())) {
                pesquisa.append(" AND CAST(II.INSCRICAO AS BIGINT) BETWEEN ").append(inscricaoInicial.trim()).append(" AND ").append(inscricaoFinal.trim());
            }

            if (contribuinte != null) {
                pesquisa.append(" AND II.ID_ISS = ").append(contribuinte.getId());
            }
        }

        if ((Util.isNumeric(parcelaInicial) && (!parcelaInicial.isEmpty())) && (Util.isNumeric(parcelaFinal) && (!parcelaFinal.isEmpty()))) {
            pesquisa.append(" AND MP.PARCELA BETWEEN ").append(parcelaInicial).append(" AND ").append(parcelaFinal);
        } else if (Util.isNumeric(parcelaInicial) && (!parcelaInicial.isEmpty())) {
            pesquisa.append(" AND MP.PARCELA = ").append(parcelaInicial);
        }

        try {
            servico.gerarCobranca(global.getUsuarioLogado().getOrgao().getIdOrgao(), pesquisa.toString(), global.getExercicio().getAno(), ordem, tipoCobrancaBoleto, vencimento);
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível fazer a impressão!");
            System.out.println(ex.getMessage());
        }
    }

    //------------ getters e setters ---------------//
    public Issqn getContribuinte() {
        return contribuinte;
    }

    public void setContribuinte(Issqn contribuinte) {
        this.contribuinte = contribuinte;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public boolean getGerarTodos() {
        return gerarTodos;
    }

    public void setGerarTodos(boolean gerarTodos) {
        this.gerarTodos = gerarTodos;
    }

    public String getInscricaoInicial() {
        return inscricaoInicial;
    }

    public void setInscricaoInicial(String inscricaoInicial) {
        this.inscricaoInicial = inscricaoInicial;
    }

    public String getInscricaoFinal() {
        return inscricaoFinal;
    }

    public void setInscricaoFinal(String inscricaoFinal) {
        this.inscricaoFinal = inscricaoFinal;
    }

    public String getParcelaInicial() {
        return parcelaInicial;
    }

    public void setParcelaInicial(String parcelaInicial) {
        this.parcelaInicial = parcelaInicial;
    }

    public String getParcelaFinal() {
        return parcelaFinal;
    }

    public void setParcelaFinal(String parcelaFinal) {
        this.parcelaFinal = parcelaFinal;
    }

    public List<IssqnTipoCobranca> getListTipoCobranca() {
        if (listTipoCobranca.isEmpty()) {
            listTipoCobranca = cobrancaServico.getTodosCobrancas(global.getExercicio().getAno());
        }
        return listTipoCobranca;
    }

    public void setListTipoCobranca(List<IssqnTipoCobranca> listTipoCobranca) {
        this.listTipoCobranca = listTipoCobranca;
    }

    public Integer getTipoCobrancaBoleto() {
        return tipoCobrancaBoleto;
    }

    public void setTipoCobrancaBoleto(Integer tipoCobrancaBoleto) {
        this.tipoCobrancaBoleto = tipoCobrancaBoleto;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

}
