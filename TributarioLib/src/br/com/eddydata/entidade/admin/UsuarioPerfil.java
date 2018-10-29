/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.admin;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_perfil")
public class UsuarioPerfil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil")
    private Integer idPerfil;

    @Basic(optional = false)
    @Column(name = "nome", nullable = false, length = 30)
    private String nome;

    @Column(name = "descricao", length = 200)
    private String descricao;

    @JoinColumn(name = "id_sistema", referencedColumnName = "id_sistema")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sistema sistema;

    @JoinColumn(name = "id_orgao", referencedColumnName = "id_orgao", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ContabilOrgao orgao;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy(value = "id")
    private List<Acesso> acessos = new LinkedList<>();

    public UsuarioPerfil() {
    }

    public UsuarioPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public UsuarioPerfil(Integer idPerfil, String nome) {
        this.idPerfil = idPerfil;
        this.nome = nome;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    public ContabilOrgao getOrgao() {
        return orgao;
    }

    public void setOrgao(ContabilOrgao orgao) {
        this.orgao = orgao;
    }

    public List<Acesso> getAcessos() {
        return acessos;
    }

    public void setAcessos(List<Acesso> acessos) {
        this.acessos = acessos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerfil != null ? idPerfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioPerfil)) {
            return false;
        }
        UsuarioPerfil other = (UsuarioPerfil) object;
        return !((this.idPerfil == null && other.idPerfil != null) || (this.idPerfil != null && !this.idPerfil.equals(other.idPerfil)));
    }

    @Override
    public String toString() {
        return "br.com.eddydata.entidade.UsuarioPerfil[ idPerfil=" + idPerfil + " ]";
    }

}
