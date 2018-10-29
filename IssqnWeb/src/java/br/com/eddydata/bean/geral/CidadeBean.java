/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author David Couto
 */
package br.com.eddydata.bean.geral;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.entidade.geo.Cidade;
import br.com.eddydata.entidade.geo.Estado;
import br.com.eddydata.servico.admin.CidadeServico;
import br.com.eddydata.servico.admin.EstadoServico;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class CidadeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private CidadeServico cidadeServico;

    @EJB
    private EstadoServico estadoServico;

    private Cidade selectedRegistro;
    
    private List<Cidade> listRegistro;
    private String textoFiltro;
    private boolean transacao = false;
    private List<Estado> listEstado;

    @PostConstruct
    public void init() {
        if (selectedRegistro == null) {
            carregarListagem();
        }
    }

    public void filtrar() {
        if (textoFiltro != null && !"".equals(textoFiltro)) {
            try {
                listRegistro = cidadeServico.getCidadePorNome(textoFiltro);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao pesquisar registros");
                System.out.println("Erro ao pesquisar registros\n" + ex.getMessage());
            }
        }
    }

    public void adicionar() {
        selectedRegistro = new Cidade();
    }

    public void alterar(Cidade registroSelecionado) {
        selectedRegistro = registroSelecionado;
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
            selectedRegistro = cidadeServico.adicionarCidade(selectedRegistro);
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
            cidadeServico.removerCidade(selectedRegistro);
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao remover registro");
            System.out.println("Erro ao remover registro\n" + ex.getMessage());
        }
        selectedRegistro = null;
        carregarListagem();
    }

    public void carregarListagem() {
        try {
            listRegistro = cidadeServico.getCidades();
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }

    //------------ getters e setters ---------------//
    public Cidade getSelectedRegistro() {
        return selectedRegistro;
    }

    public void setSelectedRegistro(Cidade selectedRegistro) {
        this.selectedRegistro = selectedRegistro;
    }

    public List<Cidade> getListRegistro() {
        return listRegistro;
    }

    public void setListRegistro(List<Cidade> listRegistro) {
        this.listRegistro = listRegistro;
    }

    public String getTextoFiltro() {
        return textoFiltro;
    }

    public void setTextoFiltro(String textoFiltro) {
        this.textoFiltro = textoFiltro;
    }

    public List<Estado> getListEstado() {
        if (listEstado == null && selectedRegistro != null) {
            try {
                listEstado = estadoServico.getEstados();
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao buscar estados");
                System.out.println("Erro ao buscar estados\n" + ex.getMessage());
            }
        }
        return listEstado;
    }

    public void setListEstado(List<Estado> listEstado) {
        this.listEstado = listEstado;
    }

}
