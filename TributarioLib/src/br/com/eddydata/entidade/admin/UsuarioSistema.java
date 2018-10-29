/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.admin;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_sistema")
public class UsuarioSistema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "senha", nullable = false, length = 15)
    private String senha;

    @Column(name = "dt_expira", length = 10)
    private String dtExpira;

    @Column(name = "dt_senha", length = 10)
    private String dtSenha;

    @Column(name = "administrador", nullable = false)
    private Boolean administrador;

    @JoinColumn(name = "id_perfil", referencedColumnName = "id_perfil")
    @ManyToOne(fetch = FetchType.LAZY)
    private UsuarioPerfil usuarioPerfil;

    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    public UsuarioSistema() {
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDtExpira() {
        return dtExpira;
    }

    public void setDtExpira(String dtExpira) {
        this.dtExpira = dtExpira;
    }

    public String getDtSenha() {
        return dtSenha;
    }

    public void setDtSenha(String dtSenha) {
        this.dtSenha = dtSenha;
    }

    public UsuarioPerfil getUsuarioPerfil() {
        return usuarioPerfil;
    }

    public void setUsuarioPerfil(UsuarioPerfil usuarioPerfil) {
        this.usuarioPerfil = usuarioPerfil;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Boolean getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioSistema other = (UsuarioSistema) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "UsuarioSistema{" + "id=" + id + '}';
    }

}
