/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2017, Eddydata ltda.
 * Diretors Reservados.
 * @author Juliano Alves
 */
package br.com.eddydata.bean.relatorio;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.entidade.geral.Pessoa;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnAtividade;
import br.com.eddydata.entidade.issqn.IssqnCategoria;
import br.com.eddydata.entidade.issqn.IssqnRamoAtuacao;
import br.com.eddydata.servico.issqn.IssqnServico;
import br.com.eddydata.suporte.BusinessViolation;
import br.com.eddydata.suporte.Util;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class RelatorioConferenciaParcelas implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IssqnServico servico;

    @Inject
    private GlobalBean global;

    private Issqn contribuinte;
    private Boolean semCpf = false;
    private Boolean chkAtivo = false;
    private Boolean chkIsento = false;
    private Boolean chkInativo = false;
    private Boolean chkEncerrado = false;
    private Boolean chkSuspenso = false;
    private Boolean chkSimples = false;
    private Boolean chkMEI = false;
    private String tipoPessoa = "*";
    private String ordem = "I.INSCRICAO";
    private String inscricao;
    private String cnpjCpf;
    private String telefone;
    private IssqnAtividade atividade;
    private IssqnCategoria categoria;
    private IssqnRamoAtuacao ramoAtuacao;
    private Date dataMax;
    private Date dataFim;
    private Integer exercicio;
    private String order = "I";
    private String tipoRelatorio = "S";
    private Integer tipoCobranca = 0;

    @PostConstruct
    public void init() {
        order = "I";
        tipoRelatorio = "S";
        tipoCobranca = 0;
    }

    public void limpar() {
        inscricao = "";
        contribuinte = new Issqn();
        order = "I";
        tipoRelatorio = "S";
        tipoCobranca = 0;
    }

    public void imprimir() {

        String ord = "";
        if (order.equals("I")) {
            ord = "1";
        } else if (order.equals("C")) {
            ord = "2,9";
        } else if (order.equals("E")) {
            ord = "3";
        } else if (order.equals("A")) {
            ord = "9,2";
        } else {
            ord = "1";
        }

        try {
            servico.imprimirConferenciaParcelas(global.getUsuarioLogado().getOrgao().getIdOrgao(), inscricao, Util.parseSqlDate(dataMax), 
                    global.getExercicio().getAno(), ord, tipoCobranca, tipoRelatorio);
        } catch (BusinessViolation bv){
            Funcao.avisoAtencao(bv.getMessage());
        }catch (Exception ex) {
            Funcao.avisoErro("Não foi possível fazer a impressão!");
            System.out.println(ex.getMessage());
        }
    }

    public void buscaPorInscricao() {
        if (inscricao == null || inscricao.trim().isEmpty()) {
            return;
        }
        try {
            contribuinte = servico.obterIssqnPorInscricao(inscricao, global.getExercicio().getAno());
        } catch (BusinessViolation ex) {
            Funcao.avisoErro(ex.getMessage());
            System.out.println("Erro ao buscar inscrição\n" + ex.getMessage());
        }

        selecionarIssqn();
    }

    public void selecionarIssqn() {
        if (contribuinte == null) {
            Funcao.avisoAtencao("Contribuinte não selecionado");
            return;
        }
        inscricao = contribuinte.getInscricao();
        cnpjCpf = contribuinte.getCnpjCpf();

        Pessoa p = contribuinte.getPessoa();
        if (p != null) {
            contribuinte.setCnpjCpf(p.getCpfCnpj());
        }
    }

    //------------ getters e setters ---------------//
    public Issqn getContribuinte() {
        return contribuinte;
    }

    public void setContribuinte(Issqn contribuinte) {
        this.contribuinte = contribuinte;
    }

    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    public Boolean getChkAtivo() {
        return chkAtivo;
    }

    public void setChkAtivo(Boolean chkAtivo) {
        this.chkAtivo = chkAtivo;
    }

    public Boolean getChkIsento() {
        return chkIsento;
    }

    public void setChkIsento(Boolean chkIsento) {
        this.chkIsento = chkIsento;
    }

    public Boolean getChkInativo() {
        return chkInativo;
    }

    public void setChkInativo(Boolean chkInativo) {
        this.chkInativo = chkInativo;
    }

    public Boolean getChkEncerrado() {
        return chkEncerrado;
    }

    public void setChkEncerrado(Boolean chkEncerrado) {
        this.chkEncerrado = chkEncerrado;
    }

    public Boolean getChkSuspenso() {
        return chkSuspenso;
    }

    public void setChkSuspenso(Boolean chkSuspenso) {
        this.chkSuspenso = chkSuspenso;
    }

    public Boolean getChkSimples() {
        return chkSimples;
    }

    public void setChkSimples(Boolean chkSimples) {
        this.chkSimples = chkSimples;
    }

    public Boolean getChkMEI() {
        return chkMEI;
    }

    public void setChkMEI(Boolean chkMEI) {
        this.chkMEI = chkMEI;
    }

    public Boolean getSemCpf() {
        return semCpf;
    }

    public void setSemCpf(Boolean semCpf) {
        this.semCpf = semCpf;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public IssqnAtividade getAtividade() {
        return atividade;
    }

    public void setAtividade(IssqnAtividade atividade) {
        this.atividade = atividade;
    }

    public IssqnCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(IssqnCategoria categoria) {
        this.categoria = categoria;
    }

    public IssqnRamoAtuacao getRamoAtuacao() {
        return ramoAtuacao;
    }

    public void setRamoAtuacao(IssqnRamoAtuacao ramoAtuacao) {
        this.ramoAtuacao = ramoAtuacao;
    }

    public Date getDataMax() {
        return dataMax;
    }

    public void setDataMax(Date dataMax) {
        this.dataMax = dataMax;
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

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getTipoCobranca() {
        return tipoCobranca;
    }

    public void setTipoCobranca(Integer tipoCobranca) {
        this.tipoCobranca = tipoCobranca;
    }

    public String getTipoRelatorio() {
        return tipoRelatorio;
    }

    public void setTipoRelatorio(String tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }

}
