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
import br.com.eddydata.entidade.issqn.IssqnZonaMunicipio;
import br.com.eddydata.servico.issqn.IssqnZonaMunicipioServico;
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
public class ZonaMunicipioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IssqnZonaMunicipioServico servico;

    private IssqnZonaMunicipio selectedRegistro;
    private List<IssqnZonaMunicipio> listRegistro;
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
                listRegistro = servico.obterZonaMunicipio(textoFiltro, 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao pesquisar registros");
                System.out.println("Erro ao pesquisar registros\n" + ex.getMessage());
            }
        }
    }

    public void adicionar() {
        selectedRegistro = new IssqnZonaMunicipio();
    }

    public void alterar(IssqnZonaMunicipio zonaSelecionado) {
        selectedRegistro = zonaSelecionado;
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
            selectedRegistro = servico.salvarZonaMunicipio(selectedRegistro);
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
            servico.removerZonaMunicipio(selectedRegistro.getId());
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao remover registro");
            System.out.println("Erro ao remover registro\n" + ex.getMessage());
        }
        selectedRegistro = null;
        carregarListagem();
    }

    public void carregarListagem() {
        try {
            listRegistro = servico.obterZonaMunicipio("", 100);
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }
    
    

    //------------ getters e setters ---------------//

    public IssqnZonaMunicipio getSelectedRegistro() {
        return selectedRegistro;
    }

    public void setSelectedRegistro(IssqnZonaMunicipio selectedRegistro) {
        this.selectedRegistro = selectedRegistro;
    }

    public List<IssqnZonaMunicipio> getListRegistro() {
        return listRegistro;
    }

    public void setListRegistro(List<IssqnZonaMunicipio> listRegistro) {
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
