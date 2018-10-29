/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 **/
package br.com.eddydata.bean;

import br.com.eddydata.entidade.admin.Acesso;
import br.com.eddydata.entidade.admin.Sistema;
import br.com.eddydata.entidade.admin.UsuarioSistema;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.servico.admin.ExercicioServico;
import br.com.eddydata.servico.admin.SistemaServico;
import br.com.eddydata.servico.admin.UsuarioPerfilServico;
import br.com.eddydata.servico.admin.UsuarioServico;
import br.com.eddydata.servico.admin.UsuarioSistemaServico;
import br.com.eddydata.servico.issqn.IssqnServico;
import br.com.eddydata.suporte.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named
@ViewScoped
public class LogonBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private UsuarioServico usuarioServico;
    @EJB
    private ExercicioServico exercicioServico;
    @EJB
    private SistemaServico sistemaServico;
    @EJB
    private UsuarioPerfilServico usuarioPerfilServico;
    @EJB
    private UsuarioSistemaServico usuarioSistemaServico;
    @EJB
    private IssqnServico issqnServico;

    @Inject
    private GlobalBean global;

    private final Logon logon = new Logon();
    private static final String SISTEMA = "ISSQN";

    private List<SelectItem> lstSistema;
    private List<SelectItem> lstEntidade;

    private String email;
    private String senha;
    private String cpfCnpj;

    private boolean aviso;
    private String menu = "";

    private boolean pessoa = false;

    private String pessoaCnpj = "";

    private Integer exercicio;

    @PostConstruct
    public void init() {

    }

    /**
     * Método para o verificar se o usuário tem permissão para acessar o sistema
     * O retorno será feito de acordo com o castrado do usuario campo sistema.
     *
     * @return retorno para página principal do sistema
     */
    public String fazerLogin() throws InterruptedException {

        if (pessoa) {
            global.setIssqnLogado(issqnServico.obterIssqnPorCPFESenha(cpfCnpj, senha));
            if (global.getIssqnLogado() == null) {
                Funcao.avisoAtencao("CPF/CNPJ informado não existe ou a senha está incorreta!");
                aviso = true;
                return "";
            }
            return "pretty:registroAlteracao";
        }

        if (Util.isNumeric(cpfCnpj)) {
            global.setUsuarioLogado(usuarioServico.obterUsuarioPorCPFESenha(cpfCnpj, senha));
            if (global.getUsuarioLogado() == null) {
                Funcao.avisoAtencao("CPF/CNPJ informado não existe ou a senha está incorreta!");
                aviso = true;
                return "";
            }
            //return "pretty:registroAlteracao";
        } else {

            global.setUsuarioLogado(usuarioServico.getUsuarioPorLoginSenha(cpfCnpj, senha));
            if (global.getUsuarioLogado() == null) {
                Funcao.avisoAtencao("E-mail informado não existe ou a senha está incorreta!");
                aviso = true;
                return "";
            }
        }

        if (!global.getUsuarioLogado().getAtivo()) {
            Funcao.avisoAtencao("Usuário está inativo, entre em contato com o administrador");
            return "";
        }

        Sistema sis = sistemaServico.obterSistema(SISTEMA);
        if (sis == null) {
            Funcao.avisoErro("Sistema não cadastrado!");
            return "";
        }

        logon.setUsuarioId(global.getUsuarioLogado().getId());
        UsuarioSistema us = usuarioSistemaServico.getUsuarioSistemaPorUsuario(global.getUsuarioLogado().getId(), SISTEMA, true);
        List<Acesso> resources = us.getUsuarioPerfil().getAcessos();
        logon.setPermissoes(resources);

        global.setMesReferencia(Util.getMonth(new Date()));

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.setAttribute("usuarioLogado", global.getUsuarioLogado());
        session.setAttribute("acesso", logon);
        session.setAttribute("ip", ip);
        session.setAttribute("user-agent", request.getHeader("user-agent"));

        global.setExercicio(exercicioServico.getExercicioPorAno(exercicio, global.getUsuarioLogado().getOrgao().getIdOrgao()));
        if (global.getExercicio() == null) {
            aviso = true;
            global.setExercicio(exercicioServico.getExercicioPorAno(Util.getYear(new Date()) - 1, global.getUsuarioLogado().getOrgao().getIdOrgao()));
            Funcao.avisoAtencao("O exercício corrente ainda não foi aberto, efetuar abertura.");
        }

        global.setSistema(sis.getNome());

        UsuarioSistema usuarioSistema = usuarioSistemaServico.getUsuarioSistemaPorUsuario(global.getUsuarioLogado().getId(), sis.getIdSistema(), true);
        if (usuarioSistema == null) {
            Funcao.avisoErro("Você não possui permissão de acesso ao sistema, entre em contato com o administrador do sistema");
            aviso = true;
            return "";
        }

        return redirecionarPagina(sis.getIdSistema());
    }

    private String redirecionarPagina(String sistema) {
        switch (sistema) {
            case "ADMINISTRACAO":
                return "pretty:admin";
            case "PLANEJAMENTO":
                return "pretty:painel-planejamento";
            case "CONTABILIDADE":
                return "pretty:contabilidade";
            case "TESOURARIA":
                return "pretty:tesouraria";
            case "ESTOQUE":
                return "pretty:painel-estoque";
            case "RECURSOS HUMANOS":
                return "pretty:folha";
            case "FROTA":
                return "pretty:frota";
            case "ISSQN":
                return "pretty:painel-issqn";
            default:
                return "";
        }
    }

    public void montaMenu() {
        switch (global.getSistema()) {
            case "ADMINISTRACAO":
                menu = "../admin/menu.xhtml";
                break;
            case "PLANEJAMENTO":
                menu = "../planejamento/menu.xhtml";
                break;
            case "CONTABILIDADE":
                menu = "../contabil/menu.xhtml";
                break;
            case "TESOURARIA":
                menu = "../tesouraria/menu.xhtml";
                break;
            case "ESTOQUE":
                menu = "../estoque/menu.xhtml";
                break;
            case "RECURSOS HUMANOS":
                menu = "../folha/menu.xhtml";
                break;
            case "FROTA":
                menu = "../frota/menu.xhtml";
                break;
            default:
                menu = "";
                break;
        }
    }

    /**
     * Encerra a sessão e sai do sistema
     *
     * @return retorna para a pagina de login
     */
    public String sair() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.invalidate();
        System.out.println("usuario: " + global.getUsuarioLogado().getNome() + " saiu do sistema");
        return "pretty:login";
    }

    public String doTrocarReferencia() {
        return "pretty:painel-issqn";
    }

    public String buscarCNPJ() {
        JSFUtils.flashScope().put("cnpjPessoa", pessoaCnpj.replace("-", "").replace(".", "").replace("/", ""));
        Issqn iss = issqnServico.obterIssqnPorCPF(pessoaCnpj.replace("-", "").replace(".", "").replace("/", ""));
        if (iss != null) {
            return null;
        }
        return "pretty:consultar-protocolos";
    }

    /**
     * Retorna a lista de todos os sistemas disponíveis, esta lista é usada no
     * registro de novos usuários para o sistema
     *
     * @return
     */
    public List<SelectItem> getSelectSistema() {
        if (lstSistema == null) {
            lstSistema = new ArrayList();
            lstSistema.add(new SelectItem("ADMINISTRACAO", "ADMINISTRAÇÃO"));
            lstSistema.add(new SelectItem("PLANEJAMENTO", "PLANEJAMENTO"));
            lstSistema.add(new SelectItem("CONTABILIDADE", "CONTABILIDADE"));
            lstSistema.add(new SelectItem("TESOURARIA", "TESOURARIA"));
            lstSistema.add(new SelectItem("REQUISICAO", "REQUISIÇÃO DE MATERIAIS"));
            lstSistema.add(new SelectItem("COMPRAS", "COMPRAS"));
            lstSistema.add(new SelectItem("ESTOQUE", "ALMOXARIFADO"));
            lstSistema.add(new SelectItem("PATRIMONIO", "PATRIMÔNIO"));
            lstSistema.add(new SelectItem("LICITACAO", "LICITAÇÕES"));
            lstSistema.add(new SelectItem("TRIBUNAL", "TRIBUTÁRIO"));
            lstSistema.add(new SelectItem("FOLHA", "RECURSOS HUMANOS"));
            lstSistema.add(new SelectItem("SOCIAL", "SOCIAL"));
        }
        return lstSistema;
    }

    /**
     * Lista dos orgãos públicos, esta lista é usada no registro de novos
     * usuários para o sistema
     *
     * @return
     */
    public List<SelectItem> getLstEntidade() {
        lstEntidade = new ArrayList();
        lstEntidade.add(new SelectItem("C", "CÂMARA MUNICIPAL"));
        lstEntidade.add(new SelectItem("P", "PREFEITURA MUNICIPAL"));
        lstEntidade.add(new SelectItem("A", "AUTARQUIA MUNICIPAL"));
        lstEntidade.add(new SelectItem("R", "PREVIDÊNCIA SOCIAL"));
        lstEntidade.add(new SelectItem("G", "ÁGUA E ESGOTO"));
        lstEntidade.add(new SelectItem("F", "FUNDAÇÃO MUNICIPAL"));
        lstEntidade.add(new SelectItem("E", "EMPRESA MUNICIPAL"));
        lstEntidade.add(new SelectItem("0", "OUTROS"));
        return lstEntidade;
    }

    // -------------------------- gets & sets --------------------------
    public List<SelectItem> getLstSistema() {
        return lstSistema;
    }

    public void setLstSistema(List<SelectItem> lstSistema) {
        this.lstSistema = lstSistema;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public boolean isAviso() {
        return aviso;
    }

    public void setAviso(boolean aviso) {
        this.aviso = aviso;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public boolean getPessoa() {
        return pessoa;
    }

    public void setPessoa(boolean pessoa) {
        this.pessoa = pessoa;
    }

    public String getPessoaCnpj() {
        return pessoaCnpj;
    }

    public void setPessoaCnpj(String pessoaCnpj) {
        this.pessoaCnpj = pessoaCnpj;
    }

    public Integer getExercicio() {
        return exercicio;
    }

    public void setExercicio(Integer exercicio) {
        this.exercicio = exercicio;
    }

}
