/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author David Couto
 */
package br.com.eddydata.bean.geral;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.entidade.geo.Cidade;
import br.com.eddydata.entidade.geral.Pessoa;
import br.com.eddydata.entidade.geral.Profissao;
import br.com.eddydata.entidade.geral.Religiao;
import br.com.eddydata.entidade.referencia.Cor;
import br.com.eddydata.entidade.referencia.EstadoCivil;
import br.com.eddydata.entidade.referencia.GrauInstrucao;
import br.com.eddydata.entidade.referencia.Natureza;
import br.com.eddydata.entidade.referencia.OrgaoExpedidor;
import br.com.eddydata.entidade.referencia.Sexo;
import br.com.eddydata.entidade.referencia.Situacao;
import br.com.eddydata.entidade.referencia.Sociedade;
import br.com.eddydata.entidade.referencia.TipoPessoa;
import br.com.eddydata.entidade.referencia.TipoSanguineo;
import br.com.eddydata.entidade.referencia.UfRg;
import br.com.eddydata.servico.admin.CidadeServico;
import br.com.eddydata.servico.admin.PessoaServico;
import br.com.eddydata.servico.admin.ProfissaoServico;
import br.com.eddydata.servico.admin.ReligiaoServico;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class PessoaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private PessoaServico pessoaServico;

    @EJB
    private ProfissaoServico profissaoServico;

    @EJB
    private ReligiaoServico religiaoServico;

    @EJB
    private CidadeServico cidadeServico;

    private Pessoa selectedRegistro;
    private List<Pessoa> listRegistro;
    private List<Profissao> listProfissao;
    private List<Religiao> listReligiao;
    private List<Cidade> listCidadeVotacao;
    private List<Cidade> listCidadeNascimento;
    private List<Cidade> listCidadeMigracao;
    private OrgaoExpedidor[] listOrgaoExpedidor;
    private GrauInstrucao[] listGrauInstrucao;
    private TipoSanguineo[] listTipoSanguineo;
    private Sexo[] listSexo;
    private EstadoCivil[] listEstadoCivil;
    private Cor[] listCor;
    private UfRg[] listUfRg;
    private Situacao[] listSituacao;
    private Natureza[] listNatureza;
    private Sociedade[] listSociedade;
    private String textoFiltro;
    private boolean transacao = false;

    @PostConstruct
    public void init() {
        if (selectedRegistro == null) {
            carregarListagem();
        }
    }

    public void filtrar() {
        if (textoFiltro != null && !"".equals(textoFiltro)) {
            try {
                listRegistro = pessoaServico.getPessoasPorNomeLimite(textoFiltro, 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao pesquisar registros");
                System.out.println("Erro ao pesquisar registros\n" + ex.getMessage());
            }
        }
    }

    private void novo() {
        selectedRegistro = new Pessoa();
    }

    public void adicionar() {
        novo();
    }

    public void alterar(Pessoa registroSelecionado) {
        selectedRegistro = registroSelecionado;
    }

    public void cancelar() {
        textoFiltro = "";
        carregarListagem();
    }

    public synchronized void salvar() {
        preSalvar();
    }

    public synchronized void salvarIncluir() {
        if (preSalvar()) {
            adicionar();
        }
    }

    private boolean preSalvar() {
        boolean retorno = salvarRegistro();
        transacao = false;
        return retorno;
    }

    private boolean salvarRegistro() {
        if (transacao) {
            Funcao.avisoErro("Já existe uma transação em andamento!");
            return false;
        }
        transacao = true;

        try {
            selectedRegistro = pessoaServico.adicionarPessoa(selectedRegistro);
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao salvar registro");
            System.out.println("Erro ao salvar registro\n" + ex.getMessage());
            return false;
        }

        Funcao.notificacaoSucesso("Registro salvo com sucesso!");

        carregarListagem();
        return true;
    }

    public void remover() {
        try {
            pessoaServico.removerPessoa(selectedRegistro.getId());
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao remover registro");
            System.out.println("Erro ao remover registro\n" + ex.getMessage());
        }
        carregarListagem();
    }

    public void carregarListagem() {
        selectedRegistro = null;
        try {
            listRegistro = pessoaServico.getPessoasPorNomeLimite("", 100000);
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }

    public void registrarPessoaExterno() {
        if (selectedRegistro == null) {
            return;
        }

        if (preSalvar()) {
            adicionar();
        }
    }

    public EnumSet<TipoPessoa> listarTipoPessoa() {
        return EnumSet.allOf(TipoPessoa.class);
    }

    //------------ getters e setters ---------------//
    public Pessoa getSelectedRegistro() {
        return selectedRegistro;
    }

    public void setSelectedRegistro(Pessoa selectedRegistro) {
        this.selectedRegistro = selectedRegistro;
    }

    public List<Pessoa> getListRegistro() {
        return listRegistro;
    }

    public void setListRegistro(List<Pessoa> listRegistro) {
        this.listRegistro = listRegistro;
    }

    public String getTextoFiltro() {
        return textoFiltro;
    }

    public void setTextoFiltro(String textoFiltro) {
        this.textoFiltro = textoFiltro;
    }

    public Situacao[] getListSituacao() {
        if (listSituacao == null || listSituacao.length == 0) {
            listSituacao = Situacao.values();
        }
        return listSituacao;
    }

    public void setListSituacao(Situacao[] listSituacao) {
        this.listSituacao = listSituacao;
    }

    public OrgaoExpedidor[] getListOrgaoExpedidor() {
        if (listOrgaoExpedidor == null || listOrgaoExpedidor.length == 0) {
            listOrgaoExpedidor = OrgaoExpedidor.values();
        }
        return listOrgaoExpedidor;
    }

    public void setListOrgaoExpedidor(OrgaoExpedidor[] listOrgaoExpedidor) {
        this.listOrgaoExpedidor = listOrgaoExpedidor;
    }

    public UfRg[] getListUfRg() {
        if (listUfRg == null || listUfRg.length == 0) {
            listUfRg = UfRg.values();
        }
        return listUfRg;
    }

    public void setListUfRg(UfRg[] listUfRg) {
        this.listUfRg = listUfRg;
    }

    public Cor[] getListCor() {
        if (listCor == null || listCor.length == 0) {
            listCor = Cor.values();
        }
        return listCor;
    }

    public void setListCor(Cor[] listCor) {
        this.listCor = listCor;
    }

    public Sexo[] getListSexo() {
        if (listSexo == null || listSexo.length == 0) {
            listSexo = Sexo.values();
        }
        return listSexo;
    }

    public void setListSexo(Sexo[] listSexo) {
        this.listSexo = listSexo;
    }

    public GrauInstrucao[] getListGrauInstrucao() {
        if (listGrauInstrucao == null || listGrauInstrucao.length == 0) {
            listGrauInstrucao = GrauInstrucao.values();
        }
        return listGrauInstrucao;
    }

    public void setListGrauInstrucao(GrauInstrucao[] listGrauInstrucao) {
        this.listGrauInstrucao = listGrauInstrucao;
    }

    public TipoSanguineo[] getListTipoSanguineo() {
        if (listTipoSanguineo == null || listTipoSanguineo.length == 0) {
            listTipoSanguineo = TipoSanguineo.values();
        }
        return listTipoSanguineo;
    }

    public void setListTipoSanguineo(TipoSanguineo[] listTipoSanguineo) {
        this.listTipoSanguineo = listTipoSanguineo;
    }

    public Natureza[] getListNatureza() {
        if (listNatureza == null || listNatureza.length == 0) {
            listNatureza = Natureza.values();
        }
        return listNatureza;
    }

    public void setListNatureza(Natureza[] listNatureza) {
        this.listNatureza = listNatureza;
    }

    public Sociedade[] getListSociedade() {
        if (listSociedade == null || listSociedade.length == 0) {
            listSociedade = Sociedade.values();
        }
        return listSociedade;
    }

    public void setListSociedade(Sociedade[] listSociedade) {
        this.listSociedade = listSociedade;
    }

    public EstadoCivil[] getListEstadoCivil() {
        if (listEstadoCivil == null || listEstadoCivil.length == 0) {
            listEstadoCivil = EstadoCivil.values();
        }
        return listEstadoCivil;
    }

    public void setListEstadoCivil(EstadoCivil[] listEstadoCivil) {
        this.listEstadoCivil = listEstadoCivil;
    }

    public List<Profissao> getListProfissao() {
        if (listProfissao == null && selectedRegistro != null) {
            try {
                listProfissao = profissaoServico.obterProfissoes("", 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao buscar profissoes");
                System.out.println("Erro ao buscar profissoes\n" + ex.getMessage());
            }
        }
        return listProfissao;
    }

    public void setListProfissao(List<Profissao> listProfissao) {
        this.listProfissao = listProfissao;
    }

    public List<Religiao> getListReligiao() {
        if (listReligiao == null && selectedRegistro != null) {
            try {
                listReligiao = religiaoServico.obterReligioes("", 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao buscar religioes");
                System.out.println("Erro ao buscar religioes\n" + ex.getMessage());
            }
        }
        return listReligiao;
    }

    public void setListReligiao(List<Religiao> listReligiao) {
        this.listReligiao = listReligiao;
    }

    public List<Cidade> getListCidadeVotacao() {
        if (listCidadeVotacao == null && selectedRegistro != null) {
            try {
                listCidadeVotacao = cidadeServico.getCidades();
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao buscar cidade votacao");
                System.out.println("Erro ao buscar cidade votacao\n" + ex.getMessage());
            }
        }
        return listCidadeVotacao;
    }

    public void setListCidadeVotacao(List<Cidade> listCidadeVotacao) {
        this.listCidadeVotacao = listCidadeVotacao;
    }

    public List<Cidade> getListCidadeNascimento() {
        if (listCidadeNascimento == null && selectedRegistro != null) {
            try {
                listCidadeNascimento = cidadeServico.getCidades();
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao buscar cidade nascimento");
                System.out.println("Erro ao buscar cidade nascimento\n" + ex.getMessage());
            }
        }
        return listCidadeNascimento;
    }

    public void setListCidadeNascimento(List<Cidade> listCidadeNascimento) {
        this.listCidadeNascimento = listCidadeNascimento;
    }

    public List<Cidade> getListCidadeMigracao() {
        if (listCidadeMigracao == null && selectedRegistro != null) {
            try {
                listCidadeMigracao = cidadeServico.getCidades();
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao buscar cidade nascimento");
                System.out.println("Erro ao buscar cidade nascimento\n" + ex.getMessage());
            }
        }
        return listCidadeMigracao;
    }

    public void setListCidadeMigracao(List<Cidade> listCidadeMigracao) {
        this.listCidadeMigracao = listCidadeMigracao;
    }

}
