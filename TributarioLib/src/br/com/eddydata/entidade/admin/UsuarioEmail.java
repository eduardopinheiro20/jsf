/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.entidade.admin;

import java.io.Serializable;
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
@Table(name = "usuario_email")
public class UsuarioEmail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String mensagem;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String titulo;
    
    @JoinColumn(name = "origem_id", referencedColumnName = "id_usuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario origemId;
    
    @JoinColumn(name = "destino_id", referencedColumnName = "id_usuario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario destinoId;

    public UsuarioEmail() {
    }

    public UsuarioEmail(Integer id) {
        this.id = id;
    }

    public UsuarioEmail(Integer id, String mensagem, String titulo) {
        this.id = id;
        this.mensagem = mensagem;
        this.titulo = titulo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Usuario getOrigemId() {
        return origemId;
    }

    public void setOrigemId(Usuario origemId) {
        this.origemId = origemId;
    }

    public Usuario getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(Usuario destinoId) {
        this.destinoId = destinoId;
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
        if (!(object instanceof UsuarioEmail)) {
            return false;
        }
        UsuarioEmail other = (UsuarioEmail) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "br.com.eddydata.entidade.admin.UsuarioEmail[ id=" + id + " ]";
    }
    
}
