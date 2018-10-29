/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.entidade.geral.Banco;
import br.com.eddydata.entidade.issqn.IssqnTaxa;
import br.com.eddydata.servico.admin.BancoServico;
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
public class TaxaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IssqnTaxaServico servico;
    @EJB
    private BancoServico bancoServico;

    @Inject
    private GlobalBean global;

    private IssqnTaxa selectedRegistro;
    private List<IssqnTaxa> listRegistro;
    private String textoFiltro;
    private boolean transacao = false;

    private List<IssqnTaxa> listItem;
    private IssqnTaxa itemTemp;

    private List<Banco> listBanco;

    private String ordem = "t1.id";

    @PostConstruct
    public void init() {
        if (selectedRegistro == null) {
            carregarListagem();
        }
    }

    private void limparCache() {
        listItem = null;
        listRegistro = null;
        selectedRegistro = null;
    }

    public void filtrar() {
        if (textoFiltro != null && !"".equals(textoFiltro)) {
            try {
                listRegistro = servico.obterTaxas(textoFiltro, 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao pesquisar registros");
                System.out.println("Erro ao pesquisar registros\n" + ex.getMessage());
            }
        }
    }

    public void adicionar() {
        limparCache();

        selectedRegistro = new IssqnTaxa();
        selectedRegistro.setIsTaxa(0);
        selectedRegistro.setTipo(0);
        selectedRegistro.setNivel(0);
    }

    public void alterar(IssqnTaxa registroSelecionado) {
        limparCache();

        selectedRegistro = registroSelecionado;
        carregarItens();
    }

    public void cancelar() {
        limparCache();

        textoFiltro = "";
        carregarListagem();
    }

    public synchronized void salvar() {
        if (preSalvar()) {
            limparCache();
            carregarListagem();
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
        
        selectedRegistro.setNome(selectedRegistro.getNome().toUpperCase());

        try {
            selectedRegistro = servico.salvarTaxa(selectedRegistro);
        } catch (BusinessViolation ex) {
            Funcao.avisoAtencao(ex.getMessage());
            return false;
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao salvar registro");
            System.out.println("Erro ao salvar registro\n" + ex.getMessage());
            return false;
        }

        salvarItens();

        Funcao.notificacaoSucesso("Registro salvo com sucesso!");

        carregarListagem();
        return true;
    }

    public void remover() {
        try {
            servico.removerTaxa(selectedRegistro.getId());
            Funcao.notificacaoSucesso("Exclusão efetuada com sucesso");
        } catch (Exception ex) {
            Funcao.avisoErro("Esta taxa já possui movimentação, exclusão não efetuada");
            System.out.println("Erro ao remover registro\n" + ex);
        }
        selectedRegistro = null;
        carregarListagem();
    }

    public void carregarListagem() {
        try {
            listRegistro = servico.obterTaxas("", 100);
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }

    private void carregarItens() {
        try {
            listItem = servico.obterItens(selectedRegistro.getId());
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }

    public String mostrarTipo(String tipo) {
        if (tipo == null) {
            return "";
        }
        switch (tipo) {
            case "0":
                return "Anual";
            case "1":
                return "Uma vez";
            default:
                return tipo;
        }
    }

    public String mostrarTipoIssqn(String isTaxa) {
        if (isTaxa == null) {
            return "";
        }
        switch (isTaxa) {
            case "0":
                return "ISSQN";
            case "1":
                return "Demais Taxas";
            default:
                return isTaxa;
        }
    }

    //------------------------itens ----------------------
    public void adicionarItem() {
        if (listItem == null) {
            listItem = new ArrayList<>();
        }

        //confirmar os itens antes de incluir
        for (IssqnTaxa t : listItem) {
            if (t.isSelecionado()) {
                try {
                    verificarItem(t);
                } catch (BusinessViolation ex) {
                    Funcao.mensagemAtencao("Atenção", ex.getMessage());
                    return;
                }
                t.setSelecionado(false);
            }
        }

        IssqnTaxa taxa = new IssqnTaxa();
        taxa.setParente(selectedRegistro);
        taxa.setSelecionado(true);

        listItem.add(0, taxa);
    }

    public void alterarItem(IssqnTaxa item) {
        itemTemp = new IssqnTaxa();

        itemTemp.setNome(item.getNome());
        itemTemp.setValor(item.getValor());
        itemTemp.setTpValor(item.getTpValor());

        item.setSelecionado(true);
    }

    public void cancelarItem(IssqnTaxa item) {
        if (item == null) {
            return;
        }

        //se veio por alteração ignora alterações
        if (itemTemp != null) {
            item.setNome(itemTemp.getNome());
            item.setValor(itemTemp.getValor());
            item.setTpValor(itemTemp.getTpValor());
            item.setSelecionado(false);

            itemTemp = null;
        } else { // inclusao
            if (item.getId() == null) {
                listItem.remove(item);
            }
        }
    }

    public void removerItem(IssqnTaxa item) {
        if (item == null) {
            return;
        }

        if (item.getId() == null) { //inclusao
            listItem.remove(item);
        } else {
            try {
                servico.removerTaxa(item.getId());
            } catch (Exception ex) {
                Funcao.avisoErro(ex.getMessage());
            }
        }
    }

    public void confirmarItem(IssqnTaxa item) {
        if (item == null) {
            return;
        }

        try {
            verificarItem(item);
        } catch (BusinessViolation ex) {
            Funcao.mensagemAtencao("Atenção", ex.getMessage());
            return;
        }

        item.setSelecionado(false);
    }

    private void verificarItem(IssqnTaxa item) throws BusinessViolation {
        if (item == null) {
            return;
        }

        if (item.getNome() == null || item.getNome().trim().equals("")) {
            throw new BusinessViolation("Informe o nome do item");
        }
        if (item.getValor() == null) {
            throw new BusinessViolation("Informe o valor do item");
        }
        if (item.getTpValor() == null) {
            throw new BusinessViolation("Informe o tipo do valor do item");
        }

    }

    private void salvarItens() {
        if (listItem == null) {
            return;
        }

        for (IssqnTaxa item : listItem) {
            item.setParente(selectedRegistro);
            item.setIsTaxa(selectedRegistro.getIsTaxa());
            item.setTipo(selectedRegistro.getTipo());
            item.setNivel(1);

            try {
                servico.salvarTaxa(item);
            } catch (BusinessViolation ex) {
                Funcao.avisoAtencao(ex.getMessage());
                return;
            } catch (Exception ex) {
                Funcao.avisoErro("Não foi possível salvar os itens");
                System.out.println(ex.getMessage());
                return;
            }
        }
    }

    public void imprimirTaxa() {
        try {
            servico.imprimirTaxas(global.getUsuarioLogado().getOrgao().getIdOrgao(), ordem);
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível fazer a impressão!");
            System.out.println(ex.getMessage());
        }
    }

    //------------ getters e setters ---------------//
    public IssqnTaxa getSelectedRegistro() {
        return selectedRegistro;
    }

    public void setSelectedRegistro(IssqnTaxa selectedRegistro) {
        this.selectedRegistro = selectedRegistro;
    }

    public List<IssqnTaxa> getListRegistro() {
        return listRegistro;
    }

    public void setListRegistro(List<IssqnTaxa> listRegistro) {
        this.listRegistro = listRegistro;
    }

    public String getTextoFiltro() {
        return textoFiltro;
    }

    public void setTextoFiltro(String textoFiltro) {
        this.textoFiltro = textoFiltro;
    }

    public List<Banco> getListBanco() {
        if (listBanco == null && selectedRegistro != null) {
            try {
                listBanco = bancoServico.obterBancos("", 100);
            } catch (Exception ex) {
                Funcao.avisoErro("Erro ao buscar bancos");
                System.out.println("Erro ao buscar bancos\n" + ex.getMessage());
            }
        }
        return listBanco;
    }

    public void setListBanco(List<Banco> listBanco) {
        this.listBanco = listBanco;
    }

    public List<IssqnTaxa> getListItem() {
        return listItem;
    }

    public void setListItem(List<IssqnTaxa> listItem) {
        this.listItem = listItem;
    }

    public IssqnTaxa getItemTemp() {
        return itemTemp;
    }

    public void setItemTemp(IssqnTaxa itemTemp) {
        this.itemTemp = itemTemp;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public GlobalBean getGlobal() {
        return global;
    }

    public void setGlobal(GlobalBean global) {
        this.global = global;
    }

}
