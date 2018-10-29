/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.entidade.issqn.IssqnTipoCobranca;
import br.com.eddydata.entidade.issqn.IssqnTipoCobrancaItem;
import br.com.eddydata.servico.issqn.TipoCobrancaServico;
import br.com.eddydata.suporte.Util;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class GerarVencimentosBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private TipoCobrancaServico tipoCobrancaServico;

    @Inject
    private GlobalBean global;
    private List<IssqnTipoCobranca> listarTipoCobranca;

    private IssqnTipoCobranca tipoCobranca;
    private List<IssqnTipoCobranca> selecionarTipoCobranca;
    private String filterValue;
    private boolean showEditar = false;
    private boolean showPesquisar = true;
    private String codigo;
    private List<IssqnTipoCobranca> issqnTipoCobrancas;

    @PostConstruct
    public void init() {

    }

    // Prepara a tela no modo de inclusão para informar os dados do novo tipo vencimento
    public void adicionar() {
        tipoCobranca = new IssqnTipoCobranca();
        showEditar = true;
        showPesquisar = false;
    }

    // Cancela o modo de edição e volta para tela de consulta
    public void cancelar() {
        listarTipoCobranca = tipoCobrancaServico.getTodosCobrancas(global.getExercicio().getAno());
        showEditar = false;
        showPesquisar = true;
        tipoCobranca = null;
    }

    public void gerarVencimentos() {
        try {
            if (tipoCobranca != null && tipoCobranca.getQtdParcela() != null
                    && tipoCobranca.getDiaVencimento() != null && tipoCobranca.getMesVencimento() != null) {
                if (tipoCobranca.getQtdParcela() < 1) {
                    Funcao.avisoAtencao("A quantidade de parcelas deve ser maior que 1");
                    return;
                }
                if (tipoCobranca.getDiaVencimento() < 1 || tipoCobranca.getDiaVencimento() > 31) {
                    Funcao.avisoAtencao("O dia do Vencimento deve estar entre 1 e 31");
                    return;
                }
                if (tipoCobranca.getMesVencimento() < 1 && tipoCobranca.getMesVencimento() > 12) {
                    Funcao.avisoAtencao("O mês inicial deve estar entre 1 e 12");
                    return;
                }
                List<IssqnTipoCobrancaItem> lstItensParcelas = new ArrayList<>();
                for (int i = 1; i <= tipoCobranca.getQtdParcela(); i++) {
                    Integer dia = (tipoCobranca.getDiaVencimento() < 10 ? 0 + tipoCobranca.getDiaVencimento() : tipoCobranca.getDiaVencimento());
                    Integer mes = (tipoCobranca.getMesVencimento() < 10 ? 0 + tipoCobranca.getMesVencimento() : tipoCobranca.getMesVencimento());
                    IssqnTipoCobrancaItem tci = new IssqnTipoCobrancaItem();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    tci.setDtVencimento(Util.addMounth(sdf.parse(dia + "/" + mes + "/" + global.getExercicio().getAno()), i - 1));
                    tci.setParcela(i);
                    tci.setTipoCobranca(tipoCobranca);
                    tci.setIdExercicio((global.getExercicio().getAno()));
                    lstItensParcelas.add(i - 1, tci);
                }
                tipoCobranca.setItens(lstItensParcelas);
            }
        } catch (Exception ex) {
            Logger.getLogger(GerarVencimentosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Salva todas as informações do tipo de vencimento no banco de dados
    public void salvar() {
        if (tipoCobranca.getItens() == null || tipoCobranca.getItens().isEmpty()) {
            Funcao.avisoAtencao("Adicione as parcelas com os vencimentos");
            return;
        }

        tipoCobranca.setDescricao(tipoCobranca.getDescricao().toUpperCase());
        tipoCobranca.setMensagem(tipoCobranca.getMensagem().toUpperCase());
        if (tipoCobranca.getId() == null) {
            tipoCobranca.setIdExercicio((global.getExercicio().getAno()));
            try {
                tipoCobrancaServico.adicionarTipoCobranca(tipoCobranca);

            } catch (RuntimeException re) {
                System.out.println(re.getMessage());
            }
        } else {
            tipoCobrancaServico.setTipoCobranca(tipoCobranca);
        }
        Funcao.notificacaoSucesso("Registro salvo com sucesso!");
        cancelar();
    }

    // Mostra para edição o vencimento selecionado no grid
    public void selecionar(IssqnTipoCobranca tpCobranca) {
        showEditar = true;
        showPesquisar = false;
        tipoCobranca = tpCobranca;
    }

    // Salva e adiciona um novo tipo de cobrança para o usuário não precisar sair da tela de edição
    public void salvarNovo() {
        salvar();
        adicionar();
    }

    // Remove todos os tipos de vencimento selecionados na grade pelo usuário.
    public void remover() {
        if (tipoCobranca == null) {
            Funcao.avisoErro("Selecione pelo menos um item!");
            return;
        }
        try {
            tipoCobrancaServico.removerTipoCobranca(tipoCobranca);
        } catch (Exception ex) {
            Funcao.avisoErro("Existe movimento(s) vinculado(s) a este tipo de vencimento. Exclusão não permitida");
            return;
        }

        cancelar();
    }

    public IssqnTipoCobranca getTipoCobranca() {
        return tipoCobranca;
    }

    public void setTipoCobranca(IssqnTipoCobranca tipoCobranca) {
        this.tipoCobranca = tipoCobranca;
    }

    public List<IssqnTipoCobranca> getListarTipoCobranca() {
        if (listarTipoCobranca == null) {
            listarTipoCobranca = tipoCobrancaServico.getTodosCobrancas(global.getExercicio().getAno());
        }
        return listarTipoCobranca;
    }

    public void setListarTipoCobranca(List<IssqnTipoCobranca> listarTipoCobranca) {
        this.listarTipoCobranca = listarTipoCobranca;
    }

    public List<IssqnTipoCobranca> getSelecionarTipoCobranca() {
        return selecionarTipoCobranca;
    }

    public void setSelecionarTipoCobranca(List<IssqnTipoCobranca> selecionarTipoCobranca) {
        this.selecionarTipoCobranca = selecionarTipoCobranca;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public boolean isShowEditar() {
        return showEditar;
    }

    public void setShowEditar(boolean showEditar) {
        this.showEditar = showEditar;
    }

    public boolean isShowPesquisar() {
        return showPesquisar;
    }

    public void setShowPesquisar(boolean showPesquisar) {
        this.showPesquisar = showPesquisar;
    }

    public String getCodigo() {
        codigo = Util.extractStr(tipoCobranca.getId());
        return Util.Texto.strZero(codigo, 5);
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<IssqnTipoCobranca> getListTipoCobrancas() {
        if (issqnTipoCobrancas == null) {
            issqnTipoCobrancas = tipoCobrancaServico.getTodosCobrancas(global.getExercicio().getAno());
        }
        return issqnTipoCobrancas;
    }

    public void setListTipoCobrancas(List<IssqnTipoCobranca> issqnTipoCobrancas) {
        this.issqnTipoCobrancas = issqnTipoCobrancas;
    }

    public List<IssqnTipoCobranca> getIssqnTipoCobrancas() {
        return issqnTipoCobrancas;
    }

    public void setIssqnTipoCobrancas(List<IssqnTipoCobranca> issqnTipoCobrancas) {
        this.issqnTipoCobrancas = issqnTipoCobrancas;
    }

}
