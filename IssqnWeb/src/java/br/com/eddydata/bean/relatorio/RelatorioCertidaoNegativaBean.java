/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Thiago Martos
 */
package br.com.eddydata.bean.relatorio;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.entidade.geral.Pessoa;
import br.com.eddydata.entidade.issqn.Issqn;
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
public class RelatorioCertidaoNegativaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IssqnServico servico;

    @Inject
    private GlobalBean global;

    private Issqn contribuinte;
    private String inscricao;
    private String cnpjCpf;
    private Date validade;
    private Integer exercicio;

    @PostConstruct
    public void init() {

    }

    public void limpar(){
        inscricao = "";
        contribuinte = new Issqn();
        cnpjCpf = "";
        validade = null;
    }
    
    public void imprimir() {
        try {
            servico.imprimirCertidaoNegativa(global.getUsuarioLogado().getOrgao().getIdOrgao(),inscricao,contribuinte.getDtValidadeAlvara(),global.getExercicio().getAno(),
                    contribuinte.getPessoa().getNome(),contribuinte.getPessoa().getId());
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível fazer a impressão!");
            System.out.println(ex.getMessage());
        }
    }

    public void buscaPorInscricao() {
        if (inscricao == null || inscricao.trim().equals("")) {
            limpar();
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
    
     public void buscaPorCnpj() {
        if (cnpjCpf == null || cnpjCpf.trim().equals("")) {
            return;
        }
        contribuinte = servico.obterIssqnPorCPF(Util.removerMascara(cnpjCpf));
        selecionarIssqn();
    }

    //------------ getters e setters ---------------//

    public IssqnServico getServico() {
        return servico;
    }

    public void setServico(IssqnServico servico) {
        this.servico = servico;
    }

    public GlobalBean getGlobal() {
        return global;
    }

    public void setGlobal(GlobalBean global) {
        this.global = global;
    }

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

    public Integer getExercicio() {
        return exercicio;
    }

    public void setExercicio(Integer exercicio) {
        this.exercicio = exercicio;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }
    
    

}
