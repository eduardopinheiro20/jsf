/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.bean;

import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.admin.ContabilUnidade;
import br.com.eddydata.entidade.admin.Usuario;
import br.com.eddydata.entidade.admin.UsuarioPerfil;
import br.com.eddydata.entidade.admin.UsuarioSistema;
import br.com.eddydata.entidade.geo.Cidade;
import br.com.eddydata.servico.admin.CidadeServico;
import br.com.eddydata.servico.admin.OrgaoServico;
import br.com.eddydata.servico.admin.UsuarioPerfilServico;
import br.com.eddydata.servico.admin.UsuarioServico;
import br.com.eddydata.servico.admin.UsuarioSistemaServico;
import br.com.eddydata.suporte.BusinessViolation;
import br.com.eddydata.suporte.StringMD5;
import br.com.eddydata.suporte.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.stream.FileImageOutputStream;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private UsuarioServico usuarioServico;
    @EJB
    private UsuarioSistemaServico usuarioSistemaServico;
    @EJB
    private OrgaoServico orgaoServico;
    @EJB
    private CidadeServico cidadeServico;
    @EJB
    private UsuarioPerfilServico usuarioPerfilServico;

    @Inject
    private GlobalBean global;

    @Inject
    private UtilBean util;

    private Usuario usuario;
    private UsuarioSistema usuarioSistema;
    private List<Usuario> todosUsuarios;
    private List<ContabilOrgao> todosOrgaos;
    private List<ContabilUnidade> listarUnidades;
    private List<UsuarioPerfil> listarPerfil;
    private String textoFiltro;
    private String emailRecuperacao = "";
    private Boolean existeSelecionado;
    private String senhaAtual;

    // Variaveis usadas para registrar um novo usuario --------------------------
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

    // Variaveis usadas para gravar a foto do usuario  --------------------------
    private UploadedFile file;
    private CroppedImage croppedImage;
    private String foto;
    private String fotoUsuario;
    private String fotoCrop = "usuario";
    private byte[] fotoByte;
    // -------------------------------------------------------------------------

    @PostConstruct
    public void init() {
        rowsPerPage = 30;
        pageRange = 10;
        fotoUsuario = "avatar.png";
        foto = "branco";
    }

    public void filtrar() {
        if (textoFiltro != null) {
            this.todosUsuarios = usuarioServico.getUsuarioPorNome(textoFiltro, global.getUsuarioLogado().getOrgao().getIdOrgao(), false);
            usuario = null;
        }
    }

    public List<Usuario> getTodosUsuarios() {
        if (todosUsuarios == null) {
            this.todosUsuarios = usuarioServico.getUsuarios(false);
        }
        return todosUsuarios;
    }

    public void adicionar() {
        usuario = new Usuario();
        fotoUsuario = "avatar.png";
        fotoCrop = "usuario";
        fotoByte = null;
        croppedImage = null;
        foto = "branco";

        usuario.setDataCadastro(new Date());
        usuario.setAtivo(true);
        usuario.setOnline(0);
        usuario.setEsquiciSenha(false);
        usuario.setSolicitacao(false);

        usuarioSistema = new UsuarioSistema();
        //usuarioSistema.setAdministrador(false);
        usuarioSistema.setDtSenha(Util.parseSqlDate(new Date()).replace("'", ""));

    }

    public void alterar(Usuario usuarioSelecionado) {
        usuario = usuarioSelecionado;
        senhaAtual = usuario.getSenha();
        usuarioSistema = usuarioSistemaServico.getUsuarioSistemaPorUsuario(usuario.getId(), global.getSistema(), true);
        existeSelecionado = false;
    }

    public void cancelar() {
        usuario = null;
        todosUsuarios = usuarioServico.getUsuarios(false);
    }

    public synchronized void salvar(boolean novo) {
        if (preSalvar()) {
            if (!novo) {
                usuario = null;
                todosUsuarios = usuarioServico.getUsuarios(false);
            }
        }
    }

    private boolean preSalvar() {
        boolean retorno = salvarRegistro();
        return retorno;
    }

    public boolean salvarRegistro() {
        try {
            
            usuario.setOrgao(global.getUsuarioLogado().getOrgao());
            if (!usuario.getSenha().equals("")) {
                usuario.setSenha(StringMD5.md5(usuario.getSenha()));
            } else{
                usuario.setSenha(senhaAtual);
            }
            usuario.setCpf(((usuario.getCpf().replace(".", "")).replace("-", "")).replace("/", ""));
            usuario.setNome(util.retiraAcento(usuario.getNome().trim().toUpperCase()));
            usuario.setSobrenome(util.retiraAcento(usuario.getSobrenome().trim().toUpperCase()));
            usuario = usuarioServico.setUsuario(usuario);
            usuarioSistema.setUsuario(usuario);
            usuarioSistema.setSenha("");
            usuarioSistema = usuarioSistemaServico.setUsuarioSistema(usuarioSistema);

        } catch (BusinessViolation ex) {
            Funcao.avisoAtencao(ex.getMessage());
            return false;
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao salvar registro");
            System.out.println("Erro ao salvar registro\n" + ex.getMessage());
            return false;
        }
        Funcao.notificacaoSucesso("Usuario foi salvo com sucesso!");
        return true;
    }

    public void salvarIncluir() {
        salvar(true);
        adicionar();
    }

    public void ativarUsuario() {
        if (usuario != null) {
            usuario.setAtivo(true);
            salvar(true);
        } else {
            Funcao.aviso("Antes de ativar o usuário é preciso salvar as informações!");
        }
    }

    public String obterLabel(boolean ativo) {
        return ativo ? "label label-primary" : "label label-success";
    }

    public void obterBrasao() {
        try {
            usuarioServico.setUsuario(usuario);
        } catch (BusinessViolation ex) {
            Funcao.avisoAtencao(ex.getMessage());
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao salvar registro");
            System.out.println("Erro ao salvar registro\n" + ex.getMessage());
        }
        Funcao.executarJavaScript("PF('dlgCrop').show()");
    }

    public void remover() {
        for (Usuario entidade : todosUsuarios) {
            usuarioServico.removerUsuario(entidade);
        }
        Funcao.resetarFormulario("frm");
        usuario = null;
        todosUsuarios = usuarioServico.getUsuarios(false);
    }

    /**
     * Fazer o relacionamento do usuário que esta sendo cadastrado com o orgão
     * onde trabalha automaticamente.
     */
    public void verificarOrgao() {
        verOrgao = false;
        if (cidadeId == null) {
            Funcao.avisoAtencao("Selecione a sua cidade !");
            return;
        }
        if (tipoOrgao != null) {
            todosOrgaos = orgaoServico.getOrgaos();
            if (todosOrgaos.size() > 1) {
                verOrgao = true;
            } else if (todosOrgaos.size() == 1) {
                orgaoId = todosOrgaos.get(0);
            }
        }
    }

    /**
     * Salva a foto no banco de dados
     */
    public void salvarFoto() {

        usuario.setFoto(fotoByte);

        fotoUsuario = "avatar.png";
        fotoCrop = "usuario";
        fotoByte = null;
        croppedImage = null;
        foto = "branco";

        Funcao.executarJavaScript("PF('dlgCrop').hide()");

    }

    public void cancelarFoto() {
        fotoUsuario = "avatar.png";
        fotoCrop = "usuario";
        fotoByte = null;
        croppedImage = null;
        foto = "branco";
        Funcao.executarJavaScript("PF('dlgCrop').hide()");
    }

    private void obterFoto() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        byte[] fotoBD = usuario.getFoto();
        FileImageOutputStream imageOutput;
        if (fotoBD != null) {
            try {
                String fotoPath = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "upload"
                        + File.separator + String.valueOf(usuario.getId()) + ".png";
                imageOutput = new FileImageOutputStream(new File(fotoPath));
                imageOutput.write(fotoBD, 0, fotoBD.length);
                imageOutput.close();
                fotoUsuario = String.valueOf(usuario.getId()) + ".png";
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cropping failed."));
            }
        } else {
            fotoUsuario = "avatar.png";
        }
    }

    /**
     * Upload da foto do usuario
     *
     */
    public void upload() {
        if (file != null) {

            Funcao.avisoSucesso(file.getFileName() + " foi enviada.");

            setFotoCrop("foto");

            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "upload"
                    + File.separator + getFotoCrop() + ".png";

            FileImageOutputStream imageOutput;
            try {
                imageOutput = new FileImageOutputStream(new File(newFileName));
                imageOutput.write(file.getContents(), 0, file.getContents().length);
                imageOutput.close();
            } catch (Exception e) {
                Funcao.avisoErro("Não foi possível enviar usuario");
            }

            Funcao.executarJavaScript("PF('dlgCrop').show()");
        }
    }

    public void crop() {
        if (croppedImage == null) {
            return;
        }

        setFoto(getRandomImageName());
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "upload"
                + File.separator + getFoto() + ".png";

        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(croppedImage.getBytes(), 0, croppedImage.getBytes().length);
            fotoByte = croppedImage.getBytes();
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

    public String downloadFoto(byte[] foto, Integer id) {
        String fotoImagem = "";
        if (id != null) {
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
                String imageUsers = servletContext.getRealPath("/resources/img/upload");
                File dirImageUsers = new File(imageUsers);
                if (!dirImageUsers.exists()) {
                    dirImageUsers.createNewFile();
                }

                if (foto != null) {
                    byte[] bytes = foto;
                    FileImageOutputStream imageOutput = new FileImageOutputStream(new File(dirImageUsers, id + ".png"));
                    imageOutput.write(bytes, 0, bytes.length);
                    imageOutput.flush();
                    imageOutput.close();

                    fotoImagem = id + ".png";
                } else {
                    fotoImagem = "48x48_user.png";
                }

            } catch (FileNotFoundException ex) {
                Funcao.aviso(ex.getMessage());
            } catch (IOException ex) {
                Funcao.aviso(ex.getMessage());
            }
        } else {
            fotoImagem = "48x48_user.png";
        }
        return fotoImagem;
    }

    // --- gets & sets ---------------------------------------------------------
    public GlobalBean getGlobal() {
        return global;
    }

    public void setGlobal(GlobalBean global) {
        this.global = global;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioSistema getUsuarioSistema() {
        return usuarioSistema;
    }

    public void setUsuarioSistema(UsuarioSistema usuarioSistema) {
        this.usuarioSistema = usuarioSistema;
    }

    public List<ContabilOrgao> getTodosOrgaos() {
        return todosOrgaos;
    }

    public void setTodosOrgaos(List<ContabilOrgao> todosOrgaos) {
        this.todosOrgaos = todosOrgaos;
    }

    public List<ContabilUnidade> getListarUnidades() {
        return listarUnidades;
    }

    public void setListarUnidades(List<ContabilUnidade> listarUnidades) {
        this.listarUnidades = listarUnidades;
    }

    public List<UsuarioPerfil> getListarPerfil() {
        if (listarPerfil == null) {
            listarPerfil = usuarioPerfilServico.getUsuarioPerfis();
        }
        return listarPerfil;
    }

    public void setListarPerfil(List<UsuarioPerfil> listarPerfil) {
        this.listarPerfil = listarPerfil;
    }

    public String getTextoFiltro() {
        return textoFiltro;
    }

    public void setTextoFiltro(String textoFiltro) {
        this.textoFiltro = textoFiltro;
    }

    public String getEmailRecuperacao() {
        return emailRecuperacao;
    }

    public void setEmailRecuperacao(String emailRecuperacao) {
        this.emailRecuperacao = emailRecuperacao;
    }

    public Boolean getExisteSelecionado() {
        return existeSelecionado;
    }

    public void setExisteSelecionado(Boolean existeSelecionado) {
        this.existeSelecionado = existeSelecionado;
    }

    public Cidade getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(Cidade cidadeId) {
        this.cidadeId = cidadeId;
    }

    public ContabilOrgao getOrgaoId() {
        return orgaoId;
    }

    public void setOrgaoId(ContabilOrgao orgaoId) {
        this.orgaoId = orgaoId;
    }

    public String getTipoOrgao() {
        return tipoOrgao;
    }

    public void setTipoOrgao(String tipoOrgao) {
        this.tipoOrgao = tipoOrgao;
    }

    public Boolean getVerOrgao() {
        return verOrgao;
    }

    public void setVerOrgao(Boolean verOrgao) {
        this.verOrgao = verOrgao;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFotoUsuario() {
        return fotoUsuario;
    }

    public void setFotoUsuario(String fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }

    public String getFotoCrop() {
        return fotoCrop;
    }

    public void setFotoCrop(String fotoCrop) {
        this.fotoCrop = fotoCrop;
    }

    public byte[] getFotoByte() {
        return fotoByte;
    }

    public void setFotoByte(byte[] fotoByte) {
        this.fotoByte = fotoByte;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    
}
