/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author David Couto
 */
package br.com.eddydata.bean.geral;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.entidade.admin.Imovel;
import br.com.eddydata.entidade.geo.Bairro;
import br.com.eddydata.entidade.geo.BairroLogradouro;
import br.com.eddydata.entidade.geo.Cidade;
import br.com.eddydata.entidade.geo.Estado;
import br.com.eddydata.servico.admin.BairroServico;
import br.com.eddydata.servico.admin.CidadeServico;
import br.com.eddydata.servico.admin.EstadoServico;
import br.com.eddydata.servico.admin.LogradouroServico;
import br.com.eddydata.servico.issqn.ImovelServico;
import br.com.eddydata.suporte.BusinessViolation;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class ImovelBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ImovelServico servico;
    @EJB
    private EstadoServico estadoServico;
    @EJB
    private CidadeServico cidadeServico;
    @EJB
    private BairroServico bairroServico;
    @EJB
    private LogradouroServico logradouroServico;

    private Imovel selectedRegistro;
    private List<Imovel> listRegistro;
    private String textoFiltro;
    private boolean transacao = false;

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

    private void limparVariaveis() {
        selectedRegistro = null;
        listRegistro = null;
        textoFiltro = "";
        selectedEstado = null;
        selectedCidade = null;
        selectedBairro = null;
        listEstado = null;
        listCidade = null;
        listBairro = null;
        listBairroLogradouro = null;
    }

    public void filtrar() {
        if (textoFiltro != null && !"".equals(textoFiltro)) {
            try {
                listRegistro = servico.obterImoveis(textoFiltro, 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao pesquisar registros");
                System.out.println("Erro ao pesquisar registros\n" + ex.getMessage());
            }
        }
    }

    public void adicionar() {
        limparVariaveis();
        selectedRegistro = new Imovel();
    }

    public void alterar(Imovel registroSelecionado) {
        limparVariaveis();
        selectedRegistro = registroSelecionado;
        if (selectedRegistro.getBairrologradouro() != null) {
            selectedBairro = selectedRegistro.getBairrologradouro().getBairro();
            if (selectedBairro != null) {
                selectedCidade = cidadeServico.getCidade(selectedBairro.getCidade().getIdCidade());
                if (selectedCidade != null) {
                    selectedEstado = selectedCidade.getEstado();
                }
            }
        }
        Funcao.executarJavaScript("updateFields();");
    }

    public void cancelar() {
        limparVariaveis();
        carregarListagem();
    }

    public synchronized void salvar() {
        if (preSalvar()) {
            limparVariaveis();
        }
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
            selectedRegistro = servico.salvarImovel(selectedRegistro);
        } catch (BusinessViolation ex) {
            Funcao.avisoAtencao(ex.getMessage());
            return false;
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
            servico.removerImovel(selectedRegistro.getId());
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao remover registro");
            System.out.println("Erro ao remover registro\n" + ex.getMessage());
        }
        limparVariaveis();
        carregarListagem();
    }

    public void carregarListagem() {
        try {
            listRegistro = servico.obterImoveis("", 100);
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }

    //------------ getters e setters ---------------//
    public Imovel getSelectedRegistro() {
        return selectedRegistro;
    }

    public void setSelectedRegistro(Imovel selectedRegistro) {
        this.selectedRegistro = selectedRegistro;
    }

    public List<Imovel> getListRegistro() {
        return listRegistro;
    }

    public void setListRegistro(List<Imovel> listRegistro) {
        this.listRegistro = listRegistro;
    }

    public String getTextoFiltro() {
        return textoFiltro;
    }

    public void setTextoFiltro(String textoFiltro) {
        this.textoFiltro = textoFiltro;
    }

    public Cidade getSelectedCidade() {
        return selectedCidade;
    }

    public void setSelectedCidade(Cidade selectedCidade) {
        this.selectedCidade = selectedCidade;
    }

    public Estado getSelectedEstado() {
        return selectedEstado;
    }

    public void setSelectedEstado(Estado selectedEstado) {
        this.selectedEstado = selectedEstado;
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

}
