/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author David Couto
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.UtilBean;
import br.com.eddydata.entidade.issqn.IssqnSegmento;
import br.com.eddydata.servico.issqn.IssqnSegmentoServico;
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
public class SegmentoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IssqnSegmentoServico servico;
    
    @Inject
    private UtilBean util;

    private IssqnSegmento selectedRegistro;
    private List<IssqnSegmento> listRegistro;
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
            textoFiltro = util.retiraAcento(textoFiltro);
            try {
                listRegistro = servico.obterSegmento(textoFiltro, 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao pesquisar registros");
                System.out.println("Erro ao pesquisar registros\n" + ex.getMessage());
            }
        }
    }

    public void adicionar() {
        selectedRegistro = new IssqnSegmento();
    }

    public void alterar(IssqnSegmento registroSelecionado) {
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
            selectedRegistro.setCaracteristica(selectedRegistro.getCaracteristica().toUpperCase());
            selectedRegistro.setNome(selectedRegistro.getNome().toUpperCase());
            selectedRegistro = servico.salvarSegmento(selectedRegistro);
        } catch (BusinessViolation ex) {
            Funcao.avisoAtencao(ex.getMessage());
            return false;
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao salvar registro");
            System.out.println("Erro ao salvar registro\n" + ex.getMessage());
            return false;
        }

        Funcao.notificacaoSucesso("Registro salvo com sucesso!");

        cancelar();
        return true;
    }

    public void remover() {
        try {
            servico.removerSegmento(selectedRegistro.getId());
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao remover registro");
            System.out.println("Erro ao remover registro\n" + ex.getMessage());
        }
        selectedRegistro = null;
        carregarListagem();
    }

    public void carregarListagem() {
        try {
            listRegistro = servico.obterSegmento("", 100);
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }

    //------------ getters e setters ---------------//
    public IssqnSegmento getSelectedRegistro() {
        return selectedRegistro;
    }

    public void setSelectedRegistro(IssqnSegmento selectedRegistro) {
        this.selectedRegistro = selectedRegistro;
    }

    public List<IssqnSegmento> getListRegistro() {
        return listRegistro;
    }

    public void setListRegistro(List<IssqnSegmento> listRegistro) {
        this.listRegistro = listRegistro;
    }

    public String getTextoFiltro() {
        return textoFiltro;
    }

    public void setTextoFiltro(String textoFiltro) {
        this.textoFiltro = textoFiltro;
    }
}
