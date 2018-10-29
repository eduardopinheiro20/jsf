/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.bean;

import br.com.eddydata.entidade.admin.Acesso;
import br.com.eddydata.entidade.admin.Sistema;
import br.com.eddydata.entidade.admin.UsuarioPerfil;
import br.com.eddydata.servico.admin.SistemaServico;
import br.com.eddydata.servico.admin.UsuarioPerfilServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class PerfilBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private UsuarioPerfilServico usuarioPerfilServico;

    @EJB
    private SistemaServico sistemaServico;

    @Inject
    private GlobalBean global;

    private static final String SISTEMA = "ISSQN";
    private UsuarioPerfil perfil;
    private Sistema sistema;

    private List<UsuarioPerfil> todosPerfis;

    private String textoFiltro;

    private Boolean existeSelecionado;

    private List<Acesso> dataItem = new ArrayList<>();
    private Acesso entidadeItem;

    private int totalRows;
    private int firstRow;
    private int rowsPerPage;
    private int totalPages;
    private int pageRange;
    private Integer[] pages;
    private int currentPage;

    @PostConstruct
    public void init() {
        rowsPerPage = 30;
        pageRange = 10;
        sistema = sistemaServico.obterSistema(SISTEMA);
    }

    public void filtrar() {
        if (textoFiltro != null && !"".equals(textoFiltro)) {
            todosPerfis = usuarioPerfilServico.getUsuarioPerfilByNome(textoFiltro, sistema.getIdSistema());
            paginacao();
            perfil = null;
            existeSelecionado = false;
        }
    }

    public List<UsuarioPerfil> getTodosPerfis() {
        if (todosPerfis == null) {
            this.todosPerfis = usuarioPerfilServico.getUsuarioBySistema(sistema.getIdSistema());
            paginacao();
        }

        return todosPerfis;
    }

    public void adicionar() {
        perfil = new UsuarioPerfil();
        existeSelecionado = false;
        dataItem = new ArrayList<>();
        preencherAcessos();
    }

    public void voltar() {
        perfil = null;
        todosPerfis = null;
        todosPerfis = getTodosPerfis();
        existeSelecionado = false;
        dataItem = new ArrayList<>();
        preencherAcessos();
    }

    public void alterar(UsuarioPerfil perfilSelecionado) {
        perfil = perfilSelecionado;
        existeSelecionado = false;
        preencherAcessos();
    }

    public void preencherAcessos() {
        dataItem = new ArrayList<>();
        if (perfil.getIdPerfil() != null) {
            dataItem = usuarioPerfilServico.getAcessos(perfil.getIdPerfil(), sistema.getNome());
        } else {
            dataItem = usuarioPerfilServico.getAcessos(0, sistema.getNome());
        }
    }

    public void cancelar() {
        perfil = null;
        todosPerfis = usuarioPerfilServico.getUsuarioBySistema(sistema.getIdSistema());
        existeSelecionado = false;
        textoFiltro = "";
    }

    public UsuarioPerfil salvar() {
        if (perfil.getIdPerfil() == null) { //adiciona
            perfil.setSistema(sistema);
            perfil.setOrgao(global.getUsuarioLogado().getOrgao());
        }
        for (Acesso access : dataItem) {
            access.setUsuarioPerfil(perfil);
        }
        perfil.setAcessos(dataItem);
        perfil = usuarioPerfilServico.setUsuarioPerfil(perfil);
        Funcao.notificacaoSucesso("UsuarioPerfil foi salvo com sucesso!");
        return perfil;
    }

    public void salvarIncluir() {
        salvar();
        voltar();
    }

    public void remover() {
        usuarioPerfilServico.removerUsuarioPerfil(perfil);
        existeSelecionado = false;
        perfil = null;
        todosPerfis = usuarioPerfilServico.getUsuarioBySistema(sistema.getIdSistema());
    }
    
    

    public List<Acesso> getDataItem() {
        return dataItem;
    }

    public void setDataItem(List<Acesso> dataItem) {
        this.dataItem = dataItem;
    }

    public Acesso getEntidadeItem() {
        return entidadeItem;
    }

    public void setEntidadeItem(Acesso entidadeItem) {
        this.entidadeItem = entidadeItem;
    }

    // --- gets & sets ---------------------------------------------------------
    public UsuarioPerfil getPerfil() {
        return perfil;
    }

    public void setPerfil(UsuarioPerfil perfil) {
        this.perfil = perfil;
    }

    public String getTextoFiltro() {
        return textoFiltro;
    }

    public void setTextoFiltro(String textoFiltro) {
        this.textoFiltro = textoFiltro;
    }

    public Boolean getExisteSelecionado() {
        return existeSelecionado;
    }

    // -------------------------------------------------------------------------
    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageRange() {
        return pageRange;
    }

    public void setPageRange(int pageRange) {
        this.pageRange = pageRange;
    }

    public Integer[] getPages() {
        return pages;
    }

    public void setPages(Integer[] pages) {
        this.pages = pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    private void paginacao() {
        totalRows = todosPerfis.size();

        // Set currentPage, totalPages and pages.
        currentPage = (totalRows / rowsPerPage) - ((totalRows - firstRow) / rowsPerPage) + 1;
        totalPages = (totalRows / rowsPerPage) + ((totalRows % rowsPerPage != 0) ? 1 : 0);
        int pagesLength = Math.min(pageRange, totalPages);
        pages = new Integer[pagesLength];

        // firstPage must be greater than 0 and lesser than totalPages-pageLength.
        int firstPage = Math.min(Math.max(0, currentPage - (pageRange / 2)), totalPages - pagesLength);

        // Create pages (page numbers for page links).
        for (int i = 0; i < pagesLength; i++) {
            pages[i] = ++firstPage;
        }
    }

    private void pagina(int firstRow) {
        this.firstRow = firstRow;
        if (textoFiltro == null || "".equals(textoFiltro)) {
            todosPerfis = usuarioPerfilServico.getUsuarioBySistema(sistema.getIdSistema());
        } else {
            filtrar();
        }
        paginacao();
    }

    // Paging actions -----------------------------------------------------------------------------
    public void pageFirst() {
        pagina(0);
    }

    public void pageNext() {
        pagina(firstRow + rowsPerPage);
    }

    public void pagePrevious() {
        pagina(firstRow - rowsPerPage);
    }

    public void pageLast() {
        pagina(totalRows - ((totalRows % rowsPerPage != 0) ? totalRows % rowsPerPage : rowsPerPage));
    }

    public void page(ActionEvent event) {
        pagina(((Integer) ((UICommand) event.getComponent()).getValue() - 1) * rowsPerPage);
    }

}
