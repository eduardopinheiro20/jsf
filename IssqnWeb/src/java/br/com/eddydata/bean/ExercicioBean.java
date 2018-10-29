/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.bean;

import br.com.eddydata.entidade.admin.Exercicio;
import br.com.eddydata.servico.admin.ExercicioServico;
import br.com.eddydata.suporte.BusinessViolation;
import java.io.Serializable;
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
public class ExercicioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ExercicioServico exercicioServico;

    @Inject
    private GlobalBean global;

    private Exercicio exercicio;
    private List<Exercicio> todosExercicios;
    private String textoFiltro;
    private Boolean existeSelecionado;

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
    }

    public void filtrar() {
        if (textoFiltro != null && !"".equals(textoFiltro)) {
            todosExercicios = exercicioServico.getExercicios();
            paginacao();
            exercicio = null;
            existeSelecionado = false;
        }
    }

    public List<Exercicio> getTodosExercicios() {
        if (todosExercicios == null) {
            this.todosExercicios = exercicioServico.getExercicios();
            paginacao();
        }
        return todosExercicios;
    }

    public void adicionar() {
        exercicio = new Exercicio();
        existeSelecionado = false;
    }

    public void alterar(Exercicio exercicioSelecionado) {
        exercicio = exercicioSelecionado;
        existeSelecionado = false;
    }

    public void cancelar() {
        exercicio = null;
        todosExercicios = exercicioServico.getExercicios();
        existeSelecionado = false;
        textoFiltro= "";
    }

    public Exercicio salvar() {
        exercicio = exercicioServico.setExercicio(exercicio);
        todosExercicios = exercicioServico.getExercicios();
        Funcao.notificacaoSucesso("Exercício foi salvo com sucesso!");
        return exercicio;
    }

    public void salvarIncluir() {
        salvar();
        adicionar();        
    }

    public void remover() {
        existeSelecionado = false;
        exercicio = null;
        todosExercicios = exercicioServico.getExercicios();
    }

    public String obterModalidade(String tipo) {
        switch (tipo) {
            case "L":
                return "LEI";
            case "D":
                return "DECRETO";
            default:
                return "-";
        }
    }

    // --- gets & sets ---------------------------------------------------------
    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
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
        totalRows = todosExercicios.size();

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
            todosExercicios = exercicioServico.getExercicios();
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
