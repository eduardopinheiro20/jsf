/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author David Couto
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.bean.UtilBean;
import br.com.eddydata.entidade.issqn.IssqnQuestionario;
import br.com.eddydata.servico.issqn.IssqnQuestionarioServico;
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
public class QuestaoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IssqnQuestionarioServico servico;
    @Inject
    private UtilBean util;

    private IssqnQuestionario selectedRegistro;
    private List<IssqnQuestionario> listRegistro;
    private String textoFiltro;
    private boolean transacao = false;
    
    @Inject
    private GlobalBean global;

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
                listRegistro = servico.obterQuestionario(textoFiltro, 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao pesquisar registros");
                System.out.println("Erro ao pesquisar registros\n" + ex.getMessage());
            }
        }
    }

    public void adicionar() {
        selectedRegistro = new IssqnQuestionario();
    }

    public void alterar(IssqnQuestionario questionarioSelecionado) {
        selectedRegistro = questionarioSelecionado;
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
            selectedRegistro = servico.salvarQuestionario(selectedRegistro);
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
            servico.removerQuestionario(selectedRegistro.getId());
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao remover registro");
            System.out.println("Erro ao remover registro\n" + ex.getMessage());
        }
        selectedRegistro = null;
        carregarListagem();
    }

    public void carregarListagem() {
        try {
            listRegistro = servico.obterQuestionario("", 100);
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }
    
      public void imprimirQuestionario() {
        try {
            servico.imprimirQuestionario(global.getUsuarioLogado().getOrgao().getIdOrgao());
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível gerar o relatório");
            System.out.println(ex.getMessage());
        }
    }

    //------------ getters e setters ---------------//

    public IssqnQuestionario getSelectedRegistro() {
        return selectedRegistro;
    }

    public void setSelectedRegistro(IssqnQuestionario selectedRegistro) {
        this.selectedRegistro = selectedRegistro;
    }

    public List<IssqnQuestionario> getListRegistro() {
        return listRegistro;
    }

    public void setListRegistro(List<IssqnQuestionario> listRegistro) {
        this.listRegistro = listRegistro;
    }

    public String getTextoFiltro() {
        return textoFiltro;
    }

    public void setTextoFiltro(String textoFiltro) {
        this.textoFiltro = textoFiltro;
    }

    public boolean isTransacao() {
        return transacao;
    }

    public void setTransacao(boolean transacao) {
        this.transacao = transacao;
    }
   
}
