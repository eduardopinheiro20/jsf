/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Thiago Martos
 */
package br.com.eddydata.bean.relatorio;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.entidade.geral.Banco;
import br.com.eddydata.entidade.geral.Pessoa;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.servico.admin.BancoServico;
import br.com.eddydata.servico.issqn.IssqnServico;
import br.com.eddydata.suporte.BusinessViolation;
import br.com.eddydata.suporte.Util;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class RelatorioContribuinteDevedorBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IssqnServico servico;
    @EJB
    private BancoServico bancoServico;

    @Inject
    private GlobalBean global;

    private Issqn contribuinte;

    private String ordem = "II.INSCRICAO";
    private String opcao = "G";
    private boolean licenca = false;
    private boolean iss = false;
    private boolean alvaraVencido = false;
    private boolean taxa = false;
    private String exercicio = "";
    private String inscricao;
    private List<Banco> listBanco;
    private Banco banco;
    private String cnpjCpf;

    @PostConstruct
    public void init() {

    }

    public void imprimir() {
        String ano = "";

        if (opcao.equals("E")) {
            if (exercicio.length() > 0) {
                ano = exercicio;
            } else {
                Funcao.avisoAtencao("Informe um exercício!");
                return;
            }
        } else {
            ano = String.valueOf(global.getExercicio().getAno());
        }

        StringBuilder pesquisa = new StringBuilder();

        if (opcao.equals("P")) {
            if (inscricao.length() > 0) {
                pesquisa.append("trim(II.INSCRICAO) = ").append(Util.quotarStr(inscricao.trim()));
            } else {
                Funcao.avisoAtencao("Contribuinte não selecionado!");
                return;
            }
        } else if (opcao.equals("G")) {
            if (pesquisa.length() != 0) {
                pesquisa.append(" AND ");
            }
            if (iss && licenca) {
                pesquisa.append("  MI.TP_MOVIMENTO IN (1, 2, 3)");
            } else if (licenca) {
                pesquisa.append("  MI.TP_MOVIMENTO = 1");
            } else if (iss) {
                pesquisa.append("  MI.TP_MOVIMENTO IN (2, 3)");
            }

            if (alvaraVencido) {
                if (pesquisa.length() != 0) {
                    pesquisa.append(" AND");
                }
                pesquisa.append(" DT_PAGAMENTO IS NULL");
            } 
        }else if(opcao.equals("B")){
            if(banco != null){
                if(pesquisa.length() != 0){
                    pesquisa.append(" AND ");
                }
                pesquisa.append(" BAN.ID_BANCO = ").append(Util.quotarStr(banco.getId()));
            }else{
                Funcao.avisoAtencao("Banco não selecionado!");
                return;
            }
        }
        
        try {
            servico.imprimirRelacaoContribuintesDevedores(
                    global.getUsuarioLogado().getOrgao().getIdOrgao(), pesquisa.toString(), ordem, ano, taxa,global.getUsuarioLogado().getLogin());
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível fazer a impressão!");
            System.out.println(ex.getMessage());
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
    }

    public void buscaPorInscricao() {
        if (inscricao == null || inscricao.trim().equals("")) {
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
    
    public void buscaPorCnpj() {
        if (cnpjCpf == null || cnpjCpf.trim().equals("")) {
            return;
        }
        contribuinte = servico.obterIssqnPorCPF(Util.removerMascara(cnpjCpf));
        selecionarIssqn();
    }
    

//    GETTERS && SETTERS
    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getOpcao() {
        return opcao;
    }

    public void setOpcao(String opcao) {
        this.opcao = opcao;
    }

    public boolean isLicenca() {
        return licenca;
    }

    public void setLicenca(boolean licenca) {
        this.licenca = licenca;
    }

    public boolean isIss() {
        return iss;
    }

    public void setIss(boolean iss) {
        this.iss = iss;
    }

    public boolean isAlvaraVencido() {
        return alvaraVencido;
    }

    public void setAlvaraVencido(boolean alvaraVencido) {
        this.alvaraVencido = alvaraVencido;
    }

    public boolean isTaxa() {
        return taxa;
    }

    public void setTaxa(boolean taxa) {
        this.taxa = taxa;
    }

    public String getExercicio() {
        return exercicio;
    }

    public void setExercicio(String exercicio) {
        this.exercicio = exercicio;
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

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }
    
    public List<Banco> getListBanco() {
        if (listBanco == null) {
            try {
                listBanco = bancoServico.obterBancos("", 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao buscar banco");
                System.out.println("Erro ao buscar banco\n" + ex.getMessage());
            }
        }
        return listBanco;
    }
    
    public void setListBanco(List<Banco> listBanco) {
        this.listBanco = listBanco;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }    
}
