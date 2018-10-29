/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author David Couto
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.UtilBean;
import br.com.eddydata.entidade.issqn.IssqnAtividade;
import br.com.eddydata.entidade.issqn.IssqnCnae;
import br.com.eddydata.entidade.issqn.IssqnCodigoFiscal;
import br.com.eddydata.entidade.issqn.IssqnOrgaoFiscalizacao;
import br.com.eddydata.entidade.issqn.IssqnTaxa;
import br.com.eddydata.servico.issqn.IssqnAtividadeServico;
import br.com.eddydata.servico.issqn.IssqnCodigoFiscalServico;
import br.com.eddydata.servico.issqn.IssqnOrgaoFiscalizacaoServico;
import br.com.eddydata.servico.issqn.IssqnTaxaServico;
import br.com.eddydata.suporte.BusinessViolation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class AtividadeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IssqnAtividadeServico atividadeServico;

    @EJB
    private IssqnCodigoFiscalServico codigoFiscalServico;

    @EJB
    private IssqnTaxaServico taxaServico;
    
    @EJB
    private IssqnOrgaoFiscalizacaoServico orgaoFiscalServico;
    
    @Inject
    private UtilBean util;

    private IssqnCnae selectedRegistro;
    private IssqnTaxa taxa;
    private List<IssqnCnae> listRegistro;
    private List<IssqnTaxa> listItens;
    private String textoFiltro;
    private boolean transacao = false;
    private List<IssqnCodigoFiscal> listCodigoFiscal;
    private List<IssqnOrgaoFiscalizacao> listOrgaoFiscal;

    @PostConstruct
    public void init() {
        if (selectedRegistro == null) {
            carregarListagem();
        }
    }

    public void filtrar() {
        if (textoFiltro != null && !"".equals(textoFiltro)) {
            try {
                textoFiltro = util.retiraAcento(textoFiltro);
                listRegistro = atividadeServico.obterAtividades(textoFiltro, null, 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao pesquisar registros");
                System.out.println("Erro ao pesquisar registros\n" + ex.getMessage());
            }
            textoFiltro = "";
        }
    }

    public void adicionar() {
        selectedRegistro = new IssqnCnae();
        listItens = new ArrayList<>();
        taxa = null;
    }

    public void alterar(IssqnCnae registroSelecionado) {
        selectedRegistro = registroSelecionado;
        listItens = new ArrayList<>();
        carregarItensVinculados();
    }

    public void cancelar() {
        selectedRegistro = null;
        taxa = null;
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
        transacao = false;

        try {
            atividadeServico.salvarAtividade(selectedRegistro);
        } catch (BusinessViolation ex) {
            Funcao.avisoAtencao(ex.getMessage());
            return true;
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao salvar registro");
            System.out.println("Erro ao salvar registro\n" + ex.getMessage());
            return true;
        }

        Funcao.notificacaoSucesso("Registro salvo com sucesso!");

        cancelar();
        return false;
    }

    public void criarVinculos() {
        if (listItens != null && !listItens.isEmpty()) {
            try {
                if (selectedRegistro.getId() == null) {
                    selectedRegistro = atividadeServico.salvarAtividade(selectedRegistro);
                }
                Funcao.notificacaoSucesso("Vínculos criados com sucesso!");
                taxa = null;
                listItens = new ArrayList<>();
                carregarItensVinculados();

            } catch (BusinessViolation ex) {
            } catch (Exception ex) {
            }
        }
    }

    public void remover() {
        try {
            if(selectedRegistro.getId() == null){
                Funcao.mensagemAtencao("Selecione uma atividade para excluir!", textoFiltro);
            }
            atividadeServico.removerAtividade(selectedRegistro.getId());
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao remover registro");
            System.out.println("Erro ao remover registro\n" + ex.getMessage());
        }
        selectedRegistro = null;
        carregarListagem();
    }

    public void carregarListagem() {
        try {
            listRegistro = atividadeServico.obterAtividades("", null, 100);
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }

    public void carregaItens() {
        if (taxa != null) {
            try {
                listItens = new ArrayList<>();
                listItens = taxaServico.obterItens(taxa.getId());
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao buscar itens");
                System.out.println("Erro ao buscar itens\n" + ex.getMessage());
            }
        }
    }

    public void carregarItensVinculados() {
        try {
            if (selectedRegistro != null && selectedRegistro.getId() != null) {
            }
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }
    
   

    //------------ getters e setters ---------------//
    public IssqnCnae getSelectedRegistro() {
        return selectedRegistro;
    }

    public void setSelectedRegistro(IssqnCnae selectedRegistro) {
        this.selectedRegistro = selectedRegistro;
    }

    public List<IssqnCnae> getListRegistro() {
        return listRegistro;
    }

    public void setListRegistro(List<IssqnCnae> listRegistro) {
        this.listRegistro = listRegistro;
    }

    public String getTextoFiltro() {
        return textoFiltro;
    }

    public void setTextoFiltro(String textoFiltro) {
        this.textoFiltro = textoFiltro;
    }

    public List<IssqnCodigoFiscal> getListCodigoFiscal() {
        if (listCodigoFiscal == null && selectedRegistro != null) {
            try {
                listCodigoFiscal = codigoFiscalServico.obterCodigosFiscais("", 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao buscar codigos fiscais");
                System.out.println("Erro ao buscar codigos fiscais\n" + ex.getMessage());
            }
        }
        return listCodigoFiscal;

    }

    public void setListCodigoFiscal(List<IssqnCodigoFiscal> listCodigoFiscal) {
        this.listCodigoFiscal = listCodigoFiscal;
    }

    public IssqnTaxa getTaxa() {
        return taxa;
    }

    public void setTaxa(IssqnTaxa taxa) {
        this.taxa = taxa;
    }

    public List<IssqnTaxa> getListItens() {
        return listItens;
    }

    public void setListItens(List<IssqnTaxa> listItens) {
        this.listItens = listItens;
    }

    public List<IssqnOrgaoFiscalizacao> getListOrgaoFiscal() {
        if (listOrgaoFiscal == null && selectedRegistro != null) {
            try {
                listOrgaoFiscal = orgaoFiscalServico.obterFiscalizacaos("", 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao buscar Orgão de Fiscalização de atuacao");
                System.out.println("Erro ao buscar Orgão de Fiscalização \n" + ex.getMessage());
            }
        }
        return listOrgaoFiscal;
    }

    public void setListOrgaoFiscal(List<IssqnOrgaoFiscalizacao> listOrgaoFiscal) {
        this.listOrgaoFiscal = listOrgaoFiscal;
    }
    
    

}
