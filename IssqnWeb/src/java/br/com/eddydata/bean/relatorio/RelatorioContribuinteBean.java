/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Thiago Martos
 */
package br.com.eddydata.bean.relatorio;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.bean.UtilBean;
import br.com.eddydata.entidade.geral.Pessoa;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnAtividade;
import br.com.eddydata.entidade.issqn.IssqnCategoria;
import br.com.eddydata.entidade.issqn.IssqnCnae;
import br.com.eddydata.entidade.issqn.IssqnRamoAtuacao;
import br.com.eddydata.servico.issqn.IssqnCategoriaServico;
import br.com.eddydata.servico.issqn.IssqnRamoAtuacaoServico;
import br.com.eddydata.servico.issqn.IssqnServico;
import br.com.eddydata.suporte.BusinessViolation;
import br.com.eddydata.suporte.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class RelatorioContribuinteBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IssqnServico servico;
    @EJB
    private IssqnRamoAtuacaoServico ramoAtuacaoServico;
    @EJB
    private IssqnCategoriaServico categoriaServico;

    @Inject
    private GlobalBean global;
    @Inject
    private UtilBean util;

    private Issqn contribuinte;
    private Boolean semCpf = false;
    private Boolean chkAtivo = false;
    private Boolean chkIsento = false;
    private Boolean chkInativo = false;
    private Boolean chkEncerrado = false;
    private Boolean chkSuspenso = false;
    private Boolean chkSimples = false;
    private Boolean chkMEI = false;
    private Boolean chkDuplicado = false;
    private String tipoPessoa = "*";
    private String ordem = "I.INSCRICAO";
    private String inscricao;
    private String cnpjCpf;
    private String telefone;
    private IssqnCategoria categoria;
    private IssqnRamoAtuacao ramoAtuacao;
    private Date dataInicio;
    private Date dataFim;
    private Integer exercicio;
    private List<IssqnRamoAtuacao> listRamoAtuacao;
    private List<IssqnCategoria> listCategorias;    
    private IssqnCnae selectedAtividade;
    private int mesCalculo; 
    private String anoCalculo;

    @PostConstruct
    public void init() {

    }

    public void limpar() {
        inscricao = "";
        contribuinte = new Issqn();
        cnpjCpf = "";
        semCpf = false;
        chkAtivo = false;
        chkIsento = false;
        chkInativo = false;
        chkEncerrado = false;
        chkSuspenso = false;
        chkSimples = false;
        chkMEI = false;
        tipoPessoa = "*";
        ordem = "I.INSCRICAO";
        categoria = new IssqnCategoria();
        ramoAtuacao = new IssqnRamoAtuacao();
        dataInicio = null;
        dataFim = null;
        exercicio = null;
        listRamoAtuacao = null;
        listCategorias = null;
    }

    public void imprimir() {
        StringBuilder pesquisa = new StringBuilder();

        pesquisa.append(" AND ");
        if (exercicio != null && exercicio > 0) {
            pesquisa.append(" I.ID_EXERCICIO = ").append(exercicio);
        } else {
            pesquisa.append(" I.ID_EXERCICIO = ").append(global.getExercicio().getAno());
        }
        if (!inscricao.isEmpty()) {
            pesquisa.append(" AND I.INSCRICAO = '").append(inscricao).append("'");
        }
        if (telefone != null && !telefone.equals("")) {
            pesquisa.append(" AND I.FONE = '").append(telefone).append("'");
        }
        if(selectedAtividade != null){
            pesquisa.append(" AND ICI.ATIVIDADE_PRIMARIA = 't' AND ICN.ID_CNAE = '").append(selectedAtividade.getId()).append("'");
        }
        if (categoria != null) {
            pesquisa.append(" AND I.ID_CATEGORIA = '").append(categoria.getId()).append("'");
        }
        if (ramoAtuacao != null) {
            pesquisa.append(" AND I.ID_RAMO_ATUACAO = '").append(ramoAtuacao.getId()).append("'");
        }
        if (dataInicio != null && dataFim != null) {
            pesquisa.append(" AND ")
                    .append(" I.ALVARA_PROVISORIO = TRUE AND (I.DT_ALVARA) + INTERVAL '60 DAYS' BETWEEN ")
                    .append(Util.parseSqlDate(dataInicio))
                    .append(" AND ")
                    .append(Util.parseSqlDate(dataFim));
        }
        if(mesCalculo > 0){
            pesquisa.append(" AND EXTRACT(MONTH FROM IM.DT_CALCULO) = ").append(mesCalculo);
        }
        if(!anoCalculo.isEmpty()){
            pesquisa.append(" AND EXTRACT(YEAR FROM IM.DT_CALCULO) = ").append(anoCalculo);
        }
        if (chkAtivo) {
            pesquisa.append(" AND I.DT_ENCERRAMENTO IS NULL \nAND I.DT_BLOQUEADO IS NULL AND I.DT_INATIVIDADE IS NULL ");
        }
        if (chkInativo) {
            pesquisa.append(" AND (I.DT_ENCERRAMENTO IS NOT NULL \nOR I.DT_BLOQUEADO IS NOT NULL OR I.DT_INATIVIDADE IS NOT NULL) ");
        }
        if (chkIsento) {
            pesquisa.append(" AND UPEER(I.ISENTO) = 'TRUE' ");
        }
        if (chkEncerrado) {
            pesquisa.append(" AND I.ENCERRADO = TRUE ");
        }
        if (chkSimples) {
            pesquisa.append(" AND I.SIMPLES_NACIONAL = TRUE");
        }
        if (chkSuspenso) {
            pesquisa.append(" AND I.DT_SUSPENSAO_FIM > ").append(Util.quotarStr(new Date()));
        }
        if (chkMEI) {
            pesquisa.append(" AND COALESCE(I.MEI,FALSE) = TRUE");
        }
        if (chkDuplicado) {
            pesquisa.append(" AND (SELECT COUNT(*) FROM PESSOA P2 INNER JOIN ISSQN I ON (P2.ID_PESSOA = I.ID_PESSOA) \n"
                    + "WHERE CPF_CNPJ = P.CPF_CNPJ AND LENGTH(CPF_CNPJ) > 1 AND NOME = P.NOME " + pesquisa + ") > 1");
        }
        if (tipoPessoa != null && !tipoPessoa.equals("*")) {
            if (tipoPessoa.equals("F")) {
                pesquisa.append(" AND P.TP_PESSOA = 0 ");
            } else if (tipoPessoa.equals("J")) {
                pesquisa.append(" AND P.TP_PESSOA <> 0 ");
            }
        }

        try {
            servico.imprimirRelacaoContribuintes(
                    global.getUsuarioLogado().getOrgao().getIdOrgao(),
                    semCpf, pesquisa.toString(), ordem, global.getUsuarioLogado().getLogin()
            );
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
            categoria = contribuinte.getCategoria();
            ramoAtuacao = contribuinte.getRamoAtuacao();
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
        categoria = contribuinte.getCategoria();
        ramoAtuacao = contribuinte.getRamoAtuacao();
    }

    public void buscaPorCnpj() {
        if (cnpjCpf == null || cnpjCpf.trim().equals("")) {
            return;
        }
        contribuinte = servico.obterIssqnPorCPF(Util.removerMascara(cnpjCpf));
        selecionarIssqn();
    }
    
    public ArrayList<IssqnCnae> doFiltroAtividade(String nome) {
        List<IssqnCnae> listar;
        ArrayList<IssqnCnae> listaNome = new ArrayList<>();
        nome = nome.replace("-", "").replace("/", "").replace(".", "");
        try {
            if (Util.isNumeric(nome)) {
                listar = servico.obterCnaePorCodigo(nome + "%");
            } else {
                nome = util.retiraAcento(nome);

                listar = servico.obterCnaePorNome(nome, null);
            }
            for (IssqnCnae p : listar) {
                listaNome.add(p);
            }
        } catch (Exception e) {
            System.out.println("Não foi possivel preencher lista para consulta " + e.getMessage());
        }
        return listaNome;
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

    public Boolean getChkDuplicado() {
        return chkDuplicado;
    }

    public void setChkDuplicado(Boolean chkDuplicado) {
        this.chkDuplicado = chkDuplicado;
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
        if (exercicio == null) {
            exercicio = global.getExercicio().getAno();
        }
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

    public List<IssqnRamoAtuacao> getListRamoAtuacao() {
        if (listRamoAtuacao == null) {
            try {
                listRamoAtuacao = ramoAtuacaoServico.obterRamosAtuacao("", 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao buscar ramos de atuacao");
                System.out.println("Erro ao buscar ramos de atuacao\n" + ex.getMessage());
            }
        }
        return listRamoAtuacao;
    }

    public void setListRamoAtuacao(List<IssqnRamoAtuacao> listRamoAtuacao) {
        this.listRamoAtuacao = listRamoAtuacao;
    }

    public List<IssqnCategoria> getListCategorias() {
        if (listCategorias == null) {
            try {
                listCategorias = categoriaServico.obterCategorias("", 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao buscar categorias");
                System.out.println("Erro ao buscar categorias\n" + ex.getMessage());
            }
        }
        return listCategorias;
    }

    public void setListCategorias(List<IssqnCategoria> listCategorias) {
        this.listCategorias = listCategorias;
    }
    public IssqnCnae getSelectedAtividade() {
        return selectedAtividade;
    }

    public void setSelectedAtividade(IssqnCnae selectedAtividade) {
        this.selectedAtividade = selectedAtividade;
    }
    
    public int getMesCalculo() {
        return mesCalculo;
    }

    public void setMesCalculo(int mesCalculo) {
        this.mesCalculo = mesCalculo;
    }

    public String getAnoCalculo() {
        return anoCalculo;
    }

    public void setAnoCalculo(String anoCalculo) {
        this.anoCalculo = anoCalculo;
    }

}
