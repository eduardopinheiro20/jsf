/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;
    @Size(max = 18)
    @Column(name = "rg")
    private String rg;
    @Size(max = 18)
    @Column(name = "cpf")
    private String cpf;
    @Size(max = 14)
    @Column(name = "fone1")
    private String fone1;
    @Size(max = 14)
    @Column(name = "fone2")
    private String fone2;
    @Lob
    @Column(name = "assinatura")
    private byte[] assinatura;
    @Column(name = "ativo")
    private Boolean ativo;
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    @Column(name = "online")
    private Integer online;
    @Size(max = 255)
    @Column(name = "senha")
    private String senha;
    @Column(name = "solicitacao")
    private Boolean solicitacao;
    @Column(name = "status")
    private Boolean status;

    @Basic(optional = false)
    @Column(name = "login", length = 15)
    private String login;

    @Basic(optional = false)
    @Column(name = "nome", length = 60)
    private String nome;

    @Basic(optional = false)
    @Column(name = "sobrenome", length = 60)
    private String sobrenome;

    @Basic(optional = false)
    @Column(name = "cargo", length = 60)
    private String cargo;

    @Column(name = "senha_expira")
    private Integer senhaExpira;

    @Column(name = "email", length = 70)
    private String email;

    @Column(name = "tipo", length = 1)
    private String tipo;

    @Column(name = "avatar", length = 70)
    private String avatar;

    @Column(name = "data_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;

    @Column(name = "data_nascimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataNascimento;

    @Column(name = "esquici_senha")
    private Boolean esquiciSenha;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orgao", referencedColumnName = "id_orgao")
    private ContabilOrgao orgao;

    @OneToMany(mappedBy = "origemId", fetch = FetchType.LAZY)
    private List<UsuarioEmail> usuarioEmailList;

    @JoinColumns({
        @JoinColumn(name = "id_exercicio", referencedColumnName = "id_exercicio"),
        @JoinColumn(name = "id_unidade", referencedColumnName = "id_unidade")})
    @ManyToOne(optional = false)
    private ContabilUnidade unidade;

    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(Integer id, String login, String nome, boolean solicitacao, boolean ativo, boolean status) {
        this.id = id;
        this.login = login;
        this.nome = nome;
        this.solicitacao = solicitacao;
        this.ativo = ativo;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getFone1() {
        return fone1;
    }

    public void setFone1(String fone1) {
        this.fone1 = fone1;
    }

    public String getFone2() {
        return fone2;
    }

    public void setFone2(String fone2) {
        this.fone2 = fone2;
    }

    public byte[] getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(byte[] assinatura) {
        this.assinatura = assinatura;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Boolean solicitacao) {
        this.solicitacao = solicitacao;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome.toUpperCase();
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome.toUpperCase();
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Integer getSenhaExpira() {
        return senhaExpira;
    }

    public void setSenhaExpira(Integer senhaExpira) {
        this.senhaExpira = senhaExpira;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Boolean getEsquiciSenha() {
        return esquiciSenha;
    }

    public void setEsquiciSenha(Boolean esquiciSenha) {
        this.esquiciSenha = esquiciSenha;
    }

    public ContabilOrgao getOrgao() {
        return orgao;
    }

    public void setOrgao(ContabilOrgao orgao) {
        this.orgao = orgao;
    }

    public List<UsuarioEmail> getUsuarioEmailList() {
        return usuarioEmailList;
    }

    public void setUsuarioEmailList(List<UsuarioEmail> usuarioEmailList) {
        this.usuarioEmailList = usuarioEmailList;
    }

    public ContabilUnidade getUnidade() {
        return unidade;
    }

    public void setUnidade(ContabilUnidade unidade) {
        this.unidade = unidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.eddydata.entidade.admin.Usuario[ id=" + id + " ]";
    }

}
