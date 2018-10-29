/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Thiago Martos
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.entidade.admin.Imovel;
import br.com.eddydata.entidade.geo.Bairro;
import br.com.eddydata.entidade.geo.BairroLogradouro;
import br.com.eddydata.entidade.geo.Cidade;
import br.com.eddydata.entidade.geo.Estado;
import br.com.eddydata.entidade.geral.Pessoa;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnTermoIntimacao;
import br.com.eddydata.servico.admin.BairroServico;
import br.com.eddydata.servico.admin.CidadeServico;
import br.com.eddydata.servico.admin.EstadoServico;
import br.com.eddydata.servico.admin.LogradouroServico;
import br.com.eddydata.servico.issqn.IssqnServico;
import br.com.eddydata.servico.issqn.TermoIntimacaoServico;
import br.com.eddydata.suporte.BusinessViolation;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class TermoIntimacaoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private TermoIntimacaoServico servico;
    @EJB
    private EstadoServico estadoServico;
    @EJB
    private CidadeServico cidadeServico;
    @EJB
    private BairroServico bairroServico;
    @EJB
    private LogradouroServico logradouroServico;
    @EJB
    private IssqnServico issqnServico;

    @Inject
    private GlobalBean global;

    private IssqnTermoIntimacao selectedRegistro;
    private List<IssqnTermoIntimacao> listRegistro;
    private String textoFiltro;
    private boolean transacao = false;

    private String inscricao;
    private Estado selectedEstado;
    private Cidade selectedCidade;
    private Bairro selectedBairro;
    private List<Estado> listEstado;
    private List<Cidade> listCidade;
    private List<Bairro> listBairro;
    private List<BairroLogradouro> listBairroLogradouro;

    @PostConstruct
    public void init() {
        if (selectedRegistro == null) {
            carregarListagem();
        }
    }

    private void buscarDados() {
        if (selectedRegistro == null) {
            return;
        }
        if (selectedRegistro.getBairrologradouro() != null) {
            selectedBairro = selectedRegistro.getBairrologradouro().getBairro();
            selectedCidade = selectedRegistro.getBairrologradouro().getCidade();
            selectedEstado = (selectedCidade == null ? null : selectedCidade.getEstado());
        }
        if (selectedRegistro.getIss() != null) {
            inscricao = selectedRegistro.getIss().getInscricao();
        }
    }

    public void filtrar() {
        if (textoFiltro != null && !"".equals(textoFiltro)) {
            try {
                listRegistro = servico.listar(textoFiltro, 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao pesquisar registros");
                System.out.println("Erro ao pesquisar registros\n" + ex.getMessage());
            }
        } else {
            carregarListagem();
        }
    }

    public void adicionar() {
        selectedRegistro = new IssqnTermoIntimacao();
    }

    public void alterar(IssqnTermoIntimacao registroSelecionado) {
        selectedRegistro = registroSelecionado;
        buscarDados();
    }

    public void cancelar() {
        selectedRegistro = null;
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
            selectedRegistro = servico.salvar(selectedRegistro);
        } catch (BusinessViolation ex) {
            Funcao.avisoAtencao(ex.getMessage());
            return false;
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao salvar registro");
            System.out.println("Erro ao salvar registro\n" + ex.getMessage());
            return false;
        }

        Funcao.notificacaoSucesso("Registro salvo com sucesso!");

        selectedRegistro = null;
        textoFiltro = "";
        carregarListagem();
        return true;
    }

    public void imprimir() {
        if (selectedRegistro == null) {
            Funcao.avisoAtencao("Registro não selecionado para impressão");
            return;
        }
        try {
            servico.imprimirTermoIntimacao(selectedRegistro.getId(), global.getUsuarioLogado().getOrgao().getIdOrgao());
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível fazer a impressão!");
            System.out.println(ex.getMessage());
        }
    }

    public void remover() {
        try {
            servico.remover(selectedRegistro.getId());
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao remover registro");
            System.out.println("Erro ao remover registro\n" + ex.getMessage());
        }
        selectedRegistro = null;
        carregarListagem();
    }

    public void carregarListagem() {
        try {
            listRegistro = servico.listar("", 100);
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }

    public void buscaPorInscricao() {
        if (inscricao == null || inscricao.trim().equals("")) {
            return;
        }
        try {
            selectedRegistro.setIss(issqnServico.obterIssqnPorInscricao(inscricao, global.getExercicio().getAno()));
        } catch (BusinessViolation ex) {
            Funcao.avisoErro(ex.getMessage());
            System.out.println("Erro ao buscar inscrição\n" + ex.getMessage());
        }

        selecionarIssqn();
    }
    
    public void selecionarIssqn() {
        Issqn iss = selectedRegistro.getIss();
        if (iss == null) {
            Funcao.avisoAtencao("Contribuinte não selecionado");
            return;
        }
        inscricao = iss.getInscricao();

        Pessoa p = iss.getPessoa();
        if (p != null) {
            selectedRegistro.setContribuinte(p.getNome());
            selectedRegistro.setCnpjCpf(p.getCpfCnpj());
            selectedRegistro.setData(p.getDtAbertura());

            Imovel i = p.getImovel();
            if (i != null) {
                selectedRegistro.setBairrologradouro(i.getBairrologradouro());
                selectedRegistro.setComplemento(i.getComplemento());
                selectedRegistro.setBairrologradouro(i.getBairrologradouro());

                if (selectedRegistro.getBairrologradouro() != null) {
                    selectedEstado = selectedRegistro.getBairrologradouro().getCidade().getEstado();
                    selectedCidade = selectedRegistro.getBairrologradouro().getCidade();
                    selectedBairro = selectedRegistro.getBairrologradouro().getBairro();
                }
            }
        }
    }

    //------------ getters e setters ---------------//
    public IssqnTermoIntimacao getSelectedRegistro() {
        return selectedRegistro;
    }

    public void setSelectedRegistro(IssqnTermoIntimacao selectedRegistro) {
        this.selectedRegistro = selectedRegistro;
    }

    public List<IssqnTermoIntimacao> getListRegistro() {
        return listRegistro;
    }

    public void setListRegistro(List<IssqnTermoIntimacao> listRegistro) {
        this.listRegistro = listRegistro;
    }

    public String getTextoFiltro() {
        return textoFiltro;
    }

    public void setTextoFiltro(String textoFiltro) {
        this.textoFiltro = textoFiltro;
    }

    public Estado getSelectedEstado() {
        return selectedEstado;
    }

    public void setSelectedEstado(Estado selectedEstado) {
        this.selectedEstado = selectedEstado;
    }

    public Cidade getSelectedCidade() {
        return selectedCidade;
    }

    public void setSelectedCidade(Cidade selectedCidade) {
        this.selectedCidade = selectedCidade;
    }

    public Bairro getSelectedBairro() {
        return selectedBairro;
    }

    public void setSelectedBairro(Bairro selectedBairro) {
        this.selectedBairro = selectedBairro;
    }

    public List<Estado> getListEstado() {
        if (listEstado == null) {
            try {
                listEstado = estadoServico.getEstados();
            } catch (Exception e) {
                Funcao.avisoErro("Não foi possível buscar estados");
                System.out.println("Não foi possivel preencher a lista de estados " + e.getMessage());
                return null;
            }
            if (listEstado.size() == 1) {
                selectedEstado = listEstado.get(0);
            }
        }
        return listEstado;
    }

    public void setListEstado(List<Estado> listEstado) {
        this.listEstado = listEstado;
    }

    public List<Cidade> getListCidade() {
        if (selectedEstado != null) {
            try {
                listCidade = cidadeServico.obterCidadesPorEstado(selectedEstado.getId());
            } catch (Exception e) {
                Funcao.avisoErro("Não foi possível buscar cidades por estado");
                System.out.println("Não foi possivel preencher a lista de cidades por estado " + e.getMessage());
                return null;
            }
            Funcao.executarJavaScript("updateFields();");
            if (listCidade.size() == 1) {
                selectedCidade = listCidade.get(0);
            }
        } else {
            listCidade = null;
        }
        return listCidade;
    }

    public void setListCidade(List<Cidade> listCidade) {
        this.listCidade = listCidade;
    }

    public List<Bairro> getListBairro() {
        if (selectedCidade != null && selectedEstado != null) {
            try {
                listBairro = bairroServico.obterBairrosPorCidade(selectedCidade.getIdCidade(), selectedEstado.getId());
            } catch (Exception e) {
                Funcao.avisoErro("Não foi possível buscar logradouros por cidade");
                System.out.println("Não foi possivel preencher a lista de logradouros por cidade " + e.getMessage());
            }
            Funcao.executarJavaScript("updateFields();");
            if (listBairro.size() == 1) {
                selectedBairro = listBairro.get(0);
            }
        } else {
            listBairro = null;
        }
        return listBairro;
    }

    public void setListBairro(List<Bairro> listBairro) {
        this.listBairro = listBairro;
    }

    public List<BairroLogradouro> getListBairroLogradouro() {
        if (selectedBairro != null && selectedCidade != null) {
            try {
                listBairroLogradouro = logradouroServico.obterBairroLogradourosPorCidadeBairro(selectedCidade.getIdCidade(), selectedBairro.getIdBairro());
            } catch (Exception e) {
                Funcao.avisoErro("Não foi possível buscar logradouros por cidade e bairro");
                System.out.println("Não foi possivel preencher a lista de logradouros por cidade e bairro " + e.getMessage());
                return null;
            }
            Funcao.executarJavaScript("updateFields();");
            if (listBairroLogradouro.size() == 1) {
                selectedRegistro.setBairrologradouro(listBairroLogradouro.get(0));
            }
        } else {
            listBairroLogradouro = null;
        }
        return listBairroLogradouro;
    }

    public void setListBairroLogradouro(List<BairroLogradouro> listBairroLogradouro) {
        this.listBairroLogradouro = listBairroLogradouro;
    }

    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

}
