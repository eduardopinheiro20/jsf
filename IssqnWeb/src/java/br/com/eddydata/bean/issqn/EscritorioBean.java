/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author David Couto
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.UtilBean;
import br.com.eddydata.entidade.issqn.IssqnEscritorio;
import br.com.eddydata.servico.issqn.IssqnEscritorioServico;
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
public class EscritorioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IssqnEscritorioServico servico;
    @Inject
    private UtilBean util;

    private IssqnEscritorio selectedRegistro;
    private List<IssqnEscritorio> listRegistro;
    private String textoFiltro;
    private boolean transacao = false;
    private int idCidade;

    @PostConstruct
    public void init() {
        if (selectedRegistro == null) {
            carregarListagem();
        }
    }

    public void filtrar() {
        if (textoFiltro != null && !"".equals(textoFiltro)) {
            textoFiltro = util.retiraAcento(textoFiltro);
            try {
                listRegistro = servico.obterEscritorios(textoFiltro, 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao pesquisar registros");
                System.out.println("Erro ao pesquisar registros\n" + ex.getMessage());
            }
        }
    }

    public void adicionar() {
        selectedRegistro = new IssqnEscritorio();
    }

    public void alterar(IssqnEscritorio registroSelecionado) {
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
            selectedRegistro = servico.salvarEscritorio(selectedRegistro);
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
            servico.removerEscritorio(selectedRegistro.getId());
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao remover registro");
            System.out.println("Erro ao remover registro\n" + ex.getMessage());
        }
        selectedRegistro = null;
        carregarListagem();
    }

    public void carregarListagem() {
        try {
            listRegistro = servico.obterEscritorios("", 100);
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }

    //------------ getters e setters ---------------//
    public IssqnEscritorio getSelectedRegistro() {
        return selectedRegistro;
    }

    public void setSelectedRegistro(IssqnEscritorio selectedRegistro) {
        this.selectedRegistro = selectedRegistro;
    }

    public List<IssqnEscritorio> getListRegistro() {
        return listRegistro;
    }

    public void setListRegistro(List<IssqnEscritorio> listRegistro) {
        this.listRegistro = listRegistro;
    }

    public String getTextoFiltro() {
        return textoFiltro;
    }

    public void setTextoFiltro(String textoFiltro) {
        this.textoFiltro = textoFiltro;
    }

    public void atualizaCidade() {
        if (selectedRegistro != null && selectedRegistro.getCidade() != null) {
            idCidade = selectedRegistro.getCidade().getIdCidade();
            selectedRegistro.setBairro(null);
            selectedRegistro.setLogradouro(null);
        }
    }

    public int getIdCidade() {
        return idCidade;
    }
}
