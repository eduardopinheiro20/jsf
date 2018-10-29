/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.dto.issqn.ParcelaDTO;
import br.com.eddydata.entidade.geral.Pessoa;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnMotivoCancel;
import br.com.eddydata.entidade.issqn.IssqnMovimento;
import br.com.eddydata.entidade.issqn.IssqnMovimentoParcela;
import br.com.eddydata.servico.issqn.CalculoServico;
import br.com.eddydata.servico.issqn.IssqnMovimentoParcelaServico;
import br.com.eddydata.servico.issqn.IssqnMovimentoServico;
import br.com.eddydata.servico.issqn.IssqnServico;
import br.com.eddydata.servico.issqn.ParcelasServico;
import br.com.eddydata.suporte.BusinessViolation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class CalculoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String opcaoCalculo;
    private String inscricao;
    private Issqn issqn;
    private List<IssqnMovimento> issqnMovList;
    private List<IssqnMovimentoParcela> issqnMovParcelaList;
    private IssqnMovimento issqnMovSelecionado;
    private Date dtAbertura = null;
    private Boolean parcelasProporcionais = false;
    private Boolean cancelarTodos = false;
    private Issqn contribuinte;
    private String movimento;
    private Integer tipoCobrancaBoleto = 1;

    @EJB
    private IssqnServico issqnServico;
    @EJB
    private IssqnMovimentoServico issqnMovimentoServico;
    @EJB
    private IssqnMovimentoParcelaServico issqnMovimentoParcelaServico;
    @EJB
    private CalculoServico calculoServico;

    @Inject
    private GlobalBean global;

    List<ParcelaDTO> existeCalculo = new ArrayList<>();

    @PostConstruct
    public void init() {
        setOpcaoCalculo("C");
        tipoCobrancaBoleto = 1;
    }

    public void buscaPorInscricao() {
        try {
            setIssqn(issqnServico.obterIssqnPorInscricao(inscricao, global.getExercicio().getAno()));
        } catch (BusinessViolation ex) {
            Funcao.avisoErro(ex.getMessage());
            System.out.println("Erro ao buscar por inscrição\n" + ex.getMessage());
        }
    }

    public void selecionarIssqn() {
        if (contribuinte == null) {
            Funcao.avisoAtencao("Contribuinte não selecionado");
            return;
        }
        inscricao = contribuinte.getInscricao();

        Pessoa p = contribuinte.getPessoa();
        if (p != null) {
            contribuinte.setCnpjCpf(p.getCpfCnpj());
        }

        issqnMovList = issqnMovimentoServico.buscaMovimentoPorIss(contribuinte.getId(), global.getExercicio().getAno());
        movimento = issqnMovList.size() > 0 ? issqnMovList.get(0).getId().toString() : "";
    }

    public void onRowSelect(SelectEvent event) {

    }

    public void selecionaLinha(IssqnMovimento issqn) {
        issqnMovParcelaList = issqnMovimentoParcelaServico.buscarMovimentoPorIdMovimento(issqn.getId());
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgParcela').show();");
    }

    public void calcular() {
        try {
            if (opcaoCalculo.equals("G")) {
                calculoServico.calculoGeral(global.getExercicio().getAno(), global.getUsuarioLogado().getNome(), parcelasProporcionais, dtAbertura,tipoCobrancaBoleto);
            } else {
                if (issqn == null) {
                    Funcao.avisoErro("Informar o contribuinte");
                    return;
                }
                calculoServico.calculoContribuinte(issqn, global.getUsuarioLogado().getNome(), parcelasProporcionais, new IssqnMotivoCancel(),tipoCobrancaBoleto);
            }
            Funcao.notificacaoSucesso("Cálculo realizado com sucesso");
        } catch (BusinessViolation bv) {
            Funcao.avisoAtencao(bv.getMessage());
        } catch (Exception e) {
            Funcao.avisoErro("Não foi possível realizar o cálculo");
        }
    }

    public String getOpcaoCalculo() {
        return opcaoCalculo;
    }

    public void setOpcaoCalculo(String opcaoCalculo) {
        this.opcaoCalculo = opcaoCalculo;
    }

    public Issqn getIssqn() {
        return issqn;
    }

    public void setIssqn(Issqn issqn) {
        this.issqn = issqn;
        if (issqn != null && issqn.getInscricao() != null) {
            inscricao = issqn.getInscricao();
        }
    }

    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    public Boolean getParcelasProporcionais() {
        return parcelasProporcionais;
    }

    public void setParcelasProporcionais(Boolean parcelasProporcionais) {
        this.parcelasProporcionais = parcelasProporcionais;
    }

    public Boolean getCancelarTodos() {
        return cancelarTodos;
    }

    public void setCancelarTodos(Boolean cancelarTodos) {
        this.cancelarTodos = cancelarTodos;
    }

    public Date getDtAbertura() {
        return dtAbertura;
    }

    public void setDtAbertura(Date dtAbertura) {
        this.dtAbertura = dtAbertura;
    }

    public Issqn getContribuinte() {
        return contribuinte;
    }

    public void setContribuinte(Issqn contribuinte) {
        this.contribuinte = contribuinte;
    }

    public String getMovimento() {
        return movimento;
    }

    public void setMovimento(String movimento) {
        this.movimento = movimento;
    }

    public List<IssqnMovimento> getIssqnMovList() {
        return issqnMovList;
    }

    public void setIssqnMovList(List<IssqnMovimento> issqnMovList) {
        this.issqnMovList = issqnMovList;
    }

    public IssqnMovimento getIssqnMovSelecionado() {
        return issqnMovSelecionado;
    }

    public void setIssqnMovSelecionado(IssqnMovimento issqnMovSelecionado) {
        this.issqnMovSelecionado = issqnMovSelecionado;
    }

    public List<IssqnMovimentoParcela> getIssqnMovParcelaList() {
        return issqnMovParcelaList;
    }

    public void setIssqnMovParcelaList(List<IssqnMovimentoParcela> issqnMovParcelaList) {
        this.issqnMovParcelaList = issqnMovParcelaList;
    }

    public List<ParcelaDTO> getExisteCalculo() {
        return existeCalculo;
    }

    public void setExisteCalculo(List<ParcelaDTO> existeCalculo) {
        this.existeCalculo = existeCalculo;
    }

    public Integer getTipoCobrancaBoleto() {
        return tipoCobrancaBoleto;
    }

    public void setTipoCobrancaBoleto(Integer tipoCobrancaBoleto) {
        this.tipoCobrancaBoleto = tipoCobrancaBoleto;
    }
    
    

}
