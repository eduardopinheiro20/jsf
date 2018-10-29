/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.bean;

import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.geo.Cidade;
import br.com.eddydata.servico.admin.CidadeServico;
import br.com.eddydata.servico.admin.OrgaoServico;
import br.com.eddydata.servlet.WebServiceCep;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.imageio.stream.FileImageOutputStream;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.MapModel;

@Named
@ViewScoped
public class OrgaoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private OrgaoServico orgaoServico;

    @EJB
    private CidadeServico cidadeServico;

    @Inject
    private GlobalBean global;

    private ContabilOrgao orgao;

    private List<ContabilOrgao> todosOrgaos;

    private String textoFiltro;

    private Boolean existeSelecionado;

    // Variaveis usadas para registrar um novo orgao --------------------------
    private Cidade cidadeId;
    private ContabilOrgao orgaoId;
    private String tipoOrgao;
    private Boolean verOrgao = false;
    // -------------------------------------------------------------------------

    // Variaveis usadas para paginação do dataTable ----------------------------
    private int totalRows;
    private int firstRow;
    private int rowsPerPage;
    private int totalPages;
    private int pageRange;
    private Integer[] pages;
    private int currentPage;
    // -------------------------------------------------------------------------

    // Variaveis usadas para gravar a logo do orgao  --------------------------
    private UploadedFile file;
    private CroppedImage croppedImage;
    private String logo;
    private String logoOrgao;
    private String logoCrop = "orgao";
    private byte[] logoByte;
    // -------------------------------------------------------------------------

    // Variaveis usadas localização no mapa  --------------------------
    private MapModel geoModel;
    private String centerGeoMap = "41.850033, -87.6500523";
    private final String centerRevGeoMap = "41.850033, -87.6500523";
    // -------------------------------------------------------------------------

    @PostConstruct
    public void init() {
        rowsPerPage = 30;
        pageRange = 10;
        logoOrgao = "logomarca.png";
        logo = "branco";
    }

    public void filtrar() {
        if (textoFiltro != null) {
            this.todosOrgaos = orgaoServico.getOrgaoPorNome(textoFiltro);
            paginacao();
            orgao = null;
            existeSelecionado = false;
        }
    }

    public List<ContabilOrgao> getTodosOrgaos() {
        if (todosOrgaos == null) {
            this.todosOrgaos = orgaoServico.getOrgaos();
            paginacao();
        }
        return todosOrgaos;
    }

    public void adicionar() {
        orgao = new ContabilOrgao();
        logoOrgao = "logomarca.png";
        logoCrop = "orgao";
        logoByte = null;
        croppedImage = null;
        logo = "branco";
    }

    public void alterar(ContabilOrgao orgaoSelecionado) {
        orgao = orgaoSelecionado;
        existeSelecionado = false;
    }

    public void cancelar() {
        orgao = null;
        todosOrgaos = orgaoServico.getOrgaos();
    }

    public void salvar() {
        orgao = orgaoServico.setOrgao(orgao);
        Funcao.resetarFormulario("form1");
        Funcao.notificacaoSucesso("Orgão foi salvo com sucesso!");
    }

    public void salvarIncluir() {
        salvar();
        adicionar();
    }

    public String obterLabel(String tipoOrgao) {
        switch (tipoOrgao) {
            case "P":
                return "task-cat pink";
            case "C":
                return "task-cat purple";
            case "A":
                return "task-cat teal";
            case "F":
                return "task-cat blue";
            default:
                return "task-cat yellow";
        }
    }

    public String obterTipo(String tipoOrgao) {
        switch (tipoOrgao) {
            case "P":
                return "PREFEITURA MUNICIPAL";
            case "C":
                return "CÂMARA MUNICIPAL";
            case "A":
                return "AUTARQUIA MUNICIPAL";
            case "F":
                return "FUNDAÇÃO MUNICIPAL";
            default:
                return "OUTROS";
        }
    }

    public void obterBrasao() {
        orgaoServico.setOrgao(orgao);
        Funcao.executarJavaScript("PF('dlgCrop').show()");
    }

    public void remover() {
        for (ContabilOrgao entidade : todosOrgaos) {
            orgaoServico.removerOrgao(entidade);
        }
        Funcao.resetarFormulario("frm");
        orgao = null;
        todosOrgaos = orgaoServico.getOrgaos();
    }

    /**
     * Salva a logo no banco de dados
     */
    public void salvarLogo() {

        orgao.setBrasao(logoByte);

        logoOrgao = "logomarca.png";
        logoCrop = "orgao";
        logoByte = null;
        croppedImage = null;
        logo = "branco";

        Funcao.executarJavaScript("PF('dlgCrop').hide()");

    }

    public void cancelarLogo() {
        logoOrgao = "logomarca.png";
        logoCrop = "orgao";
        logoByte = null;
        croppedImage = null;
        logo = "branco";
        Funcao.executarJavaScript("PF('dlgCrop').hide()");
    }

    private void obterLogo() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        byte[] logoBD = orgao.getBrasao();
        FileImageOutputStream imageOutput;
        if (logoBD != null) {
            try {
                String logoPath = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "upload"
                        + File.separator + String.valueOf(orgao.getIdOrgao()) + ".png";
                imageOutput = new FileImageOutputStream(new File(logoPath));
                imageOutput.write(logoBD, 0, logoBD.length);
                imageOutput.close();
                logoOrgao = String.valueOf(orgao.getIdOrgao()) + ".png";
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cropping failed."));
            }
        } else {
            logoOrgao = "logomarca.png";
        }
    }

    /**
     * Upload da logo do orgao
     *
     */
    public void upload() {
        if (file != null) {

            Funcao.avisoSucesso(file.getFileName() + " foi enviada.");

            setLogoCrop("logo");

            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "upload"
                    + File.separator + getLogoCrop() + ".png";

            FileImageOutputStream imageOutput;
            try {
                imageOutput = new FileImageOutputStream(new File(newFileName));
                imageOutput.write(file.getContents(), 0, file.getContents().length);
                imageOutput.close();
            } catch (Exception e) {
                Funcao.avisoErro("Não foi possível enviar orgao");
            }

            Funcao.executarJavaScript("PF('dlgCrop').show()");
        }
    }

    public void crop() {
        if (croppedImage == null) {
            return;
        }

        setLogo(getRandomImageName());
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "upload"
                + File.separator + getLogo() + ".png";

        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(croppedImage.getBytes(), 0, croppedImage.getBytes().length);
            logoByte = croppedImage.getBytes();
            imageOutput.close();
        } catch (Exception e) {
            Funcao.avisoErro("Erro ao recortar mensagem!!!");
        }
    }

    private String getRandomImageName() {
        int i = (int) (Math.random() * 100000);

        return String.valueOf(i);
    }

    public List<Cidade> buscarCidade(String nome) {
        List<Cidade> cidadeLst = new ArrayList();
        try {
            cidadeLst = cidadeServico.getCidadePorNome(nome.trim().toUpperCase());
        } catch (Exception e) {
            System.out.println("Não foi possivel preencher lista para consulta " + e.getMessage());
        }
        return cidadeLst;
    }
    
    public void buscaCep(String cep) {
        WebServiceCep webServiceCep = WebServiceCep.searchCep(cep);

        if (webServiceCep.wasSuccessful()) {
            orgao.setBairro(webServiceCep.getBairro().toUpperCase());
            orgao.setEndereco(webServiceCep.getLogradouro().toUpperCase());
            orgao.setCidade(webServiceCep.getCidade().toUpperCase());
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cep não encontrado!", "Servidor não está respondendo"));
        }
    }

    // --- gets & sets ---------------------------------------------------------
    public ContabilOrgao getOrgaoId() {
        return orgaoId;
    }

    public void setOrgaoId(ContabilOrgao orgaoId) {
        this.orgaoId = orgaoId;
    }

    public Boolean getVerOrgao() {
        return verOrgao;
    }

    public String getTipoOrgao() {
        return tipoOrgao;
    }

    public void setTipoOrgao(String tipoOrgao) {
        this.tipoOrgao = tipoOrgao;
    }

    public Cidade getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(Cidade cidadeId) {
        this.cidadeId = cidadeId;
    }

    public String getLogoLogo() {
        obterLogo();
        return logoOrgao;
    }

    public void setLogoLogo(String logoOrgao) {
        this.logoOrgao = logoOrgao;
    }

    public byte[] getLogoByte() {
        return logoByte;
    }

    public void setLogoByte(byte[] logoByte) {
        this.logoByte = logoByte;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public CroppedImage getCroppedImage() {
        return croppedImage;
    }

    public void setCroppedImage(CroppedImage croppedImage) {
        this.croppedImage = croppedImage;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogoCrop() {
        return logoCrop;
    }

    public void setLogoCrop(String logoCrop) {
        this.logoCrop = logoCrop;
    }

    public ContabilOrgao getOrgao() {
        return orgao;
    }

    public void setOrgao(ContabilOrgao orgao) {
        this.orgao = orgao;
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

    public MapModel getGeoModel() {
        return geoModel;
    }

    public void setGeoModel(MapModel geoModel) {
        this.geoModel = geoModel;
    }

    public String getCenterGeoMap() {
        return centerGeoMap;
    }

    public void setCenterGeoMap(String centerGeoMap) {
        this.centerGeoMap = centerGeoMap;
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
        totalRows = todosOrgaos.size();

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
            todosOrgaos = orgaoServico.getOrgaos();
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
