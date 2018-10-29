/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.admin;

import br.com.eddydata.entidade.referencia.Pagina;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "acesso")
public class Acesso implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_perfil", referencedColumnName = "id_perfil", nullable = false)
    private UsuarioPerfil usuarioPerfil;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(nullable = true)
    private Pagina pagina;

    @Column(name = "ativo")
    private Boolean ativo;
    
    @Transient
    private Integer ativoInt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UsuarioPerfil getUsuarioPerfil() {
        return usuarioPerfil;
    }

    public void setUsuarioPerfil(UsuarioPerfil usuarioPerfil) {
        this.usuarioPerfil = usuarioPerfil;
    }

    public Pagina getPagina() {
        return pagina;
    }

    public void setPagina(Pagina pagina) {
        this.pagina = pagina;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getAtivoInt() {
        if (ativo == null) {
            return -1;
        } else if (ativo) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setAtivoInt(Integer ativoInt) {
        this.ativoInt = ativoInt;
        if (ativoInt == -1) {
            setAtivo(null);
        } else if (ativoInt == 1) {
            setAtivo(true);
        } else {
            setAtivo(false);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Acesso other = (Acesso) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Acesso{" + "id=" + id + '}';
    }

}
