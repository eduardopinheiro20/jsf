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
import br.com.eddydata.entidade.issqn.IssqnOrgaoFiscalizacao;
import br.com.eddydata.servico.issqn.IssqnOrgaoFiscalizacaoServico;
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
public class OrgaoFiscalizacaoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IssqnOrgaoFiscalizacaoServico servico;

    private IssqnOrgaoFiscalizacao selectedRegistro;
    private List<IssqnOrgaoFiscalizacao> listRegistro;
    private String textoFiltro;
    private boolean transacao = false;
    
    @Inject
    private GlobalBean global;
    @Inject
    private UtilBean util;

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
                listRegistro = servico.obterFiscalizacaos(textoFiltro, 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao pesquisar registros");
                System.out.println("Erro ao pesquisar registros\n" + ex.getMessage());
            }
        }
    }

    public void adicionar() {
        selectedRegistro = new IssqnOrgaoFiscalizacao();
    }

    public void alterar(IssqnOrgaoFiscalizacao orgaoSelecionado) {
        selectedRegistro = orgaoSelecionado;
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
            selectedRegistro = servico.salvarOrgaoFiscalizacao(selectedRegistro);
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
            servico.removerOrgaoFiscalizacao(selectedRegistro.getId());
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao remover registro");
            System.out.println("Erro ao remover registro\n" + ex.getMessage());
        }
        selectedRegistro = null;
        carregarListagem();
    }

    public void carregarListagem() {
        try {
            listRegistro = servico.obterFiscalizacaos("", 100);
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }
    
    

    //------------ getters e setters ---------------//

    public IssqnOrgaoFiscalizacao getSelectedRegistro() {
        return selectedRegistro;
    }

    public void setSelectedRegistro(IssqnOrgaoFiscalizacao selectedRegistro) {
        this.selectedRegistro = selectedRegistro;
    }

    public List<IssqnOrgaoFiscalizacao> getListRegistro() {
        return listRegistro;
    }

    public void setListRegistro(List<IssqnOrgaoFiscalizacao> listRegistro) {
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
