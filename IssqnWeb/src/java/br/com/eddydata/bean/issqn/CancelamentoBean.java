/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnMotivoCancel;
import br.com.eddydata.entidade.issqn.IssqnTipoCobranca;
import br.com.eddydata.servico.issqn.CalculoServico;
import br.com.eddydata.servico.issqn.IssqnMotivoCancelamentoServico;
import br.com.eddydata.servico.issqn.IssqnServico;
import br.com.eddydata.servico.issqn.TipoCobrancaServico;
import br.com.eddydata.suporte.BusinessViolation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class CancelamentoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String inscricao;
    private Issqn issqn;
    private Boolean parcelasProporcionais = false;
    private List<IssqnTipoCobranca> listTipoCobranca = new ArrayList<>();
    private IssqnMotivoCancel motivo;
    private String processo;
    private List<IssqnMotivoCancel> motivoList = null;
    private Integer tipoCobranca = 0;
    private Integer tipoCobrancaGeral = 0;
    private String opcaoCalculo;

    @EJB
    private IssqnServico issqnServico;
    @EJB
    private CalculoServico calculoServico;
    @EJB
    private IssqnMotivoCancelamentoServico motivoCancelamentoServico;
    @EJB
    private TipoCobrancaServico cobrancaServico;

    @Inject
    private GlobalBean global;

    @PostConstruct
    public void init() {
        setOpcaoCalculo("C");
        listTipoCobranca = cobrancaServico.getTodosCobrancas(global.getExercicio().getAno());
        tipoCobranca = 0;
        tipoCobrancaGeral = 0;
    }

    public void limpar() {
        inscricao = null;
        issqn = null;
        motivo = null;
        processo = null;
        tipoCobranca = 0;
        listTipoCobranca = cobrancaServico.getTodosCobrancas(global.getExercicio().getAno());
    }

    public void cancelar() {
        if (tipoCobranca == null) {
            tipoCobranca = 0;
        }
        if (opcaoCalculo.equals("G")) {
            try {
                calculoServico.cancelarGeral(global.getExercicio().getAno(), tipoCobrancaGeral);
                Funcao.avisoSucesso("Cancelamento geral efetuado com sucesso!");
            } catch (BusinessViolation ex) {
                Funcao.avisoErro("Já existe pagamentos, exclusão não permitida!");
            }
        } else {
            if (motivo == null) {
                Funcao.avisoAtencao("Motivo não informado ou muito curto");
                return;
            }
            try {
                calculoServico.cancelarMovimento(issqn, motivo, processo, tipoCobranca);
                Funcao.avisoSucesso("Cálculo cancelado com sucesso!");
                limpar();
            } catch (Exception e) {
                Funcao.avisoErro(e.getMessage());
            }
        }
    }

    public void buscaPorInscricao() {
        try {
            if (!inscricao.isEmpty()) {
                setIssqn(issqnServico.obterIssqnPorInscricao(inscricao, global.getExercicio().getAno()));
            }
        } catch (BusinessViolation ex) {
            Funcao.avisoErro(ex.getMessage());
            System.out.println("Erro ao buscar por inscrição\n" + ex.getMessage());
        }
    }

    public List<IssqnMotivoCancel> buscaMotivoCancelamento() {
        try {
            motivoList = new ArrayList<>();
            motivoList = motivoCancelamentoServico.obterMotivosCancelamento("", 30);
        } catch (Exception ex) {
            Funcao.avisoErro(ex.getMessage());
            System.out.println("Erro ao buscar por inscrição\n" + ex.getMessage());
        }

        return motivoList;
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

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public IssqnMotivoCancel getMotivo() {
        return motivo;
    }

    public void setMotivo(IssqnMotivoCancel motivo) {
        this.motivo = motivo;
    }

    public List<IssqnTipoCobranca> getListTipoCobranca() {
        return listTipoCobranca;
    }

    public void setListTipoCobranca(List<IssqnTipoCobranca> listTipoCobranca) {
        this.listTipoCobranca = listTipoCobranca;
    }

    public Integer getTipoCobranca() {
        return tipoCobranca;
    }

    public void setTipoCobranca(Integer tipoCobranca) {
        this.tipoCobranca = tipoCobranca;
    }

    public String getOpcaoCalculo() {
        return opcaoCalculo;
    }

    public void setOpcaoCalculo(String opcaoCalculo) {
        this.opcaoCalculo = opcaoCalculo;
    }

    public Integer getTipoCobrancaGeral() {
        return tipoCobrancaGeral;
    }

    public void setTipoCobrancaGeral(Integer tipoCobrancaGeral) {
        this.tipoCobrancaGeral = tipoCobrancaGeral;
    }

}
