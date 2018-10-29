/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Thiago Martos
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.entidade.geral.Pessoa;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.servico.issqn.AlvaraServico;
import br.com.eddydata.servico.issqn.IssqnServico;
import br.com.eddydata.suporte.BusinessViolation;
import br.com.eddydata.suporte.DateExtended;
import br.com.eddydata.suporte.Util;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class AlvaraBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private AlvaraServico servico;

    @EJB
    private IssqnServico servicoIssqn;

    @Inject
    private GlobalBean global;

    private Issqn contribuinte;
    private String ordem = "I.INSCRICAO";
    private String inscricao;
    private String cnpjCpf;
    private Date dataAbertura;
    private Integer inscricaoIni;
    private Integer inscricaoFim;
    private Integer alvaraIni;
    private Integer alvaraFim;
    private String n_protocolo;
    private String tipoAlvara;

    @PostConstruct
    public void init() {

    }

    public void limpar() {
        inscricao = "";
        contribuinte = new Issqn();
        cnpjCpf = "";
        dataAbertura = null;
        inscricaoIni = null;
        inscricaoFim = null;
        n_protocolo = "";
        tipoAlvara = null;
    }

    public void imprimir() {
        StringBuilder pesquisa = new StringBuilder();

        if (inscricao == null || inscricao.equals("")) {
            Funcao.avisoAtencao("Favor, selecione o contribuinte");
            return;
        }
        
        if (tipoAlvara == null) {
            Funcao.avisoAtencao("Favor, selecione o Tipo de Licença");
            return;
        }
        
        if (inscricaoIni != null || inscricaoFim != null) {
            if (inscricaoIni == null || inscricaoFim == null) {
                Funcao.avisoAtencao("Informe os dois valores para as inscrições");
                return;
            }
        }

        if (inscricao != null && !inscricao.equals("")) {
            pesquisa.append("AND I.INSCRICAO = ").append(Util.quotarStr(inscricao));
        }
        if (inscricaoIni != null && inscricaoFim != null) {
            pesquisa.append(" AND I.INSCRICAO BETWEEN ").append(Util.quotarStr(inscricaoIni))
                    .append(" AND ").append(inscricaoFim);
        }
        if (dataAbertura != null) {
            pesquisa.append(" AND I.DT_ABERTURA = ").append(Util.quotarStr(Util.parseSqlDate(dataAbertura)));
        }

        if (contribuinte.getDtValidadeAlvara() == null) {
            Calendar c = new GregorianCalendar(global.getExercicio().getAno(), 11, 31);
            contribuinte.setDtValidadeAlvara(c.getTime());
        }
        String data_formatada = DateExtended.dateExtended(new java.sql.Date(contribuinte.getDtValidadeAlvara().getTime()), false);
        ordem = "ORDER BY LOGRADOURO";

        try {
            servico.imprimirAlvara(global.getUsuarioLogado().getOrgao().getIdOrgao(),
                    ordem, pesquisa.toString(), contribuinte,
                    data_formatada, global.getExercicio().getAno(), contribuinte.getDtValidadeAlvara(), tipoAlvara, global.getUsuarioLogado().getLogin(), contribuinte.getObsAlvara());
            contribuinte.setDtAlvara(new java.util.Date());
            servicoIssqn.salvarIssqn(contribuinte);
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível fazer a impressão!");
            System.out.println(ex.getMessage());
        }
    }

    public void imprimirLicencasVencidas() {
        try {
            servico.imprimirLicencasVencidas(global.getUsuarioLogado().getOrgao().getIdOrgao(), "", "e.dataValidade");
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível gerar o relatório");
            System.out.println(ex.getMessage());
        }
    }

    public void buscaPorInscricao() {
        if (inscricao == null || inscricao.trim().equals("")) {
            limpar();
            return;
        }
        try {
            contribuinte = servicoIssqn.obterIssqnPorInscricao(inscricao, global.getExercicio().getAno());
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
        contribuinte = servicoIssqn.obterIssqnPorCPF(Util.removerMascara(cnpjCpf));
        selecionarIssqn();
    }

    //------------ getters e setters ---------------//
    public Issqn getContribuinte() {
        if (contribuinte == null) {
            contribuinte = new Issqn();
        }
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

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Integer getInscricaoIni() {
        return inscricaoIni;
    }

    public void setInscricaoIni(Integer inscricaoIni) {
        this.inscricaoIni = inscricaoIni;
    }

    public Integer getInscricaoFim() {
        return inscricaoFim;
    }

    public void setInscricaoFim(Integer inscricaoFim) {
        this.inscricaoFim = inscricaoFim;
    }

    public Integer getAlvaraIni() {
        return alvaraIni;
    }

    public void setAlvaraIni(Integer alvaraIni) {
        this.alvaraIni = alvaraIni;
    }

    public Integer getAlvaraFim() {
        return alvaraFim;
    }

    public void setAlvaraFim(Integer alvaraFim) {
        this.alvaraFim = alvaraFim;
    }

    public String getN_protocolo() {
        return n_protocolo;
    }

    public void setN_protocolo(String n_protocolo) {
        this.n_protocolo = n_protocolo;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }

    public TipoLicenca[] getTipoLicenca() {
        return TipoLicenca.values();
    }

    public String getTipoAlvara() {
        return tipoAlvara;
    }

    public void setTipoAlvara(String tipoAlvara) {
        this.tipoAlvara = tipoAlvara;
    }

}
